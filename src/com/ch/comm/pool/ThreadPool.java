package com.ch.comm.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

/**
 * 线程池
 * @author xc.li
 * @since 2015-08-14
 */
public class ThreadPool {
	/**
	 * 是否已创建过线程池
	 */
	private boolean hasBuide = false;
	/**
	 * 线程数，默认1个
	 */
	private int work_cnt = 1; 
	/**
	 * 线程池列表
	 */
	private ArrayList<ThreadWork> workList; 
	/**
	 * 待执行任务列表
	 */
	private static List<RunnableTask> taskQueue = 
		Collections.synchronizedList(new LinkedList<RunnableTask>()); 
	/**
	 * 执行中任务列表
	 */
	private static List<RunnableTask> runingTaskQueue = new ArrayList<RunnableTask>();
	
	private static ThreadPool instance;
	
	private ThreadPool(){
	}
	
	public static synchronized ThreadPool getInstance() {
        if (instance == null)
            instance = new ThreadPool();
        return instance;
    }
	
	/**
	 * 设置线程池数
	 * @param size
	 * @return
	 */
	public ThreadPool setPoolSize(int size){
		this.work_cnt = size;
		return instance;
	}
	
	/**
	 * 创建线程池
	 * @return
	 */
	public ThreadPool builder(){
		if(!hasBuide){//是否已经初始化过，否则初始化
			workList = new ArrayList<ThreadWork>();
			for(int i=0; i<work_cnt; i++){
				ThreadWork imgLdTh = new ThreadWork();
				workList.add(imgLdTh);
			}
			this.hasBuide = true;
		}
		return instance;
	}
	
	/**
	 * 是否已初始化过线程池
	 * @return
	 */
	public boolean isHasBuide() {
		return hasBuide;
	}

	/**
	 * 添加单个图片加载任务
	 * @param loader
	 */
	public void addTask(RunnableTask task){
		synchronized (taskQueue) {
        	//将新任务加入到执行队列
            taskQueue.add(task);
            /* 唤醒队列, 开始执行 */
            taskQueue.notifyAll();
        }
	}
	
	/**
	 * 添加多个图片加载任务
	 * @param works
	 */
	public void addMultyTask(ArrayList<RunnableTask> tasks){
		if (tasks == null || tasks.size() == 0) {
            return;
        }
		int size = tasks.size();
        synchronized (taskQueue) {
            for (int i = 0; i < size; i++) {
                if (tasks.get(i) == null) {
                    continue;
                }
                taskQueue.add(tasks.get(i));
            }
            /* 唤醒队列, 开始执行 */
            taskQueue.notifyAll();
        }
	}
	
	/**
	 * 获取等待执行任务队列
	 * @return
	 */
	/*public List<RunnableTask> getWaitTaskQueue(){
		return taskQueue;
	}*/
	
	/**
	 * 获取执行队列任务列表
	 * @return
	 */
	public List<RunnableTask> getRunTaskQueue(){
		return runingTaskQueue;
	}
	
	/**
	 * 清空任务列表
	 */
	public synchronized void cleanTask() {
		taskQueue.clear();
		int size = runingTaskQueue.size();
		for(int i=0; i<size; i++){
			RunnableTask task = runingTaskQueue.get(i);
			if(task.isRunning() && task.isWaiting()){
				task.destroy();
			}else{
				task.waiting();
				task.destroy();
			}
		}
		runingTaskQueue.clear();
	}
	
	/**
	 * 移除执行中任务
	 * @param task
	 */
	public synchronized void removeTask(RunnableTask task) {
		if(task != null && task.isRunning()){
			task.destroy();
			runingTaskQueue.remove(task);
			taskQueue.remove(task);
			task = null;
		}
	}
	
    /**
    * 销毁线程池
    */
    public synchronized void destroy() {
        for (int i = 0; i < work_cnt; i++) {
        	workList.get(i).stopWork();
        	workList.get(i).interrupt();
        }
        taskQueue.clear();
    }
    
	class ThreadWork extends Thread {
		private boolean isRunning = true;
		private boolean isWaiting = false;

		public ThreadWork(){
			this.start();
		}
		
		@Override
		public void run() {
			while (isRunning) {
				RunnableTask task = null;
				synchronized (taskQueue) {
					while (taskQueue.isEmpty()) {
						try {/*任务队列为空，则等待有新任务加入从而被唤醒 */
							taskQueue.wait(20);
						} catch (InterruptedException ie) {
						}
					}
					/*取出任务执行，并从队列中删除 */
					if(!isWaiting){
						task = taskQueue.remove(0);
					}
				}
				if (task != null) {
					runingTaskQueue.add(task);//添加到执行队列
					isWaiting = true;
					try {
						new Thread(task).start();//开始下载
					} catch (Exception e) {
						e.printStackTrace();
					}
					synchronized (task) {
						while(task.isRunning()){//等待下载结束
							try {
								task.wait(20);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					Log.e("ThreadPool", "主线程结束");
					isWaiting = false;
					task = null;
				}
			}
		}

		/**
		 * 销毁线程
		 */
		public void stopWork() {
			this.isRunning = false;
		}
		/**
		 * 线程是否在等待
		 * @return
		 */
		public boolean isWaiting() {
			return this.isWaiting;
		}
	}
}
