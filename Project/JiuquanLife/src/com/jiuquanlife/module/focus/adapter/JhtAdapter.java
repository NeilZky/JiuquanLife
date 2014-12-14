package com.jiuquanlife.module.focus.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.BitmapHelper;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.vo.PostInfo;
import com.lidroid.xutils.BitmapUtils;

/**
 * 
 * ¾«»ªÌû
 */
public class JhtAdapter extends BaseAdapter{

	private ArrayList<PostInfo> data;
	private Context context;
	private LayoutInflater inflater;
	private BitmapUtils bitmapUtils;
	
	public JhtAdapter(Context context) {
		
		inflater = LayoutInflater.from(context);
		bitmapUtils = BitmapHelper.getBitmapUtils(context
				.getApplicationContext());
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
	}
	
	@Override
	public int getCount() {
		return data == null ? 0 :data.size();
	}

	@Override
	public PostInfo getItem(int position) {
		return data == null ? null :data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_jht, null);
			holder = new Holder();
			holder.titleTv = (TextView) convertView.findViewById(R.id.tv_title_jht_adapter);
			holder.forwardCountTv = (TextView) convertView.findViewById(R.id.tv_forward_count_jht_adapter);
			holder.replyCountTv = (TextView) convertView.findViewById(R.id.tv_reply_count_jht_adapter);
			holder.timeTv = (TextView) convertView.findViewById(R.id.tv_time_jht_adapter);
			holder.photoIv = (ImageView) convertView.findViewById(R.id.iv_photo_jht_dapter);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		PostInfo postInfo = getItem(position);
		holder.titleTv.setText(postInfo.subject);
		holder.forwardCountTv.setText(String.valueOf(postInfo.views));
		holder.replyCountTv.setText(String.valueOf(postInfo.replies));
		holder.timeTv.setText(postInfo.dateline);
		bitmapUtils.display(holder.photoIv, UrlUtils.getPhotoUrl(postInfo.authorid));
		return convertView;
	}

	private static class Holder {
		
		TextView titleTv;
		ImageView photoIv;
		TextView forwardCountTv;
		TextView replyCountTv;
		TextView timeTv;
	}
	
	public void refresh(ArrayList<PostInfo> data) {
		
		this.data = data;
		notifyDataSetChanged();
	}
	
}
