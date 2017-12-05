package com.ch.mhy.util;

import java.util.List;

import com.ch.mhy.entity.ComicsDetail;
import com.ch.mhy.entity.Down;

public class ListUtil {

	/**
	 * 冒泡正序排序
	 *
	 * @paramsrc待排序数组
	 */
	public static List<ComicsDetail> doAscSort(List<ComicsDetail> cds) {
		ComicsDetail[] src = new ComicsDetail[cds.size()];
		int m = 0;
		for (ComicsDetail comicsDetail : cds) {
			src[m++] = comicsDetail;
		}
		int len = src.length;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				ComicsDetail temp;
				if (src[i].getmNo() > src[j].getmNo()) {
					temp = src[j];
					src[j] = src[i];
					src[i] = temp;
				}
			}
		}
		cds.clear();
		for (int i = 0; i < src.length; i++) {
			cds.add(src[i]);
		}

		return cds;
	}
	
	/**
	 * 正序排列
	 * @param cds
	 * @return
	 */
	public static List<Down> doAscSortDown(List<Down> cds) {

		Down[] src = new Down[cds.size()];
		int m = 0;
		for (Down comicsDetail : cds) {
			src[m++] = comicsDetail;
		}

		int len = src.length;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				Down temp;
				if (src[i].getCd().getmNo() > src[j].getCd().getmNo()) {
					temp = src[j];
					src[j] = src[i];
					src[i] = temp;
				}
			}
		}
		cds.clear();
		for (int i = 0; i < src.length; i++) {
			cds.add(src[i]);
		}
		return cds;
	}
	/**
	 * 冒泡逆序序排序
	 *
	 * @paramsrc待排序数组
	 */
	public static List<ComicsDetail> doDescSort(List<ComicsDetail> cds) {

		ComicsDetail[] src = new ComicsDetail[cds.size()];

		int m = 0;
		for (ComicsDetail comicsDetail : cds) {
			src[m++] = comicsDetail;
		}

		int len = src.length;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				ComicsDetail temp;
				if (src[i].getmNo() < src[j].getmNo()) {
					temp = src[j];
					src[j] = src[i];
					src[i] = temp;
				}
			}
		}
		cds.clear();
		for (int i = 0; i < src.length; i++) {
			cds.add(src[i]);
		}

		return cds;
	}
}
