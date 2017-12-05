package com.ch.mhy.listener;

/**
 * 阅读帮助接口
 * @author DaxstUz 2416738717
 * 2015年8月26日
 *
 */
public interface Add {
	/**
	 * 开启加载动画
	 * @param url 加载的地址，用来判断是否被加载完
	 */
	public void start(String url); 
	
	/**
	 * 停止加载动画
	 */
	public void stop();
	
	/**
	 * 获取新的章节
	 * @param order 正/逆序
	 */
	public void  getNewData(String order);
	
	/**
	 * 更新当前阅读的页码
	 */
	public void updateIndex();
}