package com.ch.mhy.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter{  
    ArrayList<Fragment> list;  
    public FragmentPagerAdapter(FragmentManager fragmentManager,ArrayList<Fragment> list) {  
        super(fragmentManager);  
        this.list = list;  
          
    }  
      
    @Override  
    public int getCount() {  
        return list.size();  
    }  
      
    @Override  
    public Fragment getItem(int arg0) {  
        return list.get(arg0);  
    }  
      
      
      
      
}  