package com.ch.comm.pool;

import android.os.Handler;

/**
 * 线程池任务基类
 * @author xc.li
 * @since 2015-08-14
 */
public class RunnableTask implements Runnable {
	/**
	 * 线程销毁锁
	 */
	protected boolean isRunning = true; 
	/**
	 * 线程等待锁
	 */
	protected boolean isWaiting = false; 
	/**
	 * 消息Handler
	 */
	protected Handler handler;
	
	@Override
	public void run() {
		
	}

	/**
	 * 是否被销毁
	 * @return
	 */
	public boolean isRunning() {
		return isRunning;
	}
	/**
	 * 是否在等待
	 * @return
	 */
	public boolean isWaiting() {
		return isWaiting;
	}
	/**
	 * 重新开始执行
	 */
	public void reStart() {
		this.isWaiting = false;
	}
	/**
	 * 设置等待
	 */
	public void waiting(){
		this.isWaiting = true;
	}
	/**
	 * 销毁线程
	 */
	public void destroy(){
		this.isRunning = false;
		this.isWaiting = false;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
