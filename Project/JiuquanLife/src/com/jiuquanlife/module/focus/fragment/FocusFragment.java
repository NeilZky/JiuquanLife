package com.jiuquanlife.module.focus.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.ImageViewPagerAdapter;
import com.jiuquanlife.adapter.PostAdapter;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.focus.adapter.JhtAdapter;
import com.jiuquanlife.module.focus.adapter.LtdrAdapter;
import com.jiuquanlife.module.forum.activity.PostDetailActivity;
import com.jiuquanlife.module.userhome.activity.UserHomeActivity;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.HorizontalListView;
import com.jiuquanlife.view.UnScrollListView;
import com.jiuquanlife.vo.FocusInfo;
import com.jiuquanlife.vo.PhotoInfo;
import com.jiuquanlife.vo.PostInfo;
import com.jiuquanlife.vo.UserInfo;
import com.jiuquanlife.vo.convertor.ConvertUtils;
import com.jiuquanlife.vo.forum.PostItem;

public class FocusFragment extends BaseFragment {

	private ViewPager topVp;
	private ImageViewPagerAdapter focusTopAdapter;
	private LinearLayout dotLl;
	private TextView vpTitleTv;
	private HorizontalListView ltdrHlv;
	private LtdrAdapter ltdrAdapter;
	private UnScrollListView jhtLv;
	private PostAdapter jhtAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View content = inflater.inflate(R.layout.fragment_focus, null);
		setContent(content);
		init();
		return content;
	}

	private void init() {

		initViews();
		getData();
	}

	private void initViews() {

		topVp = (ViewPager) findViewById(R.id.vp_top_focus);
		dotLl = (LinearLayout) findViewById(R.id.ll_dot_top_focus);
		vpTitleTv = (TextView) findViewById(R.id.tv_vp_title_focus);
		ltdrHlv = (HorizontalListView) findViewById(R.id.hlv_ltdr_focus);
		jhtLv = (UnScrollListView) findViewById(R.id.uslv_jht_focus);
		focusTopAdapter = new ImageViewPagerAdapter(getActivity(), dotLl, topVp,
				vpTitleTv);
		topVp.setOnPageChangeListener(focusTopAdapter);
		ltdrAdapter = new LtdrAdapter(getActivity());
		ltdrHlv.setAdapter(ltdrAdapter);

		jhtAdapter = new PostAdapter(getActivity());
		jhtLv.setAdapter(jhtAdapter);
		jhtLv.setOnItemClickListener(jhtAdapter);
		ltdrHlv.setOnItemClickListener(onItemClickListener);
		focusTopAdapter.setOnClickItemListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			PhotoInfo photoInfo = focusTopAdapter.getCurrentItem();
			if (photoInfo != null) {
				Intent intent = new Intent(getActivity(), PostDetailActivity.class);
				intent.putExtra(PostDetailActivity.EXTRA_TOPIC_ID, Integer.parseInt(photoInfo.tid));
				startActivity(intent);
			}
		}
	};

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			switch (parent.getId()) {
//			case R.id.uslv_jht_focus:
//				onClickJhtItem(position);
//				break;
			case R.id.hlv_ltdr_focus:
				onClickLtdrItem(position);
				break;
			default:
				break;
			}

		}

		private void onClickLtdrItem(int position) {

			UserInfo userInfo = ltdrAdapter.getItem(position);
			Intent intent = new Intent(getActivity(), UserHomeActivity.class);
			intent.putExtra(UserHomeActivity.KEY_INTENT_UID, userInfo.uid);
			startActivity(intent);
		}

		private void onClickJhtItem(int position) {

			PostItem postInfo = jhtAdapter.getItem(position);
			Intent intent = new Intent(getActivity(), PostDetailActivity.class);
			intent.putExtra(PostDetailActivity.EXTRA_TOPIC_ID, postInfo.topic_id);
			startActivity(intent);
		}
	};

	public void getData() {

		RequestHelper.getInstance().getRequest(getActivity(),
				"http://www.5ijq.cn/App/Index/getFocusList",
				new Listener<String>() {

					@Override
					public void onResponse(String response) {

						FocusInfo info = GsonUtils.toObj(response,
								FocusInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// ÇëÇóÊý¾ÝÊ§°Ü
							return;
						}
						ArrayList<PhotoInfo> focusTopPhotoInfos = ConvertUtils
								.convertToPhotoInfos(info);
						focusTopAdapter.setPhotoInfos(focusTopPhotoInfos);
						topVp.setAdapter(focusTopAdapter);
						ltdrAdapter.refresh(info.data.userStar);
						jhtAdapter.refresh(ConvertUtils.convertPosts(info.data.focusPost));
					}
				});
	}
}
