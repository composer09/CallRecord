package kr.co.composer.callrecord.dao.configuration;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConfigurationDataHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 11;
	public static final String DATABASE_NAME = "callrecordConf.db";
	public static final String TABLE_NAME = "Config";
	public static final String ROW_ID = "rowId";
	public static final String AUTO_RECORD = "자동녹음";
//	public static final String FORMAT = "format";
//	public static final String SOURCE = "source";
	
	public ConfigurationDataHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE_NAME + " ("
				+ ROW_ID + " integer primary key autoincrement, "
				+ AUTO_RECORD + " varchar"
				+");";
		db.execSQL(sql);
}
	
//	@Override
//	public void onCreate(SQLiteDatabase database) {
//		String sql = "create table " + TABLE_NAME + " ("
//				+ ROW_ID + " integer primary key autoincrement, "
//				+ AUTO_RECORD + " integer, "
//				+ FORMAT + " varchar, "
//				+ SOURCE + " varchar"
//				+");";
//		database.execSQL(sql);
//	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		String sql = "drop table if exists " + TABLE_NAME + ";";
		database.execSQL(sql);
		onCreate(database);
	}

}
