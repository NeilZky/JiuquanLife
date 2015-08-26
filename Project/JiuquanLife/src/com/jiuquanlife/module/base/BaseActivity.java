package com.jiuquanlife.module.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.ToastHelper;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends FragmentActivity {

	private boolean active;
	private ProgressDialog progressDialog;

	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		active = true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		active = false;
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		dismissDialog(progressDialog);

		progressDialog = null;
	}

	private void initBackBtn() {

		View backBtn = getWindow().getDecorView().findViewWithTag(
				getString(R.string.back_tag));
		if (backBtn != null) {
			backBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					finish();
				}
			});
		}
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initBackBtn();
	}

	@Override
	public void setContentView(View view) {

		super.setContentView(view);
		initBackBtn();
	}

	protected void showProgressDialog() {

		showProgressDialog(true);
	}

	private void dismissDialog(Dialog dialog) {

		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	public void dismissProgressDialog() {

		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	protected void setText(TextView tv, String value) {

		if (!StringUtils.isNullOrEmpty(value)) {
			tv.setText(value);
		}
	}

	protected void setText(int res, String value) {

		TextView tv = (TextView) findViewById(res);
		if (!StringUtils.isNullOrEmpty(value)) {
			tv.setText(value);
		}
	}

	protected Activity getActivity() {

		return this;
	}

	public void showProgressDialog(boolean cancelable) {

		if (!active) {
			return;
		}

		if (null == progressDialog) {
			progressDialog = new ProgressDialog(this);
		}
		progressDialog.setTitle(null);
		progressDialog.setMessage(getString(R.string.please_wait));
		progressDialog.setCancelable(cancelable);
		progressDialog.setOnCancelListener(onCancelListener);
		progressDialog.show();
	}

	private OnCancelListener onCancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			dialog.dismiss();
			ToastHelper.showS(R.string.operation_canceled);
		}

	};

	public void startActivity(Class<?> cls) {

		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	public void startActivityForResult(Class<?> cls, int requestCode) {

		Intent intent = new Intent(this, cls);
		super.startActivityForResult(intent, requestCode);
	}

}
