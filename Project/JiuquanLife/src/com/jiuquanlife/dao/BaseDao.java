package com.jiuquanlife.dao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class BaseDao {
	
	private Context context;
	private SQLiteDatabase db;
	private SQLiteDatabase readDb;
	
	public void setSQLiteDatabase(SQLiteDatabase db) {
		
		this.db = db;
	}
	
	public BaseDao() {
	}

	
	public BaseDao(Context context) {
		
		this.context = context;
	}
	
	protected SQLiteDatabase getReadableDatabase() {
		
		if( readDb== null) {
			readDb = DatabaseHelper.getInstance().getReadableDatabase();
		}
		return readDb;
	}
	
	protected SQLiteDatabase getWritableDatabase() {
		if( db== null) {
			db = DatabaseHelper.getInstance().getWritableDatabase();
		}
		return db;
	}
	
	public String getString(Cursor cursor, String columnName) {
		
		return cursor.getString(cursor.getColumnIndex(columnName));
	}
	
	public long getLong(Cursor cursor, String columnName) {
		
		return cursor.getLong(cursor.getColumnIndex(columnName));
	}
	
	public int getInt(Cursor cursor, String columnName) {
		
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}
	
	public double getDouble(Cursor cursor, String columnName) {
		
		return cursor.getDouble(cursor.getColumnIndex(columnName));
	}
}
