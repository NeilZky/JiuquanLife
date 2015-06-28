package com.jiuquanlife.vo.forum.createpost;

import java.util.ArrayList;

import com.jiuquanlife.vo.forum.Content;

public class CreatePostJson {
	
	public int isHidden;
	public int fid;
	public int isShowPostion;
	public int isOnlyAuthor;
	public int isAnonymous;
	public int typeId;
	public String aid;
	public String title;
	public String location;
	public String longitude;
	public String latitude;
	public ArrayList<Content> content;
}
