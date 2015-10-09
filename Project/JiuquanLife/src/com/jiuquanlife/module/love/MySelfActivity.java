package com.jiuquanlife.module.love;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.company.BaseActivity;

public class MySelfActivity extends BaseActivity {
	private RelativeLayout rl_selfinfo, rl_selfgallery, rl_myfriend, rl_sayhi;
	ImageView iv_user;
	TextView tv_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_love_myself);
		rl_selfinfo = (RelativeLayout) findViewById(R.id.rl_selfinfo);
		rl_selfgallery = (RelativeLayout) findViewById(R.id.rl_selfgallery);
		rl_myfriend = (RelativeLayout) findViewById(R.id.rl_myfriend);
		rl_sayhi = (RelativeLayout) findViewById(R.id.rl_sayhi);

		rl_selfinfo.setOnClickListener(clickListener);
		rl_selfgallery.setOnClickListener(clickListener);
		rl_myfriend.setOnClickListener(clickListener);
		rl_sayhi.setOnClickListener(clickListener);

		iv_user = (ImageView) findViewById(R.id.iv_user);
		tv_name = (TextView) findViewById(R.id.tv_name);

	}

	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.rl_selfinfo:
				Intent intent1 = new Intent(MySelfActivity.this,
						PeopleDetailInfoActivity.class);
				startActivity(intent1);
				break;
			case R.id.rl_selfgallery:
				Intent intent = new Intent(MySelfActivity.this,
						UserSelftGalleryActivity.class);
				startActivity(intent);
				break;
			case R.id.rl_myfriend:

				break;
			case R.id.rl_sayhi:

				break;
			default:
				break;
			}

		}
	};

}
