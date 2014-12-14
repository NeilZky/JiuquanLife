package com.jiuquanlife.vo.convertor;

import java.util.ArrayList;

import com.jiuquanlife.vo.FocusImg;
import com.jiuquanlife.vo.FocusInfo;
import com.jiuquanlife.vo.PhotoInfo;
import com.jiuquanlife.vo.UserInfo;

public class ConvertUtils {

	public static ArrayList<PhotoInfo> convertToPhotoInfos(FocusInfo focusInfo) {

		if (focusInfo != null && focusInfo.data != null
				&& focusInfo.data.focusImgs != null
				&& !focusInfo.data.focusImgs.isEmpty()) {
			ArrayList<FocusImg> focusImgs = focusInfo.data.focusImgs;
			ArrayList<PhotoInfo> res = new ArrayList<PhotoInfo>();
			for(FocusImg temp : focusImgs) {
				PhotoInfo photoInfo = new PhotoInfo(temp.tid,"http://www.5ijq.cn"+temp.img, temp.subject);
				res.add(photoInfo);
			}
			return res;
		}
		return null;
	}
}
