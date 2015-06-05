package kr.co.composer.callrecord.sharedpref;

import android.media.MediaRecorder;

public class ConfigPreferenceManager extends AbstractPreferenceManager {
	private static ConfigPreferenceManager mSelfInstance = null;
	
	public static final String AUTO_ON_OFF = "auto_onOff";
	public static final String RECORD_FORMAT = "recordFormat";
	public static final String RECORD_TYPE = "recordType";
	public static final String RECORD_TYPE_TEXT = "recordTypeText";
	public static final String RECORD_FORMAT_TEXT = "recordFormatText";
	
	
	public static final String PATH_FORMAT = "pathFormat";
	public static final String MP4 = ".mp4";
	
	public static synchronized ConfigPreferenceManager getInstance() {
		if (mSelfInstance == null) {
			mSelfInstance = new ConfigPreferenceManager();
		}
		return mSelfInstance;
	}

	public void setAutoRecord(boolean onOff){
		setBooleanValue(AUTO_ON_OFF, onOff);
	}

	public void setRecordType(int type){
		setIntValue(RECORD_TYPE, type);
	}
	
	public void setRecordTypeText(String type){
		setStringValue(RECORD_TYPE_TEXT, type);
	}
	
	public void setRecordFormat(int format){
		setIntValue(RECORD_FORMAT, format);
	}

	public void setRecordFormatText(String type){
		setStringValue(RECORD_FORMAT_TEXT, type);
	}
	
	public void setPathFormat(String pathFormat){
		setStringValue(PATH_FORMAT, pathFormat);
	}
	
	
	
	
	
	
	
	
	public boolean getAutoRecord(){
		return getBooleanValue(AUTO_ON_OFF, true);
	}
	
	public int getRecordType(){
		return getIntValue(RECORD_TYPE, MediaRecorder.AudioSource.VOICE_CALL);
	}
	
	public String getRecordTypeText(){
		return getStringValue(RECORD_TYPE_TEXT, "전체녹음");
	}
	
	public int getRecordFormat(){
		return getIntValue(RECORD_FORMAT, MediaRecorder.OutputFormat.MPEG_4);
	}

	public String getRecordFormatText(){
		return getStringValue(RECORD_FORMAT_TEXT, "MP4");
	}
	
	public String getPathFormat(){
		return getStringValue(PATH_FORMAT, ".mp4");
	}
	
	




}
