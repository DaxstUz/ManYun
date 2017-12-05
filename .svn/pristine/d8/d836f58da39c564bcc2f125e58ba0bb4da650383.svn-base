package com.ch.mhy.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 数据解析帮助类
 * 
 * @author DaxstUz 2416738717 2015年10月13日
 *
 */
public class DataUtil {

	/**
	 * 获取单个对象
	 * 
	 * @param t
	 * @param jobObject
	 * @return
	 */
	public static <T> T getInfo(T t, JSONObject jobObject) {
		Field[] f = t.getClass().getDeclaredFields();
		for (Field field : f) {
			field.setAccessible(true);
			try {
				String name = field.getName();
				if (jobObject.has(name) && jobObject.get(name) != null) {
					field.set(t, jobObject.get(name));
				}
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	/**
	 * 获取数组
	 * 
	 * @return
	 */
	public static <T> List<T> getInfos(T t, JSONArray array) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject joObject = array.getJSONObject(i);
				list.add(getInfo(t, joObject));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

}
