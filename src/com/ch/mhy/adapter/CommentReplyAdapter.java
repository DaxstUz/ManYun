package com.ch.mhy.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ch.mhy.R;
import com.ch.mhy.entity.CommentReplyInfo;
import com.ch.mhy.util.Utils;
import com.ch.mhy.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CommentReplyAdapter extends BaseAdapter {
	private List<CommentReplyInfo> dataList;
	private LayoutInflater inflater;
	private String today;
	
	public CommentReplyAdapter(Context context, List<CommentReplyInfo> dataList){
		this.dataList = dataList;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		today = formatter.format(System.currentTimeMillis());
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

	@SuppressLint({ "ViewHolder", "SimpleDateFormat" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommentReplyInfo bean = dataList.get(position);
		if(convertView !=null){
			TextView user_date = (TextView)convertView.findViewById(R.id.comment_reply_date);
			int indx = dataList.size()-position;
			user_date.setText(indx+"楼 | "+bean.getReplyTime().replace(today, "今天"));
			TextView user_comment = (TextView)convertView.findViewById(R.id.comment_reply_content);
			user_comment.setText(bean.getDiscuss());
			String headUrl = bean.getImgUrl();
			CircleImageView user_head = (CircleImageView)convertView.findViewById(R.id.comment_reply_head);
			ImageLoader.getInstance().displayImage(headUrl, user_head, Utils.adapterOpt);
			return convertView;
		}
		convertView = inflater.inflate(R.layout.comment_reply_item, null, false);
		CircleImageView user_head = (CircleImageView)convertView.findViewById(R.id.comment_reply_head);
		TextView user_name = (TextView)convertView.findViewById(R.id.comment_reply_name);
		TextView user_date = (TextView)convertView.findViewById(R.id.comment_reply_date);
		TextView user_comment = (TextView)convertView.findViewById(R.id.comment_reply_content);
		
		String headUrl = bean.getImgUrl();
		
		ImageLoader.getInstance().displayImage(headUrl, user_head, Utils.adapterOpt);
		user_name.setText(Html.fromHtml("<B>"+bean.getNickname()+"</B>"));
		int indx = dataList.size()-position;
		user_date.setText(indx+"楼 | "+bean.getReplyTime().replace(today, "今天"));
		user_comment.setText(bean.getDiscuss());
		return convertView;
	}

}
