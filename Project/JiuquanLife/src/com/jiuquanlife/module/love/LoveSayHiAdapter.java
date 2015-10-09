package com.jiuquanlife.module.love;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.module.love.entity.FromInfo;
import com.jiuquanlife.module.love.entity.SayHi;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class LoveSayHiAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<SayHi> infos;
	DisplayImageOptions options;

	public LoveSayHiAdapter(Context context, List<SayHi> arrayList) {
		super();
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.infos = new ArrayList<SayHi>();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_default_house)
				.showImageForEmptyUri(R.drawable.ic_default_house)
				.showImageOnFail(R.drawable.ic_default_house)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(1000)).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == infos ? 0 : infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void refreshData(List<SayHi> infos) {
		this.infos = infos;
		System.out.println("refreshData");
		notifyDataSetChanged();
	}

	public void replaceData(int position, SayHi sayHi) {
		infos.set(position, sayHi);
		notifyDataSetChanged();
	}

	private ButtonOnclick buttonOnclick;

	public void setOnButtonClick(ButtonOnclick buttonOnclick) {
		this.buttonOnclick = buttonOnclick;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		System.out.println("getView");
		final ViewHolder holder;
		// 优化listview
		if (convertView == null) {
			// 使用自定义的布局
			convertView = mInflater.inflate(R.layout.love_say_hi_item, parent,
					false);
			holder = new ViewHolder();
			holder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
			holder.iv_info = (TextView) convertView
					.findViewById(R.id.tv_baseinfo);
			holder.iv_pass = (TextView) convertView.findViewById(R.id.tv_pass);
			holder.iv_deny = (TextView) convertView.findViewById(R.id.tv_deny);
			holder.iv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final SayHi sayHi = infos.get(position);

		if ("0".equals(sayHi.getStatus())) {
			holder.iv_status.setVisibility(View.GONE);
			holder.iv_pass.setVisibility(View.VISIBLE);
			holder.iv_deny.setVisibility(View.VISIBLE);
		} else {
			holder.iv_status.setVisibility(View.VISIBLE);
			holder.iv_pass.setVisibility(View.GONE);
			holder.iv_deny.setVisibility(View.GONE);
		}

		holder.iv_pass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != buttonOnclick) {
					buttonOnclick.handleClick(sayHi.getUid(),
							sayHi.getFromuid(), "0", holder, position, sayHi);

				}

			}
		});

		holder.iv_deny.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != buttonOnclick) {
					buttonOnclick.handleClick(sayHi.getUid(),
							sayHi.getFromuid(), "1", holder, position, sayHi);

				}

			}
		});

		Drawable genderDrawable = null;
		if ("1".equals(sayHi.getStatus())) {
			holder.iv_status.setText("已通过");
			genderDrawable = context.getResources().getDrawable(
					R.drawable.sayhi_pass);
			genderDrawable.setBounds(0, 0, 40, 40);
			holder.iv_status.setCompoundDrawables(genderDrawable, null, null,
					null);
		} else if ("2".equals(sayHi.getStatus())) {
			holder.iv_status.setText("已拒绝");
			genderDrawable = context.getResources().getDrawable(
					R.drawable.sayhi_deny);
			genderDrawable.setBounds(0, 0, 40, 40);
			holder.iv_status.setCompoundDrawables(genderDrawable, null, null,
					null);
		}

		System.out.println("sayHi" + sayHi);
		FromInfo fromInfo = sayHi.getFromInfo();
		StringBuilder sb = new StringBuilder();
		sb.append(sayHi.getFromusername());
		sb.append("\n");
		sb.append(fromInfo.getLocation() + " " + fromInfo.getAge() + "岁 "
				+ fromInfo.getStature() + "cm " + fromInfo.getEdu());
		// sb.append("\n");
		holder.iv_info.setText(sb.toString());
		String imagePath = fromInfo.getHeadsavepath()
				+ fromInfo.getHeadsavename();
		System.out.println("imagePath:" + "http://www.5ijq.cn/public/uploads/"
				+ imagePath);
		ImageLoader.getInstance().displayImage(
				"http://www.5ijq.cn/public/uploads/" + imagePath,
				holder.iv_user, options);
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_user;
		TextView iv_info, iv_pass, iv_deny, iv_status;
	}

	public interface ButtonOnclick {
		void handleClick(String uid, String fromuid, String action,
				ViewHolder view, int posistion, SayHi sayHi);

	}
}
