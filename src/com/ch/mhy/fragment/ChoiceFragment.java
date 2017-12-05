package com.ch.mhy.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.mhy.R;
import com.ch.mhy.activity.cate.ShowBooksActivity;
import com.ch.mhy.adapter.TypeShowAdapter;
import com.ch.mhy.application.MhyApplication;
import com.ch.mhy.entity.AuthorType;
import com.ch.mhy.entity.Comics;
import com.ch.mhy.net.NetReceiver;
import com.ch.mhy.net.NetReceiver.NetState;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.Utils;

/**
 * 精选碎片
 *
 * @author DaxstUz 2416738717 2015年5月6日
 */
public class ChoiceFragment extends Fragment implements OnClickListener {

    private List<AuthorType> ats = new ArrayList<AuthorType>();

    private TypeShowAdapter adapter;

    private View view;
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
	    	view = inflater.inflate(R.layout.fragment_jx, null);
	    } catch (InflateException e) {
	        
	    }
    	
//        View view = inflater.inflate(R.layout.fragment_jx, null);
        if (NetReceiver.isConnected(ChoiceFragment.this.getActivity()) != NetState.NET_NO) {
            initData();
        }

        GridView gridview = (GridView) view.findViewById(R.id.gv_cate_tc);

        adapter = new TypeShowAdapter(ChoiceFragment.this.getActivity(), ats);
        // 添加并且显示
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ChoiceFragment.this.getActivity(),
                        ShowBooksActivity.class);
                intent.putExtra("cate", 2);
                intent.putExtra("author", ats.get(position));
                intent.putExtra("eventId", "sort_choice");
                ChoiceFragment.this.getActivity().startActivity(intent);
            }
        });

        return view;
    }

    private int currentPage = 1;

    /**
     * 初始化数据
     */
    private void initData() {
        // TODO Auto-generated method stub
//        Utils.startnet(ChoiceFragment.this.getActivity());
        JSONObject params = new JSONObject();
        try {
            params.put("pageSize", 100);
            params.put("currentPage", 1);
            params.put("orderBy", "");
            params.put("object", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
                UrlConstant.UrlChoiceType, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Utils.endnet();
                        if (ats != null) {
                            ats.clear();
                        }

                        try {
                            JSONArray array = response.getJSONObject("object")
                                    .getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject joObject = array.getJSONObject(i);
                                AuthorType at = new AuthorType();
                                at.setTypeId(joObject.getInt("typeId"));
                                at.setTypeName(joObject.getString("typeName"));
                                at.setTypePic(joObject.getString("typePic"));
                                // at.setTypeNum(joObject.getInt("typeNum"));
                                ats.add(at);
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Utils.showErrorMsg(MhyApplication.);
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
        switch (v.getId()) {
            case R.id.ll_df_del:
                Toast.makeText(ChoiceFragment.this.getActivity(), "你点击了删除",
                        Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }
}
