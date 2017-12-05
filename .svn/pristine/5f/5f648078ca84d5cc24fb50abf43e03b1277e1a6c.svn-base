package com.ch.mhy.adapter;

import java.util.List;

import com.ch.mhy.R;
import com.ch.mhy.entity.Key;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KeyAdapter extends BaseAdapter {
	
	private Context context;
	private List<Key> keys;
	public KeyAdapter(Context context,List<Key> keys) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.keys=keys;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return keys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return keys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView=LayoutInflater.from(context).inflate(R.layout.adapter_keys_item, null, false);
		TextView tv_key=(TextView) convertView.findViewById(R.id.tv_key);
		tv_key.setText(keys.get(position).getTitle());
//		TextView tv_hua=(TextView) convertView.findViewById(R.id.tv_hua);
//		tv_hua.setText("共"+keys.get(position).getTotal()+"话");
		
//		if(position%2==0){
//			convertView.setBackgroundColor(R.drawable.ch_book_select);
//		}
		return convertView;
	}

}
