package com.ch.mhy.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ch.mhy.R;
import com.ch.mhy.dialog.Util;
import com.ch.mhy.entity.ComicsDetail;


/**
 * 章节选择适配器
 * @author DaxstUz 2416738717
 * 2015年6月26日
 *
 */
public class ChapterSelectAdapter extends BaseAdapter {

    private List<ComicsDetail> list;

    private LayoutInflater inflater;
    
    private Integer mNo;

    public ChapterSelectAdapter(Context content, List<ComicsDetail> list,Integer mNo) {
        // TODO Auto-generated constructor stub
        this.list = list;
        inflater = LayoutInflater.from(content);
        this.mNo=mNo;
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

         View view = inflater.inflate(R.layout.adapter_chapterselect_item, null, false);

        TextView btn_adapter_select = (TextView) view.findViewById(R.id.tv_adapter_select);
        btn_adapter_select.setText(list.get(position).getmName());

        if(mNo.equals(list.get(position).getmNo())){
        	btn_adapter_select.setTextColor(Color.BLACK);
        	btn_adapter_select.setBackgroundResource(R.drawable.ch_book_huabgw);
        }
        
        return view;
    }
}
