package com.jiuquanlife.vo.forum;

import java.util.ArrayList;

public class Reply {
	
	public int reply_id;
	public String posts_date;
	public int reply_posts_id;
	public String reply_name;
	public String icon;
	public String userTitle;
	public int position;
	public int is_quote;
	public String quote_content;
	public ArrayList<Content> reply_content;
	
}
