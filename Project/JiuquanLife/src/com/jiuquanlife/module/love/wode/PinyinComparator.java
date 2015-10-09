package com.jiuquanlife.module.love.wode;

import java.util.Comparator;

import com.jiuquanlife.module.love.entity.FriendInfo;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<FriendInfo> {

	public int compare(FriendInfo o1, FriendInfo o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
