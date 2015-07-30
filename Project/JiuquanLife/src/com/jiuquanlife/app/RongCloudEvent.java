package com.jiuquanlife.app;

import com.jiuquanlife.dao.UserDao;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.module.im.PhotoActivity;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.UrlUtils;

import io.rong.imkit.PushNotificationManager;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imkit.widget.provider.VoIPInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.LocationMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
import io.rong.notification.PushNotificationMessage;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;

/**
 * Created by zhjchen on 1/29/15.
 */

/**
 * ����SDK�¼��������� ���¼�ͳһ���������߿�ֱ�Ӹ��Ƶ��Լ�����Ŀ��ȥʹ�á�
 * <p/>
 * ��������ļ����¼��У� 1����Ϣ��������OnReceiveMessageListener��
 * 2��������Ϣ��������OnSendMessageListener�� 3���û���Ϣ�ṩ�ߣ�GetUserInfoProvider��
 * 4��������Ϣ�ṩ�ߣ�GetFriendsProvider�� 5��Ⱥ����Ϣ�ṩ�ߣ�GetGroupInfoProvider��
 * 6���Ự��������ļ�������ConversationBehaviorListener��
 * 7������״̬���������Ի�ȡ�������״̬��ConnectionStatusListener�� 8������λ���ṩ�ߣ�LocationProvider��
 * 9���Զ��� push ֪ͨ�� OnReceivePushMessageListener��
 * 10���Ự�б��������ļ�������ConversationListBehaviorListener��
 */
public final class RongCloudEvent implements
		RongIMClient.OnReceiveMessageListener, RongIM.OnSendMessageListener,
		RongIM.UserInfoProvider, RongIM.GroupInfoProvider,
		RongIM.ConversationBehaviorListener,
		RongIMClient.ConnectionStatusListener, RongIM.LocationProvider,
		RongIMClient.OnReceivePushMessageListener,
		RongIM.ConversationListBehaviorListener,
		RongIM.OnReceiveUnreadCountChangedListener

{

	private static final String TAG = RongCloudEvent.class.getSimpleName();

	private static RongCloudEvent mRongCloudInstance;

	private Context mContext;

	/**
	 * ��ʼ�� RongCloud.
	 * 
	 * @param context
	 *            �����ġ�
	 */
	public static void init(Context context) {

		if (mRongCloudInstance == null) {

			synchronized (RongCloudEvent.class) {

				if (mRongCloudInstance == null) {
					mRongCloudInstance = new RongCloudEvent(context);
				}
			}
		}
	}

	/**
	 * ���췽����
	 * 
	 * @param context
	 *            �����ġ�
	 */
	private RongCloudEvent(Context context) {
		mContext = context;
		initDefaultListener();
	}

	/**
	 * RongIM.init(this) ��ֱ�ӿ�ע���Listener��
	 */
	private void initDefaultListener() {

		RongIM.setUserInfoProvider(this, true);// �����û���Ϣ�ṩ�ߡ�
		// RongIM.setGroupInfoProvider(this, true);// ����Ⱥ����Ϣ�ṩ�ߡ�
		RongIM.setConversationBehaviorListener(this);// ���ûỰ��������ļ�������
		RongIM.setLocationProvider(this);// ���õ���λ���ṩ��,����λ�õ�ͬѧ����ע�����д���
		// RongIM.setPushMessageBehaviorListener(this);//�Զ��� push ֪ͨ��
	}

	/*
	 * ���ӳɹ�ע�ᡣ <p/> ��RongIM-connect-onSuccess����á�
	 */
	public void setOtherListener() {
		RongIM.getInstance().getRongIMClient()
				.setOnReceiveMessageListener(this);// ������Ϣ���ռ�������
		RongIM.getInstance().setSendMessageListener(this);// ���÷�����Ϣ���ռ�����.
		RongIM.getInstance().getRongIMClient()
				.setConnectionStatusListener(this);// ��������״̬��������
		RongIM.getInstance().setOnReceiveUnreadCountChangedListener(this,
				Conversation.ConversationType.PRIVATE);

		// ��չ�����Զ���
		InputProvider.ExtendProvider[] provider = {
				new ImageInputProvider(RongContext.getInstance()),// ͼƬ
				new CameraInputProvider(RongContext.getInstance()),// ���
				new LocationInputProvider(RongContext.getInstance()),// ����λ��
				new VoIPInputProvider(RongContext.getInstance()),// ����ͨ��
		};
		RongIM.resetInputExtensionProvider(
				Conversation.ConversationType.PRIVATE, provider);

		// RongIM.getInstance().setPrimaryInputProvider(new
		// InputTestProvider((RongContext) mContext));

	}

	/**
	 * �Զ��� push ֪ͨ��
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public boolean onReceivePushMessage(PushNotificationMessage msg) {
		Log.d(TAG, "onReceived-onPushMessageArrive:" + msg.getContent());

		PushNotificationManager.getInstance().onReceivePush(msg);

		// Intent intent = new Intent();
		// Uri uri;
		//
		// intent.setAction(Intent.ACTION_VIEW);
		//
		// Conversation.ConversationType conversationType =
		// msg.getConversationType();
		//
		// uri = Uri.parse("rong://" +
		// RongContext.getInstance().getPackageName()).buildUpon().appendPath("conversationlist").build();
		// intent.setData(uri);
		// Log.d(TAG, "onPushMessageArrive-url:" + uri.toString());
		//
		// Notification notification=null;
		//
		// PendingIntent pendingIntent =
		// PendingIntent.getActivity(RongContext.getInstance(), 0,
		// intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//
		// if (android.os.Build.VERSION.SDK_INT < 11) {
		// notification = new
		// Notification(RongContext.getInstance().getApplicationInfo().icon,
		// "�Զ��� notification", System.currentTimeMillis());
		//
		// notification.setLatestEventInfo(RongContext.getInstance(),
		// "�Զ��� title", "���� Content:"+msg.getObjectName(), pendingIntent);
		// notification.flags = Notification.FLAG_AUTO_CANCEL;
		// notification.defaults = Notification.DEFAULT_SOUND;
		// } else {
		//
		// notification = new Notification.Builder(RongContext.getInstance())
		// .setLargeIcon(getAppIcon())
		// .setSmallIcon(R.drawable.ic_launcher)
		// .setTicker("�Զ��� notification")
		// .setContentTitle("�Զ��� title")
		// .setContentText("���� Content:"+msg.getObjectName())
		// .setContentIntent(pendingIntent)
		// .setAutoCancel(true)
		// .setDefaults(Notification.DEFAULT_ALL).build();
		//
		// }
		//
		// NotificationManager nm = (NotificationManager)
		// RongContext.getInstance().getSystemService(RongContext.getInstance().NOTIFICATION_SERVICE);
		//
		// nm.notify(0, notification);

		return true;
	}

	private Bitmap getAppIcon() {
		BitmapDrawable bitmapDrawable;
		Bitmap appIcon;
		bitmapDrawable = (BitmapDrawable) RongContext.getInstance()
				.getApplicationInfo()
				.loadIcon(RongContext.getInstance().getPackageManager());
		appIcon = bitmapDrawable.getBitmap();
		return appIcon;
	}

	/**
	 * ��ȡRongCloud ʵ����
	 * 
	 * @return RongCloud��
	 */
	public static RongCloudEvent getInstance() {
		return mRongCloudInstance;
	}

	/**
	 * ������Ϣ�ļ�������OnReceiveMessageListener �Ļص����������յ���Ϣ��ִ�С�
	 * 
	 * @param message
	 *            ���յ�����Ϣ��ʵ����Ϣ��
	 * @param left
	 *            ʣ��δ��ȡ��Ϣ��Ŀ��
	 */
	@Override
	public boolean onReceived(Message message, int left) {

		MessageContent messageContent = message.getContent();

		if (messageContent instanceof TextMessage) {// �ı���Ϣ
			TextMessage textMessage = (TextMessage) messageContent;
			Log.d(TAG, "onReceived-TextMessage:" + textMessage.getContent());
		} else if (messageContent instanceof ImageMessage) {// ͼƬ��Ϣ
			ImageMessage imageMessage = (ImageMessage) messageContent;
			Log.d(TAG, "onReceived-ImageMessage:" + imageMessage.getRemoteUri());
		} else if (messageContent instanceof VoiceMessage) {// ������Ϣ
			VoiceMessage voiceMessage = (VoiceMessage) messageContent;
			Log.d(TAG, "onReceived-voiceMessage:"
					+ voiceMessage.getUri().toString());
		} else if (messageContent instanceof RichContentMessage) {// ͼ����Ϣ
			RichContentMessage richContentMessage = (RichContentMessage) messageContent;
			Log.d(TAG,
					"onReceived-RichContentMessage:"
							+ richContentMessage.getContent());
		} else if (messageContent instanceof InformationNotificationMessage) {// С������Ϣ
			InformationNotificationMessage informationNotificationMessage = (InformationNotificationMessage) messageContent;
			Log.d(TAG, "onReceived-informationNotificationMessage:"
					+ informationNotificationMessage.getMessage());
		}

		return false;

	}

	@Override
	public Message onSend(Message message) {
		return message;
	}

	/**
	 * ��Ϣ��UIչʾ��ִ��/�Լ�����Ϣ������ִ��,���۳ɹ���ʧ�ܡ�
	 * 
	 * @param message
	 *            ��Ϣ��
	 */
	@Override
	public void onSent(Message message) {

		MessageContent messageContent = message.getContent();

		if (messageContent instanceof TextMessage) {// �ı���Ϣ
			TextMessage textMessage = (TextMessage) messageContent;
			Log.d(TAG, "onSent-TextMessage:" + textMessage.getContent());
		} else if (messageContent instanceof ImageMessage) {// ͼƬ��Ϣ
			ImageMessage imageMessage = (ImageMessage) messageContent;
			Log.d(TAG, "onSent-ImageMessage:" + imageMessage.getRemoteUri());
		} else if (messageContent instanceof VoiceMessage) {// ������Ϣ
			VoiceMessage voiceMessage = (VoiceMessage) messageContent;
			Log.d(TAG, "onSent-voiceMessage:"
					+ voiceMessage.getUri().toString());
		} else if (messageContent instanceof RichContentMessage) {// ͼ����Ϣ
			RichContentMessage richContentMessage = (RichContentMessage) messageContent;
			Log.d(TAG,
					"onSent-RichContentMessage:"
							+ richContentMessage.getContent());
		} else {
			Log.d(TAG, "onSent-������Ϣ���Լ����жϴ���");
		}
	}

	/**
	 * �û���Ϣ���ṩ�ߣ�GetUserInfoProvider �Ļص���������ȡ�û���Ϣ��
	 * 
	 * @param userId
	 *            �û� Id��
	 * @return �û���Ϣ����ע���ɿ������ṩ�û���Ϣ����
	 */
	@Override
	public UserInfo getUserInfo(String userId) {

		UserInfo res = null;
		com.jiuquanlife.vo.forum.usercenter.UserInfo user = new UserDao()
				.getById(userId);
		String name = null;
		if (user != null) {
			name = user.name;
		}
		Uri photo = null;
		if (!StringUtils.isNullOrEmpty(userId)) {
			photo = Uri.parse(UrlUtils.getPhotoUrl(userId));
		}
		res = new UserInfo(userId, name, photo);
		return res;
	}

	/**
	 * Ⱥ����Ϣ���ṩ�ߣ�GetGroupInfoProvider �Ļص������� ��ȡȺ����Ϣ��
	 * 
	 * @param groupId
	 *            Ⱥ�� Id.
	 * @return Ⱥ����Ϣ����ע���ɿ������ṩȺ����Ϣ����
	 */
	@Override
	public Group getGroupInfo(String groupId) {
		/**
		 * demo ���� ���������滻���Լ��Ĵ��롣
		 */
		// if (DemoContext.getInstance().getGroupMap() == null)
		// return null;
		//
		// return DemoContext.getInstance().getGroupMap().get(groupId);
		// return null;
		return null;
	}

	/**
	 * �Ự��������ļ�������ConversationBehaviorListener �Ļص�������������û�ͷ���ִ�С�
	 * 
	 * @param context
	 *            Ӧ�õ�ǰ�����ġ�
	 * @param conversationType
	 *            �Ự���͡�
	 * @param user
	 *            ��������û�����Ϣ��
	 * @return ����True��ִ�к���SDK����������False����ִ��SDK������
	 */
	@Override
	public boolean onUserPortraitClick(Context context,
			Conversation.ConversationType conversationType, UserInfo user) {
		Log.d(TAG, "onUserPortraitClick");

		Log.d("Begavior", conversationType.getName() + ":" + user.getName());
		// String userId = user.getUserId();
		// User emp = new UserDao().getById(userId);
		// if (emp != null) {
		// Intent in = new Intent(context, EmployeeInfoActivity.class);
		// in.putExtra(EmployeeInfoActivity.INTENT_KEY_EMPLOYEE, emp);
		// in.putExtra(EmployeeInfoActivity.EXTRA_HIDE_MSG_BUTTON, true);
		// context.startActivity(in);
		// return true;
		// }
		return false;
	}

	@Override
	public boolean onUserPortraitLongClick(Context context,
			Conversation.ConversationType conversationType, UserInfo userInfo) {
		return false;
	}

	/**
	 * �Ự��������ļ�������ConversationBehaviorListener �Ļص��������������Ϣʱִ�С�
	 * 
	 * @param context
	 *            Ӧ�õ�ǰ�����ġ�
	 * @param message
	 *            ���������Ϣ��ʵ����Ϣ��
	 * @return ����True��ִ�к���SDK����������False����ִ��SDK������
	 */
	@Override
	public boolean onMessageClick(Context context, View view, Message message) {
		Log.d(TAG, "onMessageClick");

		/**
		 * demo ���� ���������滻���Լ��Ĵ��롣
		 */
		if (message.getContent() instanceof LocationMessage) {
			LocationMessage msg = (LocationMessage) message.getContent();
			msg.getLat();
			// double lat = LocationUtils.convertToBdLat(msg.getLat(),
			// msg.getLng());
			// double lon = LocationUtils.convertToBdLon(msg.getLat(),
			// msg.getLng());
			// Intent intent = new Intent(context, ViewLocOnMapActivity.class);
			// intent.putExtra(ViewLocOnMapActivity.INTENT_KEY_LATITUDE, lat);
			// intent.putExtra(ViewLocOnMapActivity.INTENT_KEY_LONGITUDE, lon);
			// context.startActivity(intent);

		} else if (message.getContent() instanceof RichContentMessage) {
			RichContentMessage mRichContentMessage = (RichContentMessage) message
					.getContent();
			Log.d("Begavior", "extra:" + mRichContentMessage.getExtra());

		} else if (message.getContent() instanceof ImageMessage) {
			ImageMessage imageMessage = (ImageMessage) message.getContent();
			Intent intent = new Intent(context, PhotoActivity.class);

			intent.putExtra(
					"photo",
					imageMessage.getLocalUri() == null ? imageMessage
							.getRemoteUri() : imageMessage.getLocalUri());
			if (imageMessage.getThumUri() != null)
				intent.putExtra("thumbnail", imageMessage.getThumUri());

			context.startActivity(intent);
		}

		Log.d("Begavior",
				message.getObjectName() + ":" + message.getMessageId());

		return false;
	}

	@Override
	public boolean onMessageLongClick(Context context, View view,
			Message message) {
		return false;
	}

	/**
	 * ����״̬���������Ի�ȡ�������״̬:ConnectionStatusListener �Ļص�����������״̬�仯ʱִ�С�
	 * 
	 * @param status
	 *            ����״̬��
	 */
	@Override
	public void onChanged(ConnectionStatus status) {
		Log.d(TAG, "onChanged:" + status);
		if (status.getMessage().equals(
				ConnectionStatus.DISCONNECTED.getMessage())) {
		}
	}

	/**
	 * λ����Ϣ�ṩ��:LocationProvider �Ļص��������򿪵�������ͼҳ�档
	 * 
	 * @param context
	 *            ������
	 * @param callback
	 *            �ص�
	 */
	@Override
	public void onStartLocation(Context context, LocationCallback callback) {
		/**
		 * demo ���� ���������滻���Լ��Ĵ��롣
		 */
		// DemoContext.getInstance().setLastLocationCallback(callback);
		// TODO
		// context.startActivity(new Intent(context,
		// SOSOLocationActivity.class));//SOSO��ͼ
		// mLastLocationCallback = callback;
		// Intent intent = new Intent(context, ImLocationActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(intent);

	}

	/**
	 * ����Ự�б� item ��ִ�С�
	 * 
	 * @param context
	 *            �����ġ�
	 * @param view
	 *            ��������� View��
	 * @param conversation
	 *            �Ự��Ŀ��
	 * @return ���� true ����ִ������ SDK �߼������� false ��ִ������ SDK �߼���ִ�и÷�����
	 */
	@Override
	public boolean onConversationClick(Context context, View view,
			UIConversation conversation) {
		return false;
	}

	/**
	 * �����Ự�б� item ��ִ�С�
	 * 
	 * @param context
	 *            �����ġ�
	 * @param view
	 *            ��������� View��
	 * @param conversation
	 *            �����Ự��Ŀ��
	 * @return ���� true ����ִ������ SDK �߼������� false ��ִ������ SDK �߼���ִ�и÷�����
	 */
	@Override
	public boolean onConversationLongClick(Context context, View view,
			UIConversation conversation) {
		return false;
	}

	private LocationCallback mLastLocationCallback;

	public RongIM.LocationProvider.LocationCallback getLastLocationCallback() {
		return mLastLocationCallback;
	}

	public void setLastLocationCallback(
			RongIM.LocationProvider.LocationCallback lastLocationCallback) {
		this.mLastLocationCallback = lastLocationCallback;
	}

	@Override
	public void onMessageIncreased(int count) {

		App.getInstance().imCount = count;
		if (App.getInstance().forumTabActvity != null) {
			App.getInstance().forumTabActvity.refresMsgBadge();
		}
	}

}
