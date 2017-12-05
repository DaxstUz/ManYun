package com.ch.comm.resquest;

/**
 * 数据处理接口
 * @author xc.li
 * @date 2015年7月7日
 */
public abstract class AbsResponseData {
	private Class<?> clz;
	private Class<?> childClz;
	
	public AbsResponseData(Class<?> clz){
		this.clz = clz;
	}
	
	public Class<?> getClz() {
		return clz;
	}

	/**
	 * 设置两级树形数据中，children转换对象
	 * @param childClz
	 */
	public void setChildClz(Class<?> childClz){
		this.childClz = childClz;
	}
	
	public Class<?> getChildClz() {
		return childClz;
	}
	/**
	 * 数据业务处理
	 * @param response data 返回的数据：分页时为列表，获取单个对象时为Object，更新保存时为字符串
	 */
	public abstract void dataBusi(Object data);
}
