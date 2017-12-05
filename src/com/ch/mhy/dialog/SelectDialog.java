package com.ch.mhy.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ch.mhy.R;
import com.ch.mhy.adapter.ChapterSelectAdapter;
import com.ch.mhy.entity.ComicsDetail;
import com.ch.mhy.listener.UtilDialogListener;

/**
 * 漫画选择对话框
 *
 * @author DaxstUz 2416738717 2015年5月5日
 */
public class SelectDialog extends Dialog{

    private GridView gv_chapterselect;

    private ChapterSelectAdapter csa;
    Context context;

    private List<ComicsDetail> cds = new ArrayList<ComicsDetail>();

    UtilDialogListener udl;
    
    private Integer mNo;
    
    public SelectDialog(Context context, int theme, List<ComicsDetail> cds,
                        UtilDialogListener udl,Integer mNo) {
        super(context, theme);
        this.context = context;
        this.cds=cds;
        this.udl = udl;
        this.mNo=mNo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select);
        gv_chapterselect = (GridView) findViewById(R.id.gv_chapterselect);

        csa = new ChapterSelectAdapter(context, cds,mNo);
        gv_chapterselect.setAdapter(csa);

        gv_chapterselect.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
            	SelectDialog.this.dismiss();
                udl.dialogToActivity(cds.get(position));
            }
        });
    }

    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
 		LayoutParams linearParams = gv_chapterselect.getLayoutParams(); // 取控件textView当前的布局参数
		 WindowManager windowManager =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//		linearParams.width = windowManager.getDefaultDisplay().getWidth();
		int n=windowManager.getDefaultDisplay().getHeight();
		if(cds.size()>28){
			linearParams.height=n/2;
			gv_chapterselect.setLayoutParams(linearParams);
		}
    }


}
