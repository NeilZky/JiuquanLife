package com.jiuquanlife.vo.convertor;

import java.util.ArrayList;
import java.util.Date;

import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.TimeUtils;
import com.jiuquanlife.vo.FocusInfo;
import com.jiuquanlife.vo.ImgData;
import com.jiuquanlife.vo.PhotoInfo;
import com.jiuquanlife.vo.PostInfo;
import com.jiuquanlife.vo.forum.PostItem;

public class ConvertUtils {

	public static ArrayList<PhotoInfo> convertToPhotoInfos(FocusInfo focusInfo) {

		if (focusInfo != null && focusInfo.data != null
				&& focusInfo.data.focusImgs != null
				&& !focusInfo.data.focusImgs.isEmpty()) {
			ArrayList<ImgData> focusImgs = focusInfo.data.focusImgs;
			ArrayList<PhotoInfo> res = new ArrayList<PhotoInfo>();
			for (ImgData temp : focusImgs) {
				PhotoInfo photoInfo = new PhotoInfo(temp.tid,
						"http://www.5ijq.cn" + temp.img, temp.subject);
				res.add(photoInfo);
			}
			return res;
		}
		return null;
	}

	public static ArrayList<PhotoInfo> convertToPhotoInfos(
			ArrayList<ImgData> imgDatas) {

		if (imgDatas != null && !imgDatas.isEmpty()) {
			ArrayList<ImgData> focusImgs = imgDatas;
			ArrayList<PhotoInfo> res = new ArrayList<PhotoInfo>();
			for (ImgData temp : focusImgs) {
				PhotoInfo photoInfo = new PhotoInfo(temp.tid,
						"http://www.5ijq.cn" + temp.img, temp.subject);
				res.add(photoInfo);
			}
			return res;
		}
		return null;
	}

	public static ArrayList<PostItem> convertPosts(ArrayList<PostInfo> postInfos) {

		if (postInfos == null || postInfos.isEmpty()) {
			return null;
		}
		ArrayList<PostItem> res = new ArrayList<PostItem>();
		for (PostInfo postInfo : postInfos) {
			PostItem postItem = new PostItem();
			postItem.user_id = Integer.parseInt(postInfo.authorid);
			postItem.user_nick_name = postInfo.author;
			postItem.replies = Integer.parseInt(postInfo.replies);
			postItem.title = postInfo.title;
			postItem.subject = postInfo.subject;
			if(!StringUtils.isNullOrEmpty(postInfo.dateline)) {
				Date date = TimeUtils.getDateFromString(postInfo.dateline);
				if(date!=null) {
					postItem.last_reply_date = String.valueOf(date.getTime());
				}
			}
			
			if(!StringUtils.isNullOrEmpty(postInfo.gender)) {
				postItem.gender = Integer.parseInt(postInfo.gender);
			}
			
			if(postInfo.imgList!=null && !postInfo.imgList.isEmpty()) {
				postItem.imageList = new ArrayList<String>();
				for(String img : postInfo.imgList) {
					postItem.imageList.add(CommonConstance.URL_PREFIX + img);
				}
			}
			res.add(postItem);
		}
		return res;

	}

}
