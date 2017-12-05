package com.ch.mhy.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.mhy.R;
import com.ch.mhy.activity.book.ShowDetailActivity;
import com.ch.mhy.activity.serarch.ResultActivity;
import com.ch.mhy.activity.serarch.SearchActivity;
import com.ch.mhy.entity.Comics;
import com.ch.mhy.listener.LoadingListener;
import com.ch.mhy.net.NetReceiver;
import com.ch.mhy.net.NetReceiver.NetState;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 搜索页面
 *
 * @author DaxstUz 2416738717 2015年5月4日
 */
public class SearchFragment extends Fragment implements OnClickListener,ImageLoadingListener {

	private TextView tv_title_search;

	private TextView tv_search_key1;
	private TextView tv_search_key2;
	private TextView tv_search_key3;
	private TextView tv_search_key4;
	private TextView tv_search_key5;
	private TextView tv_search_key6;

	private TextView tv_like_show1;
	private TextView tv_like_show2;
	private TextView tv_like_show3;
	private TextView tv_like_show4;
	private TextView tv_like_show5;
	private TextView tv_like_show6;

	private ImageView iv_search_like1;
	private ImageView iv_search_like2;
	private ImageView iv_search_like3;
	private ImageView iv_search_like4;
	private ImageView iv_search_like5;
	private ImageView iv_search_like6;
	
	private ImageView btn_getnet;

	private List<Comics> books = new ArrayList<Comics>();

	private int n = 0;

	private View view;
	
	private LinearLayout ll_getnet;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	        return view;
	    }
	    try {
	       view = inflater.inflate(R.layout.fragment_book_search, null); 
	    } catch (InflateException e) {
	        
	    }
		
		init(view);

		if (NetReceiver.isConnected(SearchFragment.this.getActivity()) != NetState.NET_NO) {
			n = 0;
			getPopKey();
			getLike();
		}else{
			ll_getnet.setVisibility(View.VISIBLE);	
		} 

		return view;
	}

	/**
	 * 获取猜你喜欢
	 */
	private void getLike() {
		// TODO Auto-generated method stub
		JSONObject params = new JSONObject();

		try {
			params.put("pageSize", "6");
			params.put("currentPage", "1");
			params.put("orderBy", "");
			params.put("object", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlGuessList, params,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						n++;
						if (n == 2) {
							// Utils.endnet();
						}

						try {
							JSONArray array = response.getJSONObject("object")
									.getJSONArray("data");

							tv_like_show1.setText(array.getJSONObject(0)
									.getString("mTitle"));
							tv_like_show2.setText(array.getJSONObject(1)
									.getString("mTitle"));
							tv_like_show3.setText(array.getJSONObject(2)
									.getString("mTitle"));
							tv_like_show4.setText(array.getJSONObject(3)
									.getString("mTitle"));
							tv_like_show5.setText(array.getJSONObject(4)
									.getString("mTitle"));
							tv_like_show6.setText(array.getJSONObject(5)
									.getString("mTitle"));

							ImageLoader.getInstance().displayImage(
									array.getJSONObject(0).getString(
													"mPic"), iv_search_like1,Utils.options,SearchFragment.this);
							ImageLoader.getInstance().displayImage(
									array.getJSONObject(1).getString(
													"mPic"), iv_search_like2,Utils.options,SearchFragment.this);
							ImageLoader.getInstance().displayImage(
									array.getJSONObject(2).getString(
													"mPic"), iv_search_like3,Utils.options,SearchFragment.this);
							ImageLoader.getInstance().displayImage(
									array.getJSONObject(3).getString(
													"mPic"), iv_search_like4,Utils.options,SearchFragment.this);
							ImageLoader.getInstance().displayImage(
									array.getJSONObject(4).getString(
													"mPic"), iv_search_like5,Utils.options,SearchFragment.this);
							ImageLoader.getInstance().displayImage(
									 array.getJSONObject(5).getString(
													"mPic"), iv_search_like6,Utils.options,SearchFragment.this);

							for (int i = 0; i < array.length(); i++) {
								JSONObject rqobject = array.getJSONObject(i);
								Comics c = new Comics();
								c.setmDirector(rqobject.getString("mDirector"));
								c.setmPic(rqobject.getString("mPic"));
								c.setmContent(rqobject.getString("mContent"));
								c.setmTitle(rqobject.getString("mTitle"));
								c.setmHits(rqobject.getInt("mHits"));
								c.setmQid(rqobject.getLong("mId"));
								c.setmType1(rqobject.getInt("mType1"));
								c.setmLianzai(rqobject.getString("mLianzai"));
								c.setUpdateMessage(rqobject.getString("updateMessage"));
								books.add(c);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						n++;
						if (n == 2) {
							// Utils.endnet();
							Utils.showMsg(SearchFragment.this.getActivity(),
									"服务器异常!");
						}

					}
				}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		NetUtil.rqueue.add(jsonObjReq);
	}

	/**
	 * 控件初始化
	 *
	 * @param view
	 */
	private void init(View view) {
		tv_like_show1 = (TextView) view.findViewById(R.id.tv_like_show1);
		tv_like_show2 = (TextView) view.findViewById(R.id.tv_like_show2);
		tv_like_show3 = (TextView) view.findViewById(R.id.tv_like_show3);
		tv_like_show4 = (TextView) view.findViewById(R.id.tv_like_show4);
		tv_like_show5 = (TextView) view.findViewById(R.id.tv_like_show5);
		tv_like_show6 = (TextView) view.findViewById(R.id.tv_like_show6);

		iv_search_like1 = (ImageView) view.findViewById(R.id.iv_search_like1);
		iv_search_like2 = (ImageView) view.findViewById(R.id.iv_search_like2);
		iv_search_like3 = (ImageView) view.findViewById(R.id.iv_search_like3);
		iv_search_like4 = (ImageView) view.findViewById(R.id.iv_search_like4);
		iv_search_like5 = (ImageView) view.findViewById(R.id.iv_search_like5);
		iv_search_like6 = (ImageView) view.findViewById(R.id.iv_search_like6);
		
		ll_getnet = (LinearLayout) view.findViewById(R.id.ll_getnet);
		btn_getnet = (ImageView) view.findViewById(R.id.btn_getnet);
		btn_getnet.setOnClickListener(SearchFragment.this);

		tv_title_search = (TextView) view.findViewById(R.id.tv_title_search);
		tv_title_search.setOnClickListener(SearchFragment.this);

		tv_search_key1 = (TextView) view.findViewById(R.id.tv_search_key1);
		tv_search_key1.setOnClickListener(SearchFragment.this);

		tv_search_key2 = (TextView) view.findViewById(R.id.tv_search_key2);
		tv_search_key2.setOnClickListener(SearchFragment.this);

		tv_search_key3 = (TextView) view.findViewById(R.id.tv_search_key3);
		tv_search_key3.setOnClickListener(SearchFragment.this);

		tv_search_key4 = (TextView) view.findViewById(R.id.tv_search_key4);
		tv_search_key4.setOnClickListener(SearchFragment.this);

		tv_search_key5 = (TextView) view.findViewById(R.id.tv_search_key5);
		tv_search_key5.setOnClickListener(SearchFragment.this);

		tv_search_key6 = (TextView) view.findViewById(R.id.tv_search_key6);
		tv_search_key6.setOnClickListener(SearchFragment.this);

		iv_search_like1.setOnClickListener(SearchFragment.this);
		iv_search_like2.setOnClickListener(SearchFragment.this);
		iv_search_like3.setOnClickListener(SearchFragment.this);
		iv_search_like4.setOnClickListener(SearchFragment.this);
		iv_search_like5.setOnClickListener(SearchFragment.this);
		iv_search_like6.setOnClickListener(SearchFragment.this);
	}

	/**
	 * 拿到热门关键字
	 */
	private void getPopKey() {
		// TODO Auto-generated method stub
		JSONObject params = new JSONObject();

		try {
			params.put("pageSize", "6");
			params.put("currentPage", "1");
			params.put("orderBy", "");
			params.put("object", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlKeyList, params,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						
						ll_getnet.setVisibility(View.GONE);	
						
						try {
							JSONArray array = response.getJSONObject("object")
									.getJSONArray("data");
							tv_search_key1.setText(array.getJSONObject(1)
									.getString("searchKey"));
							tv_search_key2.setText(array.getJSONObject(2)
									.getString("searchKey"));
							tv_search_key3.setText(array.getJSONObject(3)
									.getString("searchKey"));
							tv_search_key4.setText(array.getJSONObject(4)
									.getString("searchKey"));
							tv_search_key5.setText(array.getJSONObject(5)
									.getString("searchKey"));
							tv_search_key6.setText(array.getJSONObject(0)
									.getString("searchKey"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						n++;
						if (n == 2) {
							// Utils.endnet();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						n++;
						if (n == 2) {
							// Utils.endnet();
							Utils.showMsg(SearchFragment.this.getActivity(),
									"服务器异常!");
						}
					}
				}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		NetUtil.rqueue.add(jsonObjReq);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;

		if (books.size() > 0) {
			switch (v.getId()) {
			case R.id.iv_search_like1:
				intent = new Intent(SearchFragment.this.getActivity(),
						ShowDetailActivity.class);
				intent.putExtra("manhua", books.get(0));
				intent.putExtra("eventId", "guess_like");
				SearchFragment.this.getActivity().startActivity(intent);
				break;

			case R.id.iv_search_like2:
				intent = new Intent(SearchFragment.this.getActivity(),
						ShowDetailActivity.class);
				intent.putExtra("manhua", books.get(1));
				intent.putExtra("eventId", "guess_like");
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.iv_search_like3:
				intent = new Intent(SearchFragment.this.getActivity(),
						ShowDetailActivity.class);
				intent.putExtra("manhua", books.get(2));
				intent.putExtra("eventId", "guess_like");
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.iv_search_like4:
				intent = new Intent(SearchFragment.this.getActivity(),
						ShowDetailActivity.class);
				intent.putExtra("manhua", books.get(3));
				intent.putExtra("eventId", "guess_like");
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.iv_search_like5:
				intent = new Intent(SearchFragment.this.getActivity(),
						ShowDetailActivity.class);
				intent.putExtra("manhua", books.get(4));
				intent.putExtra("eventId", "guess_like");
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.iv_search_like6:
				intent = new Intent(SearchFragment.this.getActivity(),
						ShowDetailActivity.class);
				intent.putExtra("manhua", books.get(5));
				intent.putExtra("eventId", "guess_like");
				SearchFragment.this.getActivity().startActivity(intent);
				break;

			case R.id.tv_search_key1:
				intent = new Intent(SearchFragment.this.getActivity(),
						ResultActivity.class);
				intent.putExtra("key", tv_search_key1.getText().toString());
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.tv_search_key2:
				intent = new Intent(SearchFragment.this.getActivity(),
						ResultActivity.class);
				intent.putExtra("key", tv_search_key2.getText().toString());
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.tv_search_key3:
				intent = new Intent(SearchFragment.this.getActivity(),
						ResultActivity.class);
				intent.putExtra("key", tv_search_key3.getText().toString());
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.tv_search_key4:
				intent = new Intent(SearchFragment.this.getActivity(),
						ResultActivity.class);
				intent.putExtra("key", tv_search_key4.getText().toString());
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.tv_search_key5:
				intent = new Intent(SearchFragment.this.getActivity(),
						ResultActivity.class);
				intent.putExtra("key", tv_search_key5.getText().toString());
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.tv_search_key6:
				intent = new Intent(SearchFragment.this.getActivity(),
						ResultActivity.class);
				intent.putExtra("key", tv_search_key6.getText().toString());
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			case R.id.tv_title_search:
				intent = new Intent(SearchFragment.this.getActivity(),
						SearchActivity.class);
				SearchFragment.this.getActivity().startActivity(intent);
				break;
			default:
				break;
			}
		}else {
			if (NetReceiver.isConnected(SearchFragment.this.getActivity()) != NetState.NET_NO) {
				// Utils.startnet(SearchFragment.this.getActivity());
				n = 0;
				getPopKey();
				getLike();
			}else{
				Toast.makeText(SearchFragment.this.getActivity(), "请链接网络！", Toast.LENGTH_SHORT).show();
			}
		}
	}

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
}
