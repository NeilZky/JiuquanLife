package com.jiuquanlife.module.company;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.jiuquanlife.entity.User;
import com.jiuquanlife.module.company.entity.AddressInfo;
import com.jiuquanlife.module.publish.PublishActivity;
import com.jiuquanlife.utils.SharePreferenceUtils;

public class Common {
	public static final String TAG = "Company";
	public static final String PIC_PREFX = "http://www.5ijq.cn/Public/Uploads/";
	public static final String COMPANY_URL = "http://www.5ijq.cn/App/Company/index";
	public static final String CATEGORY_DETAIL_URL = "http://www.5ijq.cn/App/Company/lists";

	public static final String COMPANY_DETAIL_URL = "http://www.5ijq.cn/App/Company/detial/companyId/";
	public static final String COMPANY_HOT_SEARCH_URL = "http://www.5ijq.cn/App/Company/getSerachTopWords";
	public static final String SUBMIT_COMMENT_URL = "http://www.5ijq.cn/App/Company/addComment";
	public static final String COMMENT_LIST_URL = "http://www.5ijq.cn/App/Company/commentList";

	public static final String PUBLISH_SUBMIT = "http://www.5ijq.cn/App/Info/addInfo";
	public static final String PUBLISH_INDEX = "http://www.5ijq.cn/App/Info/index";
	public static final String PUBLISH_HOT_SEARCH = "http://www.5ijq.cn/App/Info/getSerachTopWords";
	public static final String PUBLISH_ADDINFO = "http://www.5ijq.cn/App/Info/newInfo";
	public static final String PUBLISH_TYPES = "http://www.5ijq.cn/App/Info/types";
	public static final String COMMON_USER_PHOTO = "http://www.5ijq.cn/hongliu/uc_server/avatar.php?size=smal&uid=";

	public static final String LOVE_PREFEX = "http://www.5ijq.cn";
	public static final String LOVE_SEARCH_PEOPLE = "http://www.5ijq.cn";
	public static final String LOVE_IMG_PREFX = "http://www.5ijq.cn";
	public static final String LOVE_SHOUYE_URL = "http://www.5ijq.cn/App/Marry/index";
	public static final String LOVE_USER_IMG = "http://www.5ijq.cn/hongliu/uc_server/avatar.php?size=small&uid=";
	public static final String LOVE_SEARCH_URL = LOVE_PREFEX
			+ "/App/Marry/findList";

	public static final String LOVE_DETAIL_URL = LOVE_PREFEX
			+ "/App/Marry/detial";

	public static final String LOVE_WO_DE = LOVE_PREFEX + "/App/Marry/myMarry";

	public static final String LOVE_DETAIL_BASE_URL = LOVE_PREFEX
			+ "/App/Marry/basicInfo";

	public static final String LOVE_USER_GALLERY_URL = LOVE_PREFEX
			+ "/App/Marry/photoList";

	public static final String LOVE_SUBMIT_IMG = LOVE_PREFEX
			+ "/App/Marry/saveUserPhoto";

	public static final String LOVE_FRIEND_LIST = LOVE_PREFEX
			+ "/App/Marry/myFriendList";

	public static final String LOVE_SAY_HI_LIST = LOVE_PREFEX
			+ "/App/Marry/hiList";

	public static final String LOVE_HANDLE_SAY_HI = LOVE_PREFEX
			+ "/App/Marry/doHello";

	public static final String LOVE_SAY_HI = LOVE_PREFEX
			+ "/App/Marry/sayHello";

	public static final String LOVE_DIAN_ZAN = LOVE_PREFEX
			+ "/App/Marry/addZan";

	public static final String LOVE_SHOUCHANGE = LOVE_PREFEX
			+ "/App/Marry/addFavorite";

	public static final String LOVE_GET_TOKEN = "http://www.5ijq.cn/App/RongClound/getRongCloundToken";

	public static final String LOVE_SAVE_USERINFO = LOVE_PREFEX
			+ "/App/Marry/updateMarryInfo";

	public static List<AddressInfo> ADDRESSINFOS;

	static {
		ADDRESSINFOS = new ArrayList<AddressInfo>();
		AddressInfo addressInfo = new AddressInfo(2, 1, 1, "肃州区");
		ADDRESSINFOS.add(addressInfo);

		AddressInfo addressInfo2 = addressInfo.cloneAddess();
		addressInfo2.setAddressName("嘉峪关");
		addressInfo2.setAid(3);

		ADDRESSINFOS.add(addressInfo2);
		AddressInfo addressInfo3 = addressInfo.cloneAddess();
		addressInfo3.setAddressName("敦煌市");
		addressInfo3.setAid(4);

		ADDRESSINFOS.add(addressInfo3);
		AddressInfo addressInfo4 = addressInfo.cloneAddess();
		addressInfo4.setAddressName("金塔县");
		addressInfo4.setAid(11);
		ADDRESSINFOS.add(addressInfo4);
		AddressInfo addressInfo5 = addressInfo.cloneAddess();
		addressInfo5.setAddressName("玉门市");
		addressInfo5.setAid(12);
		ADDRESSINFOS.add(addressInfo5);
		AddressInfo addressInfo6 = addressInfo.cloneAddess();
		addressInfo6.setAddressName("肃北县");
		addressInfo6.setAid(13);
		ADDRESSINFOS.add(addressInfo6);
		AddressInfo addressInfo7 = addressInfo.cloneAddess();
		addressInfo7.setAddressName("阿克塞县");
		addressInfo7.setAid(14);
		ADDRESSINFOS.add(addressInfo7);
		AddressInfo addressInfo8 = addressInfo.cloneAddess();
		addressInfo8.setAddressName("瓜州县");
		addressInfo8.setAid(15);
	}

	private static final double EARTH_RADIUS = 6378.137;// 地球半径

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static String username = "";
	public static boolean isLogin = true;

	public static String getMid() {
		return SharePreferenceUtils.getValue("MID", "");
	}

	public static void saveMid(String mid) {
		SharePreferenceUtils.setValue("MID", mid);
	}

	public static String getUid() {
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER,
				User.class);
		if (null != user) {
			return user.uid + "";
		}
		return "";
	}

	public static String getUserName() {
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER,
				User.class);
		if (null != user) {
			return user.userName;
		}
		return "";
	}

	public static void checkLogin(Activity activity) {
		if (TextUtils.isEmpty(getUid())) {
			activity.finish();
			Toast.makeText(activity, "请登录", Toast.LENGTH_LONG).show();
			return;
		}
	}
}
