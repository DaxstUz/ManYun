package com.ch.mhy.adapter;

import java.util.List;

import com.ch.mhy.R;
import com.ch.mhy.entity.FeedBack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeedBackAdapter extends BaseAdapter
{

	private Context context;
	private List<FeedBack> list;
	private LayoutInflater inflater;

	public FeedBackAdapter(Context context, List<FeedBack> list)
	{
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		convertView = inflater.inflate(R.layout.adapter_history_item, null);

		TextView tv_history_date = (TextView) convertView
				.findViewById(R.id.tv_history_date);
		tv_history_date.setText(list.get(position).getCreateDate());
		TextView tv_history_title = (TextView) convertView
				.findViewById(R.id.tv_history_title);
		tv_history_title.setText("类型：" + list.get(position).getTitle());
		TextView tv_history_content = (TextView) convertView
				.findViewById(R.id.tv_history_content);
		tv_history_content.setText(list.get(position).getContent());
		TextView tv_history_reback = (TextView) convertView
				.findViewById(R.id.tv_history_reback);
		String replyContent = list.get(position).getReplyContent();
		if (replyContent != null && !"null".equals(replyContent))
		{
			tv_history_reback.setText("回复：" + replyContent);
		} else
		{
			tv_history_reback.setVisibility(View.GONE);
		}
		return convertView;
	}

}
