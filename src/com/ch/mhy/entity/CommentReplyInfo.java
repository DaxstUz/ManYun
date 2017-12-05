package com.ch.mhy.entity;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentReplyInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long topicId; // 主题ID

	private String userId; // 用户ID

	private String userType; // 用户登陆类型1:QQ 2:微信 3:微博

	private String nickname; // 昵称

	private String imgUrl; // 图片URL

	private String discuss; // 对主题的评论

	private long createTime;// 发表时间

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDiscuss() {
		return discuss;
	}

	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}

	@SuppressLint("SimpleDateFormat") 
	public String getReplyTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = formatter.format(new Date(createTime));
		return date;
	}
	
	public long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
