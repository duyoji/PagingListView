package test.duyoji.listview;

import test.duyoji.util.SizeUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;

@SuppressLint({ "NewApi", "NewApi" })
public class PagingListView extends ListView {
	
	private Context context;
	private int screenHeight;	
	private int nowPage = 0;
	private int totalPage = 0;	
	
	//constructor----------------------------------------------------------------	
	public PagingListView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public PagingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public PagingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();		
	}
	
	//interface------------------------------------------------------------------			
	public static interface OnPagingListener {
		public void onScrollStart(int _nowPage);
		public void onScrollFinish(int _nowPage);
		public void onNextListLoad(int _nowPage);
	}
		
	private OnPagingListener listener = null;
	
	public void setPagingListener(OnPagingListener listener) {
		this.listener = listener;
	}
	
	//setter, getter --------------------------------------------------------------	
	public void setNowPage(int _nowPage){
		this.nowPage = _nowPage;		
	}
			
	public void setTotalPage(int totalPage){
		this.totalPage = totalPage;
	}

	public int getNowPage(){
		return nowPage;
	}
		
	//paging scroll method---------------------------------------------------------------------------	
	public void scrollNextPage(){
		View firstVisibleView = getChildAt(0);
		nowPage += 1;
		smoothScrollBy(screenHeight-SizeUtil.getStatusBarHeight(context)-Math.abs(firstVisibleView.getTop())-1, 400);
	}
		
	public void scrollPrevPage(){
		View firstVisibleView = getChildAt(0);
		nowPage -= 1;
		smoothScrollBy(firstVisibleView.getTop(), 400); // Stops the listview from overshooting.
	}
	
	//initialize ---------------------------------------------------------------
	private void init(){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();        
        this.screenHeight = display.getHeight();                  
        setScrollEvent();
	}

	
	//setOnScrollListener event-----------------------------------------------------	
	private void setScrollEvent(){
		setOnScrollListener(new OnScrollListener() {
			private boolean flingFlag = false;
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				 
			    // スクロールしていない
			    case OnScrollListener.SCROLL_STATE_IDLE:			    				    				    	
			    	if (flingFlag) {
			    		flingFlag = false;
			    		if (listener != null) {
							listener.onScrollFinish(nowPage);
						}
					}else{
						postDelayed(new Runnable() {
				    	    public void run() {
				    	    	int savedPosition = getFirstVisiblePosition();
				    	    	View firstVisibleView = getChildAt(0);
				    	    	
				    	    	if (firstVisibleView.getHeight()/2.0 < Math.abs(firstVisibleView.getTop())) { 
				    	    		nowPage = savedPosition;
						    		smoothScrollBy(screenHeight-SizeUtil.getStatusBarHeight(context)-Math.abs(firstVisibleView.getTop()-1), 400); 
						    	}else{			    		
						    		nowPage = savedPosition;
						    		smoothScrollBy(firstVisibleView.getTop(), 400); // Stops the listview from overshooting.
						    	}
				    	    		
				    	    	if (listener != null) {
									listener.onScrollFinish(nowPage);
								}
				    	    } 
				    	}, 50);
					}
			    				    	
			        break;
			 
			    // スクロール中
//			    case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:			    	
//			        break;
			 
			    // はじいたとき
			    case OnScrollListener.SCROLL_STATE_FLING:			    				    				    
			    	flingFlag = true;
			    	postDelayed(new Runnable() {
			    	    public void run() {
			    	    	int savedPosition = getFirstVisiblePosition();
			    	    	View firstVisibleView = getChildAt(0);			    	    				    	    	

			    	    	if (savedPosition < nowPage){			    	    		
			    	    		if (nowPage+1 == totalPage ) {
			    	    			int firstVisibleItem = nowPage-getFirstVisiblePosition();
									View lastView = getChildAt(firstVisibleItem);
			    	    			if (lastView.getTop() > 0) {
			    	    				scrollPrevPage();
			    	    			}
								}else{
									scrollPrevPage();
								}
					    	}else if (0 < savedPosition || ( 0 == savedPosition && 0 > firstVisibleView.getTop() ) ){					    		
					    		if (nowPage != totalPage-1) {
									scrollNextPage();
								} 		
					    	}
			    	    	
			    	    	listener.onScrollStart(nowPage);
			    	    } 			    	    
			    	}, 50);
			    				    	
			        break;
			    }
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (listener != null) {
					listener.onNextListLoad(nowPage);
				}				
			}
		});
	}			
}
