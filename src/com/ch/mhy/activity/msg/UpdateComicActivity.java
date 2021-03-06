package com.ch.mhy.activity.msg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.mhy.MainActivity;
import com.ch.mhy.R;
import com.ch.mhy.activity.book.ShowDetailActivity;
import com.ch.mhy.adapter.UpdateBooksListAdapter;
import com.ch.mhy.db.DBManager;
import com.ch.mhy.db.DBUtil;
import com.ch.mhy.entity.Comics;
import com.ch.mhy.entity.ComicsDetail;
import com.ch.mhy.entity.Down;
import com.ch.mhy.pulltorefresh.SingleLayoutListView;
import com.ch.mhy.pulltorefresh.SingleLayoutListView.OnLoadMoreListener;
import com.ch.mhy.pulltorefresh.SingleLayoutListView.OnRefreshListener;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.Utils;
import com.umeng.analytics.MobclickAgent;

/**
 * 漫画更新列表
 * 
 * @author xc.li
 * @date 2015年7月31日
 */
public class UpdateComicActivity extends Activity {
	private static final int LOAD_DATA_FINISH = 10;
	private static final int REFRESH_DATA_FINISH = 11;

	private int currentPage = 1;

	private UpdateBooksListAdapter adapter;

	private List<Comics> list;

	private SingleLayoutListView listview;

	int totalPage;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_DATA_FINISH:
				listview.onRefreshComplete(); // 加载更多完成
				break;
			case LOAD_DATA_FINISH:
				listview.onLoadMoreComplete(); // 加载更多完成
				break;
			}
		};
	};

	//private String typeName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showbooks);
		TextView tv_books_title = (TextView)this.findViewById(R.id.tv_books_title);
		tv_books_title.setText("更新漫画列表");
		initView();
		initData();
		
		MainActivity.instance.setNewUpdate(getIntent());
	}

	private void initView() {
		list = new ArrayList<Comics>();
		listview = (SingleLayoutListView) this.findViewById(R.id.mListView);

		adapter = new UpdateBooksListAdapter(this, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (id < 0) {// 点击的是headerView或者footerView
					return;
				}
				int realPosition = (int) id;
				Intent intent = new Intent(UpdateComicActivity.this,
						ShowDetailActivity.class);
				intent.putExtra("manhua", list.get(realPosition));
				intent.putExtra("eventId", "my_book_click");
				UpdateComicActivity.this.startActivity(intent);
			}
		});

		listview.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				updateData(listview, 0);
			}
		});

		listview.setOnLoadListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				updateData(listview, 1);
			}
		});
		listview.setCanLoadMore(true);
		listview.setCanRefresh(true);
		listview.setAutoLoadMore(true);
	}

	/**
	 * 刷新数据源
	 *
	 * @param response
	 */
	private void updateList(JSONObject response) {
		try {
			totalPage = response.getJSONObject("object").getJSONObject("pageInfo").getInt("totalPageCount");
			String data = response.getJSONObject("object").getString("data");
			if(!"null".equals(data) && data.length()>0){
				JSONArray array = response.getJSONObject("object").getJSONArray("data");
				if (array.length() == 0) {
					listview.setmIsMore(false);
				} else {
					listview.setmIsMore(true);
				}
				for (int i = 0; i < array.length(); i++) {
					JSONObject joObject = array.getJSONObject(i);
					Comics co = new Comics();
					co.setmPic(joObject.getString("mPic"));
					co.setmContent(joObject.getString("mContent"));
					co.setmDirector(joObject.getString("mDirector"));
					co.setmLianzai(joObject.getString("mLianzai"));
					co.setmQid(joObject.getLong("mId"));
					co.setmTitle(joObject.getString("mTitle"));
					co.setmType1(joObject.getInt("mType1"));
					co.setmType5(joObject.getString("mType5"));
					co.setmHits(joObject.getInt("mHits"));
					co.setIp(joObject.getString("mName"));
					list.add(co);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private JSONObject params = new JSONObject();

	/**
	 * 获取最近更新数据
	 */
	private void initData() {
		if(!initParam()){
			return ;
		}
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlpushUserNewComic, params, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						updateList(response);
						adapter.notifyDataSetChanged();
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					}
				}) {

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
	 * 初始化查询参数
	 *
	 * @param typeId
	 */
	private boolean initParam() {
		String mids = getMids();
		if(mids!=null && !"".equals(mids)){
			SharedPreferences spf = this.getSharedPreferences("comicUpdateDate", Context.MODE_PRIVATE);
			JSONObject obj = new JSONObject();
			try {
				long lastUpdateTime = this.getIntent().getLongExtra("lastUpdateTime", 1438156800000L);
				obj.put("mids", mids);
				obj.put("lastUpdateTime", lastUpdateTime);
				params.put("pageSize", Utils.PageSize);
				params.put("currentPage", currentPage);
				params.put("orderBy", "");
				params.put("object", obj);
				return true;
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * 从本地数据库里获取收藏、最近阅读、下载记录
	 * @return
	 */
	private String getMids(){
		List<Integer> ids = new ArrayList<Integer>();
		DBManager manager = new DBManager(this, DBUtil.CollectName, null, DBUtil.Code);
		List<ComicsDetail> list=new ArrayList<ComicsDetail>();
		list = manager.query();
		for(int i=0; i<list.size(); i++){
			ComicsDetail item = list.get(i);
			ids.add(item.mId);
		}
		manager.closeDB();
		
		manager = new DBManager(this, DBUtil.ReadName, null, DBUtil.Code);
		list=manager.query();
		for(int i=0; i<list.size(); i++){
			ComicsDetail item = list.get(i);
			ids.add(item.mId);
		}
		manager.closeDB();
		
		manager = new DBManager(this, DBUtil.ReadName, null, DBUtil.Code);
		List<Down> dlist=manager.queryDown("select * from down GROUP BY mId ",null);
		for(int i=0; i<dlist.size(); i++){
			Down item = dlist.get(i);
			ids.add(item.getCd().mId);
		}
		manager.closeDB();
		String idstr = Arrays.toString(ids.toArray());
		return idstr.replaceAll("\\[", "").replaceAll("\\]", "");
	}
	
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.btn_show_back:
			this.finish();
			break;

		default:
			break;
		}
	}

	private void updateData(final SingleLayoutListView pullToRefreshLayout,
			final int n) {

		if (n == 0) {
			currentPage = 1;
		} else {
			++currentPage;
		}

		try {
			params.put("currentPage", currentPage);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlpushUserNewComic, params, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (n != 1) {
							if (list != null) {
								list.clear();
							}
						}
						updateList(response);
						adapter.notifyDataSetChanged();

						if (n == 0) {
							Message _Msg = mHandler.obtainMessage(
									REFRESH_DATA_FINISH, list);
							mHandler.sendMessage(_Msg);
						} else if (n == 1) {
							Message _Msg = mHandler.obtainMessage(
									LOAD_DATA_FINISH, list);
							mHandler.sendMessage(_Msg);
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					}
				}) {

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

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		String eventId = this.getIntent().getStringExtra("eventId");
		if (eventId != null) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", eventId);
			map.put("typeName", "最近更新");
			MobclickAgent.onEvent(this, eventId, map);
		}
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
