package com.ch.mhy.activity.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.mhy.R;
import com.ch.mhy.adapter.WonRecAdapter;
import com.ch.mhy.entity.Comics;
import com.ch.mhy.entity.WonRecEntity;
import com.ch.mhy.service.DownAPKService;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.UrlConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 精彩推荐界面
 * 
 * @author DaxstUz 2416738717 2015年8月29日
 *
 */
public class WonRecActivity extends Activity {


	private ListView lv_wonrec;
	private WonRecAdapter wradapter;
	private List<WonRecEntity> wres=new ArrayList<WonRecEntity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wonrec);
		lv_wonrec=(ListView) this.findViewById(R.id.lv_wonrec);
		wradapter=new WonRecAdapter(this, wres);
		lv_wonrec.setAdapter(wradapter);
		
		getData();
	}
	
	/**
	 * 获取网络数据
	 */
	private void getData() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				JSONObject params = new JSONObject();
				try {
					params.put("pageSize", 10);
					params.put("currentPage", "1");
					params.put("orderBy", "");
					params.put("object", "");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
						UrlConstant.UrlGetWonRec, params,
						new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								try {
									JSONArray array = response.getJSONObject("object").getJSONArray("data");
									// 热门漫画
									for (int i = 0; i < array.length(); i++) {
										JSONObject hotObject = array.getJSONObject(i);
										WonRecEntity c = new WonRecEntity();
										if(!hotObject.isNull("url")){
											c.setUrl(hotObject.getString("url"));
										}
										if(!hotObject.isNull("picUrl")){
											c.setPicUrl(hotObject.getString("picUrl"));
										}
										if(!hotObject.isNull("content")){
											c.setContent(hotObject.getString("content"));
										}
										if(!hotObject.isNull("name")){
											c.setName(hotObject.getString("name"));
										}
										wres.add(c);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
								
								wradapter.notifyDataSetChanged();
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

}
