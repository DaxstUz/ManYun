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
import com.ch.mhy.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 适配器
 *
 * @author DaxstUz 2416738717
 *         2015年5月20日
 */
public class AuthorBooksListAdapter extends BaseAdapter {
    private List<Comics> list;
    private Context content;

    private LayoutInflater inflater;

    public AuthorBooksListAdapter(Context content, List<Comics> list) {
        this.list = list;
        this.content = content;
        this.inflater = (LayoutInflater) content.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    	String imgUrl = list.get(position).getmPic();
    	if(convertView!= null && imgUrl.equals(convertView.getTag())){
			return convertView;
		}
    	
			View view = inflater.inflate(R.layout.adapter_authorbookslist_item, null, false);
			view.setTag(imgUrl);
			
			TextView tv_author_bookname = (TextView) view
					.findViewById(R.id.tv_author_bookname);
			tv_author_bookname
					.setText(list.get(position).getmTitle().length() > 10 ? list
							.get(position).getmTitle().substring(0, 10)
							+ "..." : list.get(position).getmTitle());
			TextView tv_author_authorname = (TextView) view
					.findViewById(R.id.tv_author_authorname);
			tv_author_authorname.setText("作者：" + list.get(position).getmDirector());
			TextView tv_author_renqi = (TextView) view
					.findViewById(R.id.tv_author_renqi);
			tv_author_renqi.setText("人气：" + list.get(position).getmHits());
			
			ImageView iv_cate_authorshow = (ImageView) view
					.findViewById(R.id.iv_cate_authorshow);
				iv_cate_authorshow.setTag(imgUrl);
				iv_cate_authorshow.setBackgroundResource(0);
				ImageLoader.getInstance().displayImage(imgUrl, iv_cate_authorshow, Utils.adapterOpt);
		return view;
	}

}
