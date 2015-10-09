package com.jiuquanlife.module.company.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.activity.LargePicActivity;
import com.jiuquanlife.module.company.entity.CommentInfo;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CommentAdapter extends BaseAdapter {

	private List<CommentInfo> commentInfos;
	private Activity context;
	private LayoutInflater mInflater;
	private ListView lv;
	DisplayImageOptions options;

	public CommentAdapter(List<CommentInfo> commentInfos, Activity mContext,
			ListView lv) {
		super();
		this.commentInfos = commentInfos;
		this.context = mContext;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.lv = lv;

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_default_house)
				.showImageForEmptyUri(R.drawable.ic_default_house)
				.showImageOnFail(R.drawable.ic_default_house)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(1000)).build();

	}

	public void refreshData(List<CommentInfo> commentInfos) {
		this.commentInfos = commentInfos;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == commentInfos ? 0 : commentInfos.size();
	}

	@Override
	public CommentInfo getItem(int arg0) {
		// TODO Auto-generated method stub
		return commentInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.comment_item, lv, false);
			holder = new ViewHolder();
			holder.tv_user = (TextView) convertView.findViewById(R.id.tv_user);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_comment = (TextView) convertView
					.findViewById(R.id.tv_comment);
			holder.ll_rate = (LinearLayout) convertView
					.findViewById(R.id.ll_rate);
			holder.ll_pic = (LinearLayout) convertView
					.findViewById(R.id.ll_pic);
			holder.iv_rate1 = (ImageView) convertView
					.findViewById(R.id.iv_rate1);
			holder.iv_rate2 = (ImageView) convertView
					.findViewById(R.id.iv_rate2);
			holder.iv_rate3 = (ImageView) convertView
					.findViewById(R.id.iv_rate3);
			holder.iv_rate4 = (ImageView) convertView
					.findViewById(R.id.iv_rate4);
			holder.iv_rate5 = (ImageView) convertView
					.findViewById(R.id.iv_rate5);
			holder.iv_pic1 = (ImageView) convertView.findViewById(R.id.iv_pic1);
			holder.iv_pic2 = (ImageView) convertView.findViewById(R.id.iv_pic2);
			holder.iv_pic3 = (ImageView) convertView.findViewById(R.id.iv_pic3);
			holder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CommentInfo commentInfo = commentInfos.get(position);

		int rate = commentInfo.getEvaluateTotal();

		switch (rate) {
		case 0:
			holder.iv_rate1
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate2
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate3
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate4
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate5
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			break;
		case 1:
			holder.iv_rate1
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate2
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate3
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate4
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate5
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			break;
		case 2:
			holder.iv_rate1
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate2
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate3
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate4
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate5
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			break;
		case 3:
			holder.iv_rate1
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate2
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate3
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate4
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			holder.iv_rate5
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			break;
		case 4:
			holder.iv_rate1
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate2
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate3
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate4
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate5
					.setBackgroundResource(R.drawable.ic_rating_star_small_off);
			break;
		case 5:
			holder.iv_rate1
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate2
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate3
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate4
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			holder.iv_rate5
					.setBackgroundResource(R.drawable.ic_rating_star_small_on);
			break;

		default:
			break;
		}

		holder.tv_user.setText(commentInfo.getUserName());
		holder.tv_time.setText(commentInfo.getCreatedate());
		holder.tv_comment.setText(commentInfo.getMessage());

		if (null != commentInfo.getImg() && !commentInfo.getImg().isEmpty()) {
			List<ImgInfo> imgInfos = commentInfo.getImg();
			System.out.println("commentInfo imgInfos:" + imgInfos);
			holder.ll_pic.setVisibility(View.VISIBLE);
			int length = imgInfos.size() > 3 ? 3 : imgInfos.size();
			switch (length) {
			case 0:
				holder.iv_pic1.setOnClickListener(null);
				holder.iv_pic2.setOnClickListener(null);
				holder.iv_pic3.setOnClickListener(null);

				break;
			case 1:
				holder.iv_pic2.setOnClickListener(null);
				holder.iv_pic3.setOnClickListener(null);
				break;
			case 2:
				holder.iv_pic3.setOnClickListener(null);
				break;
			default:
				break;
			}
			for (int i = 0; i < length; i++) {
				final ImgInfo imgInfo = imgInfos.get(i);
				if (i == 0) {
					holder.iv_pic1.setVisibility(View.VISIBLE);
					ImageLoader.getInstance()
							.displayImage(Common.PIC_PREFX + imgInfo.getPic(),
									holder.iv_pic1);
					holder.iv_pic1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(context,
									LargePicActivity.class);
							intent.putExtra("picPath", imgInfo.getPic());
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.getApplicationContext().startActivity(
									intent);
						}
					});
				} else if (i == 1) {
					holder.iv_pic2.setVisibility(View.VISIBLE);
					ImageLoader.getInstance()
							.displayImage(Common.PIC_PREFX + imgInfo.getPic(),
									holder.iv_pic2);
					holder.iv_pic2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(context,
									LargePicActivity.class);
							intent.putExtra("picPath", imgInfo.getPic());
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.getApplicationContext().startActivity(
									intent);
						}
					});
				} else if (i == 2) {
					holder.iv_pic3.setVisibility(View.VISIBLE);
					ImageLoader.getInstance()
							.displayImage(Common.PIC_PREFX + imgInfo.getPic(),
									holder.iv_pic3);
					holder.iv_pic3.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(context,
									LargePicActivity.class);
							intent.putExtra("picPath", imgInfo.getPic());
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.getApplicationContext().startActivity(
									intent);
						}
					});
				}

			}
		} else {
			holder.iv_pic1.setOnClickListener(null);
			holder.iv_pic2.setOnClickListener(null);
			holder.iv_pic3.setOnClickListener(null);
		}

		ImageLoader.getInstance().displayImage(
				Common.LOVE_USER_IMG + commentInfo.getUid(), holder.iv_user,
				options);

		return convertView;
	}

	static class ViewHolder {
		TextView tv_user, tv_time, tv_comment;
		LinearLayout ll_rate, ll_pic;
		ImageView iv_rate1, iv_rate2, iv_rate3, iv_rate4, iv_rate5, iv_pic1,
				iv_pic2, iv_pic3, iv_user;

	}

}
