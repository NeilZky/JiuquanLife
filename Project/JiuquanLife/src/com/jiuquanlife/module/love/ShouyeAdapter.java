package com.jiuquanlife.module.love;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.Util;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.jiuquanlife.module.forum.activity.PostDetailActivity;
import com.jiuquanlife.module.love.entity.LoveInfo;
import com.jiuquanlife.module.publish.ShowServerImageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ShouyeAdapter extends BaseAdapter {
	private Activity context;
	private LayoutInflater mInflater;
	private List<LoveInfo> infos;
	DisplayImageOptions options;

	public ShouyeAdapter(Activity context, List<LoveInfo> infos) {
		super();
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.infos = infos;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_default_house)
				.showImageForEmptyUri(R.drawable.ic_default_house)
				.showImageOnFail(R.drawable.ic_default_house)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(1000)).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == infos ? 0 : infos.size();
	}

	@Override
	public LoveInfo getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void refreshData(List<LoveInfo> infos) {
		this.infos = infos;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		// 优化listview
		if (convertView == null) {
			// 使用自定义的布局
			convertView = mInflater.inflate(R.layout.item_love_people, parent,
					false);
			holder = new ViewHolder();
			holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
			holder.iv_gender = (ImageView) convertView
					.findViewById(R.id.iv_gender);
			holder.iv_share = (ImageView) convertView
					.findViewById(R.id.iv_share);
			holder.iv_shoucang = (ImageView) convertView
					.findViewById(R.id.iv_shoucang);

			holder.iv_pic1 = (ImageView) convertView.findViewById(R.id.iv_pic1);
			holder.iv_pic2 = (ImageView) convertView.findViewById(R.id.iv_pic2);
			holder.iv_pic3 = (ImageView) convertView.findViewById(R.id.iv_pic3);
			holder.iv_pic4 = (ImageView) convertView.findViewById(R.id.iv_pic4);

			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
			holder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
			holder.tv_huifu = (TextView) convertView
					.findViewById(R.id.tv_huifu);
			holder.tv_info = (TextView) convertView.findViewById(R.id.tv_info);

			holder.ll_iv = (LinearLayout) convertView.findViewById(R.id.ll_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final LoveInfo loveInfo = infos.get(position);
		holder.tv_title.setText(loveInfo.getTitle());
		holder.tv_desc.setText(loveInfo.getSubject());
		holder.tv_info.setText(loveInfo.getAutor());

		holder.tv_time.setText(loveInfo.getDateline());

		holder.iv_share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				StringBuilder sb = new StringBuilder();
				sb.append(loveInfo.getAutor() + " ");
				sb.append("发布::\n");
				sb.append(loveInfo.getTitle() + "\n");
				sb.append(loveInfo.getSubject());
				Util.shareContent(sb.toString(), context);

			}
		});
		
		holder.tv_huifu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

//				try {
//					Intent intent = new Intent(cont,
//							PostDetailActivity.class);
//					intent.putExtra(PostDetailActivity.EXTRA_TOPIC_ID,
//							Integer.valueOf(info.getTid()));
//					getActivity().startActivity(intent);
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
				
			}
		});

		if ("1".equals(loveInfo.getGender())) {
			holder.iv_gender.setImageResource(R.drawable.shouye_male);
		} else {
			holder.iv_gender.setImageResource(R.drawable.shouye_female);
		}
		if (null != loveInfo.getImgList() && !loveInfo.getImgList().isEmpty()) {
			// holder.ll_iv.setVisibility(View.VISIBLE);

			holder.ll_iv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					ArrayList<ImgInfo> imgInfos = new ArrayList<ImgInfo>();
					for (int i = 0; i < loveInfo.getImgList().size(); i++) {
						String path = loveInfo.getImgList().get(i);
						ImgInfo info = new ImgInfo();
						info.setPic(path);
						imgInfos.add(info);
					}

					Intent intent = new Intent(context,
							ShowServerImageActivity.class);
					intent.putExtra("fromWhere",
							ShowServerImageActivity.FROM_LOVE);
					intent.putParcelableArrayListExtra("imgInfos", imgInfos);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.getApplicationContext().startActivity(intent);

				}
			});

			for (int i = 0; i < loveInfo.getImgList().size() && i <= 3; i++) {
				ImageView iv = null;
				String path = loveInfo.getImgList().get(i);
				if (i == 0) {
					iv = holder.iv_pic1;
				} else if (i == 1) {
					iv = holder.iv_pic2;
				} else if (i == 2) {
					iv = holder.iv_pic3;
				} else if (i == 3) {
					iv = holder.iv_pic4;
				}
				System.out.println("Common.LOVE_IMG_PREFX + path:"
						+ Common.LOVE_IMG_PREFX + path);
				ImageLoader.getInstance().displayImage(
						Common.LOVE_IMG_PREFX + path, iv, App.getOptions());
				i++;
			}
		} else {
			// holder.ll_iv.setVisibility(View.GONE);
		}
		ImageLoader.getInstance().displayImage(
				Common.LOVE_USER_IMG + loveInfo.getAuthorid(), holder.iv_img,
				options);

		return convertView;
	}

	static class ViewHolder {
		ImageView iv_img, iv_gender, iv_shoucang, iv_share, iv_pic1, iv_pic2,
				iv_pic3, iv_pic4;
		TextView tv_title, tv_desc, tv_zan, tv_huifu, tv_info, tv_time;
		LinearLayout ll_iv;
	}

}
