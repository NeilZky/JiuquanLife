package com.jiuquanlife.module.company.entity;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class ImgInfo implements Parcelable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pic;
	private String attId;

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}

	@Override
	public String toString() {
		return "ImgInfo [pic=" + pic + ", attId=" + attId + "]";
	}

	public ImgInfo(String pic, String attId) {
		super();
		this.pic = pic;
		this.attId = attId;
	}

	public ImgInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeString(pic);
		out.writeString(attId);

	}

	public static final Parcelable.Creator<ImgInfo> CREATOR = new Parcelable.Creator<ImgInfo>() {

		@Override
		public ImgInfo createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new ImgInfo(in);
		}

		@Override
		public ImgInfo[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return new ImgInfo[arg0];
		}
	};

	public ImgInfo(Parcel in) {
		pic = in.readString();
		attId = in.readString();

	}

}
