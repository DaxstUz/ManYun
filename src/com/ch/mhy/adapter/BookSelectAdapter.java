package com.ch.mhy.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ch.mhy.R;
import com.ch.mhy.entity.ComicsDetail;

public class BookSelectAdapter extends BaseAdapter{

    private List<ComicsDetail> list;

    private LayoutInflater inflater;
    
    public BookSelectAdapter(Context content, List<ComicsDetail> list) {
        this.list = list;
        inflater = LayoutInflater.from(content);
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

    @SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
    	 final View cv = inflater.inflate(R.layout.book_select_item, null, false);
    	 final LinearLayout ll_book_down=(LinearLayout) cv.findViewById(R.id.ll_book_down);
    	 TextView tv_bookselect_name = (TextView) cv.findViewById(R.id.tv_bookselect_name);
    	 String name = list.get(position).getmName();
//    	 if(name!=null &&name.length()>12){//处理标题过长展示问题，以第一个空格截取
//    		 name = name.substring(0,12);
//    	 }
         tv_bookselect_name.setText(name);

         String createTime = list.get(position).getCreateTime();
         String sysDate = list.get(position).getSysDate();
         //以下为章节做标"新"处理
	     long days = getDaysBetweenNow(createTime, sysDate);
	     if(days<=30){//判断是否是最新上架的章节，是则标识为‘新’,以更新时间为准，30天内的为新更新
	     	ImageView imgView = new ImageView(cv.getContext());
	     	imgView.setBackgroundResource(R.drawable.new_chp);
	     	ll_book_down.addView(imgView, 0);
	     	//调整标题栏位置
	     	LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	     	lllp.leftMargin = -22;
	     	lllp.gravity = Gravity.CENTER;
	     	tv_bookselect_name.setLayoutParams(lllp);
	    }
	     
	     ll_book_down.setBackgroundResource(R.drawable.ch_book_select);
        return cv;
    }

	/**
	 * 获取与当前日期的天数差
	 * @param strDate
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private long getDaysBetweenNow(String strDate, String sysDate) {
		long days = 0;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date strtodate = formatter.parse(strDate);
			Date sysdate = formatter.parse(sysDate);
			long milliseconds = sysdate.getTime() - strtodate.getTime();
	        days = milliseconds / 86400000L;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}
}
