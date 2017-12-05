package com.ch.mhy.util;

public class UrlConstant {


//    public final static String Ip = "http://192.168.1.178";
//	public final static String Port = ":80";
//	
//	public final static String Ip="http://219.135.99.160";
//    public final static String Port = ":80";
    
//	public final static String hostServer=Ip+Port+"/manhuaapp";
	public final static String hostServer="http://my.chamanhua.com/manhuaapp";
    
    //图片IP
//    public final static String Ip1="http://219.135.99.158";
    public final static String Ip1="http://img1.chamanhua.com";
    public final static String Port1 = ":8080";
    
    public final static String downPort = ":8081";
    
//    public final static String Server = "/manhuaapp";

    /**
     * 搜索地址
     */
//    public static final String UrlSearch = hostServer + Server + "/remoting/search/queryComicsList";
    public static final String UrlSearch = hostServer  + "/remoting/search/queryComicsList";
   
    /**
     * 搜索关键字列表地址
     */
    public static final String UrlqueryComicsByName = hostServer + "/remoting/search/queryComicsByName";

    /**
     * 插入关键字
     */
    public static final String UrlInsertKey = hostServer +  "/remoting/search/insertKey";

    /**
     * 热门关键字
     */
    public static final String UrlKeyList = hostServer + "/remoting/search/queryKeyListPage";

    /**
     * 猜你喜欢
     */
    public static final String UrlGuessList = hostServer +  "/remoting/guess/queryGuessListPage";

    /**
     * 精选首页接口
     */
    public static final String UrlChoiceType = hostServer + "/remoting/choice/queryChoiceTypePage";

    /**
     * 精选列表接口
     */
    public static final String UrlChoiceList = hostServer + "/remoting/choice/queryChoiceListPage";

    /**
     * 题材首页接口
     */
    public static final String UrlCategoryType = hostServer +  "/remoting/category/queryCategoryTypePage";

    /**
     * 题材列表接口
     */
    public static final String UrlCategoryList = hostServer +  "/remoting/category/queryCategoryListPage";

    public static final String UrlComicsSelectDetail = hostServer + "/remoting/read/queryAllComicsDetail";
    
    public static final String UrlComicsSelectDetailById = hostServer +  "/remoting/read/queryComicsDetailById";

    /**
     * 作者列表接口
     */
    public static final String UrlAuthorType = hostServer +  "/remoting/author/queryAuthorTypePage";


    /**
     * 作者漫画列表接口
     */
    public static final String UrlAuthorList = hostServer + "/remoting/author/queryAuthorListPage";

    /**
     * 首页
     */
    public static final String UrlBookType = hostServer +  "/remoting/book/queryBookTypePage";

    /**
     * 轮播图
     */
    public static final String UrlFirstTurn =hostServer +  "/remoting/book/queryFirstTurn";
   
    /**
     * 获取精彩推荐
     */
    public static final String UrlGetWonRec = hostServer + "/remoting/suggest/querySuggestPage";

    /**
     * 首页列表
     */
    public static final String UrlBookList = hostServer+ "/remoting/book/queryBookListPage";

    /**
     * 章节列表
     */
    public static final String UrlComicsDetailPage = hostServer+  "/remoting/read/queryComicsDetailPage";
   
    /**
     * 添加反馈
     */
    public static final String UrlinsertFeedBack = hostServer +  "/remoting/feedback/insertFeedBack";
    
    /**
     *历史反馈列表
     */
    public static final String UrlqueryFeedbackList =hostServer +  "/remoting/feedback/queryFeedbackList";
    
    /**
     *分享
     */
    public static final String UrlinsertBookShare = hostServer +  "/remoting/book/insertBookShare";
    
    /**
     *新增人气
     */
    public static final String UrlupdateHits = hostServer + "/remoting/read/updateHits";

    /**
     *获取当前APP版本
     */
    public static final String UrlqueryCurrVersion = hostServer + "/remoting/version/queryCurrVersion";
    
    /**
     * 登录信息更新接口
     */
    public static final String UrlpushUserUseMessage = hostServer + "/remoting/push/pushUserUseMessage";
    /**
     * 统计信息接口
     */
    public static final String UtlCommit = hostServer + "/remoting/event/insertEventSave";
    
    /**
     * 增加阅读记录到后台
     */
    public static final String addRedToServer="http://www.chamanhua.com/web/readLog/addReadLog";
   
    /**
     * 删除阅读记录到后台
     */
    public static final String delRedToServer="http://www.chamanhua.com/web/readLog/deleteReadLog";
    /**
     * 增加收藏记录到后台
     */
    public static final String addCollectToServer="http://www.chamanhua.com/web/indexsearch/addComicToCollected";
    /**
     * 删除收藏记录到后台
     */
    public static final String delCollectToServer="http://www.chamanhua.com/web/indexsearch/deleteComicFromCollected";
    /**
     *从后台批量获取阅读记录
     */
//    public static final String getRedLogs="http://192.168.1.210:8084/manhuaweb/web/syncdata/syncUserReadComic";
    public static final String getRedLogs="http://www.chamanhua.com/web/syncdata/syncUserReadComic";
    /**
     *从后台批量获取收藏记录
     */
//    public static final String getCollectLogs="http://192.168.1.210:8084/manhuaweb/web/syncdata/syncUserCollectComic";
    public static final String getCollectLogs="http://www.chamanhua.com/web/syncdata/syncUserCollectComic";
	
    /**
	 * 漫画评论列表
	 */
    public static final String UrlCommentList = hostServer +  "/remoting/discussTopic/getTopicListById";
    /**
	 * 漫画评论数
	 */
    public static final String UrlCommentCnt = hostServer +"/remoting/discussTopic/getTopicCountById";
    /**
	 * 用户评论漫画列表
	 */
    public static final String UrlCommentListByUser = hostServer +  "/remoting/discussTopic/getTopicListByUserId";
    /**
	 * 保存漫画评论
	 */
    public static final String UrlCommentSave = hostServer + "/remoting/discussTopic/saveTopic";
   
    /**
	 * 保存漫画评论回复
	 */
    public static final String UrlCommentReplySave = hostServer +  "/remoting/discuss/saveDiscuss";
    /**
	 * 漫画评论回复列表
	 */
    public static final String UrlCommentReplyList = hostServer +"/remoting/discuss/getDiscussListByTopicId";
    /**
	 * 我参与回复的漫画评论列表
	 */
    public static final String UrlCommentReplyByUser = hostServer + "/remoting/discuss/getDiscussListByUser";
    /**
	 * 漫画评论回复数
	 */
    public static final String UrlCommentReplyCnt = hostServer +  "/remoting/discuss/getDiscussCountById";

    /**
	 * 保存漫画评论点赞
	 */
    public static final String UrlOkCommentSave = hostServer +  "/remoting/approve/saveApprove";
    /**
	 * 查询漫画评论点赞列表
	 */
    public static final String UrlOkCommentList = hostServer + "/remoting/approve/getApproveByTopicId";
    /**
	 * 查询漫画评论点赞数
	 */
    public static final String UrlOkCommentCnt = hostServer + "/remoting/approve/getApproveCountById";
    /**
	 * 用户是否对漫画评论点赞
	 */
    public static final String UrlUserIsOkComment = hostServer +  "/remoting/approve/isApproveByUser";
    /**
     * 删除用户点赞记录
     */
    public static final String UrlOkCommentDel = hostServer +  "/remoting/approve/deleteApproveById";
    
    /**
     * 用户发表的评论列表
     */
    public static final String UrlCommentsByUser = hostServer +  "/remoting/discussTopic/getTopicListByUser";
    /**
     * 用户参与的评论列表
     */
    public static final String UrlDiscussCommentsByUser = hostServer + "/remoting/discussTopic/getDiscussTopicListByUser";
    /**
     * 用户催更
     */
    public static final String UrlChuiGen = hostServer + "/remoting/feedback/insertChuiGen";
    /**
     * 获取最近漫画更新
     */
    public static final String UrlpushUserNewComic = hostServer + "/remoting/push/pushUserNewComicChapterMessageDetail";
    /**
     *用户没登陆
     */
    public static final String UrlpushUserUseMessageNotLogin = hostServer + "/remoting/push/pushUserUseMessageNotLogin";
    /**
     *章节更新
     */
    public static final String UrlpushUserNewComicChapterMessage = hostServer + "/remoting/push/pushUserNewComicChapterMessage";

    /**
     * 分页查询刷新标识
     */
    public static final int LOAD_DATA_FINISH = 10;
    public static final int REFRESH_DATA_FINISH = 11;

}
