package com.jiuquanlife.module.house.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.SampleImagePagerAdapter;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.SecondaryHouseAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.LinearListView;
import com.jiuquanlife.vo.house.HouseDetailData;
import com.jiuquanlife.vo.house.HouseDetailInfo;
import com.jiuquanlife.vo.house.Img;

public class WantedRentHouseDetailActivity extends BaseActivity {

	public static final String INTENT_KEY_HOUSE_ID = "INTENT_KEY_HOUSE_ID";
	
	private ViewPager photoVp;
	private SampleImagePagerAdapter photoAdapter;
	private TextView tv_title_ashd;
	private TextView tv_time_ashd;
	private TextView tag1_ashd;
	private TextView tag2_ashd;
	private TextView tag3_ashd;
	private TextView tag4_ashd;
	private TextView tv_price_ashd;
	private TextView tv_house_layout_ashd;
	private TextView tv_square_ashd;
	private TextView tv_meter_price;
	private TextView tv_first_pay_ashd;
	private TextView tv_month_pay_ashd;
	private TextView tv_property_type_ashd;
	private TextView tv_fittype_ashd;
	private TextView tv_property_long_ashd;
	private TextView tv_floor_ashd;
	private TextView tv_make_year_ashd;
	private TextView tv_towards_ashd;
	private TextView tv_address_ashd;
	private TextView tv_community_name_ashd;
	private TextView tv_description_ashd;
	private LinearListView llv_same_price_ashd;
	private SecondaryHouseAdapter secondaryHouseAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		initViews();
		getData();
	}

	private void initViews() {

		setContentView(R.layout.activity_seller_house_detail);
		photoVp = (ViewPager) findViewById(R.id.vp_ashd);
		photoAdapter = new SampleImagePagerAdapter(this, photoVp);
		tv_title_ashd = (TextView) findViewById(R.id.tv_title_ashd);
		tv_time_ashd = (TextView) findViewById(R.id.tv_time_ashd);
		tag1_ashd = (TextView) findViewById(R.id.tag1_ashd);
		tag2_ashd = (TextView) findViewById(R.id.tag2_ashd);
		tag3_ashd = (TextView) findViewById(R.id.tag3_ashd);
		tag4_ashd = (TextView) findViewById(R.id.tag4_ashd);
		tv_price_ashd = (TextView) findViewById(R.id.tv_price_ashd);
		tv_house_layout_ashd = (TextView) findViewById(R.id.tv_house_layout_ashd);
		tv_square_ashd = (TextView) findViewById(R.id.tv_square_ashd);
		tv_meter_price = (TextView) findViewById(R.id.tv_meter_price);
		tv_first_pay_ashd = (TextView) findViewById(R.id.tv_first_pay_ashd);
		tv_month_pay_ashd = (TextView) findViewById(R.id.tv_month_pay_ashd);
		tv_property_type_ashd = (TextView) findViewById(R.id.tv_property_type_ashd);
		tv_fittype_ashd = (TextView) findViewById(R.id.tv_fittype_ashd);
		tv_property_long_ashd = (TextView) findViewById(R.id.tv_property_long_ashd);
		tv_floor_ashd = (TextView) findViewById(R.id.tv_floor_ashd);
		tv_make_year_ashd = (TextView) findViewById(R.id.tv_make_year_ashd);
		tv_towards_ashd = (TextView) findViewById(R.id.tv_towards_ashd);
		tv_address_ashd = (TextView) findViewById(R.id.tv_address_ashd);
		tv_community_name_ashd = (TextView) findViewById(R.id.tv_community_name_ashd);
		tv_description_ashd = (TextView) findViewById(R.id.tv_description_ashd);
		llv_same_price_ashd = (LinearListView) findViewById(R.id.llv_same_price_ashd);
		secondaryHouseAdapter = new SecondaryHouseAdapter(this);
		llv_same_price_ashd.setAdapter(secondaryHouseAdapter);
	}

	private void getData() {

		String houseid = getIntent().getStringExtra(INTENT_KEY_HOUSE_ID);
		RequestHelper.getInstance().postRequestMap(WantedRentHouseDetailActivity.this,
				"http://www.5ijq.cn/App/House/getHoustById/id/" + houseid,
				null, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						HouseDetailInfo info = GsonUtils.toObj(response,
								HouseDetailInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// 请求数据失败
							return;
						}
						fillViews(info);
					}
				});
	}

	private void fillViews(HouseDetailInfo info) {

		if(info == null ||info.data == null) {
			return;
		}
		HouseDetailData data= info.data;
		setText(tv_title_ashd, data.title);
		setText(tv_time_ashd, data.dateline);
		setText(tv_price_ashd , data.housePrice+"万");
		setText(tv_house_layout_ashd, data.houseLayout+"㎡");
		setText(tv_square_ashd, data.square_metre);
		setText(tv_meter_price,""  + (int)(Double.parseDouble(data.housePrice) * 10000 / Double.parseDouble(data.square_metre)) + "/㎡");
		setText(tv_first_pay_ashd, data.firstPay + "万");
		setText(tv_month_pay_ashd, data.monthPay);
		setText(tv_property_type_ashd, data.propertyType);
		setText(tv_fittype_ashd, data.fitType);
		setText(tv_property_long_ashd, data.propertyLong);
		setText(tv_floor_ashd, data.floorth + "/" + data.floorTotal);
		setText(tv_make_year_ashd, data.makeYear);
		setText(tv_towards_ashd, data.towards);
		setText(tv_description_ashd, data.intro);
		setText(tv_address_ashd, data.addressName + "-" +data.subAddressName + "-" + data.partAddressName);
		setText(tv_community_name_ashd, data.communityName);
		secondaryHouseAdapter.refresh(data.samePriceHouseList);
		llv_same_price_ashd.notifyDataSetChanged();
//		if(data.intro!=null) {
//			String[] tags = data.intro.split("\\s");
//			if(tags!=null){
//				if(tags.length >=4) {
//					setText(tag4_ashd, tags[3]);
//				}
//				if(tags.length >=3 ) {
//					setText(tag3_ashd, tags[2]);
//				}
//				if(tags.length >=2 ) {
//					setText(tag2_ashd, tags[1]);
//				}
//				if(tags.length >=1 ) {
//					setText(tag1_ashd, tags[0]);
//				}
//			}
//		}
		ArrayList<Img> imgs = info.data.img;
		if(imgs!=null) {
			ArrayList<String> urls = new ArrayList<String>();
			for(Img img : imgs) {
				if(img.pic!=null) {
					urls.add(img.pic);
					photoAdapter.setUrls(urls);
					photoVp.setAdapter(photoAdapter);
				}
			}
		}
	}

}
