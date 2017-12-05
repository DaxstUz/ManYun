package com.ch.mhy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.mhy.R;
import com.ch.mhy.entity.Comics;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 搜索结果展示适配器
 *
 * @author DaxstUz 2416738717 2015年5月20日
 */
public class SearchResultAdapter extends BaseAdapter {
	private List<Comics> list;
	private Context content;

	private LayoutInflater inflater;

	public SearchResultAdapter(Context content, List<Comics> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.content = content;
		this.inflater = (LayoutInflater) content
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String imgUrl = list.get(position).getmPic();
		if(convertView!= null && imgUrl.equals(convertView.getTag())){
			return convertView;
		}
			View view = inflater.inflate(R.layout.adapter_searchresult_item, null, false);
			view.setTag(imgUrl);
			TextView tv_seatchitem_bookname = (TextView) view
					.findViewById(R.id.tv_seatchitem_bookname);
			tv_seatchitem_bookname.setText((list.get(position).getmTitle()));
			TextView tv_seatchitem_auth = (TextView) view
					.findViewById(R.id.tv_seatchitem_auth);
			tv_seatchitem_auth.setText("作者："
					+ (list.get(position).getmDirector()));
			TextView tv_seatchitem_peoes = (TextView) view
					.findViewById(R.id.tv_seatchitem_peoes);
			tv_seatchitem_peoes.setText("人气：" + list.get(position).getmHits()
					+ "");

			ImageView iv_searchresult_show = (ImageView) view
					.findViewById(R.id.iv_searchresult_show);
			
            	iv_searchresult_show.setTag(imgUrl);
            	iv_searchresult_show.setBackgroundResource(0);
            	ImageLoader.getInstance().displayImage(
					list.get(position).getmPic(), iv_searchresult_show, Utils.adapterOpt);
		return view;
	}

}
