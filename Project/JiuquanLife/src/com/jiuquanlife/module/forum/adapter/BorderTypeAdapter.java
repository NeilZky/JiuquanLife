package com.jiuquanlife.module.forum.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jiuquanlife.view.ChoiceDialogAdapter;
import com.jiuquanlife.vo.forum.BorderType;

public class BorderTypeAdapter extends ChoiceDialogAdapter{
	
	private List<BorderType> data;
	
	public BorderTypeAdapter(List<BorderType> data) {
		
		this.data = data;
	}
	
	@Override
	public List<String> getChoiceStrs() {
		
		if(data!=null) {
			ArrayList<String> res = new ArrayList<String>();
			for(BorderType temp : data) {
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
