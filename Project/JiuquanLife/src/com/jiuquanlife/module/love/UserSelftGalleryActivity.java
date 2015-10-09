package com.jiuquanlife.module.love;

import java.io.File;
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
import android.net.Uri;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.activity.Bimp;
import com.jiuquanlife.module.company.activity.LargePicActivity;
import com.jiuquanlife.module.company.activity.PhotoActivity;
import com.jiuquanlife.module.company.activity.TestPicActivity;
import com.jiuquanlife.module.company.adapter.ImgAdapter;
import com.jiuquanlife.module.company.util.ImageThumbnail;
import com.jiuquanlife.module.love.entity.LifeImg;
import com.jiuquanlife.module.love.entity.SelfHeadImg;
import com.jiuquanlife.module.love.entity.SelfImgInfos;
import com.jiuquanlife.module.love.entity.UploadImgInfoList;
import com.jiuquanlife.module.love.entity.UploadImgInfos;
import com.jiuquanlife.module.publish.PublishInfoActivity;
import com.jiuquanlife.utils.UploadUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserSelftGalleryActivity extends BaseActivity {
	private GridView noScrollgridview, oldScrollGridView;
	private GridAdapter adapter;
	private OldAdapter oldAdapter;
	private ImgAdapter imgAdapter;
	private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_REQUEST = 0xa1;
	private static final int CODE_RESULT_REQUEST = 0xa2;

	// 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
	private static int output_X = 480;
	private static int output_Y = 480;

	private ImageView iv_user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_love_img);
		Common.checkLogin(this);
		Bimp.FROM_WHERE = "UserSelftGalleryActivity";
		Bimp.IMG_SZIE = 12;
		initView();
		loadData();
	}

	private TextView tv_refresh, tv_save;
	private String PIC_PATH;
	private ImageView iv_back;

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.back);
		iv_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		iv_user = (ImageView) findViewById(R.id.iv_user);
		tv_refresh = (TextView) findViewById(R.id.tv_refresh);
		tv_refresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new PopupWindowsHeader(UserSelftGalleryActivity.this,
						noScrollgridview);

			}
		});
		tv_save = (TextView) findViewById(R.id.tv_save);
		tv_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				postImage();

			}
		});

		oldScrollGridView = (GridView) findViewById(R.id.gv_company_gallery_old);
		oldScrollGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		oldAdapter = new OldAdapter(this);
		oldScrollGridView.setAdapter(oldAdapter);
		oldScrollGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				LifeImg info = oldAdapter.getItem(arg2);
				Intent intent = new Intent(UserSelftGalleryActivity.this,
						LargePicActivity.class);
				String path = info.getSavepath() + info.getSavename();
				intent.putExtra("picPath", path);
				startActivity(intent);

			}
		});

		noScrollgridview = (GridView) findViewById(R.id.gv_company_gallery);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					new PopupWindows(UserSelftGalleryActivity.this,
							noScrollgridview);
				} else {
					Intent intent = new Intent(UserSelftGalleryActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

		pic_width = ImageThumbnail.dip2px(this, 75);

		PIC_PATH = Environment.getExternalStorageDirectory().getPath()
				+ "/jiuquan/love/";
		File file = new File(PIC_PATH);

		file.mkdir();
	}

	// 从本地相册选取图片作为头像
	private void choseHeadImageFromGallery() {
		Intent intentFromGallery = new Intent();
		// 设置文件类型
		intentFromGallery.setType("image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
	}

	// 启动手机相机拍摄照片作为头像
	private void choseHeadImageFromCameraCapture() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
	}

	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// 设置裁剪
		intent.putExtra("crop", "true");

		// aspectX , aspectY :宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : 裁剪图片宽高
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	private void setImageToHeadView(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");

			String savePath = PublishInfoActivity.savePhotoToSDCard(PIC_PATH,
					IMAGE_FILE_NAME, photo);
			iv_user.setImageBitmap(photo);
		}
	}

	/**
	 * 检查设备是否存在SDCard的工具方法
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 有存储的SDCard
			return true;
		} else {
			return false;
		}
	}

	public class PopupWindowsHeader extends PopupWindow {

		public PopupWindowsHeader(Context mContext, View parent) {

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
					choseHeadImageFromCameraCapture();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					choseHeadImageFromGallery();
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
					Intent intent = new Intent(UserSelftGalleryActivity.this,
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

	protected void doTakePhoto() {
		try {
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	private int pic_width;
	private static final int CAMERA_WITH_DATA = 1001;
	private static final int PHOTO_PICKED_WITH_DATA = 1002;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
			return;
		}
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

			String path = PublishInfoActivity.savePhotoToSDCard(PIC_PATH,
					createFileName(), bitMap);
			System.out.println("Path:" + path);
			if (Bimp.drr.size() < (Bimp.IMG_SZIE + 1)
					&& !TextUtils.isEmpty(path)) {
				Bimp.drr.add(path);
				Bimp.bmp.add(bitMap);
				Bimp.max += 1;
			}
			mHandler.sendEmptyMessage(MSG_UPDATE_IMG);
			break;

		case CODE_GALLERY_REQUEST:
			cropRawPhoto(data.getData());
			break;

		case CODE_CAMERA_REQUEST:
			if (hasSdcard()) {
				File tempFile = new File(
						Environment.getExternalStorageDirectory(),
						IMAGE_FILE_NAME);
				cropRawPhoto(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
						.show();
			}

			break;

		case CODE_RESULT_REQUEST:
			if (data != null) {
				setImageToHeadView(data);

			}

			break;
		}
	}

	public String createFileName() {
		StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis());
		sb.append(".jpg");
		return sb.toString();
	}

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private static final int MSG_SUBMIT_DATA_SUCCESSED = 3;
	private static final int MSG_SUBMIT_DATA_FAILED = 4;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:
				stopProgressDialog();
				Toast.makeText(UserSelftGalleryActivity.this, "未能找到数据",
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			case MSG_INIT_DATA_SUCCESS:
				initViewData();
				stopProgressDialog();

				break;
			case MSG_UPDATE_IMG:
				adapter.notifyDataSetChanged();
				break;
			case MSG_SUBMIT_DATA_SUCCESSED:
				stopProgressDialog();
				Toast.makeText(getApplicationContext(), "上传成功",
						Toast.LENGTH_LONG).show();
				break;
			case MSG_SUBMIT_DATA_FAILED:
				stopProgressDialog();
				Toast.makeText(getApplicationContext(), "上传失败",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void initViewData() {
		if (null == headImgInfos) {
			return;
		}

		SelfHeadImg selfHeadImg = headImgInfos.getHeadPhoto();
		if (null != selfHeadImg) {
			String path = selfHeadImg.getHeadsavepath()
					+ selfHeadImg.getHeadsavename();
			ImageLoader.getInstance().displayImage(Common.PIC_PREFX + path,
					iv_user);

		}
		if (null != headImgInfos.getLifePhotoList())
			oldAdapter.showImg(headImgInfos.getLifePhotoList());
	}

	private String mid = Common.getMid();

	private void loadData() {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_USER_GALLERY_URL);
		sb.append("/mid/");
		sb.append(mid);

		System.out.println("Urlll:" + sb.toString());

		RequestHelper.getInstance().getRequest(getApplicationContext(),
				sb.toString(), new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");
							mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

							return;
						}
						parse(response);

					}

				});
	}

	SelfImgInfos headImgInfos;

	private void parse(String response) {
		System.out.println("result:" + response);
		try {

			Log.d(Common.TAG, "Detail data:" + response);
			JSONObject jsonObject = new JSONObject(response);

			if (jsonObject.has("data")) {
				jsonObject = jsonObject.getJSONObject("data");
				System.out.println("detialInfo:::" + jsonObject);
				Gson gson = new Gson();
				headImgInfos = gson.fromJson(jsonObject.toString(),
						SelfImgInfos.class);
				if (null != headImgInfos) {
					System.out.println("userInfo:" + headImgInfos.toString());
					mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

				} else {
					mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static final int TAKE_PICTURE = 0x000000;

	public class OldAdapter extends BaseAdapter {
		private List<LifeImg> imgs = new ArrayList<LifeImg>();

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgs.size();
		}

		@Override
		public LifeImg getItem(int position) {
			// TODO Auto-generated method stub
			return imgs.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

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

			LifeImg img = imgs.get(position);
			String imagePath = img.getSavepath() + img.getSavename();
			ImageLoader.getInstance().displayImage(
					"http://www.5ijq.cn/public/uploads/" + imagePath,
					holder.image, App.getOptions());

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		private LayoutInflater inflater;

		public OldAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void showImg(List<LifeImg> imgs) {
			this.imgs = imgs;
			notifyDataSetChanged();
		}

	}

	@Override
	protected void onResume() {
		adapter.update();
		super.onResume();
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;
		private List<LifeImg> imgs = new ArrayList<LifeImg>();

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void showImg(List<LifeImg> imgs) {
			this.imgs = imgs;
			notifyDataSetChanged();
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1) + imgs.size();
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

			if (position == (Bimp.bmp.size() + imgs.size())) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == Bimp.IMG_SZIE) {
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

								String savePath = PublishInfoActivity
										.savePhotoToSDCard(PIC_PATH, newStr, bm);
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

	private static final int MSG_UPDATE_IMG = 5;

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		if (isFinishing()) {
			Bimp.FROM_WHERE = "";
			Bimp.drr.clear();
			Bimp.bmp.clear();
			Bimp.max = 0;
			Bimp.IMG_SZIE = 3;
			Bimp.act_bool = true;
		}
	}

	private void postImage() {
		startProgressDialog("上传中...");
		new Thread(new Runnable() {

			@Override
			public void run() {
				File uploadFolder = new File(PIC_PATH);
				File[] files = uploadFolder.listFiles();
				StringBuilder sb = new StringBuilder();
				String resultData = "";
				boolean isHeader = false;
				if (files != null) {
					for (File file : files) {
						String filePath = file.getPath();
						System.out.println("filePath name:" + filePath);
						String url = "http://www.5ijq.cn/App/Marry/addUserPhotoImg";

						if (filePath.contains(IMAGE_FILE_NAME)) {
							url += "/photoType/headpic";
							isHeader = true;
						} else {
							isHeader = false;
							url += "/photoType/lifepic";
						}

						String res = UploadUtils.upload(url, filePath, null);
						System.out.println("res:" + res);
						if (!TextUtils.isEmpty(res)) {
							try {
								JSONObject jsonObject = new JSONObject(res);
								if (jsonObject.has("code")) {
									int code = jsonObject.getInt("code");
									if (code == 1) {

										if (isHeader) {
											resultData = (String) jsonObject
													.get("data");
											updateImage(true, resultData);
										} else {
											Gson gson = new Gson();
											UploadImgInfoList infoList = gson.fromJson(
													jsonObject.toString(),
													UploadImgInfoList.class);
											List<UploadImgInfos> imgInfos = infoList
													.getData();
											String saveName = "";
											String savePath = "";
											if (null != imgInfos) {
												UploadImgInfos uploadImgInfos = imgInfos
														.get(0);
												if (null != uploadImgInfos) {
													saveName = uploadImgInfos
															.getSavename();
													savePath = uploadImgInfos
															.getSavepath();
												}
											}
											System.out
													.println("result::"
															+ savePath + ","
															+ saveName);
											sb.append(savePath + "," + saveName);
											sb.append("*fg*");
										}
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
					System.out.println("SB:" + sb.toString());
					if (!TextUtils.isEmpty(sb))
						updateImage(false, sb.toString());

				}

			}
		}).start();
	}

	private void updateImage(boolean isHeader, String resultData) {

		/* 建立HTTPost对象 */
		HttpPost httpRequest = new HttpPost(Common.LOVE_SUBMIT_IMG);
		/*
		 * NameValuePair实现请求参数的封装
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uid", Common.getUid()));
		params.add(new BasicNameValuePair("mid", Common.getMid()));
		if (!TextUtils.isEmpty(resultData)) {
			System.out.println("sb content:" + resultData.toString());
			if (isHeader) {
				int last = resultData.lastIndexOf("/");
				String saveName = resultData.substring(last + 1);
				System.out.println("saveName" + saveName);
				String savePath = resultData.substring(0, last + 1);
				System.out.println("savePatg:" + savePath);
				params.add(new BasicNameValuePair("photoType", "headpic"));
				params.add(new BasicNameValuePair("savePath", savePath));
				params.add(new BasicNameValuePair("saveName", saveName));
			} else {
				params.add(new BasicNameValuePair("photoType", "lifepic"));
				params.add(new BasicNameValuePair("imgPaths", resultData
						.toString()));
			}
		}
		try {
			/* 添加请求参数到请求对象 */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 发送请求并等待响应 */

			System.out.println("Submit url:" + httpRequest.getURI().toString());
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				String response = EntityUtils
						.toString(httpResponse.getEntity());
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

	@Override
	protected void onPause() {
		if (isFinishing()) {
			File file = new File(PIC_PATH);
			File[] files = file.listFiles();
			if (files != null) {
				for (File tempFile : files) {
					tempFile.delete();
				}
			}

		}
		super.onPause();
	}

}
