package kr.co.composer.callrecord.dao.configuration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConfigurationDAO {
	ConfigurationDataHelper configurationDataHelper;
	SQLiteDatabase db;
	int columnIndex;
	String fileName;
	
	public ConfigurationDAO(Context context) {
		this.configurationDataHelper = new ConfigurationDataHelper(context);
	}
	
	public void insert(String autoRecord) {
		ContentValues contVal = new ContentValues();
		contVal.put(ConfigurationDataHelper.AUTO_RECORD, autoRecord);
		db = configurationDataHelper.getWritableDatabase();
		db.insert(ConfigurationDataHelper.TABLE_NAME, null, contVal);
		db.close();
	}
	
	public String select(){
		db = configurationDataHelper.getReadableDatabase();
		Cursor cursor = db.query(ConfigurationDataHelper.TABLE_NAME, null,null,null,null,null,null);
		while(cursor.moveToNext()) {
			columnIndex = cursor.getColumnIndex(ConfigurationDataHelper.AUTO_RECORD);
	}
		return cursor.getString(columnIndex);
	
//	public String getAutoRecordConfig(){
//		db = configurationDataHelper.getReadableDatabase();
//		Cursor cursor = db.query(ConfigurationDataHelper.TABLE_NAME, null,null,null,null,null
//				,null);
//		while(cursor.moveToNext()){
//		columnIndex = cursor.getColumnIndex(ConfigurationDataHelper.AUTO_RECORD);
//		}
//		return cursor.getString(columnIndex);
//	}
	
	}
}
