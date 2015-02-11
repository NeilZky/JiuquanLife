package com.jiuquanlife.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseListAdapter<T> extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<T> data;
	
	public LayoutInflater getInflater() {
		return inflater;
	}

	public BaseListAdapter(Context context) {
		
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		
		return data == null ? 0 : data.size();
	}

	@Override
	public T getItem(int position) {
		
		return data == null ? null : data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
		
	
	public void refresh(ArrayList<T> data) {
		
		this.data = data;
		notifyDataSetChanged();
	}
	
}
