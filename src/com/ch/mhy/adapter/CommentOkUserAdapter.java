package com.ch.mhy.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ch.mhy.R;
import com.ch.mhy.entity.CommentOkInfo;
import com.ch.mhy.util.Utils;
import com.ch.mhy.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CommentOkUserAdapter extends BaseAdapter {
	private List<CommentOkInfo> dataList;
	private LayoutInflater inflater;
	 
	public CommentOkUserAdapter(Context context, List<CommentOkInfo> dataList){
		this.dataList = dataList;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.comment_ok_user_item, null, false);
		CommentOkInfo bean = dataList.get(position);
		CircleImageView user_head = (CircleImageView)convertView.findViewById(R.id.ok_user_head);
		//TextView user_name = (TextView)convertView.findViewById(R.id.ok_user_name);
		
		String headUrl = bean.getImgUrl();
		
		ImageLoader.getInstance().displayImage(headUrl, user_head, Utils.adapterOpt);
		//user_name.setText(bean.get("name"));
		return convertView;
	}

}
