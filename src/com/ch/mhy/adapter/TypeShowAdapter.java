package com.ch.mhy.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
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
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class TypeShowAdapter extends BaseAdapter {
	Context content;
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.obj != null){
				final ImageObj imgObj = (ImageObj)msg.obj;
				imgObj.getImgView().setBackgroundResource(0);
				
				ImageLoader.getInstance().displayImage(imgObj.getImgUrl(), imgObj.getImgView(),Utils.options,new ImageLoadingListener() {
					
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						view.setBackgroundResource(R.drawable.ic_loading_small);
					}
					
					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						
					}
					
					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						
					}
					
					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						
					}
				});
			}
		}
		
	};
    private List<AuthorType> list;

    private LayoutInflater inflater;

    public TypeShowAdapter(Context content, List<AuthorType> list) {
        this.list = list;
        inflater = LayoutInflater.from(content);
        this.content = content;
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
    	
    	View view = null;
    	if(position==0){
    		if(convertView==null){
    			convertView = inflater.inflate(R.layout.night_item, null, false);
    		}
    		view=convertView;
    	}else{
    		view = inflater.inflate(R.layout.night_item, null, false);
    	}
	        TextView tv_type_show = (TextView) view.findViewById(R.id.tv_type_show);
	        tv_type_show.setText(list.get(position).getTypeName());
	        final ImageView iv_type_show = (ImageView) view.findViewById(R.id.iv_type_show);
	        final String imgUrl = UrlConstant.Ip1 + UrlConstant.Port1 + list.get(position).getTypePic();
	       
	        Message msg = new Message();
	        ImageObj obj = new ImageObj();
	        obj.setImgUrl(imgUrl);
	        obj.setImgView(iv_type_show);
	        msg.obj = obj;
	        handler.sendMessage(msg);
        return view;
    }
    
    
    class ImageObj {
    	String imgUrl;
    	ImageView imgView;
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		public ImageView getImgView() {
			return imgView;
		}
		public void setImgView(ImageView imgView) {
			this.imgView = imgView;
		}
    	
    }
}
