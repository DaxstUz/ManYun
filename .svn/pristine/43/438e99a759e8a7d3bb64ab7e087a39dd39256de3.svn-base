package com.ch.mhy.activity.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.mhy.R;
import com.ch.mhy.adapter.FeedBackAdapter;
import com.ch.mhy.entity.FeedBack;
import com.ch.mhy.pulltorefresh.PullToRefreshLayout;
import com.ch.mhy.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.Utils;
import com.ch.mhy.widget.ChScrollView;
import com.ch.mhy.widget.FeedBackListView;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by DaxstUz on 2015/5/30 0030.Qq 2416738717
 */
public class HistoryShow extends Activity implements OnRefreshListener
{

	private List<FeedBack> fb1 = new ArrayList<FeedBack>();//装载已经受理的反馈对象
	private List<FeedBack> fb2 = new ArrayList<FeedBack>();//装载未受理的反馈对象

	private FeedBackListView lv_feed_accepted;
	private FeedBackListView lv_feed_noaccept;

	private FeedBackAdapter fba1;
	private FeedBackAdapter fba2;
	
	private int currentPage;
	private int totalPage;
	
	private ChScrollView chScrollView1;
	private ChScrollView chScrollView2;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if (msg.what == 1)
			{   // 到底部，加载更多数据
				Log.i("myTag", "到底部，加载更多数据");
				//getMoreChapter(1);
				onLoadMore(new PullToRefreshLayout(HistoryShow.this));
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historyshow);
		fba1 = new FeedBackAdapter(this, fb1);
		fba2 = new FeedBackAdapter(this, fb2);
		
		lv_feed_accepted = (FeedBackListView) findViewById(R.id.lv_feed_accepted);
		lv_feed_noaccept = (FeedBackListView) findViewById(R.id.lv_feed_noaccept);
		
		chScrollView1=(ChScrollView) this.findViewById(R.id.chScrollViewFeed1);
		chScrollView1.setHandler(handler);
		chScrollView2=(ChScrollView) this.findViewById(R.id.chScrollViewFeed2);
		chScrollView2.setHandler(handler);
		
		lv_feed_accepted.setAdapter(fba1);
		lv_feed_noaccept.setAdapter(fba2);

		getData();
	}

	private JSONObject params = new JSONObject();

	private void initParam(JSONObject jsonObject)
	{
		try
		{
			params.put("pageSize", Utils.PageSize);
			params.put("currentPage", currentPage++);
			params.put("orderBy", "");
			params.put("object", "");
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取反馈数据
	 */
	private void getData()
	{
		JSONObject joObject = new JSONObject();

		initParam(joObject);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlqueryFeedbackList, params,
				new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response)
					{
						Log.d("tag", "response :  " + response);
						
						try
						{
							totalPage = response.getJSONObject("object")
									.getJSONObject("pageInfo")
									.getInt("totalPageCount");
							JSONArray array = response.getJSONObject("object")
									.getJSONArray("data");

							for (int i = 0; i < array.length(); i++)
							{
								JSONObject jbObject = array.getJSONObject(i);
								FeedBack fb = new FeedBack();
								fb.setContent(jbObject.getString("content"));
								fb.setTitle(jbObject.getString("feedbackType"));
								fb.setIsaccept(jbObject.getString("isAccept"));
								fb.setReplyContent(jbObject
										.getString("replyContent"));
								fb.setCreateDate(jbObject
										.getString("createDate"));
								if ("未受理".equals(fb.getIsaccept()))
								{
									fb2.add(fb);
								} 
								else
								{
									fb1.add(fb);
								}
							}
							Log.d("tag", "加完了 ");

							fba1.notifyDataSetChanged();
							fba2.notifyDataSetChanged();
							
							set(fba1, fb1, lv_feed_accepted);
							set(fba2, fb2, lv_feed_noaccept);
						} 
						catch (JSONException e)
						{
							e.printStackTrace();
						}
					}

				}, new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error)
					{
						// Utils.endnet();
						// Utils.showMsg(ShowBooksActivity.this, "服务器异常");
					}
				})
				{
		
					/**
					 * Passing some request headers
					 * */
					@Override
					public Map<String, String> getHeaders() throws AuthFailureError
					{
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
	 * 获取更多的章节
	 * 
	 * @param n
	 *            上拉还是下拉： 0 下拉，1上拉
	 */
	private void getMoreChapter(final int n)
	{
		JSONObject joObject = new JSONObject();

		initParam(joObject);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlqueryFeedbackList, params,
				new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response)
					{
						Log.d("tag", "response :  " + response);
						
						try
						{
							totalPage = response.getJSONObject("object")
									.getJSONObject("pageInfo")
									.getInt("totalPageCount");
							
							JSONArray array = response.getJSONObject("object")
									.getJSONArray("data");

							for (int i = 0; i < array.length(); i++)
							{
								JSONObject jbObject = array.getJSONObject(i);
								FeedBack fb = new FeedBack();
								fb.setContent(jbObject.getString("content"));
								fb.setTitle(jbObject.getString("feedbackType"));
								fb.setIsaccept(jbObject.getString("isAccept"));
								fb.setReplyContent(jbObject
										.getString("replyContent"));
								fb.setCreateDate(jbObject
										.getString("createDate"));
								if ("未受理".equals(fb.getIsaccept()))
								{
									fb2.add(fb);
								} 
								else
								{
									fb1.add(fb);
								}
							}
							Log.d("tag", "加完了 ");

							fba1.notifyDataSetChanged();
							fba2.notifyDataSetChanged();
							
							set(fba1, fb1, lv_feed_accepted);
							set(fba2, fb2, lv_feed_noaccept);
						} 
						catch (JSONException e)
						{
							e.printStackTrace();
						}
					}

				}, new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error)
					{
						// Utils.endnet();
						// Utils.showMsg(ShowBooksActivity.this, "服务器异常");
					}
				})
				{
		
					/**
					 * Passing some request headers
					 * */
					@Override
					public Map<String, String> getHeaders() throws AuthFailureError
					{
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
	
	//重新计算高度
	public void set(FeedBackAdapter feedadapter,List<FeedBack> fb,ListView lv_feed)
	{
		if (fb.size() > 0)
		{// 由于ScrollView嵌套了ListView，所以要重新计算高度，ListView才会显示
			int totalHeight = 0;
			int rows = feedadapter.getCount() / 2;
			if (feedadapter.getCount() % 2 > 0)
			{
				rows += 1;
			}
			for (int i = 0; i < rows; i++)
			{
				View listItem = feedadapter.getView(i,
						null, lv_feed);
				listItem.measure(0, 0);
				totalHeight += listItem
						.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params = lv_feed
					.getLayoutParams();
			params.height = totalHeight
					+ (lv_feed.getHeight() * (rows - 1));
			lv_feed.setLayoutParams(params);
		}
		
		chScrollView1.setIsQry(false);
		chScrollView2.setIsQry(false); // 释放查询锁
	}

	public void onclick(View view)
	{
		switch (view.getId())
		{
		case R.id.btn_return_back:
			this.finish();
			break;
		default:
			break;
		}
	}

	public void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
	{
		
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout)
	{
        // 加载操作
		if (currentPage <= totalPage)
		{
			updateData(pullToRefreshLayout, 1);
		}
		else
		{
			//pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NoMore);
		}
	}

	public void updateData(final PullToRefreshLayout pullToRefreshLayout, final int n)
	{
		if (n == 0)
		{
			currentPage = 1;
		}
		if(n==1)
		{
			getData();
		}
	}
}