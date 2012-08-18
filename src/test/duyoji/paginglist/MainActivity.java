package test.duyoji.paginglist;

import java.util.ArrayList;
import test.duyoji.adapter.MainAdapter;
import test.duyoji.listview.PagingListView;
import test.duyoji.listview.PagingListView.OnPagingListener;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {	
	private PagingListView listView;
	private MainAdapter adapter;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        setListItem();
    }
    
    private void init(){
    	this.listView = (PagingListView) findViewById(R.id.main_list);    	
    	this.listView.setPagingListener(new OnPagingListener() {			
			@Override
			public void onScrollStart(int _nowPage) {
				//ここにスクロールが開始したときの処理を書く
			}
			
			@Override
			public void onScrollFinish(int _nowPage) {
				//ここにスクロールが終了したときの処理を書く
			}
			
			@Override
			public void onNextListLoad(int _nowPage) {
				//ここにまだ読み込んでいない画象を追加したい時に処理を書く
			}
		});
    }
    
    //set listview items
  	private void setListItem(){
  		ArrayList<String> tmpArray = new ArrayList<String>();
  		for (int i = 0; i < 10; i++) 
  			tmpArray.add("tmp");
  		
  		listView.setTotalPage(tmpArray.size());
  		this.listView.setNowPage(0);
  		
  		adapter = new MainAdapter(this, tmpArray);
  		listView.setAdapter(adapter);
  	}
    
}