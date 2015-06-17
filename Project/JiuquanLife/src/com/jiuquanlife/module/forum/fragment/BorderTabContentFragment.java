package com.jiuquanlife.module.forum.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.reflect.TypeToken;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.ImageViewPagerAdapter;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.forum.activity.ForumTabActvity;
import com.jiuquanlife.module.forum.fragment.pageradapter.ForumFragmentAdapter;
import com.jiuquanlife.module.post.PostDetailActivity;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.vo.BaseData;
import com.jiuquanlife.vo.FocusInfo;
import com.jiuquanlife.vo.PhotoInfo;
import com.jiuquanlife.vo.PostInfo;
import com.jiuquanlife.vo.convertor.ConvertUtils;
import com.jiuquanlife.vo.forum.ForumIndexData;

public class BorderTabContentFragment extends BaseFragment {

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private ForumFragmentAdapter adapter;
	private ViewPager topVp;
	private ImageViewPagerAdapter focusTopAdapter;
	private LinearLayout dotLl;
	private TextView vpTitleTv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View content = inflater.inflate(R.layout.fragment_forum, null);
		setContent(content);
		initViews();
		getData();
		return content;
	}
    
    
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			PhotoInfo photoInfo = focusTopAdapter.getCurrentItem();
			if (photoInfo != null) {
				Intent intent = new Intent(getActivity(),
						PostDetailActivity.class);
				intent.putExtra(PostDetailActivity.INTENT_KEY_TID,
						photoInfo.tid);
				startActivity(intent);
			}
		}
	};
    
	public void getData() {

		RequestHelper.getInstance().getRequest(getActivity(),
				UrlConstance.GET_HOT_FORUM,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {

//						FocusInfo info = GsonUtils.toObj(response,
//								FocusInfo.class);
//						
						Type type = new TypeToken<BaseData<ForumIndexData>>() {
						}.getType();
						BaseData<ForumIndexData> info = GsonUtils.toObj(
								response, type);
						
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// ÇëÇóÊý¾ÝÊ§°Ü
							return;
						}
						
						
						ArrayList<PhotoInfo> focusTopPhotoInfos = ConvertUtils
								.convertToPhotoInfos(info.data.focusImgs);
						focusTopAdapter.setPhotoInfos(focusTopPhotoInfos);
						topVp.setAdapter(focusTopAdapter);
					}
				});
	}
	
	private void initViews(){
	
		topVp = (ViewPager) findViewById(R.id.vp_top_focus);
		dotLl = (LinearLayout) findViewById(R.id.ll_dot_top_focus);
		vpTitleTv = (TextView) findViewById(R.id.tv_vp_title_focus);
		focusTopAdapter = new ImageViewPagerAdapter(getActivity(), dotLl, topVp,
				vpTitleTv);
		topVp.setOnPageChangeListener(focusTopAdapter);
//		focusTopAdapter.setOnClickItemListener(onClickListener);
		
		tabs = (PagerSlidingTabStrip) findViewById(R.id.pts_forum);
		pager = (ViewPager) findViewById(R.id.vp_forum);
		ArrayList<ForumBaseFragment> fragments = new ArrayList<ForumBaseFragment>();
		NewForumFragment newForumFragment = new NewForumFragment();
		newForumFragment.setTitle(getActivity().getString(R.string.title_forum_new));
		
		HotPostForumFragment hotPostForumFragment = new HotPostForumFragment();
		hotPostForumFragment.setTitle(getActivity().getString(R.string.title_forum_hot));
	
		EssenceForumFragment essenceForumFragment = new EssenceForumFragment();
		essenceForumFragment.setTitle(getActivity().getString(R.string.title_forum_essence));
		
		fragments.add(newForumFragment);
		fragments.add(hotPostForumFragment);
		fragments.add(essenceForumFragment);
		adapter = new ForumFragmentAdapter(getFragmentManager(), fragments);
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
	}

}
