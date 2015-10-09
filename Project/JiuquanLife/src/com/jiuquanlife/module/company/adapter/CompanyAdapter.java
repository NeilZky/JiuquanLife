package com.jiuquanlife.module.company.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.entity.CompanyInfo;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CompanyAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<CompanyInfo> mCompanyInfos;

	public CompanyAdapter(Context context, List<CompanyInfo> mCompanyInfos) {
		super();
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mCompanyInfos = mCompanyInfos;

	}

	public void setData(List<CompanyInfo> mCompanyInfos) {
		this.mCompanyInfos = mCompanyInfos;
	}

	public void updateData(List<CompanyInfo> mCompanyInfos) {
		this.mCompanyInfos = mCompanyInfos;
		System.out.println("UpdateDataSize:"+mCompanyInfos.size());
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == mCompanyInfos ? 0 : mCompanyInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mCompanyInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		// �Ż�listview
		if (convertView == null) {
			// ʹ���Զ���Ĳ���
			convertView = mInflater.inflate(R.layout.company_item, parent,
					false);
			holder = new ViewHolder();
			// ��ʼ�������е�Ԫ��

			holder.itemsIcon = (ImageView) convertView
					.findViewById(R.id.itemsIcon);

			holder.range = (TextView) convertView.findViewById(R.id.tv_address);
			holder.shortTitle = (TextView) convertView
					.findViewById(R.id.tv_shorttitle);
			holder.commentNum = (TextView) convertView
					.findViewById(R.id.tv_commentnum);
			holder.distance = (TextView) convertView
					.findViewById(R.id.tv_distance);
			holder.visitNum = (TextView) convertView
					.findViewById(R.id.tv_visitnum);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// ��ȡ���е�����
		final CompanyInfo merch = (CompanyInfo) mCompanyInfos.get(position);
		// �õ�ͼƬ·��
		ImgInfo imgInfo = merch.getImg();
		String imageUrl = imgInfo.getPic();
		// ������������
		// ����

		// �۸�
		holder.commentNum.setText("(" + merch.getCommentCount() + "����"+")");
		// ����
		holder.range.setText(merch.getAddress() );
		// �̱���
		holder.shortTitle.setText(merch.getCompany());
		String distance = "";
		if (merch.getDistance() < 4000) {
			distance = merch.getDistance() + "m";
		} else {
			int dis = merch.getDistance() / 1000 + 1;
			distance = "<" + dis + "km";
		}
		holder.distance.setText(distance);
		holder.visitNum.setText("������: " + merch.getVisitNum() );
		System.out.println("image url:" + Common.PIC_PREFX + imageUrl);
		ImageLoader.getInstance().displayImage(Common.PIC_PREFX + imageUrl,
				holder.itemsIcon, App.getOptions());

		return convertView;
	}

	/**
	 * �����е�Ԫ��
	 */
	static class ViewHolder {
		ImageView itemsIcon;
		TextView shortTitle;
		TextView range;
		TextView name;
		TextView commentNum;
		TextView distance;
		TextView visitNum;
	}
}
