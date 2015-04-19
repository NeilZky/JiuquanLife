package com.jiuquanlife.module.house.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jiuquanlife.view.ChoiceDialogAdapter;
import com.jiuquanlife.vo.house.CommonType;

public class CommonTypeAdapter extends ChoiceDialogAdapter{
	
	private List<CommonType> data;
	
	public CommonTypeAdapter(List<CommonType> data) {
		
		this.data = data;
	}
	
	@Override
	public List<String> getChoiceStrs() {
		
		if(data!=null) {
			ArrayList<String> res = new ArrayList<String>();
			for(CommonType temp : data) {
				res.add(temp.name);
			}
			return res;
		}
		return null;
	}

	@Override
	public List<?> getData() {
		
		return data;
	}

}
