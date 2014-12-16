package com.jiuquanlife.module.userhome.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.focus.adapter.JhtAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.vo.PostInfo;
import com.jiuquanlife.vo.UserHomeInfo;

/**
 * 
 *个人主页
 */
public class UserHomeActivity extends BaseActivity{
	
	public static final String KEY_INTENT_UID = "KEY_INTENT_UID";
	
	private JhtAdapter postAdapter;
	private ListView postsLv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		
		initViews();
		getData();
	}
	
	private void initViews(){
		
		setContentView(R.layout.activity_user_home);
		postsLv = (ListView) findViewById(R.id.lv_posts_user_home);
		postAdapter = new JhtAdapter(this);
		postsLv.setAdapter(postAdapter);
	}
	
	private void getData() {
		
		final String uid = getIntent().getStringExtra(KEY_INTENT_UID);
		if(StringUtils.isNullOrEmpty(uid)) {
			return;
		}
		
		RequestHelper.getInstance().getRequest(this, "http://www.5ijq.cn/App/Index/getInfoByUserId/userId/" + uid, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
				UserHomeInfo info = GsonUtils.toObj(response, UserHomeInfo.class);
				if(info == null || info.data == null || !CommonConstance.REQUEST_CODE_SUCCESS.equals(info.code)) {
					//请求数据失败
					return;
				}
				if(info.data.myPosts!=null) {
					for(PostInfo temp : info.data.myPosts) {
						temp.authorid = uid;
					}
				}
				postAdapter.refresh(info.data.myPosts);
			}
		});
	}
	
}
