package com.ch.mhy.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ch.mhy.R;
import com.ch.mhy.net.NetReceiver;
import com.umeng.analytics.MobclickAgent;

/**
 * 分类碎片
 *
 * @author DaxstUz 2416738717 2015年5月4日
 */
public class CatgFragment extends Fragment implements OnClickListener {

    private AuthorFragment aFragment;
    private ChoiceFragment jFragment;
    private TcFragment tFragment;

    private LinearLayout ll_cate_operate;

    private Button btn_cate_btn_tc;//题材tab
    private Button btn_cate_btn_auth;//作者tab
    private Button btn_cate_btn_jx;//精选tab

   private View view;
   
   private LinearLayout ll_getnet;
   
   private ImageView btn_getnet;
    
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
	        view = inflater.inflate(R.layout.fragment_cate, null);
	    } catch (InflateException e) {
	        
	    }
        MobclickAgent.onEvent(CatgFragment.this.getActivity(), "CatgFragment");
        ll_cate_operate = (LinearLayout) view
                .findViewById(R.id.ll_cate_operate);
        
        btn_cate_btn_tc = (Button) view.findViewById(R.id.btn_cate_btn_tc);
        btn_cate_btn_auth = (Button) view.findViewById(R.id.btn_cate_btn_auth);
        btn_cate_btn_jx = (Button) view.findViewById(R.id.btn_cate_btn_jx);
        
        ll_getnet = (LinearLayout) view.findViewById(R.id.ll_getnet);
        btn_getnet = (ImageView) view.findViewById(R.id.btn_getnet);
        
        btn_cate_btn_tc.setOnClickListener(this);
        btn_getnet.setOnClickListener(this);
        btn_cate_btn_auth.setOnClickListener(this);
        btn_cate_btn_jx.setOnClickListener(this);

        tFragment = new TcFragment();
        aFragment = new AuthorFragment();
        jFragment = new ChoiceFragment();
        
        if (NetReceiver.isConnected(CatgFragment.this.getActivity()) != NetReceiver.NetState.NET_NO) {
			ll_getnet.setVisibility(View.GONE);	
			setDefaultFragment();
	    }
        
        return view;
    }

    /**
     * 设置默认显示的碎片
     */
    private void setDefaultFragment() {
    	FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            	  transaction.replace(R.id.fl_content, tFragment);
            	  btn_cate_btn_tc.setTextColor(Color.WHITE);
            	  transaction.commit();
    }

    @Override
    public void onClick(View v) {
    	FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    	/*点击选择不同的题材时，先把他们的字体设置为红色，在把选中的tab字体设为白色*/
        btn_cate_btn_tc.setTextColor(Color.RED);
        btn_cate_btn_auth.setTextColor(Color.RED);
        btn_cate_btn_jx.setTextColor(Color.RED);
        
        switch (v.getId()) {
        case R.id.btn_getnet://点击刷新
        	 if (NetReceiver.isConnected(CatgFragment.this.getActivity()) != NetReceiver.NetState.NET_NO) {
     			ll_getnet.setVisibility(View.GONE);	
     			setDefaultFragment();
     	    }else{
     	    	Toast.makeText(CatgFragment.this.getActivity(), "请链接网络！", Toast.LENGTH_SHORT).show();
     	    }
        	break;
            case R.id.btn_cate_btn_tc://点击题材
            	btn_cate_btn_tc.setTextColor(Color.WHITE);
                ll_cate_operate.setBackgroundResource(R.drawable.ch_cate_tc);
                if (!tFragment.isAdded()) {
                		try {
							transaction.replace(R.id.fl_content, tFragment);
							transaction.commit();
						} catch (Exception e) {
							e.printStackTrace();
						}
                }
                break;
            case R.id.btn_cate_btn_auth://点击作者
            	btn_cate_btn_auth.setTextColor(Color.WHITE);
                ll_cate_operate.setBackgroundResource(R.drawable.ch_cate_author);
                if (!aFragment.isAdded()) {
                    transaction.replace(R.id.fl_content, aFragment);
                }
                transaction.commit();
                break;
            case R.id.btn_cate_btn_jx://点击精选
            	btn_cate_btn_jx.setTextColor(Color.WHITE);
                ll_cate_operate.setBackgroundResource(R.drawable.ch_cate_btn_jx);
                if (!jFragment.isAdded()) {
                    transaction.replace(R.id.fl_content, jFragment);
                }
                transaction.commit();
                break;
            default:
                break;
        }
    }
}
