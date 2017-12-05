package com.ch.mhy.activity.book;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.comm.resquest.AbsResponseData;
import com.ch.comm.saevent.MobSaAgent;
import com.ch.mhy.R;
import com.ch.mhy.activity.cate.ShowBooksActivity;
import com.ch.mhy.activity.comment.CommentActivity;
import com.ch.mhy.activity.comment.CommentDataUtil;
import com.ch.mhy.adapter.BookSelectAdapter;
import com.ch.mhy.db.DBManager;
import com.ch.mhy.db.DBUtil;
import com.ch.mhy.entity.AuthorType;
import com.ch.mhy.entity.Comics;
import com.ch.mhy.entity.ComicsDetail;
import com.ch.mhy.entity.Down;
import com.ch.mhy.net.NetReceiver;
import com.ch.mhy.net.NetReceiver.NetState;
import com.ch.mhy.pulltorefresh.PullToRefreshLayout;
import com.ch.mhy.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.ch.mhy.util.DateUtil;
import com.ch.mhy.util.FileUtil;
import com.ch.mhy.util.ListUtil;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.SDUtil;
import com.ch.mhy.util.TelPhoneInfo;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.Utils;
import com.ch.mhy.widget.ChGridView;
import com.ch.mhy.widget.ChScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

/**
 * 漫画详情
 *
 * @author DaxstUz 2416738717 2015年5月4日
 */
public class ShowDetailActivity extends Activity implements
		NetReceiver.NetEventHandle, OnRefreshListener,UpdateMsg {

	public static ShowDetailActivity activity;
	
	/* 详情页上半部控件 */
	private ImageView iv_detail_book_img; // 详情
	private ImageView iv_uord; // 封面
	private TextView tv_showdetail_author; // 作者
	private TextView tv_showdetail_update; // 更新到
	private TextView tv_showdetail_content;// 简介
	private TextView tv_showdetail_cate; // 分类

	private TextView tv_addcollect; // 收藏
	private TextView tv_update; // 收藏
	private boolean isadd = false; // 判断是否被收藏过
	private TextView tv_bookname; // 书名

	// 章节数据源
	private List<ComicsDetail> cds = new ArrayList<ComicsDetail>();
	// 这本书的第一个章节
	private ComicsDetail cdpr = new ComicsDetail();
	// 章节显示控件
	private ChGridView gv_book_select;
	// 章节显示控件适配器
	private BookSelectAdapter adapter;
	// 漫画显示的实体类
	private Comics av;
	// 记录请求后台时，请求第几页，默认为第一页
	private int currentpage = 1;
	// 总共有多少页
	private int totalPage;
	// 排序规则
	private String order = "desc";
	// 保存数据到本地数据库管理器
	private DBManager manager;
	// 正序逆序切换按钮
	private Button btn_book_readchange;
	// 评分控件
	private RatingBar ratingBar1;
	// 开始阅读按钮
	private Button btn_reading;
	// 添加wifi提醒
	Toast toast;
	// 章节显示
	private ChScrollView chScrollView;

	/* 利用handler刷新数据 */
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {// 到底部，加载更多数据
				if (NetReceiver.isConnected(ShowDetailActivity.this) != NetState.NET_NO) {
					getMoreChapter(1);
				}
			}
		}

	};

	/**
	 * 获取openid
	 */
	private String openid;

	/**
	 * 用于点击作者跳转到作者列表
	 */
	private int authorId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showdetail);
		
		activity = this;
		Utils.um=this;

		SharedPreferences sp = this.getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		openid = sp.getString("openid", "");

		gv_book_select = (ChGridView) findViewById(R.id.gv_book_select);
		gv_book_select.setmVerticalSpacing(10);// 设置gridview的行间距
		tv_bookname = (TextView) findViewById(R.id.tv_bookname);
		iv_uord = (ImageView) findViewById(R.id.iv_uord);
		ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
		ratingBar1.setIsIndicator(true);
		btn_reading = (Button) findViewById(R.id.btn_reading);
		// 获取传过来的漫画信息
		av = (Comics) getIntent().getSerializableExtra("manhua");
		// 设置标题
		String name = av.getmTitle().length() > 6 ? av.getmTitle().substring(0,
				6) : av.getmTitle();
		tv_bookname.setText(name);
		name = null;

		initView();
		getData();
		
	}
	
	/**
	 * 获取漫画评论数
	 */
	private void initCommentCnt() {
		String bigbookId = cdpr.getBigbookId();
		// 查询漫画评论总数
		CommentDataUtil.loadCommentCnt(this, bigbookId, new AbsResponseData(null) {
			@Override
			public void dataBusi(Object data) {
				if(data != null){
					TextView comment_cnt = (TextView) ShowDetailActivity.this.findViewById(R.id.comment_cnt);
					try {
						JSONObject jo = new JSONObject(data.toString());
						String val = jo.get("object").toString();
						if(!"".equals(val) && !"0".equals(val)){
							if(Integer.valueOf(val)>999){
								val = "999+";
							}
							comment_cnt.setText("评论(" + val + ")");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

		});
	}
	
	/**
	 * 回调设置评论数
	 */
	public void setCommentCnt(int cnt){
		TextView comment_cnt = (TextView) this.findViewById(R.id.comment_cnt);
		String ccCnt = (String)comment_cnt.getText();
		if(ccCnt.indexOf("999+") == -1){
			ccCnt = ccCnt.replaceAll("[^0-9]", "");
			if("999".equals(ccCnt)){
				comment_cnt.setText("评论(999+)");
			}else{
				if(ccCnt==null || "".equals(ccCnt)){
					ccCnt = "0";
				}
				comment_cnt.setText("评论("+(Integer.valueOf(ccCnt)+cnt+")"));
			}
		}
	}
	
	/**
	 * 初始化控件
	 */
	private void initView() {
		btn_book_readchange = (Button) findViewById(R.id.btn_book_readchange);
		tv_addcollect = (TextView) findViewById(R.id.tv_addcollect);
		tv_update = (TextView) findViewById(R.id.tv_update);

		// 异步展示封面图片
		iv_detail_book_img = (ImageView) findViewById(R.id.iv_detail_book_img);
		ImageLoader.getInstance().displayImage(av.getmPic(),
				iv_detail_book_img, Utils.options);

		/* 设置作者的值，并对作者显示的长度进行处理 */
		tv_showdetail_author = (TextView) findViewById(R.id.tv_showdetail_author);
		String author = av.getmDirector();
		tv_showdetail_author.setText("作者：" + author);

		/* 设置更新 */
		tv_showdetail_update = (TextView) findViewById(R.id.tv_showdetail_update);
		String lianzai = av.getUpdateMessage();
		if (lianzai != null) {
			lianzai = lianzai.replaceAll("null", "");
		} else {
			lianzai = "";
		}
		tv_showdetail_update.setText("更新到：" + lianzai);

		/* 设置分类 */
		tv_showdetail_cate = (TextView) findViewById(R.id.tv_showdetail_cate);
		tv_showdetail_content = (TextView) findViewById(R.id.tv_showdetail_content);
		tv_showdetail_cate.setText("分类：" + Utils.getCate(av.getmType1()));

		/* 设置内容简介，并对显示的字数进行处理 */
		String content;
		if (av.getmContent() != null) {
			content = av.getmContent().length() > 45 ? av.getmContent()
					.substring(0, 44) + "..." : av.getmContent();
			tv_showdetail_content.setText("         " + content);
			if (av.getmContent().length() < 45) {
				iv_uord.setVisibility(View.GONE);
			}
		}
		content = null;// 及时消化对象

		adapter = new BookSelectAdapter(this, cds);
		gv_book_select.setAdapter(adapter);
		gv_book_select.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int n = (int) id;

				if (cds.get(n).getmDirector() != null) {
					Intent intent = new Intent(ShowDetailActivity.this,
							ReadingActivity.class);
					intent.putExtra("mh", cds.get(n));

					List<Down> cddd = null;
					DBManager manager = new DBManager(ShowDetailActivity.this,
							DBUtil.ReadName, null, DBUtil.Code);
					cddd = manager.queryDown(
							"SELECT * FROM down where mId=? and mNo=?",
							new String[] { cds.get(n).getmId().toString(),
									cds.get(n).getmNo() + "" });

					if (cddd.size() > 0 && cddd.get(0).getIsdonw().equals(0)) {
						intent.putExtra("comefrom", "0");
						ShowDetailActivity.this.startActivity(intent);
						btn_reading.setText("续看 " + (cds.get(n).getmName()));
					} else {
						intent.putExtra("comefrom", "1");
						if(NetReceiver.isConnected(ShowDetailActivity.this) != NetState.NET_NO){
							ShowDetailActivity.this.startActivity(intent);
							btn_reading.setText("续看 " + (cds.get(n).getmName()));
						}else{
							Toast.makeText(ShowDetailActivity.this, "没有可看的资源！", Toast.LENGTH_SHORT).show();
						}
					}
				}

			}
		});
		chScrollView = (ChScrollView) this.findViewById(R.id.chScrollView);
		chScrollView.setHandler(handler);
	}

	JSONObject param = new JSONObject();

	/**
	 * 拿到数据
	 */
	private void getData() {

		if (NetReceiver.isConnected(ShowDetailActivity.this) == NetState.NET_NO) {
			/**
			 * 本地路径
			 */
			String dirpath = SDUtil.getSecondExterPath() + "/manyun/"
					+ av.getmTitle() + "/temp.txt";
			String content = null;
			try {
				content = FileUtil.getStringFormFile(dirpath);
			} catch (Exception e1) {
				
			}
			try {
				/*
				 * 判断本地数据有没有，没有就继续取网络数据
				 */
				if (content != null) {
					JSONArray ja = new JSONArray(content);
					getListInfo(ja);
					cdpr=cds.get(cds.size()-1);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			initParam();
			JsonObjectRequest joObjectRequest = new JsonObjectRequest(
					Method.POST, UrlConstant.UrlComicsDetailPage, param,
					new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							try {
								totalPage = response.getJSONObject("object")
										.getJSONObject("page")
										.getJSONObject("pageInfo")
										.getInt("totalPageCount");
								if (response.getJSONObject("object")
										.getString("object").length() > 4) {
									JSONObject jj = response.getJSONObject(
											"object").getJSONObject("object");
									authorId = jj.getInt("authorId");
									cdpr.setmId(jj.getInt("mId"));
									cdpr.setmQid(jj.getInt("mQid"));
									cdpr.setmUrl(jj.getString("mUrl"));
									cdpr.setmName(jj.getString("mName"));
									cdpr.setGradescore(jj
											.getDouble("gradeScore"));
									cdpr.setmType1(av.getmType1());
									cdpr.setmFenAll(av.getmFenAll());
									cdpr.setmContent(av.getmContent());
									ratingBar1.setMax(50);
									ratingBar1.setProgress((int) (cdpr
											.getGradescore() * 10));
									cdpr.setmTitle(av.getmTitle());
									cdpr.setmDirector(av.getmDirector());
									cdpr.setmPic(av.getmPic());
									cdpr.setmTotal(av.getmTotal());
									cdpr.setmNo(jj.getInt("mNo"));
									cdpr.setmLianzai(av.getmLianzai());
									cdpr.setUpdateMessage(av.getUpdateMessage());
									cdpr.setMaxNo(jj.getInt("maxNo"));
									cdpr.setMinNo(jj.getInt("minNo"));
									cdpr.setCreateTime(DateUtil.Date2String(new Date(
											Long.parseLong(jj
													.getString("mDate")))));
									cdpr.setSysDate(jj.getString("sysDate"));
									cdpr.setLocalUrl(jj.getString("localUrl"));
									cdpr.setPort(jj.getString("port"));
									if (!jj.isNull("prevNo")) {
										cdpr.setPmNo(jj.getInt("prevNo"));
									}
									if (!jj.isNull("nextNo")) {
										cdpr.setNmNo(jj.getInt("nextNo"));
									}
									
									if(jj.has("bigbookId")){
										cdpr.setBigbookId(jj.getString("bigbookId"));
									}
									
									manager = new DBManager(
											ShowDetailActivity.this,
											DBUtil.ReadName, null, DBUtil.Code);
									ComicsDetail cdd = manager.queryByMid(cdpr
											.getmId());
									manager.closeDB();

									if (cdd != null) {
										btn_reading.setText("续看"
												+ cdd.getmName());
									}

									// 判断是否被收藏
									manager = new DBManager(
											ShowDetailActivity.this,
											DBUtil.CollectName, null,
											DBUtil.Code);
									isadd = manager.isAdd(cdpr.getmId());
									manager.closeDB();
									if (isadd) {
										tv_addcollect
												.setBackgroundResource(R.drawable.ch_book_collected);
									}

									cds.clear();
									JSONArray array = response
											.getJSONObject("object")
											.getJSONObject("page")
											.getJSONArray("data");
									getListInfo(array);
									
									initCommentCnt();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub
						}
					}) {
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					// TODO Auto-generated method stub
					Map<String, String> map = new HashMap<String, String>();
					map.put("Content-Type", "application/json; charset=utf-8");
					return map;
				}
			};
			/* 设置请求策略 */
			joObjectRequest.setRetryPolicy(new DefaultRetryPolicy(6000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			NetUtil.rqueue.add(joObjectRequest);// 开始请求网络
		}
	}

	public void getListInfo(JSONArray array) throws JSONException {
		for (int i = 0; i < array.length(); i++) {
			JSONObject joObject = array.getJSONObject(i);
			ComicsDetail cd = new ComicsDetail();
			cd.setmId(joObject.getInt("mId"));
			cd.setmQid(joObject.getInt("mQid"));
			cd.setmUrl(joObject.getString("mUrl"));
			cd.setmName(joObject.getString("mName"));
			cd.setmContent(av.getmContent());
			cd.setMaxNo(cdpr.getMaxNo());
			cd.setMinNo(cdpr.getMinNo());
			cd.setmTitle(av.getmTitle());
			cd.setmDirector(av.getmDirector());
			cd.setmPic(av.getmPic());
			cd.setmType1(av.getmType1());
			cd.setmFenAll(av.getmFenAll());
			cd.setmTotal(av.getmTotal());
			cd.setmNo(joObject.getInt("mNo"));
			cd.setmLianzai(av.getmLianzai());
			cd.setUpdateMessage(av.getUpdateMessage());
			cd.setTotalPage(joObject.getInt("totalpage"));
			cd.setCreateTime(DateUtil.Date2String(new Date(Long
					.parseLong(joObject.getString("mDate")))));
			cd.setSysDate(joObject.getString("sysDate"));
			cd.setLocalUrl(joObject.getString("localUrl"));
			cd.setPort(joObject.getString("port"));

			if (!joObject.isNull("prevNo")) {
				cd.setPmNo(joObject.getInt("prevNo"));
			}
			if (!joObject.isNull("nextNo")) {
				cd.setNmNo(joObject.getInt("nextNo"));
			}

			cds.add(cd);
		}

		
		adapter.notifyDataSetChanged();

		if (array.length() > 0) {// 由于ScrollView嵌套了ListView，所以要重新计算高度，ListView才会显示
			int totalHeight = 0;
			int rows = adapter.getCount() / 2;
			if (adapter.getCount() % 2 > 0) {
				rows += 1;
			}
			for (int i = 0; i < rows; i++) {
				View listItem = adapter.getView(i,
						null, gv_book_select);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight()+ 
						gv_book_select.getmVerticalSpacing();
			}
			
			ViewGroup.LayoutParams params = gv_book_select.getLayoutParams();
			params.height = totalHeight;
			gv_book_select.setLayoutParams(params);
		}

		chScrollView.setIsQry(false); // 释放查询锁
		
		
	}

	/**
	 * 获取更多的章节
	 * 
	 * @param n
	 *            上拉还是下拉： 0 下拉，1上拉
	 */
	private void getMoreChapter(final int n) {
		if (n == 0) {
			currentpage = 1;
		}
		initParam();

		JsonObjectRequest joObjectRequest = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlComicsDetailPage, param,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							if (n == 0) {// 下拉刷新
								cds.clear();
							}
							JSONArray array = response.getJSONObject("object")
									.getJSONObject("page").getJSONArray("data");

							for (int i = 0; i < array.length(); i++) {
								JSONObject joObject = array.getJSONObject(i);
								ComicsDetail cd = new ComicsDetail();
								cd.setmId(joObject.getInt("mId"));
								cd.setmQid(joObject.getInt("mQid"));
								cd.setmUrl(joObject.getString("mUrl"));
								cd.setmName(joObject.getString("mName"));
								cd.setmTitle(av.getmTitle());
								cd.setmDirector(av.getmDirector());
								cd.setmPic(av.getmPic());
								cd.setmTotal(av.getmTotal());
								cd.setmNo(joObject.getInt("mNo"));
								cd.setTotalPage(joObject.getInt("totalpage"));
								cd.setPartSize(joObject.getString("partsize"));
								cd.setLocalUrl(joObject.getString("localUrl"));
								cd.setPort(joObject.getString("port"));
								cd.setCreateTime(DateUtil.Date2String(new Date(
										Long.parseLong(joObject
												.getString("mDate")))));
								cd.setSysDate(joObject.getString("sysDate"));
								cd.setBigbookId(joObject.getString("bigbookId"));
								cds.add(cd);
							}
							adapter.notifyDataSetChanged();

							if (array.length() > 0) {// 当有返回数据时，重新计算ListView的高度
								int totalHeight = 0;
								// 计算行数
								int rows = adapter.getCount() / 2; // 总行数
								if (adapter.getCount() % 2 > 0) {
									rows += 1;
								}
								// 计算高度
								for (int i = 0; i < rows; i++) {
									View listItem = adapter.getView(i, null,
											gv_book_select);
									listItem.measure(0, 0);
									totalHeight += listItem.getMeasuredHeight()
											+ gv_book_select.getmVerticalSpacing();
								}
								// 设置高度
								ViewGroup.LayoutParams params = gv_book_select
										.getLayoutParams();
								params.height = totalHeight;
								gv_book_select.setLayoutParams(params);
							}

							chScrollView.setIsQry(false); // 释放查询锁
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						if (n == 1) {
						} else {
						}
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-Type", "application/json; charset=utf-8");
				return map;
			}
		};

		joObjectRequest.setRetryPolicy(new DefaultRetryPolicy(6000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		NetUtil.rqueue.add(joObjectRequest);
	}

	private boolean isjj = true;// 标记是否是展开状态

	/*催更*/
	private long upTime=0;
	private int clicks;//点击次数
	
	public synchronized void onclick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.tv_showdetail_content:// 点击内容简介区域，实现简介的展开与收缩
			if (av.getmContent() != null) {
				if (isjj) {
					isjj = false;
					tv_showdetail_content.setText("         "
							+ av.getmContent());
					iv_uord.setBackgroundResource(R.drawable.up);
				} else {
					isjj = true;
					String content = av.getmContent().length() > 45 ? av
							.getmContent().substring(0, 44) + "..." : av
							.getmContent();
					tv_showdetail_content.setText("         " + content);
					iv_uord.setBackgroundResource(R.drawable.down);
				}

			}
			break;
		case R.id.ll_book_addcollect: // 点击收藏按钮
			if (cdpr.getmDirector() != null) {
				if (!isadd) {
					isadd = true;
					manager = new DBManager(ShowDetailActivity.this,
							DBUtil.CollectName, null, DBUtil.Code);
					manager.addOrUpdateComicDetail(cdpr);
					manager.closeDB();
					tv_addcollect
							.setBackgroundResource(R.drawable.ch_book_collected);

					if (openid.length() > 0) {
						addToServer();
					}

					// 友盟统计
					String eventId = "my_book_store";
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("type", eventId);
					map.put("bookName", cdpr.getmTitle());
					MobclickAgent.onEventValue(this, eventId, map, 1);
					try {
						MobSaAgent.onEventValue(this, eventId, map, 1);
					} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					isadd = false;
					manager = new DBManager(ShowDetailActivity.this,
							DBUtil.CollectName, null, DBUtil.Code);
					manager.delete(cdpr);
					manager.closeDB();
					if (openid.length() > 0) {
						delToServer();
					}
					tv_addcollect
							.setBackgroundResource(R.drawable.ch_book_addcollect);
				}

			} else {
				long exitTime = 0;
				/* 设置不能一直点击弹出消息框 */
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					exitTime = System.currentTimeMillis();
				} else {
					Toast.makeText(ShowDetailActivity.this, "收藏失败！",
							Toast.LENGTH_SHORT).show();

				}
			}

			if (Utils.updateCollectInfo != null) {
				Utils.updateCollectInfo.updateCollects();
			}

			break;
		case R.id.btn_book_down:// 点击下载按钮
			intent = new Intent(ShowDetailActivity.this,
					DownSelectActivity.class);
			intent.putExtra("mh", av);
			ShowDetailActivity.this.startActivity(intent);
			break;
		case R.id.ll_comic_update:// 点击催更
			clicks++;
			/* 设置不能一直点击弹出消息框 */
			if ((System.currentTimeMillis() - upTime) > 2000) {
				upTime = System.currentTimeMillis();
				if(clicks<5){
					setUpdateBg();
				}else{
					Toast.makeText(ShowDetailActivity.this, "再戳要坏掉啦~::>_<::", Toast.LENGTH_SHORT).show();
				}
			} 
			
			break;
		case R.id.ll_author:// 点击作者区域
			intent = new Intent(ShowDetailActivity.this,
					ShowBooksActivity.class);
			intent.putExtra("cate", 3);
			AuthorType at = new AuthorType();
			at.setTypeId(authorId);
			at.setTypeName(cdpr.getmDirector());
			at.setTypePic(cdpr.getmPic());
			intent.putExtra("author", at);
			ShowDetailActivity.this.startActivity(intent);

			break;
		case R.id.btn_show_back:// 点击返回按钮
			ShowDetailActivity.this.finish();
			break;
		case R.id.btn_book_readchange: // 正序/逆序切换
			currentpage = 1;
			if ("desc".equals(order)) {
				order = "asc";
				btn_book_readchange
						.setBackgroundResource(R.drawable.ch_books_list);
				ListUtil.doAscSort(cds);
			} else {
				order = "desc";
				btn_book_readchange
						.setBackgroundResource(R.drawable.ch_books_listf);
				ListUtil.doDescSort(cds);
			}
			ViewGroup.LayoutParams params = gv_book_select.getLayoutParams();
			params.height = LayoutParams.WRAP_CONTENT;
			gv_book_select.setLayoutParams(params);
			if(NetReceiver.isConnected(ShowDetailActivity.this) != NetState.NET_NO){
				getData();
			}else{
				adapter.notifyDataSetChanged();
					int totalHeight = 0;
					int rows = adapter.getCount() / 2;
					if (adapter.getCount() % 2 > 0) {
						rows += 1;
					}
					for (int i = 0; i < rows; i++) {
						View listItem = adapter.getView(i,
								null, gv_book_select);
						listItem.measure(0, 0);
						totalHeight += listItem.getMeasuredHeight()+ 
								gv_book_select.getmVerticalSpacing();
					}
					
					ViewGroup.LayoutParams params2 = gv_book_select.getLayoutParams();
					params2.height = totalHeight;
					gv_book_select.setLayoutParams(params2);
			}
			break;
		case R.id.btn_reading:// 点击开始阅读
			/* 阅读之前先把这个章节保存到本地数据库，不然在最近里看不到阅读记录 */
			if (cds.size()>0) {
				manager = new DBManager(ShowDetailActivity.this,
						DBUtil.ReadName, null, DBUtil.Code);
				// 先查询下本地有没有，如果有的话就是续看功能
				ComicsDetail cdd = manager.queryByMid(cdpr.getmId());

				if (cdd != null) {
					cdd.setmDirector(cdpr.getmDirector());
					cdpr.setmName(cdd.getmName());
					if (cdd.getmUrl() != null) {
						cdpr = cdd;
					}
				}

				intent = new Intent(ShowDetailActivity.this,
						ReadingActivity.class);
				intent.putExtra("mh", cdpr);

				/* 判断本地是否有下载好资源 */

				List list = manager.queryDown(
						"select * from down  where mQid =?",
						new String[] { cdpr.getmQid() + "" });
				if (list.size() > 0&&urlIsexit()) {
					intent.putExtra("comefrom", "0");
					ShowDetailActivity.this.startActivity(intent);
					btn_reading.setText("续看" + (cdpr.getmName()));
				} else {
					intent.putExtra("comefrom", "1");
					if(NetReceiver.isConnected(ShowDetailActivity.this) != NetState.NET_NO){
						ShowDetailActivity.this.startActivity(intent);
						btn_reading.setText("续看" + (cdpr.getmName()));
					}else{
						Toast.makeText(ShowDetailActivity.this, "没有可看的资源", Toast.LENGTH_SHORT).show();
					}
				}

				manager.closeDB();
			} else {
				Toast.makeText(ShowDetailActivity.this, "本次请求失败，稍后再试！",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.ll_book_pinglun:// 点击进入评论界面
			String bigbookId = cdpr.getBigbookId();
			if(bigbookId == null || "".equals(bigbookId)){
				Toast.makeText(ShowDetailActivity.this, "亲，数据加载中，请稍候再点~_-",
						Toast.LENGTH_SHORT).show();
				return ;
			}
			intent = new Intent(ShowDetailActivity.this, CommentActivity.class);
			intent.putExtra("bigbookId", cdpr.getBigbookId());
			intent.putExtra("bookName", cdpr.getmTitle());
			intent.putExtra("faceUrl", cdpr.getmPic());
			ShowDetailActivity.this.startActivity(intent);
			break;
		default:
			break;
		}
	}

	/**
	 * 将阅读的漫画数据进行处理
	 */
	private void addToServer() {
		// TODO Auto-generated method stub
		/**
		 * 需要插入的记录
		 */
		Map<String, String> obj = new HashMap<String, String>();
		obj.put("comicId", cdpr.getmId() + "");
		obj.put("userId", openid);
		// obj.put("chapterId", mh.getmQid() + "");
		// obj.put("pageNum", ch_read_view.mImageIndex + "");
		JSONObject jobObject = new JSONObject();
		try {
			jobObject.put("object", new JSONObject(obj));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonObjectRequest jsonrRequest = new JsonObjectRequest(Method.POST,
				UrlConstant.addCollectToServer, jobObject,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("Content-Type", "application/json; charset=utf-8");
				return map1;
			}
		};

		jsonrRequest.setRetryPolicy(new DefaultRetryPolicy(6000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		NetUtil.rqueue.add(jsonrRequest);
	}

	/**
	 * 将阅读的漫画数据进行处理
	 */
	private void delToServer() {
		// TODO Auto-generated method stub
		/**
		 * 需要插入的记录
		 */
		Map<String, String> obj = new HashMap<String, String>();
		obj.put("comicId", cdpr.getmId() + "");
		obj.put("userId", openid);
		JSONObject jobObject = new JSONObject();
		try {
			jobObject.put("object", new JSONObject(obj));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonObjectRequest jsonrRequest = new JsonObjectRequest(Method.POST,
				UrlConstant.delCollectToServer, jobObject,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					}
				}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("Content-Type", "application/json; charset=utf-8");
				return map1;
			}
		};

		jsonrRequest.setRetryPolicy(new DefaultRetryPolicy(6000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		NetUtil.rqueue.add(jsonrRequest);
	}

	// 不重复显示Toast的方法
	private void showTextToast(String msg) {
		if (toast == null) {
			toast = Toast.makeText(getApplicationContext(), msg, 500);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	@Override
	protected void onPause() {
		super.onPause();
		NetReceiver.ehList.remove(this);
		MobclickAgent.onPause(this);
	}

	@Override
	public void netState(NetReceiver.NetState netCode) {
		switch (netCode) {
		case NET_NO:
			Toast.makeText(ShowDetailActivity.this, "没有网络连接",
					Toast.LENGTH_SHORT).show();
			break;
		case NET_2G:
			Toast.makeText(ShowDetailActivity.this, "当前是2g网络",
					Toast.LENGTH_SHORT).show();
			break;
		case NET_3G:
			Toast.makeText(ShowDetailActivity.this, "当前是3g网络",
					Toast.LENGTH_SHORT).show();
			break;
		case NET_4G:
			Toast.makeText(ShowDetailActivity.this, "当前是4g网络",
					Toast.LENGTH_SHORT).show();
			break;
		case NET_WIFI:
			Toast.makeText(ShowDetailActivity.this, "当前是WIFI网络",
					Toast.LENGTH_SHORT).show();
			break;
		case NET_UNKNOWN:
			Toast.makeText(ShowDetailActivity.this, "未知网络", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			Toast.makeText(ShowDetailActivity.this, "不知道什么情况~>_<~",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		updateData(pullToRefreshLayout, 0);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// // 加载操作
		if (currentpage <= totalPage) {
			updateData(pullToRefreshLayout, 1);
		} else {
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NoMore);
		}
	}

	void updateData(final PullToRefreshLayout pullToRefreshLayout, final int n) {
		if (n == 0) {
			currentpage = 1;
		}
		initParam();

		JsonObjectRequest joObjectRequest = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlComicsDetailPage, param,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						try {

							if (n == 0) {
								cds.clear();
							}
							JSONArray array = response.getJSONObject("object")
									.getJSONObject("page").getJSONArray("data");

							for (int i = 0; i < array.length(); i++) {
								JSONObject joObject = array.getJSONObject(i);
								ComicsDetail cd = new ComicsDetail();
								cd.setmId(joObject.getInt("mId"));
								cd.setmQid(joObject.getInt("mQid"));
								cd.setmUrl(joObject.getString("mUrl"));
								cd.setmName(joObject.getString("mName"));
								cd.setmTitle(av.getmTitle());
								cd.setmDirector(av.getmDirector());
								cd.setmPic(av.getmPic());
								cd.setmTotal(av.getmTotal());
								cd.setmNo(joObject.getInt("mNo"));
								cd.setTotalPage(joObject.getInt("totalpage"));
								cd.setPartSize(joObject.getString("partsize"));
								cd.setLocalUrl(joObject.getString("localUrl"));
								cd.setCreateTime(DateUtil.Date2String(new Date(
										Long.parseLong(joObject
												.getString("mDate")))));
								cd.setSysDate(joObject.getString("sysDate"));
								cds.add(cd);
							}

							if (n == 1) {
								pullToRefreshLayout
										.loadmoreFinish(PullToRefreshLayout.SUCCEED);
							} else {
								pullToRefreshLayout
										.refreshFinish(PullToRefreshLayout.SUCCEED);
							}
							adapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						if (n == 1) {
							pullToRefreshLayout
									.loadmoreFinish(PullToRefreshLayout.FAIL);
						} else {
							pullToRefreshLayout
									.refreshFinish(PullToRefreshLayout.FAIL);
						}
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-Type", "application/json; charset=utf-8");
				return map;
			}
		};

		joObjectRequest.setRetryPolicy(new DefaultRetryPolicy(6000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		NetUtil.rqueue.add(joObjectRequest);
	}

	/**
	 * 初始化请求的参数
	 */
	private void initParam() {
		try {
			param.put("pageSize", Utils.PageSize);
			param.put("currentPage", currentpage++);
			param.put("orderBy", "");
			param.put("object", order + "@@" + av.getmQid());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 是否已记录过统计数据
	 */
	private boolean hasRecord = false;
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		String eventId = this.getIntent().getStringExtra("eventId");
		if (eventId != null && !hasRecord) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", eventId);
			map.put("bookName", "" + av.getmTitle());
			if ("my_book_click".equals(eventId)) {
				MobclickAgent.onEventValue(this, eventId, map, 1);

				try {
					MobSaAgent.onEventValue(this, eventId, map, 1);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					MobSaAgent.onEvent(this, eventId, map);
					MobSaAgent.onEventValue(this, "my_book_click", map, 1);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			hasRecord = true;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void update(String mtitle) {
		// TODO Auto-generated method stub
		btn_reading.setText("续看 " + mtitle);
	}
	
	/**
	 * 判断本地文件是否存在
	 * @return
	 */
	private boolean urlIsexit(){
				String sdparth = SDUtil.getSecondExterPath()
				+ "/manyun/";
		sdparth = sdparth + cdpr.getmTitle() 
				+ "/" + cdpr.getmName();
		File tempFile=new File(sdparth);
		if(tempFile.exists()){
			return true;
		}else{
			return false;
		}
 	}

	/**
	 * 更新催更图标
	 */
	private void setUpdateBg(){
		int n=(int) (Math.random()*8);
		switch (n) {
		case 0:
			tv_update.setBackgroundResource(R.drawable.ch_book_updatered);
			Toast.makeText(ShowDetailActivity.this, "主人:已收到指令!`(*∩_∩*)′", Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(ShowDetailActivity.this, "小编正快马加鞭帮你更新哟~(☆_☆)/~~", Toast.LENGTH_SHORT).show();
			tv_update.setBackgroundResource(R.drawable.ch_book_updateblue);
			break;
		case 2:
			Toast.makeText(ShowDetailActivity.this, "淫家知道了啦!!(～ o ～)~zZ", Toast.LENGTH_SHORT).show();
			tv_update.setBackgroundResource(R.drawable.ch_book_updateyellow);
			break;
		case 3:
			Toast.makeText(ShowDetailActivity.this, "主人:已收到指令!`(*∩_∩*)′", Toast.LENGTH_SHORT).show();
			tv_update.setBackgroundResource(R.drawable.ch_book_updateorange);
			break;
		case 4:
			Toast.makeText(ShowDetailActivity.this, "小编正快马加鞭帮你更新哟~(☆_☆)/~~", Toast.LENGTH_SHORT).show();
			tv_update.setBackgroundResource(R.drawable.ch_book_updatewhite);
			break;
		case 5:
			Toast.makeText(ShowDetailActivity.this, "淫家知道了啦!!(～ o ～)~zZ", Toast.LENGTH_SHORT).show();
			tv_update.setBackgroundResource(R.drawable.ch_book_updateblack);
			break;
		case 6:
			Toast.makeText(ShowDetailActivity.this, "主人:已收到指令!`(*∩_∩*)′", Toast.LENGTH_SHORT).show();
			tv_update.setBackgroundResource(R.drawable.ch_book_updategreen);
			break;
		default:
			break;
		}
		
		Map<String, String> map=new HashMap<String, String>();
		map.put("bigbookId", cdpr.getBigbookId());
		map.put("mtitle", cdpr.getmTitle());
		map.put("mdirector", cdpr.getmDirector());
		map.put("deviceId", TelPhoneInfo.getDeviceId());
		JSONObject jo=new JSONObject(map);
		JSONObject param=new JSONObject();
		
		try {
			param.put("object",jo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		CommentDataUtil.getTopicList(ShowDetailActivity.this,UrlConstant.UrlChuiGen,param,null);
	}
}
