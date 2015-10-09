package com.jiuquanlife.module.company.activity;

import java.util.Date;

import com.jiuquanlife.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class RefreshListView extends ListView implements OnScrollListener,
		OnClickListener {

	public interface IListViewState {
		int LVS_NORMAL = 0; // 鏅拷?鐘讹拷?
		int LVS_PULL_REFRESH = 1; // 涓嬫媺鍒锋柊鐘讹拷?
		int LVS_RELEASE_REFRESH = 2; // 鏉惧紑鍒锋柊鐘讹拷?
		int LVS_LOADING = 3; // 鍔犺浇鐘讹拷?
	}

	public interface IOnRefreshListener {
		void OnRefresh();
	}

	private View mHeadView;
	private TextView mRefreshTextview;
	private TextView mLastUpdateTextView;
	private ImageView mArrowImageView;
	private ProgressBar mHeadProgressBar;

	private int mHeadContentWidth;
	private int mHeadContentHeight;

	private IOnRefreshListener mOnRefreshListener; // 澶撮儴鍒锋柊鐩戝惉锟�

	// 鐢ㄤ簬淇濊瘉startY鐨勶拷?鍦ㄤ竴涓畬鏁寸殑touch浜嬩欢涓彧琚褰曚竴锟�
	private boolean mIsRecord = false;
	// 鏍囪鐨刌鍧愭爣锟�
	private int mStartY = 0;
	// 褰撳墠瑙嗗浘鑳界湅鍒扮殑绗竴涓」鐨勭储锟�
	private int mFirstItemIndex = -1;
	// MOVE鏃朵繚瀛樼殑Y鍧愭爣锟�
	private int mMoveY = 0;
	// LISTVIEW鐘讹拷?
	private int mViewState = IListViewState.LVS_NORMAL;

	private final static int RATIO = 2;

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private boolean mBack = false;

	public RefreshListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void setOnRefreshListener(IOnRefreshListener listener) {
		mOnRefreshListener = listener;
	}

	private void onRefresh() {
		if (mOnRefreshListener != null) {
			mOnRefreshListener.OnRefresh();
		}
	}

	public void onRefreshComplete() {
		mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
		mLastUpdateTextView.setText("锟�锟斤拷鏇存柊:" + new Date().toLocaleString());
		switchViewState(IListViewState.LVS_NORMAL);
	}

	private void init(Context context) {
		initHeadView(context);

		initLoadMoreView(context);

		setOnScrollListener(this);
	}

	// 鍒濆鍖杊eadview璇曞浘
	private void initHeadView(Context context) {
		mHeadView = LayoutInflater.from(context).inflate(R.layout.head, null);

		mArrowImageView = (ImageView) mHeadView
				.findViewById(R.id.head_arrowImageView);
		mArrowImageView.setMinimumWidth(60);

		mHeadProgressBar = (ProgressBar) mHeadView
				.findViewById(R.id.head_progressBar);

		mRefreshTextview = (TextView) mHeadView
				.findViewById(R.id.head_tipsTextView);

		mLastUpdateTextView = (TextView) mHeadView
				.findViewById(R.id.head_lastUpdatedTextView);

		measureView(mHeadView);
		mHeadContentHeight = mHeadView.getMeasuredHeight();
		mHeadContentWidth = mHeadView.getMeasuredWidth();

		mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
		mHeadView.invalidate();

		addHeaderView(mHeadView, null, false);

		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
	}

	// 鍒濆鍖杅ootview璇曞浘
	private void initLoadMoreView(Context context) {
		mFootView = LayoutInflater.from(context).inflate(R.layout.loadmore,
				null);

		mLoadMoreView = mFootView.findViewById(R.id.load_more_view);

		mLoadMoreTextView = (TextView) mFootView
				.findViewById(R.id.load_more_tv);

		mLoadingView = mFootView.findViewById(R.id.loading_layout);

		mLoadMoreView.setOnClickListener(this);

		addFooterView(mFootView);

	}

	// 姝ゆ柟娉曠洿鎺ョ収鎼嚜缃戠粶涓婄殑锟�锟斤拷涓嬫媺鍒锋柊鐨刣emo锛岃绠梙eadView鐨剋idth浠ュ強height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void onScroll(AbsListView arg0, int firstVisiableItem,
			int visibleItemCount, int totalItemCount) {
		mFirstItemIndex = firstVisiableItem;

	}

	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		if (mOnRefreshListener != null) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				doActionDown(ev);
				break;
			case MotionEvent.ACTION_MOVE:
				doActionMove(ev);
				break;
			case MotionEvent.ACTION_UP:
				doActionUp(ev);
				break;
			default:
				break;
			}
		}

		return super.onTouchEvent(ev);
	}

	private void doActionDown(MotionEvent ev) {
		if (mIsRecord == false && mFirstItemIndex == 0) {
			mStartY = (int) ev.getY();
			mIsRecord = true;
		}
	}

	private void doActionMove(MotionEvent ev) {
		mMoveY = (int) ev.getY();

		if (mIsRecord == false && mFirstItemIndex == 0) {
			mStartY = (int) ev.getY();
			mIsRecord = true;
		}

		if (mIsRecord == false || mViewState == IListViewState.LVS_LOADING) {
			return;
		}

		int offset = (mMoveY - mStartY) / RATIO;

		switch (mViewState) {
		case IListViewState.LVS_NORMAL: {
			if (offset > 0) {
				mHeadView.setPadding(0, offset - mHeadContentHeight, 0, 0);
				switchViewState(IListViewState.LVS_PULL_REFRESH);
			}
		}
			break;
		case IListViewState.LVS_PULL_REFRESH: {
			setSelection(0);
			mHeadView.setPadding(0, offset - mHeadContentHeight, 0, 0);
			if (offset < 0) {
				switchViewState(IListViewState.LVS_NORMAL);
			} else if (offset > mHeadContentHeight) {
				switchViewState(IListViewState.LVS_RELEASE_REFRESH);
			}
		}
			break;
		case IListViewState.LVS_RELEASE_REFRESH: {
			setSelection(0);
			mHeadView.setPadding(0, offset - mHeadContentHeight, 0, 0);
			if (offset >= 0 && offset <= mHeadContentHeight) {
				mBack = true;
				switchViewState(IListViewState.LVS_PULL_REFRESH);
			} else if (offset < 0) {
				switchViewState(IListViewState.LVS_NORMAL);
			} else {

			}

		}
			break;
		default:
			return;
		}
		;

	}

	private void doActionUp(MotionEvent ev) {
		mIsRecord = false;
		mBack = false;

		if (mViewState == IListViewState.LVS_LOADING) {
			return;
		}

		switch (mViewState) {
		case IListViewState.LVS_NORMAL:

			break;
		case IListViewState.LVS_PULL_REFRESH:
			mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
			switchViewState(IListViewState.LVS_NORMAL);
			break;
		case IListViewState.LVS_RELEASE_REFRESH:
			mHeadView.setPadding(0, 0, 0, 0);
			switchViewState(IListViewState.LVS_LOADING);
			onRefresh();
			break;
		}

	}

	// 鍒囨崲headview瑙嗗浘
	private void switchViewState(int state) {

		switch (state) {
		case IListViewState.LVS_NORMAL: {
			Log.e("!!!!!!!!!!!", "convert to IListViewState.LVS_NORMAL");
			mArrowImageView.clearAnimation();
			mArrowImageView.setImageResource(R.drawable.arrow);
		}
			break;
		case IListViewState.LVS_PULL_REFRESH: {
			Log.e("!!!!!!!!!!!", "convert to IListViewState.LVS_PULL_REFRESH");
			mHeadProgressBar.setVisibility(View.GONE);
			mArrowImageView.setVisibility(View.VISIBLE);
			mRefreshTextview.setText("涓嬫媺鍙互鍒锋柊");
			mArrowImageView.clearAnimation();

			// 鏄敱RELEASE_To_REFRESH鐘讹拷?杞彉鏉ョ殑
			if (mBack) {
				mBack = false;
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(reverseAnimation);
			}
		}
			break;
		case IListViewState.LVS_RELEASE_REFRESH: {
			Log.e("!!!!!!!!!!!",
					"convert to IListViewState.LVS_RELEASE_REFRESH");
			mHeadProgressBar.setVisibility(View.GONE);
			mArrowImageView.setVisibility(View.VISIBLE);
			mRefreshTextview.setText("鏉惧紑鑾峰彇鏇村");
			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(animation);
		}
			break;
		case IListViewState.LVS_LOADING: {
			Log.e("!!!!!!!!!!!", "convert to IListViewState.LVS_LOADING");
			mHeadProgressBar.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.GONE);
			mRefreshTextview.setText("加载中..");
		}
			break;
		default:
			return;
		}

		mViewState = state;

	}

	private View mFootView;
	private View mLoadMoreView;
	private TextView mLoadMoreTextView;
	private View mLoadingView;
	private IOnLoadMoreListener mLoadMoreListener; // 鍔犺浇鏇村鐩戝惉锟�
	private int mLoadMoreState = IListViewState.LVS_NORMAL;

	public interface ILoadMoreViewState {
		int LMVS_NORMAL = 0; // 鏅拷?鐘讹拷?
		int LMVS_LOADING = 1; // 鍔犺浇鐘讹拷?
		int LMVS_OVER = 2; // 缁撴潫鐘讹拷?
	}

	public interface IOnLoadMoreListener {
		void OnLoadMore();
	}

	public void setOnLoadMoreListener(IOnLoadMoreListener listener) {
		mLoadMoreListener = listener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.load_more_view: {
			if (mLoadMoreListener != null
					&& mLoadMoreState == IListViewState.LVS_NORMAL) {
				updateLoadMoreViewState(ILoadMoreViewState.LMVS_LOADING);
				mLoadMoreListener.OnLoadMore();
			}
		}
			break;
		}
	}

	// flag 鏁版嵁鏄惁宸插叏閮ㄥ姞杞藉畬锟�
	public void onLoadMoreComplete(boolean flag) {
		if (flag) {
			updateLoadMoreViewState(ILoadMoreViewState.LMVS_OVER);
		} else {
			updateLoadMoreViewState(ILoadMoreViewState.LMVS_NORMAL);
		}

	}

	// 鏇存柊footview瑙嗗浘
	private void updateLoadMoreViewState(int state) {
		switch (state) {
		case ILoadMoreViewState.LMVS_NORMAL:
			mLoadingView.setVisibility(View.GONE);
			mLoadMoreTextView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setText("更多详情");
			break;
		case ILoadMoreViewState.LMVS_LOADING:
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setVisibility(View.GONE);
			break;
		case ILoadMoreViewState.LMVS_OVER:
			mLoadingView.setVisibility(View.GONE);
			mLoadMoreTextView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setText("加载完成");
			break;
		default:
			break;
		}

		mLoadMoreState = state;
	}

	public void removeFootView() {
		removeFooterView(mFootView);
	}

}
