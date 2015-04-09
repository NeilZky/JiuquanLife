package com.jiuquanlife.view;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.SizeUtils;

public class ListDialog extends Dialog {

	public ListDialog(Context context) {
		super(context, R.style.transparentFrameWindowStyle);
	}

	public static class Builder {

		private LinearLayout rootView;
		private ArrayList<ListDialogItem> items;
		private Activity context;
		private LayoutInflater inflater;
		private Dialog dialog;
		
		public Builder(Activity context) {

			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		public Builder addItem(String name, final View.OnClickListener listener) {

			ListDialogItem item = new ListDialogItem();
			item.name = name;
			item.listener = new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					listener.onClick(v);
					if(dialog !=null) {
						dialog.dismiss();
					}
				}
			};
			if(items == null) {
				items = new ArrayList<ListDialog.ListDialogItem>();
			}
			items.add(item);
			return this;
		}
		
		public Builder addItem(int resId, View.OnClickListener onClickListener) {
			
			String name = context.getString(resId);
			return addItem(name, onClickListener);
		}
		
		public ListDialog create() {

			if(items == null || items.isEmpty()) {
				return null;
			}

			if (rootView == null) {
				rootView = new LinearLayout(context);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				rootView.setOrientation(LinearLayout.VERTICAL);
				rootView.setLayoutParams(params);
			}
			
			for(ListDialogItem item : items) {
				 Button child = (Button) inflater.inflate(R.layout.list_dialog_item, null);
				 child.setText(item.name);
				 child.setOnClickListener(item.listener);
				 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				 int tenDp = SizeUtils.dip2px(context, 10);
				 int twentyDp = SizeUtils.dip2px(context, 20);
				 params.setMargins(twentyDp, 0, twentyDp, tenDp);
				 rootView.addView(child, params);
			}	
			
			ListDialog dialog = new ListDialog(context);
			dialog.setContentView(rootView, new LayoutParams(context
					.getWindowManager().getDefaultDisplay().getWidth() - 10,
					LayoutParams.WRAP_CONTENT));
			dialog.setCanceledOnTouchOutside(true);
			this.dialog = dialog;
			return dialog;
		}
	}

	private static class ListDialogItem {

		String name;
		View.OnClickListener listener;
	}

}
