package com.jiuquanlife.module.forum.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.view.GridLinearLayout;
import com.jiuquanlife.view.LinearListView;
import com.jiuquanlife.vo.forum.Topic;

/**
 * 
 * °å¿éÁÐ±í
 */
public class TopicListAdapter extends BaseListAdapter<Topic> implements
		OnItemClickListener {

	public TopicListAdapter(Context context) {
		super(context);
	}
	
	private boolean selectBorder;
	
	
	public void setSelectBorder(boolean selectBorder) {
		this.selectBorder = selectBorder;
	}

	private ArrayList<Boolean> childVisiableList;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder = null;
		if (convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_toplic_list,
					null);
			holder = new Holder();
			holder.iv_topic_img = (ImageView) convertView
					.findViewById(R.id.iv_topic_img);
			holder.iv_topic_arrow = (ImageView) convertView
					.findViewById(R.id.iv_topic_arrow);
			holder.tv_topic_name = (TextView) convertView
					.findViewById(R.id.tv_topic_name);
			holder.tv_topic_name = (TextView) convertView
					.findViewById(R.id.tv_topic_name);
			holder.llv_topic_border = (GridLinearLayout) convertView
					.findViewById(R.id.llv_topic_border);
			holder.ll_topic_border_list = (LinearLayout) convertView
					.findViewById(R.id.ll_topic_border_list);
			holder.ll_topic_content = (LinearLayout) convertView
					.findViewById(R.id.ll_topic_content);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.llv_topic_border.setColumnCount(4);
		Topic topic = getItem(position);
		TextViewUtils.setText(holder.tv_topic_name, topic.board_category_name);
		if (position > 5) {
			holder.iv_topic_img.setImageResource(topicImgs[5]);
		} else {
			holder.iv_topic_img.setImageResource(topicImgs[position]);
		}
		if (childVisiableList.get(position)) {
			holder.iv_topic_arrow.setImageResource(R.drawable.ic_arrow_up);
			holder.ll_topic_border_list.setVisibility(View.VISIBLE);
		} else {
			holder.ll_topic_border_list.setVisibility(View.GONE);
			holder.iv_topic_arrow.setImageResource(R.drawable.ic_arrow_down);
		}
		BorderAdapter borderAdapter = new BorderAdapter(getContext());
		borderAdapter.setSelectBorder(selectBorder);
		holder.llv_topic_border.setAdapter(borderAdapter);
		borderAdapter.refresh(topic.board_list);
		if (position < borderArrays.size()) {
			borderAdapter.setImgs(borderArrays.get(position));
		} else {
			borderAdapter.setImgs(borderArrays.get(borderArrays.size() - 1));
		}
		holder.llv_topic_border.notifyDataSetChanged();
		return convertView;
	}

	private int[] topicImgs = { R.drawable.ic_zwgl, R.drawable.ic_hdbk,
			R.drawable.ic_dcxs, R.drawable.ic_chwl, R.drawable.ic_rsds,
			R.drawable.ic_wnfw };

	private SparseArray<int[]> borderArrays;

	{
		borderArrays = new SparseArray<int[]>();
		int[] zwgl = { R.drawable.ic_zwgl_ltsw, R.drawable.ic_zwgl_bzjl };
		borderArrays.put(0, zwgl);
		int[] hdzb = { R.drawable.ic_hdzb_1_xshd, R.drawable.ic_hdzb_2_xxhd,
				R.drawable.ic_hdzb_3_hdbd };
		borderArrays.put(1, hdzb);
		int[] dcxs = { R.drawable.ic_dcxs_1_bxxs, R.drawable.ic_dcxs_2_kjjq,
				R.drawable.ic_dcxs_3_yzy };
		borderArrays.put(2, dcxs);
		int[] chwl = { R.drawable.ic_chwl_1_hlst, R.drawable.ic_chwl_2_lyrj,
				R.drawable.ic_chwl_3_syh, R.drawable.ic_chwl_4_tbtj,
				R.drawable.ic_chwl_5_wcsh, R.drawable.ic_chwl_6_chsb };
		borderArrays.put(3, chwl);
		int[] rsds = { R.drawable.ic_rsds_1_jjzx, R.drawable.ic_rsds_2_qgtd,
				R.drawable.ic_rsds_3_mmbb };
		borderArrays.put(4, rsds);
		int[] wnfw = { R.drawable.ic_wnfw_1_yhcx, R.drawable.ic_wnfw_2_esjs,
				R.drawable.ic_wnfw_3_xdtg, R.drawable.ic_wnfw_4_qyzp,
				R.drawable.ic_wnfw_5_syzr, R.drawable.ic_wnfw_6_wszx };
		borderArrays.put(5, wnfw);
	}

	private static class Holder {

		TextView tv_topic_name;
		ImageView iv_topic_img;
		ImageView iv_topic_arrow;
		GridLinearLayout llv_topic_border;
		LinearLayout ll_topic_border_list;
		LinearLayout ll_topic_content;
	}

	@Override
	public void refresh(ArrayList<Topic> data) {
		super.refresh(data);
		if (data == null) {
			childVisiableList = null;
			return;
		}

		if (childVisiableList == null) {
			childVisiableList = new ArrayList<Boolean>();
		}

		for (Topic temp : data) {
			childVisiableList.add(false);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		boolean state = childVisiableList.get(position - 1);
		childVisiableList.set(position - 1, !state);
		for (int i = 0; i < getCount(); i++) {
			if (i != (position - 1)) {
				childVisiableList.set(i, false);
			}
		}
		notifyDataSetChanged();
	}
}
