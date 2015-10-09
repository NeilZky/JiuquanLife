package com.jiuquanlife.module.publish.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.Util;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.jiuquanlife.module.publish.ShowServerImageActivity;
import com.jiuquanlife.module.publish.entity.PublishInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class PublishAdapter extends BaseAdapter {
	private List<PublishInfo> infos;
	private Activity context;
	private LayoutInflater mInflater;

	public PublishAdapter(List<PublishInfo> publishInfos, Activity context) {
		super();
		this.infos = publishInfos;
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public PublishInfo getItem(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public void refreshData(List<PublishInfo> publishInfos) {
		infos = publishInfos;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.publish_item, parent,
					false);
			holder = new ViewHolder();
			holder.iv_pl_item_icon = (ImageView) convertView
					.findViewById(R.id.iv_pl_item_icon);
			holder.iv_pl_item_share = (ImageView) convertView
					.findViewById(R.id.iv_pl_item_share);
			holder.iv_pl_item_img = (ImageView) convertView
					.findViewById(R.id.iv_pl_item_img);
			holder.iv_pl_item_contact = (ImageView) convertView
					.findViewById(R.id.iv_pl_item_contact);
			holder.iv_pl_item_phone = (ImageView) convertView
					.findViewById(R.id.iv_pl_item_phone);
			holder.tv_pl_item_desc = (TextView) convertView
					.findViewById(R.id.tv_pl_item_desc);
			holder.tv_imgnum = (TextView) convertView
					.findViewById(R.id.tv_imgnum);
			holder.tv_pl_item_name = (TextView) convertView
					.findViewById(R.id.tv_pl_item_name);
			holder.iv_pl_item_contact_name = (TextView) convertView
					.findViewById(R.id.iv_pl_item_contact_name);
			holder.iv_pl_item_contact_date = (TextView) convertView
					.findViewById(R.id.iv_pl_item_contact_date);
			holder.img_container = (FrameLayout) convertView
					.findViewById(R.id.img_container);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		final PublishInfo publishInfo = infos.get(position);
		if (null == publishInfo.getCoverPics()
				|| publishInfo.getCoverPics().isEmpty()) {
			holder.img_container.setVisibility(View.GONE);
			System.out.println("publish info is null");
		} else {
			holder.img_container.setVisibility(View.VISIBLE);
			final ArrayList<ImgInfo> imgInfos = publishInfo.getCoverPics();
			final ImgInfo imgInfo = imgInfos.get(0);
			ImageLoader.getInstance().displayImage(
					Common.PIC_PREFX + imgInfo.getPic(), holder.iv_pl_item_img);
			holder.iv_pl_item_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context,
							ShowServerImageActivity.class);
					intent.putParcelableArrayListExtra("imgInfos", imgInfos);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.getApplicationContext().startActivity(intent);
				}

			});
			System.out.println(imgInfos.size() + "图");
			holder.tv_imgnum.setText(imgInfos.size() + "图");
		}
		ImageLoader.getInstance().displayImage(
				Common.COMMON_USER_PHOTO + publishInfo.getUid(),
				holder.iv_pl_item_contact);

		String type = publishInfo.getInfoTypeCss();
		if (null != type) {
			if (type.equals("job")) {
				holder.iv_pl_item_icon.setBackgroundResource(R.drawable.job);
			} else if (type.equals("rental")) {
				holder.iv_pl_item_icon.setBackgroundResource(R.drawable.rental);
			} else if (type.equals("zhuan")) {
				holder.iv_pl_item_icon.setBackgroundResource(R.drawable.zhuan);
			} else if (type.equals("tui")) {
				holder.iv_pl_item_icon.setBackgroundResource(R.drawable.tui);
			} else if (type.equals("other")) {
				holder.iv_pl_item_icon.setBackgroundResource(R.drawable.other);
			} else if (type.equals("shou")) {
				holder.iv_pl_item_icon.setBackgroundResource(R.drawable.shou);
			}
		}
		holder.tv_pl_item_name.setText(publishInfo.getInfoTitle());
		holder.tv_pl_item_desc.setText(publishInfo.getContent());

		holder.iv_pl_item_contact_date.setText("发布于:"
				+ publishInfo.getDateline());
		holder.iv_pl_item_contact_name.setText(publishInfo.getUserName());
		final String phoneNum = publishInfo.getTelNum();
		if (!TextUtils.isEmpty(publishInfo.getTelNum())) {
			holder.iv_pl_item_phone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri
							.parse("tel:" + phoneNum));
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);

				}
			});

		}
		holder.iv_pl_item_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final UMSocialService mController = UMServiceFactory
						.getUMSocialService("com.umeng.share");
				// 设置分享内容
				StringBuffer sb = new StringBuffer();
				sb.append(publishInfo.getUserName());
				sb.append(" 发布:\n");
				sb.append(publishInfo.getContent());
				sb.append("\n");
				sb.append("联系电话:");
				sb.append(publishInfo.getTelNum());
				Util.shareContent(sb.toString(), context);

			}
		});
		return convertView;
	}

	public static class ViewHolder {
		ImageView iv_pl_item_icon, iv_pl_item_share, iv_pl_item_img,
				iv_pl_item_contact, iv_pl_item_phone;
		TextView tv_pl_item_name, tv_pl_item_desc, iv_pl_item_contact_name,
				iv_pl_item_contact_date, tv_imgnum;
		FrameLayout img_container;
	}

}
