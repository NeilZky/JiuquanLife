package com.jiuquanlife.module.house.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.PhotoAdapter;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.Floor;
import com.jiuquanlife.module.house.adapter.FloorAdapter;
import com.jiuquanlife.module.house.adapter.CommonTypeAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.PhotoManager;
import com.jiuquanlife.utils.ToastHelper;
import com.jiuquanlife.utils.UploadUtils;
import com.jiuquanlife.view.LinearListView;
import com.jiuquanlife.view.ListDialog;
import com.jiuquanlife.view.SingleChoiceDialog;
import com.jiuquanlife.vo.house.AddHouseInfo;
import com.jiuquanlife.vo.house.AddressRange;
import com.jiuquanlife.vo.house.Community;
import com.jiuquanlife.vo.house.CommmonType;
import com.jiuquanlife.vo.house.out.NewHouse;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;

public class PublishSecondaryHouseActivity extends BaseActivity {

	private static final int REQUEST_CODE_CAMERA = 1;
	protected static final int REQUEST_SELECT_PHOTOS = 2;
	protected static final int REQUEST_SELECT_AREA = 3;
	protected static final int REQUEST_SELECT_COMMUNITY = 4;
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
	private LinearLayout ll_pay_content;
	private CheckBox cb_is_loan_aps;
	private ArrayList<AddressRange> addressList;
	private ArrayList<CommmonType> houseLayoutList;
	private ArrayList<CommmonType> houseTypeList;
	private ArrayList<CommmonType> houseFitList;
	private AddressRange addressRange;
	private Community community;

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

		setContentView(R.layout.activity_publish_secondary);
		llv_photo_aps = (LinearListView) findViewById(R.id.llv_photo_aps);
		tv_area_aps = (TextView) findViewById(R.id.tv_area_aps);
		tv_community_aps = (TextView) findViewById(R.id.tv_community_aps);
		tv_select_layout_aps = (TextView) findViewById(R.id.tv_select_layout_aps);
		tv_select_floor_aps = (TextView) findViewById(R.id.tv_select_floor_aps);
		tv_select_decorate = (TextView) findViewById(R.id.tv_select_decorate);
		tv_type_aps = (TextView) findViewById(R.id.tv_type_aps);
		tv_all_floor_aps = (TextView) findViewById(R.id.tv_all_floor_aps);
		cb_is_loan_aps = (CheckBox) findViewById(R.id.cb_is_loan_aps);
		ll_pay_content = (LinearLayout) findViewById(R.id.ll_pay_content);
		cb_is_loan_aps.setOnCheckedChangeListener(onCheckedChangeListener);
		photoAdapter = new PhotoAdapter(this);
		photoAdapter.setLlv(llv_photo_aps);
		llv_photo_aps.setAdapter(photoAdapter);
		photoManager.deletePhotos(PhotoManager.UPLOAD_PHOTO_PATH);
	}
	
	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			if(isChecked) {
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
		default:
			break;
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

		if (addressRange == null) {
			ToastHelper.showL("请先选择区域");
		} else {
			Intent intent = new Intent(this, SelectCommunityActivity.class);
			intent.putExtra(SelectCommunityActivity.INTENT_KEY_AID,
					addressRange.aid);
			startActivityForResult(intent, REQUEST_SELECT_COMMUNITY);
		}
	}

	private void onClickSelectArea() {

		Intent intent = new Intent(this, SelectAreaActivity.class);
		intent.putExtra(SelectAreaActivity.INTENT_KEY_ADDRESS_LIST, addressList);
		startActivityForResult(intent, REQUEST_SELECT_AREA);
	}

	private void onClickPublish() {

		new Thread() {

			@Override
			public void run() {
				super.run();
				File uploadFolder = new File(PhotoManager.UPLOAD_PHOTO_PATH);
				File[] files = uploadFolder.listFiles();
				if (files != null) {
					for (File file : files) {
						String filePath = file.getPath();
						String res = UploadUtils.upload(
								"http://www.5ijq.cn/App/House/addHouseImg",
								filePath, null);
						file.delete();
					}
					handler.sendEmptyMessage(0);
				}
			}
		}.start();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {

			ToastHelper.showL("上传图片完成");
		};

	};

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
			onResultSelectArea(data);
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

	private void onResultSelectArea(Intent data) {

		if (data != null && data.getExtras() != null) {
			addressRange = (AddressRange) data
					.getSerializableExtra(SelectSubAreaActivity.RESULT_DATA_ADDRESS_RANGE);
			if (addressRange != null && addressRange.addressName != null) {
				tv_area_aps.setText(addressRange.addressName);
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

		RequestHelper.getInstance().getRequest(getActivity(),
				UrlConstance.ADD_HOUSE_CONFIG, new Listener<String>() {

					@Override
					public void onResponse(String response) {

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
						createHouseLayoutDialog();
						createHouseFitDialog();
						createHouseTypeDialog();
					}
				});
	}

	private void createHouseLayoutDialog() {

		if (houseLayoutDialog == null) {
			houseLayoutDialog = new SingleChoiceDialog(getActivity());
			CommonTypeAdapter hladapter = new CommonTypeAdapter(
					houseLayoutList);
			houseLayoutDialog.setAdapter(hladapter);
			houseLayoutDialog.setTitle("选择户型");
			houseLayoutDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommmonType hl = (CommmonType) houseLayoutDialog
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
			CommonTypeAdapter hladapter = new CommonTypeAdapter(
					houseTypeList);
			houseTypeDialog.setAdapter(hladapter);
			houseTypeDialog.setTitle("选择户型");
			houseTypeDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommmonType hl = (CommmonType) houseTypeDialog
							.getCheckedItem();
					if (hl != null && hl.name != null) {
						tv_type_aps.setText(hl.name);
					}
				}
			});
		}

	}
	
	private void createHouseFitDialog() {
		
		if (houseFitDialog == null) {
			houseFitDialog = new SingleChoiceDialog(getActivity());
			CommonTypeAdapter hladapter = new CommonTypeAdapter(
					houseFitList);
			houseFitDialog.setAdapter(hladapter);
			houseFitDialog.setTitle("选择装修");
			houseFitDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					CommmonType hl = (CommmonType) houseFitDialog
							.getCheckedItem();
					if (hl != null && hl.name != null) {
						tv_select_decorate.setText(hl.name);
					}
				}
			});
		}

	}
	
	private SingleChoiceDialog houseTypeDialog;
	private SingleChoiceDialog houseFitDialog;
	private SingleChoiceDialog houseLayoutDialog;
	private SingleChoiceDialog floorDialog;
	private SingleChoiceDialog allFloorDialog;

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
	
	
	
	private void publishHouse() {

		NewHouse newHouse = new NewHouse();
		RequestHelper.getInstance().postRequestEntity(getActivity(),
				UrlConstance.NEW_HOUSE, newHouse, new Listener<String>() {

					@Override
					public void onResponse(String response) {

					}
				});
	}

}
