package com.jiuquanlife.module.forum.activity;

import java.io.File;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.PhotoAdapter;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.Photo;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.PostDetailAdapter;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.MulityLocationManager;
import com.jiuquanlife.utils.MulityLocationManager.OnLocationChangedListener;
import com.jiuquanlife.utils.PhotoManager;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.ToastHelper;
import com.jiuquanlife.utils.UploadUtils;
import com.jiuquanlife.view.HorizontalListView;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView.OnFooterRefreshListener;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView.OnHeaderRefreshListener;
import com.jiuquanlife.vo.forum.Content;
import com.jiuquanlife.vo.forum.PostDetailVo;
import com.jiuquanlife.vo.forum.Reply;
import com.jiuquanlife.vo.forum.createpost.Attachment;
import com.jiuquanlife.vo.forum.createpost.CreatePost;
import com.jiuquanlife.vo.forum.createpost.CreatePostBody;
import com.jiuquanlife.vo.forum.createpost.CreatePostJson;
import com.jiuquanlife.vo.forum.createpost.PhotoRes;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;

public class PostDetailActivity extends BaseActivity {

	public static final String EXTRA_TOPIC_ID = "EXTRA_TOPIC_ID";
	private PullToRefreshView ptrv_apd;
	private ListView xlv_apd;
	private LinearLayout ll_more_reply_post_detail;
	private TextView tv_addr_create_post_reply;
	private PostDetailAdapter postDetailAdapter;
	private LinearLayout ll_request_location_reply;
	private int page = 1;
	private int topicId;
	private MulityLocationManager mulityLocationManager;
	private double longitude;
	private double latitude;
	private HorizontalListView hlv_photo_create_post;
	private PhotoAdapter photoAdapter;
	private PhotoManager photoManager = PhotoManager.getInstance();
	private LinearLayout ll_addr_create_post_reply;
	private static final int REQUEST_CODE_CAMERA = 1;
	protected static final int REQUEST_SELECT_PHOTOS = 2;
	private EditText et_content_reply;
	private int tid;
	private Reply reply;
	
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

		setContentView(R.layout.activity_post_detail2);
		et_content_reply = (EditText) findViewById(R.id.et_content_reply);
		ptrv_apd = (PullToRefreshView) findViewById(R.id.ptrv_apd);
		ll_addr_create_post_reply = (LinearLayout) findViewById(R.id.ll_addr_create_post_reply);
		xlv_apd = (ListView) findViewById(R.id.xlv_apd);
		ll_more_reply_post_detail = (LinearLayout) findViewById(R.id.ll_more_reply_post_detail);
		tv_addr_create_post_reply = (TextView) findViewById(R.id.tv_addr_create_post_reply);
		ll_request_location_reply = (LinearLayout) findViewById(R.id.ll_request_location_reply);
		hlv_photo_create_post = (HorizontalListView) findViewById(R.id.hlv_photo_create_post);
		ptrv_apd.setPullDownEnable(true);
		ptrv_apd.setPullUpEnable(false);
		postDetailAdapter = new PostDetailAdapter(getActivity());
		xlv_apd.addHeaderView(postDetailAdapter.getPostDetailView());
		xlv_apd.setAdapter(postDetailAdapter);
		xlv_apd.setOnItemClickListener(onItemClickListener);
		ptrv_apd.setOnHeaderRefreshListener(onHeaderRefreshListener);
		ptrv_apd.setOnFooterRefreshListener(onFooterRefreshListener);
		mulityLocationManager = MulityLocationManager.getInstance(getApplicationContext());
		mulityLocationManager.setOnLocationChangedListener(onLocationChangedListener);
		photoAdapter = new PhotoAdapter(getActivity());
		hlv_photo_create_post.setAdapter(photoAdapter);
	}
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(position == 0) {
				et_content_reply.setHint("评论楼主");
				reply = null;
			} else {
				reply =  (Reply) parent.getItemAtPosition(position);
				et_content_reply.setHint("回复"+reply.reply_name);
			}
		}
	};
	

	private PullToRefreshView.OnFooterRefreshListener onFooterRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			addData();
		}
	};
	
	private OnLocationChangedListener onLocationChangedListener = new OnLocationChangedListener() {
		
		@Override
		public void onLocationChanged(double latitude, double longitude,
				double accyarcy, String addr) {
			PostDetailActivity.this.longitude = longitude;
			PostDetailActivity.this.latitude = latitude;
			TextViewUtils.setText(tv_addr_create_post_reply, addr);
		}
	};
	
	private OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {

			getData();
		}
	};


	private void initData() {

		topicId = getIntent().getIntExtra(EXTRA_TOPIC_ID, 0);
		showProgressDialog();
		ptrv_apd.setRefreshing();
	}
	
	private void requestLoc() {
		
		tv_addr_create_post_reply.setText("");
		mulityLocationManager.requestLocation();
		this.longitude = 0;
		this.latitude = 0;
	}
	
	public void getData() {
		page = 1;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "forum/postlist");
		map.put("topicId", String.valueOf(topicId));
		map.put("page", String.valueOf(page));
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						PostDetailVo info = GsonUtils.toObj(response,
								PostDetailVo.class);
						if (info == null || info.topic == null) {
							return;
						}
						postDetailAdapter.refresh(info.topic, info.list);
						if (info.has_next == 1) {
							ptrv_apd.setPullUpEnable(true);
						} else {
							ptrv_apd.setPullUpEnable(false);
						}
						if(info!=null && info.topic!=null && info.topic.topic_id!=0) {
							tid = info.topic.topic_id;
						}
						ll_more_reply_post_detail.setVisibility(View.GONE);
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
						dismissProgressDialog();
						ptrv_apd.onHeaderRefreshComplete();
					}
				});
	}

	private void addData() {

		page++;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "forum/postlist");
		map.put("topicId", String.valueOf(topicId));
		map.put("page", String.valueOf(page));
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						PostDetailVo info = GsonUtils.toObj(response,
								PostDetailVo.class);
						if (info == null) {
							return;
						}
						if (info.has_next == 1) {
							ptrv_apd.setPullUpEnable(true);
						} else {
							ptrv_apd.setPullUpEnable(false);
						}
						postDetailAdapter.add(info.list);

					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
						ptrv_apd.onFooterRefreshComplete();
					}
				});
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_collect_apd:
			onClickCollect();
			break;
		case R.id.iv_more_reply_post_detail:
			onClickMoreReply();
			break;
		case R.id.ll_request_location_reply:
			onClickRequestLocation();
			break;
		case R.id.ll_camera_reply:
			startCamera();
			break;
		case R.id.ll_add_photo_reply:
			onClickAddPhoto();
			break;
		case R.id.btn_send_reply:
			onClickPublish();
			break;
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}
	}

	private void onClickAddPhoto() {
		
		Intent intent = new Intent(getActivity(),
				PhotoSelectorActivity.class);
		startActivityForResult(intent, REQUEST_SELECT_PHOTOS);
	}
	
	private void startCamera() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri filePahtUri = Uri
				.fromFile(new File(PhotoManager.TEMP_PHOTO_BITMAP));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, filePahtUri);
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}

	private void onClickRequestLocation() {
		
		ll_addr_create_post_reply.setVisibility(View.VISIBLE);
		requestLoc();
	}

	private void onClickMoreReply() {
		
		if(ll_more_reply_post_detail.getVisibility() == View.VISIBLE) {
			ll_more_reply_post_detail.setVisibility(View.GONE);
		} else {
			ll_more_reply_post_detail.setVisibility(View.VISIBLE);
		}
		

	}

	private void onClickCollect() {

		if (logined()) {
			collect(topicId);
		}
	}

	protected void collect(int topicId) {

		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER,
				User.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userfavorite");
		map.put("action", "favorite");
		map.put("id", String.valueOf(topicId));
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		RequestHelper.getInstance().getRequestMap(this, UrlConstance.FORUM_URL,
				map, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						ToastHelper.showL("收藏成功");
					}
				});
	}

	private boolean logined() {

		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER,
				User.class);
		if (user == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return false;
		}
		return true;
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
		default:
			break;
		}
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
				ArrayList<Attachment> attachments = new ArrayList<Attachment>();
				if (photos != null) {
					for (Photo photo : photos) {
						String filePath = photo.filePath;
						PhotoRes photoRes = uploadPhoto(filePath);
						if(photoRes!=null && photoRes.body!=null && photoRes.body.attachment!=null&& photoRes.body.attachment.size() >0) {
							attachments.add(photoRes.body.attachment.get(0));
						}
					}
					Message msg = handler.obtainMessage();
					msg.obj = attachments;
					msg.what = MSG_UPLOADED_PHOTOS;
					handler.sendMessage(msg);
				}
			}
		}.start();
	}
	
	private void publishData(ArrayList<Attachment> photos) {
		
		if(!verifyInput()) {
			return;
		}
		Map<String, String> map = new HashMap<String, String>();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("act", "reply");
		String mAppHash = AppUtils.getAppHash();

		map.put("apphash", mAppHash);

		CreatePost createPost = new CreatePost();
		createPost.body = new CreatePostBody();
		CreatePostJson createPostJson= new CreatePostJson();
		createPost.body.json = createPostJson;
		if(longitude!=0){
			createPostJson.location = tv_addr_create_post_reply.getText().toString();
			createPostJson.longitude = String.valueOf(this.longitude);
			createPostJson.latitude = String.valueOf(this.latitude);
		}
		createPostJson.tid = tid;
		createPostJson.isQuote = 1;
		createPostJson.isShowPostion = 1;
		if(reply!=null) {
			createPostJson.replyId = reply.reply_posts_id;
		}
		ArrayList<Content> contents = new ArrayList<Content>();
		if(photos!=null && !photos.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for(Attachment temp : photos) {
				Content content = new Content();
				content.type = 1;
				content.infor = temp.urlName;
				contents.add(content);
				content.aid = temp.id ;
				sb.append(temp.id + ",");
			}
			createPostJson.aid = sb.substring(0, sb.length() -1);
		}
		Content content = new Content();
		content.type = 0;
		content.infor = et_content_reply.getText().toString();
		contents.add(content);
		createPostJson.content = GsonUtils.toJson(contents);
		map.put("json", GsonUtils.toJson(createPost));
		RequestHelper.getInstance().postRequestMap(getActivity(),
				UrlConstance.FORUM_CREATE_POST_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						int res  = 0;
						 try {
							 JSONObject json = new JSONObject(response);
							res = json.getInt("rs");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(res == 1) {
							ToastHelper.showL("回复成功");
							ptrv_apd.setRefreshing();
							et_content_reply.setText("");
						} else {
							ToastHelper.showL("回复失败");
						}
					}
				});
	}
	
	private PhotoRes uploadPhoto(String path) {
		
		path = getUploadPath(path);
		String host = UrlConstance.FORUM_UPLOAD_PHOTO;
		HashMap<String, String> values = new HashMap<String, String>();
		String mAppHash = AppUtils.getAppHash();
		values.put("module", "forum");
		values.put("packageName", "com.appbyme.app139447");
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		values.put("accessToken", user.token);
		values.put("accessSecret", user.secret);
		//TODO
//		values.put("fid", border.board_id + "");
		values.put("tid", String.valueOf(tid));
		values.put("appHash", mAppHash);
		String result = UploadUtils.upload(host, path,"uploadFile[]", values);
		if(StringUtils.isNullOrEmpty(result)) {
			return null;
		}
		PhotoRes res = new Gson().fromJson(result, PhotoRes.class);
		return res;
	}
	
	private String getUploadPath(String path) {
		
		File file = new File(path);
		if(file.length() > 500*1000) {
			return PhotoManager.getInstance().compressPicture(path, PhotoManager.UPLOAD_PHOTO_PATH);
		} else {
			return path;
		}
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

			publishData((ArrayList<Attachment>) msg.obj);
		};

	};
	protected static final int MSG_UPLOADED_PHOTOS = 1;
	
	private boolean verifyInput() {
		

		if(et_content_reply.getText().toString().trim().isEmpty()) {
			ToastHelper.showS("请填写内容");
			return false;
		}
		if(et_content_reply.getText().toString().trim().length()<15) {
			ToastHelper.showS("内容不能少于15字");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void onBackPressed() {

		if(ll_more_reply_post_detail.getVisibility() == View.VISIBLE) {
			ll_more_reply_post_detail.setVisibility(View.GONE);
		} else {
			super.onBackPressed();
		}
	}
	
}
