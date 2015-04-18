package com.jiuquanlife.module.house.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jiuquanlife.view.ChoiceDialogAdapter;

public class FloorAdapter extends ChoiceDialogAdapter{
	
	private List<Floor> data;
	
	public FloorAdapter() {
		
		data = new ArrayList<Floor>();
		for(int i =0 ; i< 40; i++) {
			data.add(new Floor(i+1));
		}
	}
	
	@Override
	public List<String> getChoiceStrs() {
		
		if(data!=null) {
			ArrayList<String> res = new ArrayList<String>();
			for(Floor temp : data) {
				res.add(temp.id);
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
