package com.jiuquanlife.dao;

import com.jiuquanlife.app.App;
import com.jiuquanlife.vo.forum.usercenter.UserInfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

	private static App app = App.getInstance();
	private static final String DATABASE_NAME = "CheckIn";
	private static int DATABASE_VERSION = 1;
	private static final String TAG = DatabaseHelper.class.getSimpleName();
	private static DatabaseHelper mInstance;
	
	private DatabaseHelper(Context context) {
		// calls the super constructor, requesting the default cursor factory.
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public synchronized static DatabaseHelper getInstance() {
		if (mInstance == null) {
			mInstance = new DatabaseHelper(app);
		}
		return mInstance;
	}

	public synchronized static void destoryInstance() {
		if (mInstance != null) {
			mInstance.close();
			mInstance = null;
		}
	}
	
	/**
	 * 
	 * Creates the underlying database with table name and column names taken
	 * from the NotePad class.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + UserInfo.TABLE_NAME + " ("
				+ UserInfo.COLUMN_UID + " int PRIMARY KEY,"
				+ UserInfo.COLUMN_NAME+ " TEXT," 
				+ UserInfo.COLUMN_SIGNATURE+ "  TEXT );" );
	}

	/**
	 * 
	 * Demonstrates that the provider must consider what happens when the
	 * underlying datastore is changed. In this sample, the database is upgraded
	 * the database by destroying the existing data. A real application should
	 * upgrade the database in place.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Logs that the database is being upgraded
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");

		 db.execSQL("DROP TABLE IF EXISTS " + UserInfo.TABLE_NAME);
		// db.execSQL("DROP TABLE IF EXISTS " + StatisticDao.TABLE_STATISTIC);
		// db.execSQL("DROP TABLE IF EXISTS " + GpsPhoto.TABLE_GPS_PHOTO);
		// db.execSQL("DROP TABLE IF EXISTS " +
		// ClientStatus.TABLE_CLIENTSTATUS);
		// Recreates the database with a new version
		onCreate(db);
	}
	
}