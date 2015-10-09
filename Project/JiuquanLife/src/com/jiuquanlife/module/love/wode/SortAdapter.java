package com.jiuquanlife.module.love.wode;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.module.love.entity.FriendInfo;
import com.jiuquanlife.module.love.entity.FromInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
	private List<FriendInfo> list = null;
	private Context mContext;
	DisplayImageOptions options;

	public SortAdapter(Context mContext, List<FriendInfo> list) {
		this.mContext = mContext;
		this.list = list;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_default_house)
				.showImageForEmptyUri(R.drawable.ic_default_house)
				.showImageOnFail(R.drawable.ic_default_house)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(1000)).build();
	}

	/**
	 * ��ListView���ݷ����仯ʱ,���ô˷���������ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<FriendInfo> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public FriendInfo getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final FriendInfo mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.love_friend_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_baseinfo);
			viewHolder.iv_user = (ImageView) view.findViewById(R.id.iv_user);
			viewHolder.tvSecondInfo = (TextView) view
					.findViewById(R.id.tv_secondinfo);
			viewHolder.iv_gender = (ImageView) view
					.findViewById(R.id.iv_gender);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		FromInfo fromInfo = mContent.getFromInfo();
		viewHolder.tvTitle.setText(fromInfo.getNickname());
		StringBuilder sb = new StringBuilder();

		sb.append(fromInfo.getAge() + "�� ");
		sb.append(fromInfo.getLocation() + " ");
		sb.append(fromInfo.getStature() + "cm ");
		sb.append(fromInfo.getEdu());
		viewHolder.tvSecondInfo.setText(sb.toString());
		String imagePath = fromInfo.getHeadsavepath()
				+ fromInfo.getHeadsavename();
		System.out.println("imagePath:" + "http://www.5ijq.cn/public/uploads/"
				+ imagePath);
		ImageLoader.getInstance().displayImage(
				"http://www.5ijq.cn/public/uploads/" + imagePath,
				viewHolder.iv_user, options);
		return view;

	}

	final static class ViewHolder {
		ImageView iv_user, iv_gender;
		TextView tvTitle;
		TextView tvSecondInfo;
	}

	/**
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}