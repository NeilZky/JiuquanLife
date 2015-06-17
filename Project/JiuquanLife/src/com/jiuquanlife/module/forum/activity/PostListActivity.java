package com.jiuquanlife.module.forum.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.fragment.BorderTabContentFragment;
import com.jiuquanlife.vo.forum.Border;

public class PostListActivity extends BaseActivity {

	public static final String EXTRA_BORDER = "EXTRA_BORDER";
	private TextView tv_border_title;
	private Border border;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		initViews();
		initData();
	}

	private void initViews() {

		setContentView(R.layout.activity_post_list);
		tv_border_title = (TextView) findViewById(R.id.tv_border_title);
	}

	private void initData() {

		border = (Border) getIntent().getSerializableExtra(EXTRA_BORDER);
		tv_border_title.setText(border.board_name);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fl_border_content, new BorderTabContentFragment())
				.commit();
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_back_activity_post_list:
			onClickBack();
			break;

		default:
			break;
		}
	}

	private void onClickBack() {

		finish();
	}

}
