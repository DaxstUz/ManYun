package com.ch.mhy.activity.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.mhy.R;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.Utils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by DaxstUz on 2015/5/29 0029.Qq 2416738717
 */
public class PersonReturn extends Activity
{

	private EditText et_link;
	private EditText et_content;

	private RadioButton radio0;
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;

	private RadioButton man_error;
	private RadioButton man_tui;
	private RadioButton man_update;
	private RadioButton man_other;

	private String contactType;
	private String feedbackType;

	private String content;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personreturn);
		et_link = (EditText) findViewById(R.id.et_link);
		et_content = (EditText) findViewById(R.id.et_content);

		radio0 = (RadioButton) findViewById(R.id.radio0);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio2 = (RadioButton) findViewById(R.id.radio2);
		radio3 = (RadioButton) findViewById(R.id.radio3);
		man_error = (RadioButton) findViewById(R.id.man_error);
		man_tui = (RadioButton) findViewById(R.id.man_tui);
		man_update = (RadioButton) findViewById(R.id.man_update);
		man_other = (RadioButton) findViewById(R.id.man_other);

	}
	
	public void onclick(View view)
	{
		switch (view.getId())
		{
		case R.id.btn_return_back:
			this.finish();
			break;

		case R.id.btn_commit:
			getfeedbackType();
			getContactType();

			content = et_content.getText() == null ? "" : et_content.getText()
					.toString();
			if (content.length() > 0)
			{
				inSertFeed();
			} 
			else
			{
				Toast.makeText(this, "请输入反馈内容！", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.ll_my_showhistory://历史反馈模板暂不需要实现
		default:
			break;
		}
	}

	/**
	 * 获取返回类型
	 */
	private void getfeedbackType(){
		if (man_error.isChecked())
		{
			feedbackType = "1";
		}
		if (man_tui.isChecked())
		{
			feedbackType = "2";
		}
		if (man_update.isChecked())
		{
			feedbackType = "3";
		}
		if (man_other.isChecked())
		{
			feedbackType = "4";
		}
	}

	/**
	 * 获取联系方式
	 */
	private void getContactType(){
		if (radio0.isChecked())
		{
			contactType = "1";
		}
		if (radio1.isChecked())
		{
			contactType = "2";
		}
		if (radio2.isChecked())
		{
			contactType = "3";
		}
		if (radio3.isChecked())
		{
			contactType = "4";
		}
	}

	private JSONObject params = new JSONObject();

	/**
	 * 初始化查询参数
	 * 
	 * @param typeId
	 */
	private void initParam(JSONObject jsonObject)
	{
		try
		{
			params.put("pageSize", Utils.PageSize);
			params.put("currentPage", 1);
			params.put("orderBy", "");
			params.put("object", jsonObject);
		} 
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 添加反馈
	 */
	private void inSertFeed()
	{
		JSONObject joObject = new JSONObject();
		try
		{
			joObject.put("feedbackType", feedbackType);
			joObject.put("contactType", contactType);
			joObject.put("contactText", et_link.getText() == null ? ""
					: et_link.getText().toString());
			joObject.put("content", content);
		} 
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		initParam(joObject);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				UrlConstant.UrlinsertFeedBack, params,
				new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response)
					{
						try
						{
							if(response.getString("message").equals("success"))
							{
								Toast.makeText(PersonReturn.this, "留言提交成功", Toast.LENGTH_SHORT).show();//表示留言已经提交成功！
							}
							else
							{
								Toast.makeText(PersonReturn.this, "留言提交失败，请稍候再试", Toast.LENGTH_SHORT).show();//表示留言已经提交成功！
							}
						} 
						catch (JSONException e)
						{
							e.printStackTrace();
						}
						
						// Utils.endnet();
						PersonReturn.this.finish();
					}
				}, 
				new Response.ErrorListener()
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
}