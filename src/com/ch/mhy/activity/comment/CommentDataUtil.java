package com.ch.mhy.activity.comment;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;

import com.ch.comm.resquest.AbsResponseData;
import com.ch.comm.resquest.RequestDataUtil;
import com.ch.mhy.entity.CommentInfo;
import com.ch.mhy.entity.CommentReplyInfo;
import com.ch.mhy.util.MyLog;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.UserUtil;

/**
 * 评论功能
 * @author xc.li
 * @date 2015-10-15
 */
public class CommentDataUtil {
	/**
	 * 加载评论主题列表
	 * @param context
	 * @param bigbookId
	 * @param dataCtr
	 */
	public static void loadCommentList(Context context, String bigbookId, int page, AbsResponseData dataCtr) {
		if(bigbookId==null || "".equals(bigbookId)){
			return;
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("bigbookId", Integer.valueOf(bigbookId));
		param.put("userId", UserUtil.getOpenId(context));
		param.put("userType", UserUtil.getPlatForm(context));
		param.put("nickname", UserUtil.getScreen_name(context));
		param.put("imgUrl", UserUtil.getHeadurl(context));
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("currentPage", page);
		params.put("pageSize", RequestDataUtil.pageSize);
		params.put("object", param);
		RequestDataUtil.requestPageData(context, UrlConstant.UrlCommentList, params, dataCtr);
	}
	/**
	 * 查询评论主题数
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void loadCommentCnt(Context context,  String bigbookId, AbsResponseData dataCtr) {
		if(bigbookId==null || "".equals(bigbookId)){
			return;
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("object", bigbookId);
		RequestDataUtil.requestObjectData(context, UrlConstant.UrlCommentCnt, params, dataCtr);
	}
	/**
	 * 查询用户发表的评论主题
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void loadCommentListByUser(Context context, HashMap<String, Object> params, AbsResponseData dataCtr) {
		RequestDataUtil.requestPageData(context, UrlConstant.UrlCommentListByUser, params, dataCtr);
	}
	/**
	 * 保存评论主题
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void saveComment(Context context, CommentInfo info, AbsResponseData dataCtr){
		HashMap<String, Object> param = new HashMap<String, Object>();
		RequestDataUtil.entityToMap(param, info);
		/*param.put("id", info.getId());
		param.put("bigbookId", info.getBigbookId());
		param.put("userId", info.getUserId());
		param.put("userType", info.getUserType());
		param.put("nickname", info.getNickname());
		param.put("topic", info.getTopic());
		param.put("imgUrl", info.getImgUrl());
		param.put("createTime", info.getCreateTime());
		param.put("comicName", info.getComicName());
		param.put("comicUrl", info.getComicUrl());*/
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("object", param);
		RequestDataUtil.updateData(context, UrlConstant.UrlCommentSave, params, dataCtr);
	}
	
	/**
	 * 查询回复列表
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void loadCommentReplyList(Context context, long topicId, int page, AbsResponseData dataCtr){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("currentPage", page);
		params.put("pageSize", RequestDataUtil.pageSize);
		params.put("object", topicId);
		RequestDataUtil.requestPageData(context, UrlConstant.UrlCommentReplyList, params, dataCtr);
	}
	/**
	 * 查询用户回复的主题列表
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void loadCommentReplyByUser(Context context, HashMap<String, Object> params, AbsResponseData dataCtr){
		RequestDataUtil.requestPageData(context, UrlConstant.UrlCommentReplyByUser, params, dataCtr);
	}
	/**
	 * 查询用户评论回复数
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void loadCommentReplyCnt(Context context, long topicId, AbsResponseData dataCtr){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("object", topicId);
		RequestDataUtil.requestObjectData(context, UrlConstant.UrlCommentReplyCnt, params, dataCtr);
	}
	/**
	 * 保存用户评论回复
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void saveCommentReply(Context context, CommentReplyInfo info, AbsResponseData dataCtr){
		HashMap<String, Object> param = new HashMap<String, Object>();
		RequestDataUtil.entityToMap(param, info);
		/*param.put("id", info.getId());
		param.put("topicId", info.getTopicId());
		param.put("userId", info.getUserId());
		param.put("userType", info.getUserType());
		param.put("nickname", info.getNickname());
		param.put("discuss", info.getDiscuss());
		param.put("imgUrl", info.getImgUrl());
		param.put("createTime", info.getCreateTime());*/
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("object", param);
		RequestDataUtil.updateData(context, UrlConstant.UrlCommentReplySave, params, dataCtr);
	}
	
	
	/**
	 * 查询用户点赞列表
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void loadOkCommentList(Context context, long topicId, int page, AbsResponseData dataCtr){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("currentPage", page);
		params.put("pageSize", RequestDataUtil.pageSize);
		params.put("object", topicId);
		RequestDataUtil.requestPageData(context, UrlConstant.UrlOkCommentList, params, dataCtr);
	}
	/**
	 * 查询用户点赞数
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void loadOkCommentCnt(Context context, long topicId, AbsResponseData dataCtr){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("object", topicId);
		RequestDataUtil.requestObjectData(context, UrlConstant.UrlOkCommentCnt, params, dataCtr);
	}
	/**
	 * 是否给评论点赞
	 * @param context
	 * @param topicId
	 * @param dataCtr
	 */
	public static void isOkComment(Context context, CommentInfo bean, AbsResponseData dataCtr){
		String key = bean.getId()+"@@"+bean.getUserId()+"@@"+bean.getUserType()+"@@"+bean.getNickname()+"@@"+bean.getImgUrl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("object", key);
		RequestDataUtil.requestObjectData(context, UrlConstant.UrlUserIsOkComment, params, dataCtr);
	}
	/**
	 * 保存用户点赞
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void saveOkComment(Context context, HashMap<String, Object> param, AbsResponseData dataCtr){
		HashMap<String, Object> params = new HashMap<String, Object>();
		param.put("userId", UserUtil.getOpenId(context));
		param.put("nickname", UserUtil.getScreen_name(context));
		param.put("imgUrl", UserUtil.getHeadurl(context));
		param.put("userType", UserUtil.getPlatForm(context));
		param.put("createTime", System.currentTimeMillis());
		params.put("object", param);
		RequestDataUtil.updateData(context, UrlConstant.UrlOkCommentSave, params, dataCtr);
	}
	/**
	 * 删除用户点赞
	 * @param context
	 * @param params
	 * @param dataCtr
	 */
	public static void delOkComment(Context context, long topicId, AbsResponseData dataCtr){
		HashMap<String, Object> params = new HashMap<String, Object>();
		String key = UserUtil.getOpenId(context)+"@@"+UserUtil.getPlatForm(context)+"@@"+UserUtil.getScreen_name(context)+"@@"+UserUtil.getHeadurl(context);
		params.put("object", topicId+"@@"+key);
		RequestDataUtil.updateData(context, UrlConstant.UrlOkCommentDel, params, dataCtr);
	}
	
	
	/**
	 * 获取数据
	 * @param context
	 * @param url
	 * @param param
	 * @param dataCtr
	 */
	public static void getTopicList(Context context,String url, JSONObject param, AbsResponseData dataCtr){
		RequestDataUtil.getData(context,url, param, dataCtr);
	}
}
