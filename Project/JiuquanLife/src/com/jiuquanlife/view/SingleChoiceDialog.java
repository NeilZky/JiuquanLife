package com.jiuquanlife.view;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.EntityUtils;

public class SingleChoiceDialog {

	private String title;
	private ChoiceDialogAdapter adapter;
	private Context context;
	private List data;
	private int position;
	private Dialog dialog;
	private OnDismissListener onDismissListener;
	
	
	public SingleChoiceDialog(Context context ,int position) {
		
		this.context = context;
		this.position = position;
	}
	
	public SingleChoiceDialog setTitle(String title) {

		this.title = title;
		return this;
	}
	
	public Object getCheckedItem() {

		Object res = null;
		if (data != null && !data.isEmpty()) {
			res = data.get(position);
		}
		return res;
	}
	
	public void setCheckItem(Object obj) {
		
		if(obj == null || data ==null) {
			return;
		}
		List<String> ids = EntityUtils.getIDsStrOfData(data, "id");
		int id = EntityUtils.getIdOfEntity(obj);
		int index = 0;
		for(String temp : ids) {
			if(temp.equals(id)){
				this.position = index;
				break;
			}
			index++;
		}
	}
	
	public SingleChoiceDialog(Context context) {
		
		this(context, 0);
	}

	public SingleChoiceDialog setAdapter(ChoiceDialogAdapter adapter) {

		this.adapter = adapter;
		this.data = adapter.getData();
		return this;
	}
	
	public SingleChoiceDialog setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
		
		this.onDismissListener = onDismissListener;
		return this;
	}
	
	public void show() {

		List<String> items = adapter.getChoiceStrs();
		if (items != null && !items.isEmpty()) {
			String[] itemStrs = new String[items.size()];
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
			dialogBuilder
					.setTitle(title)
					.setSingleChoiceItems(items.toArray(itemStrs), position,
							new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									position = which;
									dialog.dismiss();
								}
							}).setPositiveButton(R.string.btn_sure, null)
							.setCancelable(false);
			dialog = dialogBuilder.create();
			dialog.setOnDismissListener(onDismissListener);
			dialog.show();
		}
	}
	
	public void dismiss() {
		
		if(dialog !=null) {
			dialog.dismiss();
		}
	}
	
	
	private boolean[] toPrimitiveArray(final List<Boolean> booleanList) {
		final boolean[] primitives = new boolean[booleanList.size()];
		int index = 0;
		for (Boolean object : booleanList) {
			primitives[index++] = object;
		}
		return primitives;
	}
}
