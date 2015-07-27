package com.jiuquanlife.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiuquanlife.vo.forum.usercenter.UserInfo;

public class UserDao extends BaseDao{
	
	
	
	public UserInfo getById(String userId) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(UserInfo.TABLE_NAME, null, "uid = ?", new String[]{userId}, null,
				null, null);
		UserInfo user  = null;
		if (cursor.moveToNext()) {
			user = new UserInfo();
			user.uid = cursor.getInt(cursor.getColumnIndex(UserInfo.COLUMN_UID));
			user.name = cursor.getString(cursor.getColumnIndex(UserInfo.COLUMN_NAME));
			user.signature = cursor.getString(cursor.getColumnIndex(UserInfo.COLUMN_SIGNATURE));
		}
		cursor.close();
		return user;
	}

	public ArrayList<UserInfo> getEmployees() {

		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(UserInfo.TABLE_NAME, null, null, null, null,
				null, null);
		ArrayList<UserInfo> users = new ArrayList<UserInfo>();
		while (cursor.moveToNext()) {
			UserInfo user = new UserInfo();
			user.uid = cursor.getInt(cursor.getColumnIndex(UserInfo.COLUMN_UID));
			user.name = cursor.getString(cursor.getColumnIndex(UserInfo.COLUMN_NAME));
			user.signature = cursor.getString(cursor.getColumnIndex(UserInfo.COLUMN_SIGNATURE));
			users.add(user);
		}
		cursor.close();
		return users;
	}

	public void save(ArrayList<UserInfo> list) {
		
		delete();
		for (UserInfo userInfo : list) {
			insert(userInfo);
		}
	}
	public long insert(UserInfo employee) {

		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(UserInfo.COLUMN_UID, employee.uid);
		cv.put(UserInfo.COLUMN_NAME, employee.name);
		cv.put(UserInfo.COLUMN_SIGNATURE, employee.signature);
		long res = db.insert(UserInfo.TABLE_NAME, null, cv);
		return res;
	}
	
	
	public int delete() {

		SQLiteDatabase db = getWritableDatabase();
		return db.delete(UserInfo.TABLE_NAME, null,
				null);
	}

}
