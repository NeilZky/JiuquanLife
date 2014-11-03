package com.jiuquanlife.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.jiuquanlife.R;
import com.jiuquanlife.view.SexangleImageView;
import com.jiuquanlife.view.SexangleImageView.OnSexangleImageClickListener;

public class MainActivity extends Activity {

	SexangleImageView homeSiv;
	SexangleImageView flightSiv;
	SexangleImageView hotelSiv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}
	
	public void initView(){
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		homeSiv = (SexangleImageView) findViewById(R.id.siv_main);
		flightSiv = (SexangleImageView) findViewById(R.id.siv_fight);
		hotelSiv = (SexangleImageView) findViewById(R.id.siv_hotel);
		homeSiv.setOnSexangleImageClick(listener);
		flightSiv.setOnSexangleImageClick(listener);
		hotelSiv.setOnSexangleImageClick(listener);
	}
	
	OnSexangleImageClickListener listener=new OnSexangleImageClickListener() {
		
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.siv_main:
				Toast.makeText(MainActivity.this,"µã»÷Ö÷Ò³", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
}
