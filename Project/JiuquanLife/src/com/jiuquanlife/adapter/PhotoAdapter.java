package com.jiuquanlife.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jiuquanlife.R;
import com.jiuquanlife.entity.Photo;
import com.jiuquanlife.utils.BitmapUtils;
import com.jiuquanlife.utils.PhotoManager;
import com.jiuquanlife.view.LinearListView;
public class PhotoAdapter extends BaseAdapter implements OnItemClickListener {

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<Photo> photos;
	private PhotoManager photoManager = PhotoManager.getInstance();
	private AlertDialog dialog;
	private ImageView photoIv;
	private ImageButton deletePhotoBtn;
	private int position;
	private int screenWidth;
	private int screenHeight;
	private LinearListView llv;
	
	public PhotoAdapter(Context context) {
		
		this.context = context;
		inflater = LayoutInflater.from(context);
		DisplayMetrics dm = new DisplayMetrics();
		Activity activity = (Activity) context;;
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}
	
	
	@Override
	public int getCount() {

		return null == photos ? 0 : photos.size();
	}

	@Override
	public Photo getItem(int position) {

		return null == photos ? null : photos.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.adapter_photo,
					null);
			holder = new ViewHolder();
			holder.photo = (ImageView) convertView
					.findViewById(R.id.photo_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.photo.setImageBitmap(photos.get(position).getBitmap());
		final ImageView photoIv = holder.photo;
		final int id = position;
		holder.photo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				onItemClick(null, photoIv, id, id);
			}
		});
		return convertView;
	}
		
	public LinearListView getLlv() {
		return llv;
	}


	public void setLlv(LinearListView llv) {
		this.llv = llv;
	}

	public void refreshData() {
		
		notifyDataSetChanged();
		llv.notifyDataSetChanged();
	}
	
	private static class ViewHolder {

		private ImageView photo;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (null == dialog) {
			View content = inflater.inflate(R.layout.large_photo_dialog, null);
			dialog = new AlertDialog.Builder(context).create();
			photoIv = (ImageView) content.findViewById(R.id.iv_large_photo);
			deletePhotoBtn = (ImageButton) content
					.findViewById(R.id.btn_large_photo_delete);
			dialog.setView(content);
			content.setOnClickListener(onClickListener);
			deletePhotoBtn.setOnClickListener(onClickListener);
			dialog.setOnDismissListener(onDismissListener);
		}
		this.position = position;
		String photoPath = photos.get(position).getFilePath();


		Bitmap largePhoto = BitmapUtils.decodeSampledBitmapFromSDCard(photoPath, screenWidth, screenHeight);
		photoIv.setImageBitmap(largePhoto);
		dialog.show();
	}
	
	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	private DialogInterface.OnDismissListener onDismissListener = new DialogInterface.OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface dialog) {
			photoIv.setImageBitmap(null);
		}
		
	};

	private OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_large_photo_delete:
				deletePhoto();
				refreshData();
				break;
			default:
				break;
			}
			dialog.dismiss();
		}
	};

	private void deletePhoto() {

		Photo photo = getItem(position);
		if(photos!=null && photo != null) {
			photos.remove(photo);
		}
		refreshData();
	}
	
	public void addPhoto(String path) {
		
		if(!containPhoto(path)) {
			Photo photo = photoManager.getThumbnailPhoto(path);
			if(photo!=null) {
				if(photos == null) {
					photos = new ArrayList<Photo>();
				}
				photos.add(photo);
				refreshData();
			}
		}
	}
	
	private boolean containPhoto(String path) {
		
		if(photos!=null&& path !=null) {
			for(Photo photo : photos) {
				if(path.equals(photo.getFilePath())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
