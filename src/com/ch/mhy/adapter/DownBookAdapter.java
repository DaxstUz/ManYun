package com.ch.mhy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.mhy.R;
import com.ch.mhy.db.DBManager;
import com.ch.mhy.db.DBUtil;
import com.ch.mhy.entity.Down;
import com.ch.mhy.entity.DownListData;
import com.ch.mhy.fragment.DownloadFragment;
import com.ch.mhy.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 下载适配器
 * 
 * @author DaxstUz 2416738717 2015年6月5日
 *
 */
public class DownBookAdapter extends BaseAdapter {

	private DBManager manager;

	private Context context;
	private List<Down> list;

	private LayoutInflater inflater;

	public DownBookAdapter(Context context, List<Down> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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

		View view  = convertView.inflate(context,
					R.layout.adapter_downbook_item, null);
			ImageView iv_book_head = (ImageView) view
					.findViewById(R.id.iv_book_head);
			iv_book_head.setBackgroundResource(0);
			ImageLoader.getInstance().displayImage(
					list.get(position).getCd().getmPic(), iv_book_head,Utils.options3,new ImageLoadingListener() {
						
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							// TODO Auto-generated method stub
							view.setBackgroundResource(R.drawable.ic_loading_small);
						}
						
						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onLoadingCancelled(String imageUri, View view) {
							// TODO Auto-generated method stub
							
						}
					});

			TextView tv_book_name = (TextView) view
					.findViewById(R.id.tv_book_name);
			tv_book_name.setText(list.get(position).getCd().getmTitle());

			TextView tv_book_auth = (TextView) view
					.findViewById(R.id.iv_book_auth);
			tv_book_auth.setText("作者："
					+ list.get(position).getCd().getmDirector());

			TextView progressBar = (TextView) view
					.findViewById(R.id.progressBar);

			String status = "暂停中";
			List<Down> downs;
			/*
			 * 加锁，防止数据源被关闭报错。
			 */
			synchronized (this) {
				manager = new DBManager(context, DBUtil.ReadName, null, DBUtil.Code);
				downs = manager.queryDown(
						"select * from down  where mId =?", new String[] { list
								.get(position).getCd().getmId()
								+ "" });
				manager.closeDB();
				int n = 0;
				for (int i = 0; i < downs.size(); i++) {
					if (downs.get(i).getIsdonw() == 0) {
						n++;
					}
					if (Utils.downsmap.containsKey(list.get(position).getCd().getmId())) {
						status = "下载中";
					}
				}
				
				if (n == downs.size()) {
					status = "下载完成";
				}
			}
			

			

			progressBar.setText(status);

			TextView tv_book_lz = (TextView) view
					.findViewById(R.id.tv_book_lz);
			tv_book_lz.setText("总共" + downs.size() + "个");

			LinearLayout ll_down=(LinearLayout) view.findViewById(R.id.ll_down);
			if (list.get(position).getCd().isFlag()) {
				ll_down.removeViewAt(0);
			}else{
				CheckBox cb_read = (CheckBox) view
						.findViewById(R.id.cb_read);
				cb_read.setChecked(list.get(position).getCd().isIsselect());
 			}

		return view;
	}

}
