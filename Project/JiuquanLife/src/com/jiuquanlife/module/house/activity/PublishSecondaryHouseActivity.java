package com.jiuquanlife.module.house.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.PhotoAdapter;
import com.jiuquanlife.constance.ActionRelationConstance;
import com.jiuquanlife.constance.ActionTypeConstance;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.Photo;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.CommonTypeAdapter;
import com.jiuquanlife.module.house.adapter.Floor;
import com.jiuquanlife.module.house.adapter.FloorAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.PhotoManager;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.ToastHelper;
import com.jiuquanlife.utils.UploadUtils;
import com.jiuquanlife.view.LinearListView;
import com.jiuquanlife.view.ListDialog;
import com.jiuquanlife.view.SingleChoiceDialog;
import com.jiuquanlife.vo.house.AddHouseInfo;
import com.jiuquanlife.vo.house.AddressRange;
import com.jiuquanlife.vo.house.CommonType;
import com.jiuquanlife.vo.house.Community;
import com.jiuquanlife.vo.house.out.NewHouse;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;

public class PublishSecondaryHouseActivity extends BaseActivity {

	public static final String EXTRA_ACTION_TYPE = "EXTRA_ACTION_TYPE";
	public static final String EXTRA_ACTION_RELATION = "EXTRA_ACTION_RELATION";

	private static final int REQUEST_CODE_CAMERA = 1;
	protected static final int REQUEST_SELECT_PHOTOS = 2;
	protected static final int REQUEST_SELECT_AREA = 3;
	protected static final int REQUEST_SELECT_COMMUNITY = 4;
	protected static final int MSG_UPLOADED_PHOTOS = 1;
	private PhotoAdapter photoAdapter;
	private PhotoManager photoManager = PhotoManager.getInstance();
	private LinearListView llv_photo_aps;
	private TextView tv_area_aps;
	private TextView tv_community_aps;
	private TextView tv_select_layout_aps;
	private TextView tv_select_floor_aps;
	private TextView tv_all_floor_aps;
	private TextView tv_select_decorate;
	private TextView tv_type_aps;
	private TextView tv_sex_aps;
	private TextView tv_room_aps;
	private TextView tv_pay_type_aps;
	private TextView tv_proper_long_aps;
	private TextView tv_proper_type_aps;
	private TextView tv_towards_aps;
	private TextView tv_label_house_price;
	private TextView tv_publish_title;
	private EditText et_price_aps;
	private EditText et_first_pay_aps;
	private EditText et_month_pay_aps;
	private EditText et_title_aps;
	private EditText et_intro_aps;
	private EditText et_area_aps;
	private EditText et_building_time_aps;
	private EditText et_apply_rent_price_low_aps;
	private EditText et_apply_rent_price_high_aps;
	private EditText et_apply_buy_price_high_aps;
	private EditText et_apply_buy_price_low_aps;
	private LinearLayout ll_pay_content;
	private CheckBox cb_is_loan_aps;
	private ArrayList<AddressRange> addressList;
	private ArrayList<CommonType> houseLayoutList;
	private ArrayList<CommonType> houseTypeList;
	private ArrayList<CommonType> houseFitList;
	private ArrayList<CommonType> properLongList;
	public ArrayList<CommonType> roomList;
	public ArrayList<CommonType> payTypeList;
	public ArrayList<CommonType> sexLimitList;
	private AddressRange subAddressRange;
	private AddressRange fatherAddressRange;
	private Community community;
	private EditText et_contactor_aps;
	private EditText et_contact_phone_aps;
	private EditText et_qq_aps;
	private RadioButton rb_agent_aps;
	private String token;
	private String actionRelation;
	private String actionType;
	private View hsv_photo_aps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		initViews();
		initActionType();
		initInputFields();
		getData();
	}

	private void initInputFields() {

		actionRelation = getIntent().getStringExtra(EXTRA_ACTION_RELATION);
		if (ActionRelationConstance.RENT.equals(actionRelation)) {

			hideView(R.id.ll_content_sell_aps);
			hideView(R.id.ll_content_apply_rent_price);
			hideView(R.id.ll_content_apply_buy_price);
			tv_publish_title.setText("出租");
		}
		if (ActionRelationConstance.SELL.equals(actionRelation)) {

			hideView(R.id.ll_content_rent_price);
			hideView(R.id.ll_content_apply_rent_price);
			hideView(R.id.ll_content_apply_buy_price);
			hideView(R.id.ll_content_pay_type);
			tv_publish_title.setText("出售");
		}

		if (ActionRelationConstance.APPLY_RENT.equals(actionRelation)) {

			hideView(R.id.hsv_photo_aps);
			hideView(R.id.ll_community_aps);
			hideView(R.id.ll_content_address_detail_aps);
			hideView(R.id.ll_content_area_aps);
			hideView(R.id.ll_content_fit_aps);
			hideView(R.id.ll_content_floor_aps);
			hideView(R.id.ll_content_all_floor_aps);
			hideView(R.id.ll_content_sell_aps);
			hideView(R.id.ll_content_rent_price);
			hideView(R.id.ll_content_apply_buy_price);
			tv_publish_title.setText("求租");
		}

		if (ActionRelationConstance.APPLY_BUY.equals(actionRelation)) {

			hideView(R.id.hsv_photo_aps);
			hideView(R.id.ll_community_aps);
			hideView(R.id.ll_content_address_detail_aps);
			hideView(R.id.ll_content_area_aps);
			hideView(R.id.ll_content_fit_aps);
			hideView(R.id.ll_content_floor_aps);
			hideView(R.id.ll_content_all_floor_aps);
			hideView(R.id.ll_content_sell_aps);
			hideView(R.id.ll_content_rent_price);
			hideView(R.id.ll_content_apply_rent_price);
			hideView(R.id.ll_content_pay_type);
			tv_publish_title.setText("求购");
		}

		if (ActionTypeConstance.FACTORY.equals(actionType)
				|| ActionTypeConstance.STORE.equals(actionType)) {
			hideView(R.id.ll_content_layout_aps);
			hideView(R.id.ll_content_floor_aps);
			hideView(R.id.ll_content_all_floor_aps);
		}

		if (ActionTypeConstance.ROOM.equals(actionType)
				|| ActionTypeConstance.BED.equals(actionType)) {
			showView(R.id.ll_content_room_aps);
			showView(R.id.ll_content_sex_aps);
		} else {
			hideView(R.id.ll_content_room_aps);
			hideView(R.id.ll_content_sex_aps);
		}

		if (ActionTypeConstance.SECONDARY.equals(actionType)) {
			showView(R.id.ll_content_loan_aps);
			showView(R.id.ll_content_proper);
		} else {
			hideView(R.id.ll_content_loan_aps);
			hideView(R.id.ll_content_proper);

		}
	}

	protected void initActionType() {

		String btnText = getIntent().getStringExtra(EXTRA_ACTION_TYPE);
		if ("二手房".equals(btnText)) {
			actionType = "1";
		} else if ("整套".equals(btnText)) {
			actionType = "2";
		} else if ("单间".equals(btnText)) {
			actionType = "3";
		} else if ("床位".equals(btnText)) {
			actionType = "4";
		} else if ("商铺".equals(btnText)) {
			actionType = "5";
		} else if ("厂房/仓库/土地/车位".equals(btnText)) {
			actionType = "6";
		}
	}

	private void hideView(int resId) {

		findViewById(resId).setVisibility(View.GONE);
	}

	private void showView(int resId) {

		findViewById(resId).setVisibility(View.VISIBLE);
	}

	private void initViews() {

		setContentView(R.layout.activity_publish_secondary);
		hsv_photo_aps =  findViewById(R.id.hsv_photo_aps);
		et_area_aps = (EditText) findViewById(R.id.et_area_aps);
		rb_agent_aps = (RadioButton) findViewById(R.id.rb_agent_aps);
		llv_photo_aps = (LinearListView) findViewById(R.id.llv_photo_aps);
		tv_area_aps = (TextView) findViewById(R.id.tv_area_aps);
		tv_community_aps = (TextView) findViewById(R.id.tv_community_aps);
		tv_select_layout_aps = (TextView) findViewById(R.id.tv_select_layout_aps);
		tv_select_floor_aps = (TextView) findViewById(R.id.tv_select_floor_aps);
		tv_select_decorate = (TextView) findViewById(R.id.tv_select_decorate);
		tv_type_aps = (TextView) findViewById(R.id.tv_type_aps);
		tv_all_floor_aps = (TextView) findViewById(R.id.tv_all_floor_aps);
		tv_towards_aps = (TextView) findViewById(R.id.tv_towards_aps);
		tv_proper_type_aps = (TextView) findViewById(R.id.tv_proper_type_aps);
		tv_proper_long_aps = (TextView) findViewById(R.id.tv_proper_long_aps);
		cb_is_loan_aps = (CheckBox) findViewById(R.id.cb_is_loan_aps);
		ll_pay_content = (LinearLayout) findViewById(R.id.ll_pay_content);
		cb_is_loan_aps.setOnCheckedChangeListener(onCheckedChangeListener);
		et_price_aps = (EditText) findViewById(R.id.et_price_aps);
		et_first_pay_aps = (EditText) findViewById(R.id.et_first_pay_aps);
		et_month_pay_aps = (EditText) findViewById(R.id.et_month_pay_aps);
		et_title_aps = (EditText) findViewById(R.id.et_title_aps);
		et_intro_aps = (EditText) findViewById(R.id.et_intro_aps);
		et_contactor_aps = (EditText) findViewById(R.id.et_contactor_aps);
		et_contact_phone_aps = (EditText) findViewById(R.id.et_contact_phone_aps);
		et_qq_aps = (EditText) findViewById(R.id.et_qq_aps);
		tv_label_house_price = (TextView) findViewById(R.id.tv_label_house_price);
		tv_sex_aps = (TextView) findViewById(R.id.tv_sex_aps);
		tv_room_aps = (TextView) findViewById(R.id.tv_room_aps);
		tv_pay_type_aps = (TextView) findViewById(R.id.tv_pay_type_aps);
		tv_publish_title = (TextView) findViewById(R.id.tv_publish_title);
		et_building_time_aps = (EditText) findViewById(R.id.et_building_time_aps);
		et_apply_rent_price_low_aps = (EditText) findViewById(R.id.et_apply_rent_price_low_aps);
		et_apply_rent_price_high_aps = (EditText) findViewById(R.id.et_apply_rent_price_high_aps);
		et_apply_buy_price_low_aps = (EditText) findViewById(R.id.et_apply_buy_price_low_aps);
		et_apply_buy_price_high_aps = (EditText) findViewById(R.id.et_apply_buy_price_high_aps);
		photoAdapter = new PhotoAdapter(this);
		photoAdapter.setLlv(llv_photo_aps);
		llv_photo_aps.setAdapter(photoAdapter);
		photoManager.deletePhotos(PhotoManager.UPLOAD_PHOTO_PATH);
	}

	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {

			if (isChecked) {
				ll_pay_content.setVisibility(View.VISIBLE);
			} else {
				ll_pay_content.setVisibility(View.GONE);
			}
		}
	};

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_add_photo_aps:
			onClickAddPhoto();
			break;
		case R.id.btn_publish_aps:
			onClickPublish();
			break;
		case R.id.ll_area_aps:
			onClickSelectArea();
			break;
		case R.id.ll_community_aps:
			onClickCommunity();
			break;
		case R.id.ll_layout_aps:
			onClickLayout();
			break;
		case R.id.ll_floor_aps:
			onClickFloor();
			break;
		case R.id.ll_all_floor_aps:
			onClickAllFloor();
			break;
		case R.id.ll_select_fit_aps:
			onClickSelectFit();
			break;
		case R.id.ll_select_type_aps:
			onClickSelectType();
			break;
		case R.id.ll_proper_long_aps:
			onClickSelectProperLong();
			break;
		case R.id.ll_proper_type_aps:
			onClickSelectProperType();
			break;
		case R.id.ll_select_towards_aps:
			onClickSelectTowards();
			break;
		case R.id.ll_content_room_aps:
			if(roomDialog!=null) {
				roomDialog.show();
			}
			break;
		case R.id.ll_content_sex_aps:
			if(sexDialog!=null) {
				sexDialog.show();
			}
			break;
		case R.id.ll_content_pay_type:
			if(payTypeDialog!=null) {
				payTypeDialog.show();
			}
			break;
		default:
			break;
		}
	}

	private void onClickSelectTowards() {

		if (towardsDialog != null) {
			towardsDialog.show();
		}
	}

	private void onClickSelectProperType() {

		if (properTypeDialog != null) {
			properTypeDialog.show();
		}
	}

	private void onClickSelectProperLong() {

		if (properLongDialog != null) {
			properLongDialog.show();
		}
	}

	private void onClickSelectType() {

		if (houseTypeDialog != null) {
			houseTypeDialog.show();
		}
	}

	private void onClickSelectFit() {

		if (houseFitDialog != null) {
			houseFitDialog.show();
		}
	}

	private void onClickAllFloor() {

		showAllFlowDialog();
	}

	private void onClickFloor() {

		showFloorLayoutDialog();
	}

	private void onClickLayout() {

		if (houseLayoutDialog != null) {
			houseLayoutDialog.show();
		}
	}

	private void onClickCommunity() {

		if (subAddressRange == null) {
			ToastHelper.showL("请先选择区域");
		} else {
			Intent intent = new Intent(this, SelectCommunityActivity.class);
			intent.putExtra(SelectCommunityActivity.INTENT_KEY_AID,
					subAddressRange.aid);
			startActivityForResult(intent, REQUEST_SELECT_COMMUNITY);
		}
	}

	private void onClickSelectArea() {

		Intent intent = new Intent(this, SelectAreaActivity.class);
		intent.putExtra(SelectAreaActivity.INTENT_KEY_ADDRESS_LIST, addressList);
		startActivityForResult(intent, REQUEST_SELECT_AREA);
	}

	private void onClickPublish() {
		ArrayList<Photo> uploadPhotos = photoAdapter.getPhotos();
		if (uploadPhotos == null || uploadPhotos.isEmpty()) {
			publishData(null);
		}
		new Thread() {

			@Override
			public void run() {
				super.run();
				ArrayList<Photo> photos = photoAdapter.getPhotos();
				ArrayList<String> photoUrls = new ArrayList<String>();
				if (photos != null) {
					for (Photo photo : photos) {
						String filePath = photo.filePath;
						String res = UploadUtils.upload(
								"http://www.5ijq.cn/App/House/addHouseImg",
								filePath, null);
						try {
							JSONObject json = new JSONObject(res);
							String imgUrl = (String) json.get("data");
							if (!StringUtils.isNullOrEmpty(imgUrl)) {
								photoUrls.add(imgUrl);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Message msg = handler.obtainMessage();
					msg.obj = photoUrls;
					msg.what = MSG_UPLOADED_PHOTOS;
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case MSG_UPLOADED_PHOTOS:
				onUploadPhotos(msg);
				break;

			default:
				break;
			}

		}

		private void onUploadPhotos(Message msg) {

			publishData((ArrayList<String>) msg.obj);
		};

	};

	private void publishData(ArrayList<String> imgs) {
		
		if(!verifyInput()) {
			return;
		}
		
		NewHouse newHouse = new NewHouse();
		newHouse.uid = String.valueOf(SharePreferenceUtils.getObject(
				SharePreferenceUtils.USER, User.class).uid);
		newHouse.actionRelation = actionRelation;
		newHouse.actionType = actionType;
		newHouse.devices = "2";
		newHouse.token = token;
		newHouse.fromType = rb_agent_aps.isChecked() ? "2" : "1";
		if(fatherAddressRange!=null) {
			newHouse.locationId = fatherAddressRange.aid;
		}
		if(subAddressRange!=null) {
			newHouse.partLocationId = subAddressRange.aid;
		}
		if(community!=null) {
			newHouse.communityId = community.cid;
		}
		if(subAddressRange!=null) {
			newHouse.houseArea = subAddressRange.aid;
		}
		
		newHouse.houseArea = "130";
		newHouse.housePrice = et_price_aps.getText().toString().trim();
		newHouse.monthPay = et_month_pay_aps.getText().toString().trim();
		newHouse.firstPay = et_first_pay_aps.getText().toString().trim();
		newHouse.isLoan = cb_is_loan_aps.isChecked() ? "1" : "0";
		newHouse.title = et_title_aps.getText().toString().trim();
		newHouse.intro = et_intro_aps.getText().toString().trim();
		newHouse.contactor = et_contactor_aps.getText().toString().trim();
		newHouse.contactPhone = et_contact_phone_aps.getText().toString()
				.trim();
		newHouse.qq = et_qq_aps.getText().toString().trim();
		newHouse.isLoan = rb_agent_aps.isChecked() ? "1" : "0";
		newHouse.toward = getIdOf(towardsDialog);
		newHouse.sexLimit = getIdOf(sexDialog);
		newHouse.roomType = getIdOf(roomDialog);
		newHouse.payType = getIdOf(payTypeDialog);
		if(floorDialog!=null && floorDialog.getCheckedItem()!=null) {
			newHouse.floor = ((Floor) floorDialog.getCheckedItem()).id;
		}
		if(allFloorDialog!=null && allFloorDialog.getCheckedItem()!=null) {
			newHouse.totalFloor = ((Floor) allFloorDialog.getCheckedItem()).id;
		}
		newHouse.houseType = getIdOf(houseTypeDialog);
		newHouse.houseLayout = getIdOf(houseLayoutDialog); 
		newHouse.fitType =   getIdOf(houseFitDialog);
		newHouse.isFloor =  getIdOf(houseTypeDialog); 
		newHouse.propertyLong =   getIdOf(properLongDialog);
		newHouse.propertyType = getIdOf(properTypeDialog);
		newHouse.makeYear = et_building_time_aps.getText().toString();
		newHouse.houseArea = et_area_aps.getText().toString().trim();
		if (ActionRelationConstance.APPLY_RENT.equals(actionRelation)) {
			newHouse.housePrice = et_apply_rent_price_low_aps.getText()
					.toString().trim()
					+ "-"
					+ et_apply_rent_price_high_aps.getText().toString().trim();
		}
		if (ActionRelationConstance.APPLY_BUY.equals(actionRelation)) {
			newHouse.housePrice = et_apply_buy_price_low_aps.getText()
					.toString().trim()
					+ "-"
					+ et_apply_buy_price_high_aps.getText().toString().trim();
		}
		
		if (imgs != null) {
			boolean first = true;
			StringBuffer sb = new StringBuffer();
			for (String img : imgs) {
				if (first) {
					sb.append(img);
					first = false;
				} else {
					sb.append("*fg*" + img);
				}
			}
			newHouse.imgs = sb.toString();
		}
		RequestHelper.getInstance().getRequestEntity(getActivity(),
				UrlConstance.NEW_HOUSE, newHouse, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						ToastHelper.showL(response);
					}
				});
	}
	
	private boolean verifyInput() {
		
		ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
		TextView textView = findViewHasHint(decorView);
		if(textView!=null) {
			ToastHelper.showS(textView.getHint().toString());
			return false;
		}
		String tel = et_contact_phone_aps.getText().toString().trim();
		if(tel!=null && tel.length()<7) {
			ToastHelper.showS("联系电话格式错误");
			return false;
		}
		return true;
	}
	
	private TextView findViewHasHint(ViewGroup vg) {
		
		if(vg == null || vg.getChildCount() == 0) {
			return null;
		}
		for(int i = 0 ; i< vg.getChildCount(); i++){
			View child = vg.getChildAt(i);
			if(child!=null && child instanceof TextView) {
				TextView childTv = (TextView )child;
				if(childTv.isShown() && childTv.getHint()!=null && (childTv.getText().toString().trim().isEmpty())) {
					return childTv;
				} 
			}
			if(child instanceof ViewGroup) {
				TextView textView = findViewHasHint((ViewGroup) child);
				if(textView!=null) {
					return textView;
				}
			}
		}
		return null;
	}
	
	private String getIdOf(SingleChoiceDialog dialog) {
		
		if(dialog !=null && dialog.getCheckedItem()!=null) {
			return ((CommonType) houseTypeDialog.getCheckedItem()).id;
		}
		return null;
	}
	
	private void onClickAddPhoto() {

		// TODO 拆分菜单，从相册里选择图片，图片多选控件等等
		ListDialog.Builder builder = new ListDialog.Builder(this);
		builder.addItem(R.string.start_camera, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startCamera();
			}
		}).addItem(R.string.add_pic_from_local, new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(PublishSecondaryHouseActivity.this,
						PhotoSelectorActivity.class);
				startActivityForResult(intent, REQUEST_SELECT_PHOTOS);
			}
		}).create().show();
	}

	private void startCamera() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri filePahtUri = Uri
				.fromFile(new File(PhotoManager.TEMP_PHOTO_BITMAP));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, filePahtUri);
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Activity.RESULT_OK != resultCode) {
			return;
		}
		switch (requestCode) {
		case REQUEST_CODE_CAMERA:
			onResultCamera();
			break;
		case REQUEST_SELECT_PHOTOS:
			onResultSelectPhotos(data);
		case REQUEST_SELECT_AREA:
			onResultSelectAddressRange(data);
			break;
		case REQUEST_SELECT_COMMUNITY:
			onResultSelectCommunity(data);
			break;
		default:
			break;
		}
	}

	private void onResultSelectCommunity(Intent data) {

		if (data != null && data.getExtras() != null) {
			community = (Community) data
					.getSerializableExtra(SelectCommunityActivity.RESULT_DATA_COMUUNITY);
			if (community != null && community.communityName != null) {
				tv_community_aps.setText(community.communityName);
			}
		}
	}

	private void onResultSelectAddressRange(Intent data) {

		if (data != null && data.getExtras() != null) {
			subAddressRange = (AddressRange) data
					.getSerializableExtra(SelectSubAreaActivity.RESULT_DATA_ADDRESS_RANGE);
			fatherAddressRange = (AddressRange) data
					.getSerializableExtra(SelectSubAreaActivity.RESULT_DATA_FATHER_ADDRESS_RANGE);
			if (subAddressRange != null && subAddressRange.addressName != null) {
				tv_area_aps.setText(subAddressRange.addressName);
				tv_community_aps.setText("");
				community = null;
			}
		}
	}

	private void onResultSelectPhotos(Intent data) {

		if (data != null && data.getExtras() != null) {
			@SuppressWarnings("unchecked")
			List<PhotoModel> photos = (List<PhotoModel>) data.getExtras()
					.getSerializable("photos");
			if (photos != null) {
				for (PhotoModel pm : photos) {
					photoAdapter.addPhoto(pm.getOriginalPath());
				}
			}
		}
	}

	private void onResultCamera() {

		String photoPath = photoManager.compressPicture(
				PhotoManager.TEMP_PHOTO_BITMAP, PhotoManager.UPLOAD_PHOTO_PATH);
		photoAdapter.addPhoto(photoPath);
	}

	private void getData() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("actionRelation", actionRelation);
		map.put("actionType", actionType);
	    String uid = String.valueOf(SharePreferenceUtils.getObject(
				SharePreferenceUtils.USER, User.class).uid);
		map.put("uid", uid);
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.ADD_HOUSE_CONFIG, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						System.out.println(response);
						AddHouseInfo info = GsonUtils.toObj(response,
								AddHouseInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// 请求数据失败
							return;
						}
						addressList = info.data.addressList;
						houseLayoutList = info.data.houseLayoutList;
						houseTypeList = info.data.houseTypeList;
						houseFitList = info.data.houseFitList;
						properLongList = info.data.properLongList;
						roomList = info.data.roomList;
						payTypeList = info.data.payTypeList;
						sexLimitList = info.data.sexLimitList;
						token = info.data.token;
						//0 表示是经纪人
						if(info.data.isAgent == 0) {
							rb_agent_aps.setEnabled(true);
						} else {
							rb_agent_aps.setEnabled(false);
						}
						createHouseLayoutDialog();
						createHouseFitDialog();
						createHouseTypeDialog();
						createProperLongDialog();
						createPayTypeDialog();
						createRoomTypeDialog();
						createSexLimitDialog();
						if (info.data.towardsList != null) {
							towardsDialog = new SingleChoiceDialog(
									getActivity());
							createCommonDialog(towardsDialog,
									info.data.towardsList, tv_towards_aps,
									"选择朝向");
						}
						if (info.data.properTypeList != null) {
							properTypeDialog = new SingleChoiceDialog(
									getActivity());
							createCommonDialog(properTypeDialog,
									info.data.properTypeList,
									tv_proper_type_aps, "选择产权类型");
						}
						
						if (info.data.payTypeList != null) {
							payTypeDialog = new SingleChoiceDialog(
									getActivity());
							createCommonDialog(payTypeDialog,
									info.data.payTypeList,
									tv_pay_type_aps, "选择付款方式");
						}
						
						

					}
				});
	}

	private void createHouseLayoutDialog() {

		if (houseLayoutDialog == null) {
			houseLayoutDialog = new SingleChoiceDialog(getActivity());
			CommonTypeAdapter hladapter = new CommonTypeAdapter(houseLayoutList);
			houseLayoutDialog.setAdapter(hladapter);
			houseLayoutDialog.setTitle("选择户型");
			houseLayoutDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommonType hl = (CommonType) houseLayoutDialog
							.getCheckedItem();
					if (hl != null && hl.name != null) {
						tv_select_layout_aps.setText(hl.name);
					}
				}
			});
		}

	}

	private void createHouseTypeDialog() {

		if (houseTypeDialog == null) {
			houseTypeDialog = new SingleChoiceDialog(getActivity());
			CommonTypeAdapter hladapter = new CommonTypeAdapter(houseTypeList);
			houseTypeDialog.setAdapter(hladapter);
			houseTypeDialog.setTitle("选择类型");
			houseTypeDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommonType hl = (CommonType) houseTypeDialog
							.getCheckedItem();
					if (hl != null && hl.name != null) {
						tv_type_aps.setText(hl.name);
					}
				}
			});
		}
	}

	private void createSexLimitDialog() {

		if (sexDialog == null) {
			sexDialog = new SingleChoiceDialog(getActivity());
			CommonTypeAdapter hladapter = new CommonTypeAdapter(sexLimitList);
			sexDialog.setAdapter(hladapter);
			sexDialog.setTitle("选择性别");
			sexDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommonType hl = (CommonType) sexDialog.getCheckedItem();
					if (hl != null && hl.name != null) {
						tv_sex_aps.setText(hl.name);
					}
				}
			});
		}
	}

	private void createPayTypeDialog() {

		if (payTypeDialog == null) {
			payTypeDialog = new SingleChoiceDialog(getActivity());
			CommonTypeAdapter hladapter = new CommonTypeAdapter(payTypeList);
			payTypeDialog.setAdapter(hladapter);
			payTypeDialog.setTitle("选择付款方式");
			payTypeDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommonType hl = (CommonType) payTypeDialog.getCheckedItem();
					if (hl != null && hl.name != null) {
						tv_pay_type_aps.setText(hl.name);
					}
				}
			});
		}
	}

	private void createRoomTypeDialog() {

		if (roomDialog == null) {
			roomDialog = new SingleChoiceDialog(getActivity());
			CommonTypeAdapter hladapter = new CommonTypeAdapter(roomList);
			roomDialog.setAdapter(hladapter);
			roomDialog.setTitle("选择房间");
			roomDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommonType hl = (CommonType) roomDialog.getCheckedItem();
					if (hl != null && hl.name != null) {
						tv_room_aps.setText(hl.name);
					}
				}
			});
		}
	}

	private void createProperLongDialog() {
		if (properLongDialog == null) {
			properLongDialog = new SingleChoiceDialog(getActivity());
			CommonTypeAdapter hladapter = new CommonTypeAdapter(properLongList);
			properLongDialog.setAdapter(hladapter);
			properLongDialog.setTitle("选择产权");
			properLongDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommonType hl = (CommonType) properLongDialog
							.getCheckedItem();
					if (hl != null && hl.name != null) {
						tv_proper_long_aps.setText(hl.name);
					}
				}
			});
		}
	}

	private void createHouseFitDialog() {

		if (houseFitDialog == null) {
			houseFitDialog = new SingleChoiceDialog(getActivity());
			CommonTypeAdapter hladapter = new CommonTypeAdapter(houseFitList);
			houseFitDialog.setAdapter(hladapter);
			houseFitDialog.setTitle("选择装修");
			houseFitDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommonType hl = (CommonType) houseFitDialog
							.getCheckedItem();
					if (hl != null && hl.name != null) {
						tv_select_decorate.setText(hl.name);
					}
				}
			});
		}

	}

	private void createCommonDialog(
			final SingleChoiceDialog signleChoiceDialog,
			ArrayList<CommonType> commonTypes, final TextView tv, String title) {

		CommonTypeAdapter hladapter = new CommonTypeAdapter(commonTypes);
		signleChoiceDialog.setAdapter(hladapter);
		signleChoiceDialog.setTitle(title);
		signleChoiceDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {

				CommonType hl = (CommonType) signleChoiceDialog
						.getCheckedItem();
				if (hl != null && hl.name != null) {
					tv.setText(hl.name);
				}
			}
		});
	}

	private SingleChoiceDialog houseTypeDialog;
	private SingleChoiceDialog houseFitDialog;
	private SingleChoiceDialog houseLayoutDialog;
	private SingleChoiceDialog floorDialog;
	private SingleChoiceDialog allFloorDialog;
	private SingleChoiceDialog properLongDialog;
	private SingleChoiceDialog properTypeDialog;
	private SingleChoiceDialog towardsDialog;
	private SingleChoiceDialog sexDialog;
	private SingleChoiceDialog roomDialog;
	private SingleChoiceDialog payTypeDialog;

	private void showFloorLayoutDialog() {

		if (floorDialog == null) {
			floorDialog = new SingleChoiceDialog(getActivity());
			FloorAdapter floorAdapter = new FloorAdapter();
			floorDialog.setAdapter(floorAdapter);
			floorDialog.setTitle("选择楼层");
			floorDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					Floor floor = (Floor) floorDialog.getCheckedItem();
					if (floor != null && floor.id != null) {
						tv_select_floor_aps.setText(floor.id);
					}
				}
			});
		}
		floorDialog.show();
	}

	private void showAllFlowDialog() {

		if (allFloorDialog == null) {
			allFloorDialog = new SingleChoiceDialog(getActivity());
			FloorAdapter floorAdapter = new FloorAdapter();
			allFloorDialog.setAdapter(floorAdapter);
			allFloorDialog.setTitle("选择总楼层");
			allFloorDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					Floor floor = (Floor) allFloorDialog.getCheckedItem();
					if (floor != null && floor.id != null) {
						tv_all_floor_aps.setText(floor.id);
					}
				}
			});
		}
		allFloorDialog.show();
	}

}
