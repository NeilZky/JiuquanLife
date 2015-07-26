package com.jiuquanlife.constance;

public class UrlConstance {

	public static final String LOGIN = "http://www.5ijq.cn/hongliu/mobcent/app/web/index.php?r=user/login";//登陆
	public static final String ADD_HOUSE_CONFIG = "http://www.5ijq.cn/App/House/addHouse";//添加房源时的区域信息
	public static final String GET_COMMUNITY_BY_ADDRESS_ID = "http://www.5ijq.cn/App/House/community/addressId/";//根据区域获取小区列表
	public static final String NEW_HOUSE = "http://www.5ijq.cn/App/House/newHouse";//添加房源
	public static final String AGENT_LIST = "http://www.5ijq.cn/App/House/agentList";//经纪人列表
	public static final String AGENT_DETAIL = "http://www.5ijq.cn/App/House/agentDetial";//经纪人详情
	public static final String COMMUNITY_LIST = "http://www.5ijq.cn/App/House/communityList";//小区列表筛选传值：{address,currentLon，currentLat(当前手机用户定位的经纬度)}
	public static final String GET_RENT_HOUSE_LIST = "http://www.5ijq.cn/App/House/getRentalHouseList";//出租列表
	public static final String GET_SELL_HOUSE_LIST = "http://www.5ijq.cn/App/House/getSellHouseList";//出租列表
	public static final String GET_APPLY_RENT_HOUSE_LIST = "http://www.5ijq.cn/App/House/getWantedRentalHouseList";//出租列表
	public static final String GET_APPLY_BUY_HOUSE_LIST = "http://www.5ijq.cn/App/House/getWantedByHouseList";//出租列表
	
	/*
	 * 融云
	 */
	public static final String GET_RONGYUN_TOKEN = "http://www.5ijq.cn/App/RongClound/getRongCloundToken";//出租列表
	
	/*
	 * ---------------------
	 * 	论坛
	 * ---------------------
	 */
	public static final String GET_NEW_FORUM_LIST = "http://www.5ijq.cn/hongliu/mobcent/app/web/index.php?r=forum/topiclist/sortby/new/isImageList/1/page/";//论坛新帖列表
	public static final String GET_ESSENCE_FORUM_LIST = "http://www.5ijq.cn/hongliu/mobcent/app/web/index.php?r=forum/topiclist/sortby/marrow/isImageList/1/page/";//论坛精华列表
	public static final String GET_HOT_FORUM = "http://www.5ijq.cn/App/Form" ;//论坛顶部轮播和论坛热点
	public static final String FORUM_URL = "http://www.5ijq.cn/hongliu/mobcent/app/web/index.php";
	public static final String FORUM_CREATE_POST_URL = "http://www.5ijq.cn/hongliu/mobcent/app/web/index.php?r=forum/topicadmin";
	public static final String FORUM_TOPIC_TYPE_LIST = "http://www.5ijq.cn/App/Form/getTypeListByFormId";
	public static final String FORUM_UPLOAD_PHOTO = "http://www.5ijq.cn/hongliu/mobcent/app/web/index.php?r=forum/sendattachmentex";
	public static final String FORUM_READLIY_TAKE = "http://www.5ijq.cn/app/Form/readilyTake";
}
