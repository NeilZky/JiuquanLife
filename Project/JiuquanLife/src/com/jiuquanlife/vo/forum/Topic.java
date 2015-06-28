package com.jiuquanlife.vo.forum;

import java.io.Serializable;
import java.util.ArrayList;

public class Topic implements Serializable{
	
	private static final long serialVersionUID = 1761419638613910534L;
	
	public int board_category_id;
	public String board_category_name;
	public int board_category_type;
	public ArrayList<Border> board_list;
}
