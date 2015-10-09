package com.jiuquanlife.module.publish;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiuquanlife.R;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ShowServerImageActivity extends BaseActivity {
	public static final int FROM_LOVE = 1;
	private ViewPager pager;
	private MyPageAdapter adapter;
	private List<ImgInfo> imgInfos;
	private ArrayList<View> listViews = new ArrayList<View>();
	private LayoutInflater inflater;
	private ImageView[] indicator_imgs;
	private int where = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_image);
		pager = (ViewPager) findViewById(R.id.viewpager);
		inflater = LayoutInflater.from(this);
		parseIntent();
		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				for (int i = 0; i < indicator_imgs.length; i++) {

					indicator_imgs[i]
							.setBackgroundResource(R.drawable.indicator);

				}

				// 改变当前背景图片为：选中
				indicator_imgs[position]
						.setBackgroundResource(R.drawable.indicator_focused);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		initIndicator();

	}

	private void parseIntent() {
		Intent intent = getIntent();
		imgInfos = intent.getParcelableArrayListExtra("imgInfos");
		where = intent.getIntExtra("fromWhere", 0);

		if (null != imgInfos) {
			View item;
			for (ImgInfo imgInfo : imgInfos) {
				item = inflater.inflate(R.layout.server_img_item, null);
				listViews.add(item);
			}

			indicator_imgs = new ImageView[imgInfos.size()];
		}

		System.out.println("listViews:" + listViews.size());
	}

	private void initIndicator() {
		ImageView imgView;
		View v = findViewById(R.id.indicator);
		for (int i = 0; i < indicator_imgs.length; i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(
					10, 10);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			indicator_imgs[i] = imgView;

			if (i == 0) { // 初始化第一个为选中状态

				indicator_imgs[i]
						.setBackgroundResource(R.drawable.indicator_focused);
			} else {
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
			}
			((ViewGroup) v).addView(indicator_imgs[i]);
		}
	}

	class MyPageAdapter extends PagerAdapter {
		private List<View> views;

		public MyPageAdapter(List<View> views) {
			super();
			this.views = views;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			System.out.println("sozee:" + views.size());
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(final ViewGroup container,
				final int position) {
			View view = views.get(position);
			ImgInfo imgInfo = imgInfos.get(position);
			System.out.println(position + " imgInfos:" + imgInfo);
			ImageView image = ((ImageView) view.findViewById(R.id.iv_server));
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();

				}
			});
			ImageLoader.getInstance().displayImage(
					where == 1 ? Common.LOVE_IMG_PREFX + imgInfo.getPic()
							: Common.PIC_PREFX + imgInfo.getPic(), image,
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {
							startProgressDialog("加载中...");

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {
							stopProgressDialog();
							Toast.makeText(getApplicationContext(), "加载失败",
									Toast.LENGTH_SHORT).show();

						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {
							System.out.println("加载成功");
							stopProgressDialog();
							container.removeView(views.get(position));
							container.addView(views.get(position));

						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
							System.out.println("加载取消");
							stopProgressDialog();

						}
					});
			container.removeView(views.get(position));
			container.addView(views.get(position));
			return views.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(views.get(position));

		}

	}
}
