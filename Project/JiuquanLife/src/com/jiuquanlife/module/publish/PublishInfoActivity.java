package com.jiuquanlife.module.publish;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.Util;
import com.jiuquanlife.module.company.activity.Bimp;
import com.jiuquanlife.module.company.activity.PhotoActivity;
import com.jiuquanlife.module.company.activity.TestPicActivity;
import com.jiuquanlife.module.company.entity.AddressInfo;
import com.jiuquanlife.module.company.util.ImageThumbnail;
import com.jiuquanlife.module.publish.entity.NewInfo;
import com.jiuquanlife.module.publish.entity.TypeGroupInfo;
import com.jiuquanlife.module.publish.entity.TypeInfo;
import com.jiuquanlife.utils.UploadUtils;

public class PublishInfoActivity extends BaseActivity {
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private int pic_width;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_publish);
		Common.checkLogin(PublishInfoActivity.this);
		Bimp.FROM_WHERE = "Publish";
		initView();
		loadData();
	}

	private NewInfo newInfo;

	private void loadData() {
		RequestHelper.getInstance().getRequest(getApplicationContext(),
				Common.PUBLISH_ADDINFO, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get company info");
							mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
							return;
						}
						System.out.println("Result:" + response.toString());
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(response);

							if (jsonObject.has("data")) {
								jsonObject = jsonObject.getJSONObject("data");

								Gson gson = new Gson();
								newInfo = gson.fromJson(jsonObject.toString(),
										NewInfo.class);
								mHandler.sendEmptyMessage(MESSAGE_INIT_DATA_SUCCESS);
								System.out.println("newInfo:" + newInfo);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private int checkInput() {
		if (TextUtils.isEmpty(etTitle.getText())) {
			return 1;
		}
		if (TextUtils.isEmpty(etPhone.getText())) {
			return 2;
		}
		if (TextUtils.isEmpty(etContent.getText())) {
			return 3;
		}

		if (!Util.isMobileNO(etPhone.getText().toString())) {
			return 4;
		}
		return 0;
	}

	private Spinner spAddress, spCategory, spSubCategory;
	private EditText etTitle, etPhone, etContent;
	private TextView tvCancel, tvSubmit;
	private Button btn_publish2;

	private OnClickListener publishListener=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			int result = checkInput();
			if (result == 0) {
				tvSubmit.setClickable(false);
				startProgressDialog("提交中......");
				postImage();
				// TODO Auto-generated method stub
			} else {
				switch (result) {
				case 4:
					Toast.makeText(PublishInfoActivity.this, "请输入正确的手机号码",
							Toast.LENGTH_SHORT).show();

					break;

				default:
					Toast.makeText(PublishInfoActivity.this, "请输入必填内容",
							Toast.LENGTH_SHORT).show();
					break;
				}

			}
			
		}
	};
	private void initView() {
		btn_publish2=(Button) findViewById(R.id.btn_publish2);
		btn_publish2.setOnClickListener(publishListener);
		tvCancel = (TextView) findViewById(R.id.btn_cancel);
		tvCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		tvSubmit = (TextView) findViewById(R.id.btn_publish);
		tvSubmit.setOnClickListener(publishListener);
		etTitle = (EditText) findViewById(R.id.et_title);
		etPhone = (EditText) findViewById(R.id.et_phone);
		etContent = (EditText) findViewById(R.id.et_content);
		spAddress = (Spinner) findViewById(R.id.sp_address);
		spCategory = (Spinner) findViewById(R.id.sp_category);
		spSubCategory = (Spinner) findViewById(R.id.sp_subcategory);

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					new PopupWindows(PublishInfoActivity.this, noScrollgridview);
				} else {
					Intent intent = new Intent(PublishInfoActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		pic_width = ImageThumbnail.dip2px(this, 75);

		PIC_PATH = Environment.getExternalStorageDirectory().getPath()
				+ "/jiuquan/publish/";
		deleteDir(PIC_PATH);
	}

	public static void deleteDir(String path) {
		File dir = new File(path);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 鍒犻櫎鎵?湁鏂囦欢
			else if (file.isDirectory())
				deleteDir(file.getPath()); // 閫掕鐨勬柟寮忓垹闄ゆ枃浠跺す
		}
		dir.delete();// 鍒犻櫎鐩綍鏈韩
	}

	protected void doTakePhoto() {
		try {
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		Bitmap bitMap = null;
		switch (requestCode) {

		case CAMERA_WITH_DATA: // 拍照
			Bundle bundle = data.getExtras();
			bitMap = (Bitmap) bundle.get("data");
			if (bitMap != null)
				bitMap.recycle();
			bitMap = (Bitmap) data.getExtras().get("data");
			int scale = ImageThumbnail.reckonThumbnail(bitMap.getWidth(),
					bitMap.getHeight(), pic_width, pic_width);
			bitMap = ImageThumbnail.PicZoom(bitMap,
					(int) (bitMap.getWidth() / scale),
					(int) (bitMap.getHeight() / scale));

			String path = savePhotoToSDCard(PIC_PATH, createFileName(), bitMap);
			System.out.println("Path:" + path);
			if (Bimp.drr.size() < 4 && !TextUtils.isEmpty(path)) {
				Bimp.drr.add(path);
				Bimp.bmp.add(bitMap);
				Bimp.max += 1;
			}
			mHandler.sendEmptyMessage(MSG_UPDATE_IMG);
			break;
		}
	}

	private String PIC_PATH;
	private static final int MESSAGE_INIT_DATA_SUCCESS = 1;
	private static final int MSG_INIT_DATA_FAILED = 2;
	private static final int MSG_SUBMIT_DATA_SUCCESSED = 3;
	private static final int MSG_SUBMIT_DATA_FAILED = 4;
	private static final int MSG_UPDATE_IMG = 5;
	private static final int CAMERA_WITH_DATA = 1001;
	private static final int PHOTO_PICKED_WITH_DATA = 1002;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_INIT_DATA_SUCCESS:
				initViewData();
				break;
			case MSG_INIT_DATA_FAILED:
				Toast.makeText(getApplicationContext(), "加载数据失败",
						Toast.LENGTH_LONG).show();
				finish();
				break;
			case MSG_SUBMIT_DATA_FAILED:
				stopProgressDialog();
				Toast.makeText(getApplicationContext(), "提交失败",
						Toast.LENGTH_LONG).show();
				break;
			case MSG_SUBMIT_DATA_SUCCESSED:
				Toast.makeText(getApplicationContext(), "发布成功",
						Toast.LENGTH_LONG).show();
				stopProgressDialog();
				sendBroadcast(new Intent("com.jiuquan.updatePublishInfo"));

				Intent intent = new Intent(PublishInfoActivity.this,
						PublishActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();

				break;
			case MSG_UPDATE_IMG:
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};
	private ArrayAdapter<AddressInfo> addressAdapter;
	private ArrayAdapter<TypeGroupInfo> typeGroupAdapter;
	private ArrayAdapter<TypeInfo> typeAdapter;

	private void initViewData() {
		if (null == newInfo)
			return;
		final List<TypeGroupInfo> typeGroupInfos = newInfo.getTypeList();
		if (null != typeGroupInfos && !typeGroupInfos.isEmpty()) {
			typeGroupAdapter = new ArrayAdapter<TypeGroupInfo>(this,
					android.R.layout.simple_spinner_item, typeGroupInfos);
			typeGroupAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spCategory.setAdapter(typeGroupAdapter);
			spCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					TypeGroupInfo typeGroupInfo = typeGroupInfos.get(position);
					final List<TypeInfo> typeInfos = typeGroupInfo
							.getSubInfoTypeList();
					categoryId = typeGroupInfo.getTypeId();
					if (null != typeInfos && !typeInfos.isEmpty()) {
						typeAdapter = new ArrayAdapter<TypeInfo>(
								PublishInfoActivity.this,
								android.R.layout.simple_spinner_item, typeInfos);
						typeAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spSubCategory.setAdapter(typeAdapter);
						spSubCategory
								.setOnItemSelectedListener(new OnItemSelectedListener() {

									@Override
									public void onItemSelected(
											AdapterView<?> arg0, View arg1,
											int position, long arg3) {

										TypeInfo typeInfo = typeInfos
												.get(position);
										subCategoryId = typeInfo.getTypeId();
										System.out
												.println("call seleteced sub...."
														+ subCategoryId);
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> arg0) {
										// TODO Auto-generated method stub

									}
								});
					}

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}

		final List<AddressInfo> addressInfos = newInfo.getAddressList();
		if (null != addressInfos && !addressInfos.isEmpty()) {
			addressAdapter = new ArrayAdapter<AddressInfo>(this,
					android.R.layout.simple_spinner_item, addressInfos);
			addressAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spAddress.setAdapter(addressAdapter);
			spAddress.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int pos, long arg3) {
					// TODO Auto-generated method stub
					addressId = addressInfos.get(pos).getAid();

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	public String createFileName() {
		StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis());
		sb.append(".jpg");
		return sb.toString();
	}

	public static String savePhotoToSDCard(String path, String photoName,
			Bitmap photoBitmap) {

		String photoPath = "";
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File photoFile = new File(path, photoName); // 在指定路径下创建文件
			photoPath = photoFile.getPath();
			if (!photoFile.exists()) {
				try {
					photoFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					if (null != fileOutputStream)
						fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return photoPath;
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					doTakePhoto();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PublishInfoActivity.this,
							TestPicActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private int addressId;
	private String categoryId, subCategoryId;

	private void postImage() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				File uploadFolder = new File(PIC_PATH);
				File[] files = uploadFolder.listFiles();
				StringBuilder sb = new StringBuilder();
				if (files != null) {
					for (File file : files) {
						String filePath = file.getPath();
						System.out.println("filePath name:" + filePath);
						String res = UploadUtils.upload(
								"http://www.5ijq.cn/App/Info/addInfoImg",
								filePath, null);
						if (!TextUtils.isEmpty(res)) {
							try {
								JSONObject jsonObject = new JSONObject(res);
								if (jsonObject.has("code")) {
									int code = jsonObject.getInt("code");
									if (code == 1) {
										sb.append(jsonObject.get("data"));
										sb.append("*fg*");
									}

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						System.out.println("upload image:" + res);
						file.delete();
					}
				}
				System.out.println("SB:" + sb.toString());
				String title = etTitle.getText().toString();
				String phone = etPhone.getText().toString();
				String content = etContent.getText().toString();

				/* 建立HTTPost对象 */
				HttpPost httpRequest = new HttpPost(Common.PUBLISH_SUBMIT);
				/*
				 * NameValuePair实现请求参数的封装
				 */
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("uid", Common.getUid()));
				params.add(new BasicNameValuePair("addressId", String
						.valueOf(addressId)));
				params.add(new BasicNameValuePair("token", newInfo.getToken()));
				params.add(new BasicNameValuePair("infoTypeId", String
						.valueOf(subCategoryId)));
				params.add(new BasicNameValuePair("infoTitle", title));
				params.add(new BasicNameValuePair("infoTel", phone));
				params.add(new BasicNameValuePair("device", "1"));
				params.add(new BasicNameValuePair("infoContent", content));
				if (!TextUtils.isEmpty(sb)) {
					params.add(new BasicNameValuePair("coverImage", sb
							.toString()));
				}
				try {
					/* 添加请求参数到请求对象 */
					httpRequest.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					/* 发送请求并等待响应 */
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(httpRequest);
					/* 若状态码为200 ok */
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						/* 读返回数据 */
						String response = EntityUtils.toString(httpResponse
								.getEntity());
						System.out.println("Response comment:" + response);
						if (TextUtils.isEmpty(response)) {
							mHandler.sendEmptyMessage(MSG_SUBMIT_DATA_FAILED);
							return;
						}
						System.out.println("Comment response:" + response);
						mHandler.sendEmptyMessage(MSG_SUBMIT_DATA_SUCCESSED);
					}

				} catch (ClientProtocolException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}).start();
	}

	private static final int TAKE_PICTURE = 0x000000;

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item璁剧疆
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 3) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							mHandler.sendEmptyMessage(MSG_UPDATE_IMG);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out
										.println("photo from gallery:" + path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(path
										.lastIndexOf("/") + 1);

								String savePath = savePhotoToSDCard(PIC_PATH,
										newStr, bm);
								Bimp.max += 1;
								mHandler.sendEmptyMessage(MSG_UPDATE_IMG);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (isFinishing()) {
			Bimp.FROM_WHERE = "";
			Bimp.drr.clear();
			Bimp.bmp.clear();
			Bimp.max = 0;
			Bimp.act_bool = true;
		}
		super.onPause();
	}

}
