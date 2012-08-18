package test.duyoji.adapter;

import java.util.ArrayList;
import test.duyoji.paginglist.R;
import test.duyoji.util.SizeUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class MainAdapter extends BaseAdapter {	
	private ArrayList<String> items;	
	private LinearLayout.LayoutParams llp;
	private LayoutInflater inflater;	
	
	public MainAdapter(Context context, ArrayList<String> items) {
		this.items = items;		
		this.inflater = LayoutInflater.from(context);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();                
        this.llp = new LinearLayout.LayoutParams(display.getWidth(), display.getHeight()-SizeUtil.getStatusBarHeight(context));        
	}
	
	public void addItems(ArrayList<String> _items){
		items.addAll(items);
		notifyDataSetChanged();
	}
	
	//View使い回し用
	private static class ViewHolder{
		private ImageView imageView;
	}	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;						
		if (convertView == null) {	
			convertView = inflater.inflate(R.layout.item_paging_list, null);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.item_paging_list_image);						
			imageView.setLayoutParams(llp);
			holder = new ViewHolder();
			holder.imageView = imageView;						
			convertView.setTag(holder);
		}else{		
			holder = (ViewHolder) convertView.getTag();
		}													
		holder.imageView.setImageResource(R.drawable.ic_launcher);		
		return convertView;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public String getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
