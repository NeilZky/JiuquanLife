package com.jiuquanlife.module.forum.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.fragment.BorderTabContentFragment;
import com.jiuquanlife.view.titleexpand.TitleExpandView;
import com.jiuquanlife.vo.forum.Border;

public class PostListActivity extends BaseActivity {

	public static final String EXTRA_BORDER = "EXTRA_BORDER";
	public static final String EXTRA_BORDER_POSITION = "EXTRA_BORDER_POSITION";
	public static final String EXTRA_TOPIC= "EXTRA_TOPIC";
	public static final String EXTRA_BORDER_LIST = "EXTRA_BORDER_LIST";
	
	private TitleExpandView tev_post_list_activity;
	private Border border;
	private RelativeLayout rl_title_activity_post_list;
	private BorderTabContentFragment fragment;

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
		tev_post_list_activity = (TitleExpandView) findViewById(R.id.tev_post_list_activity);
		rl_title_activity_post_list = (RelativeLayout) findViewById(R.id.rl_title_activity_post_list);

	}

	private void initData() {

		tev_post_list_activity.setBelowView(rl_title_activity_post_list);
		fragment = new BorderTabContentFragment();
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fl_border_content, fragment)
				.commit();
		ArrayList<Border> borders = (ArrayList<Border>) getIntent().getSerializableExtra(EXTRA_BORDER_LIST);
		for(final Border border : borders) {
			tev_post_list_activity.addItem(border.board_name,
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							PostListActivity.this.border = border;
							fragment.borderChagned();
						}
					});
		}
		int position = getIntent().getIntExtra(EXTRA_BORDER_POSITION, 0);
		border = borders.get(position);
		tev_post_list_activity.performClickAtPosition(position);
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

	public Border getBorder() {
		return border;
	}

}
