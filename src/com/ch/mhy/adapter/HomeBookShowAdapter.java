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
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeBookShowAdapter extends BaseAdapter {

    private List<Comics> list;

    private LayoutInflater inflater;

    public HomeBookShowAdapter(Context content, List<Comics> list) {
        // TODO Auto-generated constructor stub
        this.list = list;
        inflater = LayoutInflater.from(content);
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
        // TODO Auto-generated method stub

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.night_item, null, false);
        }

        TextView tv_type_show = (TextView) convertView.findViewById(R.id.tv_type_show);
        tv_type_show.setText(list.get(position).getmTitle());
        ImageView iv_type_show = (ImageView) convertView.findViewById(R.id.iv_type_show);

        iv_type_show.setBackgroundResource(0);
        ImageLoader.getInstance().displayImage(list.get(position).getmPic(), iv_type_show);
        return convertView;
    }

}
