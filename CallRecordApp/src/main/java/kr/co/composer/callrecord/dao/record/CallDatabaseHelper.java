package kr.co.composer.callrecord.dao.record;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class CallDatabaseHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = Environment.getExternalStorageDirectory().getAbsolutePath() + "/callrecord/callrecord.db";
//	public static final String DATABASE_NAME = "mnt/sdcard/callrecord/callrecord.db";
	public static final String TABLE_NAME = "recordStatus";
	public static final String ROW_ID = "rowId";
	public static final String NAME = "name";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String CALL_TIME = "callTime";
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	public static final String SEND_RECEIVE = "sendReceive";
	public static final String CONTACT_ID = "contactID";
	public static final String FILE_NAME = "fileName";
	
	public CallDatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE_NAME + " ("
				+ ROW_ID + " integer primary key autoincrement, "
				+ NAME + " varchar, "
				+ PHONE_NUMBER + " varchar, "
				+ START_TIME + " varchar, "
				+ CALL_TIME + " varchar, "
				+ SEND_RECEIVE + " varchar, "
				+ CONTACT_ID + " int, "
				+ FILE_NAME + " varchar"
				+");";
		Log.i("데이터베이스 테이블생성", sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table if exists " + TABLE_NAME + ";";
		db.execSQL(sql);
		onCreate(db);
	}

}