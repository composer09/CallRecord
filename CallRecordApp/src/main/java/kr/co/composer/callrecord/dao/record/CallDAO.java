package kr.co.composer.callrecord.dao.record;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

import kr.co.composer.callrecord.bo.Call;


public class CallDAO {

	ContentResolver contentResolver;
	CallDatabaseHelper callDatabaseHelper;
	SQLiteDatabase db;
	String fileName;
	String rowId;
	
	public CallDAO(Context context){

		this.callDatabaseHelper = new CallDatabaseHelper(context);
	}
	
//	public void init() {
//		this.contentResolver = recordApplication.getContentResolver();
//	}
	
	
	public void insert(String name, String number, String startTime, String callTime, String sendReceive
			, int photoId, String fileName) {
		ContentValues contVal = new ContentValues();
		contVal.put(CallDatabaseHelper.NAME, name);
		contVal.put(CallDatabaseHelper.PHONE_NUMBER, number);
		contVal.put(CallDatabaseHelper.START_TIME, startTime);
		contVal.put(CallDatabaseHelper.CALL_TIME, callTime);
		contVal.put(CallDatabaseHelper.SEND_RECEIVE, sendReceive);
		contVal.put(CallDatabaseHelper.PHOTO_ID, photoId);
		contVal.put(CallDatabaseHelper.FILE_NAME, fileName);
		db = callDatabaseHelper.getWritableDatabase();
		db.insert(CallDatabaseHelper.TABLE_NAME, null, contVal);
	}
	
	
//	public Cursor select(){
//		db = callDatabaseHelper.getReadableDatabase();
//		Cursor cursor = db.query(CallDatabaseHelper.TABLE_NAME, null,null,null,null,null
//				,null);
//		return cursor;
//	}
	
	
//	디비역순
	public Cursor select(){
		db = callDatabaseHelper.getReadableDatabase();
		Cursor cursor = db.query(CallDatabaseHelper.TABLE_NAME, null,null,null,null,null
				,CallDatabaseHelper.ROW_ID + " desc");
		return cursor;
	}
	
	public File selectFile(int rowId){
		db = callDatabaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "+CallDatabaseHelper.TABLE_NAME+" where rowId = "+String.valueOf(rowId),null);
			while(cursor.moveToNext()){
			fileName = cursor.getString(cursor.getColumnIndex(CallDatabaseHelper.FILE_NAME));
			}
//			File file = new File(fileName);
			
//			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/callrecord/" 
//					+fileName);
			File file = new File(Environment.getExternalStorageDirectory().getPath() + "/callrecord/" 
					+fileName);
			return file;
	}
	
	public void deleteCallV2(int rowId) {
		db = callDatabaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "+CallDatabaseHelper.TABLE_NAME+" where rowId = "+String.valueOf(rowId),null);
		while(cursor.moveToNext()){
		this.rowId = CallDatabaseHelper.ROW_ID +"="+cursor.getString(cursor.getColumnIndex(CallDatabaseHelper.ROW_ID));
		}
		db.delete(CallDatabaseHelper.TABLE_NAME, this.rowId, null);
	
	}
	
	public void deleteCall(Call call) {
		db = callDatabaseHelper.getReadableDatabase();
		String where = CallDatabaseHelper.ROW_ID + "=" + call.getRowId();
		db.delete(CallDatabaseHelper.TABLE_NAME, where, null);
	}

	
	public void close(){
		db.close();
	}
	
	public ArrayList<Call> cursorCallList(Cursor cursor) {
		ArrayList<Call> callList = new ArrayList<Call>();
		
		try {
			while (cursor.moveToNext()) {
				Call call = new Call();
				
				int columnIndex = cursor.getColumnIndex(CallDatabaseHelper.ROW_ID);
				call.setRowId(cursor.getInt(columnIndex));
				
				columnIndex = cursor.getColumnIndex(CallDatabaseHelper.NAME);
				call.setCallerName(cursor.getString(columnIndex));
				
				columnIndex = cursor.getColumnIndex(CallDatabaseHelper.PHONE_NUMBER);
				call.setPhoneNumber(cursor.getString(columnIndex));
				
				columnIndex = cursor.getColumnIndex(CallDatabaseHelper.START_TIME);
				call.setStartTime(cursor.getString(columnIndex));
				
				columnIndex = cursor.getColumnIndex(CallDatabaseHelper.CALL_TIME);
				call.setCallTime(cursor.getString(columnIndex));
				
				columnIndex = cursor.getColumnIndex(CallDatabaseHelper.SEND_RECEIVE);
				call.setSendReceive(cursor.getString(columnIndex));

				columnIndex = cursor.getColumnIndex(CallDatabaseHelper.PHOTO_ID);
				call.setPhotoId(cursor.getInt(columnIndex));

				columnIndex = cursor.getColumnIndex(CallDatabaseHelper.FILE_NAME);
				call.setFileName(cursor.getString(columnIndex));
				
				callList.add(call);
			}
		} catch (Exception e) {
		}
		
		return callList;
	}
	
//	public void insert(Call call) {
//		ContentValues contVal = new ContentValues();
//		contVal.put(CallDatabaseHelper.NAME, call.getCallerName());
//		contVal.put(CallDatabaseHelper.PHONE_NUMBER, call.getPhoneNumber());
//		contVal.put(CallDatabaseHelper.START_TIME, call.getStartTime());
////		contVal.put(CallDatabaseHelper.END_TIME, call.getEndTime());
//		contentResolver.insert(Uri.parse(String.format("content://%s/%s", "kr.co.callrecord", "call")), contVal);
//	}


//	public List<Call> getHealthList() {
//		ContentResolver contentResolver = recordApplication.getContentResolver();
//		String[] projection = null;
//		String selection = null;
//		String[] selectionArgs = null;
//		String sortOrder = CallDatabaseHelper.ROW_ID + " desc";
//		Cursor cursor = contentResolver.query((Uri.parse(String.format("content://%s/%s", "kr.co.callrecord", "call")))
//				, projection, selection,
//				selectionArgs, sortOrder);
//		List<Call> callList = cursorCallList(cursor);
//		cursor.close();
//
//		return callList;
//	}
	

//	public void updateHealth(Call call) {
//		String where = CallDatabaseHelper.ROW_ID + "=" + call.getRowId();
//		ContentValues contVal = new ContentValues();
//		contVal.put(CallDatabaseHelper.NAME, call.getCallerName());
//		contVal.put(CallDatabaseHelper.PHONE_NUMBER, call.getPhoneNumber());
//		contVal.put(CallDatabaseHelper.START_TIME, call.getEndTime());
//		
//		contentResolver.update(Uri.parse(String.format("content://%s/%s", "kr.co.callrecord", "call")), contVal, where, null);
//	}
	
	
	
}
