package com.ch.mhy.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import com.ch.mhy.activity.my.DownShowActivity;
import com.ch.mhy.application.MhyApplication;
import com.ch.mhy.db.DBManager;
import com.ch.mhy.db.DBUtil;
import com.ch.mhy.download.ImageDownProgress;
import com.ch.mhy.entity.Down;
import com.ch.mhy.util.MyLog;
import com.ch.mhy.util.SDUtil;
import com.ch.mhy.util.Utils;

/**
 * 漫画下载服务
 * 
 * @author DaxstUz 2416738717 2015年8月13日
 *
 */
public class DownComicService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();

	}

	int downindex = 0;// 记录正在下载的下标

	DBManager manager;

	/**
	 * 返回一个Binder对象
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return new MsgBinder();
	}

	public class MsgBinder extends Binder {
		/**
		 * 获取当前Service的实例
		 *
		 * @return
		 */
		public DownComicService getService() {
			return DownComicService.this;
		}

		public synchronized void startDownload(Down down)
				throws InterruptedException {

			Intent intent = new Intent();
			intent.putExtra("down", down);
			intent.putExtra("isfull", false);
			intent.setAction("android.intent.action.test");// action与接收器相同
			sendBroadcast(intent);

			Integer mid = down.getCd().getmId();

			if (!Utils.downloader.containsKey(mid)) {
				Utils.downloader.put(mid, new ImageDownProgress());
			}

			List<Down> temp = new ArrayList<Down>();
			/* 先判断是否有下载，如果有就添加后台的队列，否则重新开启下载队列 */
			if (Utils.downsmap.containsKey(mid)) {
				temp = Utils.downsmap.get(mid);
				temp.add(down);
			} else {
				temp.add(down);
				Utils.downsmap.put(mid, temp);
				Utils.downloaded.put(mid, true);
				new Thread(new WebHttpHandler(temp.get(0).getCd().getmId()))
						.start();
			}

			/**
			 * 更新到数据库
			 */
			manager = new DBManager(DownComicService.this, DBUtil.ReadName,
					null, DBUtil.Code);
			manager.addOrUpdateDown(down);
			manager.closeDB();

		}

		/**
		 * 同时选择多个章节，开始下载
		 * 
		 * @param downs
		 * @throws InterruptedException
		 */
		public synchronized void addDownload(List<Down> downs)
				throws InterruptedException {
			Integer mid = downs.get(0).getCd().getmId();

			if (!Utils.downloader.containsKey(mid)) {
				Utils.downloader.put(mid, new ImageDownProgress());
			}

			/* 先判断是否有下载，如果有就添加后台的队列，否则重新开启下载队列 */
			if (Utils.downsmap.containsKey(mid)) {
				List<Down> temp = Utils.downsmap.get(mid);
				for (Down down1 : downs) {
					if (!temp.contains(down1)) {
						temp.add(down1);
					}
				}
				/**
				 * 如果是处于暂停状态就开启下载
				 */
				if (!Utils.downloaded.get(mid)) {
					new Thread(
							new WebHttpHandler(downs.get(0).getCd().getmId()))
							.start();
					Utils.downloaded.put(mid, true);
				}
			} else {
				Utils.downsmap.put(mid, downs);
				Utils.downloaded.put(mid, true);
				new Thread(new WebHttpHandler(downs.get(0).getCd().getmId()))
						.start();
			}

		}

		/**
		 * 用来装正在下载的队列
		 */
		List<Down> tempend = new ArrayList<Down>();

		/**
		 * 单个点击暂停
		 * 
		 * @param down
		 * @throws InterruptedException
		 */
		public synchronized void endDownload(Down down)
				throws InterruptedException {

			Intent intent = new Intent();
			intent.putExtra("down", down);
			intent.putExtra("isfull", false);
			intent.setAction("android.intent.action.test");// action与接收器相同
			sendBroadcast(intent);

			final Integer mid = down.getCd().getmId();

			/* 先判断是否有下载，如果有就添加后台的队列，否则重新开启下载队列 */
			if (Utils.downsmap.containsKey(mid)) {
				tempend = Utils.downsmap.get(mid);
				for (int i = 0; i < tempend.size(); i++) {
					if (down.getCd().getmQid()
							.equals(tempend.get(i).getCd().getmQid())) {
						/**
						 * 如果是第0个就先暂停下载，再开启下一个下载，否则直接从下载队列里删除
						 */
						if (i == 0) {
							Utils.isdownnext.put(mid, true);
						} else {
							tempend.remove(i);
						}
					}
				}
			}

			/**
			 * 更新到数据库
			 */
			manager = new DBManager(DownComicService.this, DBUtil.ReadName,
					null, DBUtil.Code);
			manager.addOrUpdateDown(down);
			manager.closeDB();

		}

		/**
		 * 单个点击暂停
		 * 
		 * @param down
		 * @throws InterruptedException
		 */
		public void endDownload(List<Down> downs) throws InterruptedException {
			for (Down down : downs) {
				Intent intent = new Intent();
				intent.putExtra("down", down);
				intent.putExtra("isfull", false);
				intent.setAction("android.intent.action.test");// action与接收器相同
				sendBroadcast(intent);

				final Integer mid = down.getCd().getmId();

				/* 先判断是否有下载，如果有就添加后台的队列，否则重新开启下载队列 */
				if (Utils.downsmap.containsKey(mid)) {
					tempend = Utils.downsmap.get(mid);
					for (int i = 0; i < tempend.size(); i++) {
						if (down.getCd().getmQid()
								.equals(tempend.get(i).getCd().getmQid())) {
							/**
							 * 如果是第0个就先暂停下载，再开启下一个下载，否则直接从下载队列里删除
							 */
							if (i == 0) {
								Utils.isdownnext.put(mid, true);
							} else {
								tempend.remove(i);
							}
						}
					}
				}
			}

		}

		/**
		 * 暂停所有
		 * 
		 * @param down
		 * @throws InterruptedException
		 */
		public void endDownloadAll(Down down) throws InterruptedException {
			Integer mid = down.getCd().getmId();

			Utils.downloaded.put(mid, false);
			Utils.downsmap.remove(mid);
		}

		/**
		 * 暂停所有
		 * 
		 * @param down
		 * @throws InterruptedException
		 */
		public void endDownloadAll(List<Down> downs)
				throws InterruptedException {
			for (Down down2 : downs) {
				Integer mid = down2.getCd().getmId();
				Utils.downloaded.put(mid, false);
				Utils.downsmap.remove(mid);
			}
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private boolean isStore = true;

	/**
	 * 下载线程
	 * 
	 * @author DaxstUz 2416738717 2015年8月13日
	 *
	 */
	class WebHttpHandler implements Runnable {
		private Integer mid; // 要下载的章节id

		public WebHttpHandler(Integer mid) {
			this.mid = mid;
		}

		@Override
		public void run() {
			if (getStoreSize() <= 0) {
				isStore = false;
				/**
				 * 告知没内存了
				 */
				Intent intent = new Intent();
				intent.putExtra("isfull", true);
				intent.setAction("android.intent.action.test");// action与接收器相同
				sendBroadcast(intent);

				/**
				 * 清空这边漫画的信息
				 */
				Utils.downloaded.put(mid, false);
				Utils.downloader.remove(mid);
				Utils.downsmap.remove(mid);

			} else {
				isStore = true;
			}

			while (Utils.downloaded.get(mid) && isStore) {

				if (Utils.downsmap.get(mid) != null
						&& Utils.downsmap.get(mid).size() > 0) {
					Down down = Utils.downsmap.get(mid).get(0);
					String saveFilePath = SDUtil.getSecondExterPath()
							+ "/manyun/" + down.getCd().getmTitle() + "/"
							+ down.getCd().getmName();
					try {
						String[] urlArray = down.getCd().getmUrl()
								.split(Utils.split);
						String[] urlLocal = down.getCd().getLocalUrl()
								.split(Utils.split);
						
						if(urlLocal.length<urlArray.length){
							urlLocal=urlArray;
							down.getCd().setLocalUrl(down.getCd().getmUrl());
							
//							/**
//							 * 下载完了，把他从下载队列里删除
//							 */
//							if (Utils.downsmap.get(mid) != null) {
//								Utils.downsmap.get(mid).remove(0);
//							}
//							down.setIsdonw(2);
//							/**
//							 * 暂停时,通知UI更新
//							 */
//							Intent intent = new Intent();
//							intent.putExtra("down", down);
//							intent.putExtra("isfull", false);
//							intent.setAction("android.intent.action.test");// action与接收器相同
//							sendBroadcast(intent);
//							continue;
						}
						
						for (int i = 0; i < urlArray.length; i++) {
							/**
							 * 判断是否把正在下载的暂停
							 */
							if (Utils.isdownnext.containsKey(mid)
									&& Utils.isdownnext.get(mid)) {
								Utils.isdownnext.put(mid, false);
								down.setIsdonw(2);
								/**
								 * 暂停时,通知UI更新
								 */
								Intent intent = new Intent();
								intent.putExtra("down", down);
								intent.putExtra("isfull", false);
								intent.setAction("android.intent.action.test");// action与接收器相同
								sendBroadcast(intent);

								break;
							}

							/* 先判断是否是下载状态 */
							if (Utils.downloaded.get(mid)) {
								boolean result = Utils.downloader.get(
										down.getCd().getmId()).startDown(
										saveFilePath, urlArray[i], urlLocal[i],
										down.getCd().port);
								if (result) {
									down.setDowns(Integer.valueOf(i + 1));
									/* 根据下载的数量变更现在的状态 */
									if (i == urlArray.length - 1) {
										down.setIsdonw(0);
										/**
										 * 更新到数据库
										 */
										synchronized (down) {
											manager = new DBManager(DownComicService.this,
													DBUtil.ReadName, null, DBUtil.Code);
											manager.addOrUpdateDown(down);
											manager.closeDB();
										} 
									} else {
										if (Utils.downloaded.get(mid)) {
											down.setIsdonw(1);
										} else {
											down.setIsdonw(2);
										}
									}

								} else {
									// 没下载成功，继续
									down.setIsdonw(2);
								}

								/**
								 * 每下完一话，就通知UI更新
								 */
								Intent intent = new Intent();
								intent.putExtra("down", down);
								intent.putExtra("isfull", false);
								intent.setAction("android.intent.action.test");// action与接收器相同
								sendBroadcast(intent);

							} else {
								// 退出下载
								Utils.downloader.get(down.getCd().getmId())
										.stopDown();
								break;
							}
						}

						/**
						 * 下载完了，把他从下载队列里删除
						 */
						if (Utils.downsmap.get(mid) != null) {
							Utils.downsmap.get(mid).remove(0);
						}

					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				} else {

					Intent intent = new Intent();
					intent.putExtra("isover", "over@@" + mid);
					intent.setAction("android.intent.action.update");// action与接收器相同
					sendBroadcast(intent);

					/**
					 * 全部下载完了，就清空这边漫画的信息
					 */
					Utils.downloaded.put(mid, false);
					Utils.downloader.remove(mid);
					Utils.downsmap.remove(mid);
				}
				if (getStoreSize() <= 0) {
					isStore = false;
					/**
					 * 告知没内存了
					 */
					Intent intent = new Intent();
					intent.putExtra("isfull", true);
					intent.setAction("android.intent.action.test");// action与接收器相同
					sendBroadcast(intent);
				} else {
					isStore = true;
				}
			}
		}

	}

	/**
	 * 获取手机sd卡存储空间
	 */
	private long getStoreSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		long availableBlocks = stat.getAvailableBlocks();

		long totalSize = totalBlocks * blockSize;
		long availSize = availableBlocks * blockSize;

		String totalStr = Formatter.formatFileSize(this, totalSize);
		String availStr = Formatter.formatFileSize(this, availSize);

		return availSize;
	}
}
