package com.ch.mhy.activity.serarch;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.comm.saevent.MobSaAgent;
import com.ch.mhy.R;
import com.ch.mhy.adapter.KeyAdapter;
import com.ch.mhy.application.MhyApplication;
import com.ch.mhy.entity.Key;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.UrlConstant;
import com.umeng.analytics.MobclickAgent;

/**
 * 搜索界面
 * 
 * @author DaxstUz 2416738717 2015年6月30日
 *
 */
public class SearchActivity extends Activity {

	// 搜索输入框
	private EditText et_search_key;

	// 删除图标
	private ImageView iv_searchcanser;

	int n = 0;
	// 搜索关键字
	private String key;

	// 搜索结果展示
	private ListView lv_keylist;
	List<Key> keys = new ArrayList<Key>();
	private KeyAdapter adapter;

	/**
	 * 记录清除本地记录图标是否被remove掉
	 */
	private boolean isGone = false;

	/**
	 * 搜索界面整体布局
	 */
	private LinearLayout ll_search_main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		keys.addAll(MhyApplication.keylist);

		// 初始化控件
		lv_keylist = (ListView) findViewById(R.id.lv_keylist);
		iv_searchcanser = (ImageView) findViewById(R.id.iv_searchcanser);
		adapter = new KeyAdapter(this, keys);
		lv_keylist.setAdapter(adapter);

		// 添加行点击事件
		lv_keylist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				key = keys.get(position).getTitle();
				searchOperate();
			}
		});

		et_search_key = (EditText) findViewById(R.id.et_search_key);
		ll_search_main = (LinearLayout) findViewById(R.id.ll_search_main);
		if (!(keys.size() > 0)) {
			ll_search_main.removeViewAt(1);
			isGone = true;
		}

		et_search_key.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!isGone) {// 将删除操作行去掉
					ll_search_main.removeViewAt(1);
					isGone = true;
				}
				/* 更加关键字的长度是否显示删除图标 */
				if (s.toString().trim().length() > 0) {
					iv_searchcanser.setVisibility(View.VISIBLE);
				} else {
					iv_searchcanser.setVisibility(View.GONE);
				}
				getKeys(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		et_search_key.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {// 修改回车键功能
					n++;
					if (n > 0 && n % 2 == 0) {
						key = et_search_key.getEditableText() == null ? ""
								: et_search_key.getEditableText().toString();
						if (key.length() > 0) {
							searchOperate();

							/* 记录当前的搜索关键字 */
							Key keyy = new Key(key);

							if (!MhyApplication.keylist.contains(keyy)) {
								MhyApplication.keylist.add(0, keyy);

							}
							if (MhyApplication.keylist.size() > 10) {
								MhyApplication.keylist.remove(10);
							}

						} else {
							Toast.makeText(SearchActivity.this, "请输入搜索关键字",
									Toast.LENGTH_SHORT).show();
						}
					}

				}
				return false;
			}
		});
	}

	/**
	 * 跳转到搜索结果页面
	 */
	private void searchOperate() {
		Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
		intent.putExtra("key", key);
		intent.putExtra("eventId", "search_value");
		startActivity(intent);
		InsertKey(key);
	}

	/**
	 * 出入关键字
	 */
	private void InsertKey(String key) {
		// 加载操作
		JSONObject params = new JSONObject();
		try {
			params.put("object", key.trim());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			key = new String(key.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlInsertKey, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
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
	 * 联动关键字
	 */
	private void getKeys(String key) {
		// 加载操作
		JSONObject params = new JSONObject();
		try {
			params.put("object", key.trim());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			key = new String(key.getBytes(), "UTF-8");// 查询关键字以utf-8格式传到后台
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlqueryComicsByName, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						keys.clear();
						try {
							JSONArray array = response.getJSONObject("object")
									.getJSONArray("data");
							for (int i = 0; i < array.length(); i++) {
								JSONObject jo = array.getJSONObject(i);
								Key key = new Key();
								key.setTitle(jo.getString("mTitle"));
								key.setTotal(jo.getString("mTotal"));
								keys.add(key);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
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

	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.btn_search_back:// 点击返回按钮
			this.finish();
			break;
		case R.id.btn_search_edit:// 点击清空本地搜索记录去按钮
			MhyApplication.keylist.clear();
			ll_search_main.removeViewAt(1);
			isGone = true;
			keys.clear();
			adapter.notifyDataSetChanged();
			break;
		case R.id.iv_searchcanser:// 点击删除，讲输入框里置空
			et_search_key.setText("");
			break;
		case R.id.btn_search_go:
			key = et_search_key.getEditableText() == null ? "" : et_search_key
					.getEditableText().toString();
			if (key.length() > 0) {
				searchOperate();

				/* 记录当前的搜索关键字 */
				Key keyy = new Key(key);

				if (!MhyApplication.keylist.contains(keyy)) {
					MhyApplication.keylist.add(0, keyy);

				}
				if (MhyApplication.keylist.size() > 10) {
					MhyApplication.keylist.remove(10);
				}
			} else {
				Toast.makeText(SearchActivity.this, "请输入搜索关键字",
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 是否已记录过统计数据
	 */
	private boolean hasRecord = false;
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);// 统计页面打开
		try {
			if(!hasRecord){
				MobSaAgent.onEvent(SearchActivity.this, "search");
				hasRecord = true;
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);// 统计页面打开
	}
}
