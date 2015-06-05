package kr.co.composer.callrecord.sharedpref;

import kr.co.composer.callrecord.callbroadcast.CallBroadcast;

public class CallInfoPreferenceManager extends AbstractPreferenceManager{
	private static CallInfoPreferenceManager mSelfInstance = null;
	
	public static final String ROW_ID = "rowID";
	public static final String START_DATE = "prefStartDate";
	public static final String START_TIME = "prefStartTime";
	public static final String CALL_TIME = "callTime";
	public static final String STOP_TIME = "prefStopTime";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String NAME = "NumberName";
	public static final String SENDING = "sending";
	public static final String CALL_STATE = "callState";
	public static final String CONTACT_ID = "contactId";
	
	public static synchronized CallInfoPreferenceManager getInstance() {
		if (mSelfInstance == null) {
			mSelfInstance = new CallInfoPreferenceManager();
		}
		return mSelfInstance;
	}
	
//	public void setStartDate(long time){
//		setStringValue(START_DATE, DateFormat.format("yyyy-MM-dd kk:mm:ss ",time).toString());
//	}
//	
//	public void setStopDate(long time){
//		setStringValue(STOP_DATE, DateFormat.format("yyyy-MM-dd kk:mm:ss ",time).toString());
//	}
	public void setRowId(int rowId){
		setIntValue(ROW_ID, rowId);
	}
	
	public void setStartDate(String time){
		setStringValue(START_DATE, time);
	}
	
	public void setStartTime(long time){
		setLongValue(START_TIME, time);
	}
	
	public void setCallTime(String time){
		setStringValue(CALL_TIME,time);
	}
	
	public void setPhoneNumber(String number){
		setStringValue(PHONE_NUMBER, number);
	}

	public void setName(String name){
		setStringValue(NAME, name);
	}
	
	public void setSending(String name){
		setStringValue(SENDING, name);
	}
	
	public void setCallState(boolean state){
		setBooleanValue(CALL_STATE, state);
	}
	
	public void setContactId(int name){
		setIntValue(CONTACT_ID, name);
	}
	
//	public long getStartDate(){
//		return getLongValue(START_DATE, 0L);
//	}
	
	public long getRowId(){
		return getLongValue(ROW_ID, 0);
	}
	
	public String getStartDate(){
		return getStringValue(START_DATE, "");
	}
	
	public long getStartTime(){
		return getLongValue(START_TIME, 0L);
	}
	
	public String getCallTime(){
		return getStringValue(CALL_TIME, "");
	}
	
	public String getPhoneNumber(){
		return getStringValue(PHONE_NUMBER, "");
	}
	
	public String getName(){
		return getStringValue(NAME, "");
	}
	
	public String getSending(){
		return getStringValue(SENDING, "");
	}
	
	public boolean getCallState(){
		return getBooleanValue(CALL_STATE, true);
	}
	
	public int getContactId(){
		return getIntValue(CONTACT_ID, 1);
	}
	
}
