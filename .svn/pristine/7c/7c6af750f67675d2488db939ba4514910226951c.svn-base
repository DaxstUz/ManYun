package com.ch.mhy.adapter;

import java.text.NumberFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ch.mhy.R;
import com.ch.mhy.entity.Down;
import com.ch.mhy.util.Utils;

/**
 * 下载适配器
 * 
 * @author DaxstUz 2416738717 2015年6月5日
 *
 */
public class DownShowAdapter extends BaseAdapter {
	private List<Down> list;
	private Context context;

	public DownShowAdapter(Context context, List<Down> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Down down = list.get(position);
		View view;
		// if (convertView!=null) {
		/*
		 * if
		 * (convertView!=null&&convertView.getTag()!=null&&down.getCd().getmName
		 * ().endsWith(convertView.getTag().toString())) { view = convertView; }
		 * else {
		 */
		view = LayoutInflater.from(context).inflate(R.layout.adapter_down_item,
				null, false);

		view.setTag(down.getCd().getmName());

		TextView tv_book_name = (TextView) view.findViewById(R.id.tv_book_name);
		tv_book_name.setText(down.getCd().getmName());

		/* 设置进度条 */
		ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.progressBar);// 进度条
		String[] downUrlArray = down.getCd().getmUrl().split(Utils.split);
		int max = downUrlArray.length;
		progressBar.setMax(max);
		progressBar.setProgress(down.getDowns());

		TextView tx_down_percentage = (TextView) view
				.findViewById(R.id.tv_book_downinfo);

		/* 设置百分比 */
		NumberFormat numberFormat = NumberFormat.getInstance();// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(0);
		String str = "";
		if (down.getIsdonw() == 0) {
			str = "100%";
		} else {
			String result = numberFormat.format((float) down.getDowns() / max
					* 100);
			str = result + "%";
		}
		tx_down_percentage.setText(str);

		ImageView down_status_image = (ImageView) view
				.findViewById(R.id.iv_book_auth);

		switch (down.getIsdonw()) {
		case 0:// "完成";
			down_status_image.setBackgroundResource(R.drawable.downread);
			progressBar.setVisibility(View.GONE);
			break;
		case 1:// "开始";
			down_status_image.setBackgroundResource(R.drawable.downstop);
			progressBar.setVisibility(View.VISIBLE);
			break;
		case 2:// "暂停";
			down_status_image.setBackgroundResource(R.drawable.downstart);
			progressBar.setVisibility(View.VISIBLE);
			break;
		case 3:// "等待";
			down_status_image.setBackgroundResource(R.drawable.downwait);
			progressBar.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}

		CheckBox cb_donwd=(CheckBox) view.findViewById(R.id.cb_donwd);
		cb_donwd.setChecked(down.getCd().isIsselect());
		
		LinearLayout ll_downinfo=(LinearLayout) view.findViewById(R.id.ll_downinfo);
		
		if (down.getCd().isFlag()) {
			ll_downinfo.removeViewAt(0);
//			ImageView tv_bookreaded_del = (ImageView) view
//					.findViewById(R.id.tv_bookreaded_del);
//			tv_bookreaded_del.setVisibility(View.GONE);
		}
//		else {
//			down_status_image.setVisibility(View.GONE);
//		}
		return view;
	}

}
