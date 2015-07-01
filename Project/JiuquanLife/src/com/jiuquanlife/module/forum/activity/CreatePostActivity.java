package com.jiuquanlife.module.forum.activity;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.google.gson.reflect.TypeToken;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.PhotoAdapter;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.Photo;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.http.RequestHelper.OnFinishListener;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.Md5Utils;
import com.jiuquanlife.utils.MulityLocationManager;
import com.jiuquanlife.utils.MulityLocationManager.OnLocationChangedListener;
import com.jiuquanlife.utils.PhotoManager;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.ToastHelper;
import com.jiuquanlife.utils.UploadUtils;
import com.jiuquanlife.view.HorizontalListView;
import com.jiuquanlife.view.ListDialog;
import com.jiuquanlife.view.SingleChoiceDialog;
import com.jiuquanlife.vo.BaseData;
import com.jiuquanlife.vo.forum.Border;
import com.jiuquanlife.vo.forum.BorderType;
import com.jiuquanlife.vo.forum.Content;
import com.jiuquanlife.vo.forum.ForumIndexData;
import com.jiuquanlife.vo.forum.Topic;
import com.jiuquanlife.vo.forum.createpost.CreatePost;
import com.jiuquanlife.vo.forum.createpost.CreatePostBody;
import com.jiuquanlife.vo.forum.createpost.CreatePostJson;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;

public class CreatePostActivity extends BaseActivity{
	
	public static final String EXTRA_CREATE_TYPE = "EXTRA_CREATE_TYPE";
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_LOCAL_PHOTO = 1;
	public static final int TYPE_CAMERA = 2;
	
	private static final int REQUEST_SELECT_TOPIC = 3;
	protected static final int MSG_UPLOADED_PHOTOS = 1;
	private static final int REQUEST_CODE_CAMERA = 1;
	protected static final int REQUEST_SELECT_PHOTOS = 2;
	
	private TextView tv_addr_create_post;
	private TextView et_content_create_post;
	private TextView tv_select_topic_create_post;
	private TextView et_title_create_post;
	private TextView tv_select_type_create_post;
	private CheckBox cb_is_only_author_create_post;
	private HorizontalListView hlv_photo_create_post;
	private PhotoAdapter photoAdapter;
	private PhotoManager photoManager = PhotoManager.getInstance();
	private MulityLocationManager mulityLocationManager;
	private Topic topic;
	private Border border;
	private double longitude;
	private double latitude;
	private SingleChoiceDialog typeDialog = new SingleChoiceDialog(getActivity());

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		initViews();
		requestLoc();
	}

	private void initViews() {
		
		setContentView(R.layout.activity_create_post);
		cb_is_only_author_create_post = (CheckBox) findViewById(R.id.cb_is_only_author_create_post);
		tv_addr_create_post = (TextView) findViewById(R.id.tv_addr_create_post);
		tv_select_topic_create_post = (TextView) findViewById(R.id.tv_select_topic_create_post);
		et_content_create_post = (EditText) findViewById(R.id.et_content_create_post);
		et_title_create_post = (EditText) findViewById(R.id.et_title_create_post);
		hlv_photo_create_post = (HorizontalListView) findViewById(R.id.hlv_photo_create_post);
		tv_select_type_create_post = (TextView) findViewById(R.id.tv_select_type_create_post);
		photoAdapter = new PhotoAdapter(getActivity());
		hlv_photo_create_post.setAdapter(photoAdapter);
		mulityLocationManager = MulityLocationManager.getInstance(getApplicationContext());
		mulityLocationManager.setOnLocationChangedListener(onLocationChangedListener);
		photoManager.deletePhotos(PhotoManager.UPLOAD_PHOTO_PATH);
	}
	
	private OnLocationChangedListener onLocationChangedListener = new OnLocationChangedListener() {
		
		@Override
		public void onLocationChanged(double latitude, double longitude,
				double accyarcy, String addr) {
			CreatePostActivity.this.longitude = longitude;
			CreatePostActivity.this.latitude = latitude;
			TextViewUtils.setText(tv_addr_create_post, addr);
		}
	};
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ll_select_topic:
			onClickSelectTopic();
			break;
		case R.id.ll_request_loc:
			requestLoc();
			break;
		case R.id.iv_add_photo_create_post:
			onClickAddPhoto();
			break;
		case R.id.btn_publish_create_post:
			onClickPublish();
			break;
		case R.id.ll_select_type:
			onClickSelectType();
			break;
		default:
			break;
		}
	}

	private void onClickSelectType() {
		
		if(tv_select_topic_create_post.getText().toString().isEmpty()) {
			ToastHelper.showL("请先选择板块");
			return;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fid", String.valueOf(border.board_id));
		showProgressDialog();
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_TOPIC_TYPE_LIST, map, new Listener<String>() {
					
					@Override
					public void onResponse(String response) {
						
						Type type = new TypeToken<BaseData<List<BorderType>>>() {
						}.getType();
						BaseData<List<BorderType>> info = GsonUtils.toObj(
								response, type);
						typeDialog.
						ToastHelper.showL(info.data.toString());
					}
					
				}, new OnFinishListener() {
					
					@Override
					public void onFinish() {
						
						dismissProgressDialog();
					}
				});
		
	}
	
	private void requestLoc() {
		
		tv_addr_create_post.setText("");
		mulityLocationManager.requestLocation();
		this.longitude = 0;
		this.latitude = 0;
	}

	private void onClickSelectTopic() {
		
		startActivityForResult(SelectTopicActivity.class, REQUEST_SELECT_TOPIC);
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

				Intent intent = new Intent(getActivity(),
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
		case REQUEST_SELECT_TOPIC:
			onResultSelectTopic(data);
			break;
		case REQUEST_CODE_CAMERA:
			onResultCamera();
			break;
		case REQUEST_SELECT_PHOTOS:
			onResultSelectPhotos(data);
		default:
			break;
		}
	}
	private void onResultSelectTopic(Intent data) {
		
		border = (Border) data.getSerializableExtra(PostListActivity.EXTRA_BORDER);
		topic = (Topic) data.getSerializableExtra(PostListActivity.EXTRA_TOPIC);
		TextViewUtils.setText(tv_select_topic_create_post, border.board_name);
	}

	private void onResultCamera() {

		String photoPath = photoManager.compressPicture(
				PhotoManager.TEMP_PHOTO_BITMAP, PhotoManager.UPLOAD_PHOTO_PATH);
		photoAdapter.addPhoto(photoPath);
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
	
	private void publishData(ArrayList<String> photos) {
		
		if(!verifyInput()) {
			return;
		}
		Map<String, String> map = new HashMap<String, String>();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("act", "new");
		String mAppHash = Md5Utils.md5(
				new StringBuilder(String.valueOf(System
						.currentTimeMillis())).toString().substring(0,
						5)
						+ "appbyme_key").substring(8, 16).toLowerCase();
		map.put("apphash", mAppHash);

		CreatePost createPost = new CreatePost();
		createPost.body = new CreatePostBody();
		CreatePostJson createPostJson= new CreatePostJson();
		createPost.body.json = createPostJson;
		if(cb_is_only_author_create_post.isChecked()) {
			createPostJson.isOnlyAuthor = 1;
		}
		
		if(longitude!=0){
			createPostJson.location = tv_addr_create_post.getText().toString();
			createPostJson.longitude = String.valueOf(this.longitude);
			createPostJson.latitude = String.valueOf(this.latitude);
		}
		
		createPostJson.title = et_title_create_post.getText().toString();
		createPostJson.typeId = border.board_id;
		createPostJson.fid = topic.board_category_id;
		createPostJson.isShowPostion = 1;
		
		ArrayList<Content> contents = new ArrayList<Content>();
		if(photos!=null && !photos.isEmpty()) {
			for(String temp : photos) {
				Content content = new Content();
				content.type = 1;
				content.infor = temp;
				contents.add(content);
			}
		}
		
		Content content = new Content();
		content.type = 0;
		content.infor = et_content_create_post.getText().toString();
		contents.add(content);
		createPostJson.content = GsonUtils.toJson(contents);
		map.put("json", GsonUtils.toJson(createPost));
		RequestHelper.getInstance().postRequestMap(getActivity(),
				UrlConstance.FORUM_CREATE_POST_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						ToastHelper.showL(response);
					}
				});
	}

	private boolean verifyInput() {
		
		if(et_title_create_post.getText().toString().trim().isEmpty()) {
			ToastHelper.showS("请填写标题");
			return false;
		}
		
		if(et_content_create_post.toString().trim().isEmpty()) {
			ToastHelper.showS("请填写内容");
			return false;
		}
		
		if(tv_select_topic_create_post.toString().trim().isEmpty()) {
			ToastHelper.showS("请选择板块");
			return false;
		}
		return true;
	}
	
}
