package com.jiuquanlife.view.popuplist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuquanlife.R;

public class PopupAdapter <T> extends BaseAdapter {
    private int resource;
    private int normalBg;
    private int pressBg;
    private int selection;
    private ArrayList<T> data;
    private Context context;

    public PopupAdapter(Context context, int resource, int normalBg, int pressBg) {
    	
    	this.context = context;
        initParams(resource, normalBg, pressBg);
    }

    
    public void refresh(ArrayList<T> data) {
    	
    	this.data = data;
    	notifyDataSetChanged();
    }
    

    private void initParams(int resource, int normalBg, int pressBg){
        this.resource = resource;
        this.normalBg = normalBg;
        this.pressBg = pressBg;
        this.selection = -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T s = getItem(position);
        View view;
        ViewHolder holder;
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(resource,null);
            holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.tv);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(s.toString());
        if(position == selection) {
            holder.tv.setBackgroundResource(pressBg);
        } else {
            holder.tv.setBackgroundResource(normalBg);
        }
        return view;
    }

    public void setPressPostion(int position) {
        this.selection = position;
    }
    class ViewHolder{
        TextView tv;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data ==null ?0 : data.size();
	}


	@Override
	public T getItem(int position) {
		return data ==null ? null : data.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}
}
