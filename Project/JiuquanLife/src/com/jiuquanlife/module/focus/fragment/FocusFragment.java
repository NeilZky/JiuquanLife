package com.jiuquanlife.module.focus.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.focus.adapter.FocusTopAdapter;
import com.jiuquanlife.module.focus.adapter.JhtAdapter;
import com.jiuquanlife.module.focus.adapter.LtdrAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.HorizontalListView;
import com.jiuquanlife.view.UnScrollListView;
import com.jiuquanlife.vo.FocusInfo;
import com.jiuquanlife.vo.PhotoInfo;
import com.jiuquanlife.vo.PostInfo;
import com.jiuquanlife.vo.UserInfo;
import com.jiuquanlife.vo.convertor.ConvertUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class FocusFragment extends BaseFragment{

	private ViewPager topVp;
	private FocusTopAdapter focusTopAdapter;
	private LinearLayout dotLl;
	private TextView vpTitleTv;
	private HorizontalListView ltdrHlv;
	private LtdrAdapter ltdrAdapter;
	private UnScrollListView jhtLv;
	private JhtAdapter jhtAdapter;
	
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
		focusTopAdapter = new FocusTopAdapter(getActivity(), dotLl, topVp, vpTitleTv);
		topVp.setOnPageChangeListener(focusTopAdapter);
		ltdrAdapter = new LtdrAdapter(getActivity());
		ltdrHlv.setAdapter(ltdrAdapter);
		
		jhtAdapter = new JhtAdapter(getActivity());
		jhtLv.setAdapter(jhtAdapter);
	}

	
	public void getData() {
		
		RequestHelper.getInstance().getRequest(getActivity(), "http://www.5ijq.cn/App/Index/getFocusList", new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
				FocusInfo info = GsonUtils.toObj(response, FocusInfo.class);
				if(info == null || info.data == null || !CommonConstance.REQUEST_CODE_SUCCESS.equals(info.code)) {
					//ÇëÇóÊý¾ÝÊ§°Ü
					return;
				}
				ArrayList<PhotoInfo> focusTopPhotoInfos = ConvertUtils.convertToPhotoInfos(info);
				focusTopAdapter.setPhotoInfos(focusTopPhotoInfos);
				topVp.setAdapter(focusTopAdapter);
				ltdrAdapter.refresh(info.data.userStar);
				jhtAdapter.refresh(info.data.focusPost);
			}
		});
	}
}
