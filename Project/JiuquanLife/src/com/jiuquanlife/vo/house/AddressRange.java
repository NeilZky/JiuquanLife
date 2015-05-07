package com.jiuquanlife.vo.house;

import java.io.Serializable;
import java.util.ArrayList;

public class AddressRange implements Serializable {

	private static final long serialVersionUID = -2922134722171862178L;
	public String aid;
	public String pid;
	public String level;
	public String addressName;
	public ArrayList<AddressRange> subAddressList;
	
	@Override
	public String toString() {
		
		return addressName;
	}
}
