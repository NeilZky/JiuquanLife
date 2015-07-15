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
	private Context thisContext;
	
	public LayoutInflater getInflater() {
		return inflater;
	}

	public BaseListAdapter(Context context) {
		
		this.thisContext = context;
		inflater = LayoutInflater.from(context);
	}
	
	public Context getContext() {
		return this.thisContext;
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
	
	public ArrayList<T> getData() {
		
		return data;
	}
	
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
		
	
	public void refresh(ArrayList<T> data) {
		
		this.data = data;
		notifyDataSetChanged();
	}


	public void add(ArrayList<T> data) {

		if (data != null && !data.isEmpty()) {
			if (this.data == null) {
				this.data = new ArrayList<T>();
			}
			this.data.addAll(data);
			notifyDataSetChanged();
		}
	}

	public void addItem(T item) {

		if (this.data == null) {
			this.data = new ArrayList<T>();
		}
		this.data.add(item);
		notifyDataSetChanged();
	}

	public void addAtPosition(int position, T item) {

		if (item != null) {
			if (this.data == null) {
				this.data = new ArrayList<T>();
			}
			this.data.add(position, item);
			notifyDataSetChanged();
		}
	}

	public T getLastItem() {

		if (data != null && !data.isEmpty()) {
			return data.get(data.size() - 1);
		}
		return null;
	}

	public T getFirstItem() {

		if (data != null && !data.isEmpty()) {
			return data.get(0);
		}
		return null;
	}

}
