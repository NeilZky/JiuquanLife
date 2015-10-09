package com.jiuquanlife.module.company;

import android.app.Activity;

public class BaseActivity extends Activity {

	public CustomProgressDialog progressDialog = null;

	public void startProgressDialog(String msg) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(this);
			progressDialog.setCanceledOnTouchOutside(false);
			// progressDialog.setCancelable(false);
			progressDialog.setMessage(msg);
		}

		progressDialog.show();
	}

	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopProgressDialog();
		super.onDestroy();
	}

}
