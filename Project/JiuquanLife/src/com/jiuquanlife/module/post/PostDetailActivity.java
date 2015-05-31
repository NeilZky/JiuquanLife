package com.jiuquanlife.module.post;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.factory.BitmapUtilsFactory;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.post.adapter.ContentAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.view.CircleImageView;
import com.jiuquanlife.view.LinearListView;
import com.jiuquanlife.vo.GetPostByTidInfo;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PostDetailActivity extends BaseActivity{

	public static final String INTENT_KEY_TID = "INTENT_KEY_TID";
	
	private LinearListView contentLlv;
	private TextView usernameTv;
	private TextView dateTv;
	private TextView subjectTv;
	private CircleImageView photoCiv;
	private TextView viewCountTv;
	private TextView replayCounTv;
	private ContentAdapter contentAdapter;

	private ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		
		initViews();
		initData();
	}
	
	private void initData() {
		
		String tid = getIntent().getStringExtra(INTENT_KEY_TID);
		getData(tid);
	}

	private void initViews() {
		
		setContentView(R.layout.activity_post_detail);
		contentLlv = (LinearListView) findViewById(R.id.llv_content_post_detail);
		contentAdapter = new ContentAdapter(this);
		contentLlv.setAdapter(contentAdapter);
		usernameTv = (TextView) findViewById(R.id.tv_username_post_detail);
		dateTv = (TextView) findViewById(R.id.tv_date_post_detail);
		photoCiv = (CircleImageView) findViewById(R.id.civ_photo_post_detail);
		subjectTv = (TextView) findViewById(R.id.tv_subject_post_detail);
		viewCountTv = (TextView) findViewById(R.id.tv_view_count_post_detail);
		replayCounTv = (TextView) findViewById(R.id.tv_reply_count_post_detail);
		imageLoader = ImageLoader.getInstance();

	}
	
	private void getData(String tid) {
	
		RequestHelper.getInstance().getRequest(this, "http://www.5ijq.cn/App/Index/getPostByTid/tid/" + tid, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
				GetPostByTidInfo info = GsonUtils.toObj(response, GetPostByTidInfo.class);
				if(info == null || info.data == null || !CommonConstance.REQUEST_CODE_SUCCESS.equals(info.code)) {
					//ÇëÇóÊý¾ÝÊ§°Ü
					return;
				}
//				if(info.data.content!=null) {
//					contentAdapter.refresh(info.data.content);
//					contentLlv.notifyDataSetChanged();
//				}
				
				if(!StringUtils.isNullOrEmpty(info.data.authorid)) {
					imageLoader.displayImage( UrlUtils.getPhotoUrl(info.data.authorid), photoCiv);
				}
				
//				setText(replayCounTv, info.data.replies);
				setText(viewCountTv, info.data.views);
				setText(usernameTv, info.data.author);
				setText(dateTv, info.data.dateline);
				setText(subjectTv, info.data.subject);
			}
		});
	}
	
}
