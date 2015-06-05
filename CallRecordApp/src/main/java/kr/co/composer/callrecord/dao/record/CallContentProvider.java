package kr.co.composer.callrecord.dao.record;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class CallContentProvider extends ContentProvider {
	public static final String TABLE_NAME = CallDatabaseHelper.TABLE_NAME;
	SQLiteDatabase db;
	CallDatabaseHelper helper;

	@Override
	public boolean onCreate() {
		
		helper = new CallDatabaseHelper(getContext());
		db = helper.getWritableDatabase();
		return true;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String whereClause = selection;
		String[] whereArgs = selectionArgs;
		int result = db.delete(TABLE_NAME, whereClause, whereArgs);
		return result;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		db = helper.getWritableDatabase();
		
		long rowId = db.insert(CallDatabaseHelper.TABLE_NAME, "", values);
		if (rowId > 0) {
			Uri rowUri = ContentUris.appendId(Uri.parse(String.format("content://%s/%s", "kr.co.callrecord", "call")).buildUpon(), rowId).build();
			getContext().getContentResolver().notifyChange(rowUri, null);
			return rowUri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}


	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = helper.getReadableDatabase();
		qb.setTables(TABLE_NAME);
		Cursor c = qb.query(db, projection, selection, null, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		String whereClause = selection;
		String[] whereArgs = selectionArgs;
		int result = db.update(TABLE_NAME, values, whereClause, whereArgs);
		return result;
	}

}
