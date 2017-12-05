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
import com.ch.mhy.entity.AuthorType;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 适配器
 *
 * @author DaxstUz 2416738717
 *         2015年5月20日
 */
public class AuthorListAdapter extends BaseAdapter {
    private List<AuthorType> list;
    private Context content;

    private LayoutInflater inflater;

    public AuthorListAdapter(Context content, List<AuthorType> list) {
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
    	String imgUrl = list.get(position).getTypePic();
    	if(convertView!= null && imgUrl.equals(convertView.getTag())){
			return convertView;
		}
    	
    		View  view = inflater.inflate(R.layout.adapter_authorlist_item, parent, false);
    		view.setTag(imgUrl);
            
            TextView tv_cate_authorname = (TextView) view.findViewById(R.id.tv_cate_authorname);
	        String name = list.get(position).getTypeName();
	        name = (name == null ? "无名" : name.trim());
	        tv_cate_authorname.setText(name);
	        TextView tv_cate_authorbooks = (TextView) view.findViewById(R.id.tv_cate_authorbooks);
	        tv_cate_authorbooks.setText("(共有" + (list.get(position).getTypeNum() + "部作品)"));
	        
	        ImageView iv_cate_authorshow = (ImageView) view.findViewById(R.id.iv_cate_authorshow);
	        
//	        if(imgUrl.equals(iv_cate_authorshow.getTag())){
	        	iv_cate_authorshow.setTag(imgUrl);
	        	iv_cate_authorshow.setBackgroundResource(0);
	        	ImageLoader.getInstance().displayImage(imgUrl, iv_cate_authorshow, Utils.adapterOpt);
//	        }
	        
        return view;
    }

}
