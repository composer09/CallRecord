package kr.co.composer.callrecord.Listener;
//package kr.co.callrecord.Listener;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import kr.co.callrecord.CallFormatter;
//import kr.co.callrecord.dao.record.CallDAO;
//import kr.co.callrecord.recorder.CallRecordService;
//import kr.co.callrecord.sharedpref.CallInfoPreferenceManager;
//import kr.co.callrecord.sharedpref.ConfigPreferenceManager;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.provider.ContactsContract;
//import android.telephony.PhoneStateListener;
//import android.telephony.TelephonyManager;
//import android.text.format.DateFormat;
//import android.util.Log;
//import android.widget.Toast;
//
//public class CopyOfPhoneListener extends PhoneStateListener {
//	CallInfoPreferenceManager callInfoPreferenceManager;
//	ConfigPreferenceManager configPreferenceManager;
//
//	private static int pState = TelephonyManager.CALL_STATE_IDLE;
//	private static boolean IDLE = true;
//	private static boolean CALLING = false;
//	private String[] projection;
//	private CallDAO callDAO;
//	private Intent sIntent;
//	private Intent intent;
//	private Context context;
//	private Uri uri;
//
//	public CopyOfPhoneListener(Context context, Intent intent) {
//		this.context = context;
//		this.intent = intent;
//	}
//
//	@Override
//	public void onCallStateChanged(int state, String incomingNumber) {
//		callInfoPreferenceManager = CallInfoPreferenceManager.getInstance();
//		configPreferenceManager = ConfigPreferenceManager.getInstance();
//		callDAO = new CallDAO(context);
//	if (configPreferenceManager.getAutoRecord()) {
//		if (state != pState) {
//			switch(state){
//			case TelephonyManager.CALL_STATE_OFFHOOK:
//			if(callInfoPreferenceManager.getCallState() == IDLE) {
//				Log.i("OFFHOOK", "OFFHOOK상태");
////				
//				System.out.println("발신번호 확인 :"+intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
//				System.out.println("수신번호 확인 :"+intent.getExtras()
//						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER));
//				uri = Uri.withAppendedPath(
//						ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
//						Uri.encode(callInfoPreferenceManager.getPhoneNumber()));
//				projection = new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME };
//
//				// Query the filter URI
//				Cursor cursor = context.getContentResolver().query(uri,
//						projection, null, null, null);
//				if (cursor != null) {
//					System.out.println("cursor != null");
//					if (cursor.moveToFirst()) {
//						System.out.println("cursor.moveToFirst");
//						callInfoPreferenceManager.setName(cursor.getString(0));
//						cursor.close();
//					} else {
//						callInfoPreferenceManager.setName("알수없음");
//					}
//				}
////				
//				
//				callInfoPreferenceManager.setCallState(CALLING);
//				callInfoPreferenceManager.setStartDate(DateFormat.format(
//						CallFormatter.START_DATE_FORMAT,
//						System.currentTimeMillis()).toString());
//
//				callInfoPreferenceManager.setStartTime(System
//						.currentTimeMillis());
//
//				// record서비스시작
//				sIntent = new Intent(context.getApplicationContext(),
//						CallRecordService.class);
//				context.startService(sIntent);
//
//				Toast.makeText(
//						context,
//						callInfoPreferenceManager.getName() + " "
//								+ callInfoPreferenceManager.getPhoneNumber()
//								+ "\n" + "님과 통화녹음 시작", Toast.LENGTH_SHORT).show();
//			}
//			break;
//			
//			case TelephonyManager.CALL_STATE_RINGING: 
//				Log.i("RINGING", "수신");
//				callInfoPreferenceManager.setPhoneNumber(intent.getExtras()
//						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER));
//				uri = Uri.withAppendedPath(
//						ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
//						Uri.encode(intent.getExtras().getString(
//								TelephonyManager.EXTRA_INCOMING_NUMBER)));
//				projection = new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME };
//
//				// Query the filter URI
//				Cursor cursor = context.getContentResolver().query(uri,
//						projection, null, null, null);
//				if (cursor != null) {
//					if (cursor.moveToFirst()) {
//						// 이름저장
//						callInfoPreferenceManager.setName(cursor.getString(0));
//						cursor.close();
//					} else {
//						callInfoPreferenceManager.setName("알수없음");
//					}
//				}
//
//				Toast.makeText(context,callInfoPreferenceManager.getName() + " "
//								+ callInfoPreferenceManager.getPhoneNumber()
//								+ "\n" + "수신확인", Toast.LENGTH_SHORT).show();
////				발,수신여부 저장
//				callInfoPreferenceManager.setSending(String.valueOf(false));
//				break;
//				
//			case TelephonyManager.CALL_STATE_IDLE :
//				Log.i("Phone", "대기상태");
//				Toast.makeText(context, "통화녹음 완료", Toast.LENGTH_SHORT).show();
//				long endTime = System.currentTimeMillis();
//				long startTime = callInfoPreferenceManager.getStartTime();
//				long callTime = endTime - startTime;
//				String totalTime = new SimpleDateFormat(
//						CallFormatter.TIME_FORMAT).format(new Date(
//						callTime - 32400000));
//				callInfoPreferenceManager.setCallTime(totalTime);
//				callDAO.insert(callInfoPreferenceManager.getName(),
//						callInfoPreferenceManager.getPhoneNumber(),
//						callInfoPreferenceManager.getStartDate(),
//						callInfoPreferenceManager.getCallTime(),
//						callInfoPreferenceManager.getSending());
//				callDAO.close();
//
//				// record서비스종료
//				sIntent = new Intent(context.getApplicationContext(),
//						CallRecordService.class);
//				context.stopService(sIntent);
//
//				Log.i("번호", callInfoPreferenceManager.getPhoneNumber());
//				Log.i("이름", callInfoPreferenceManager.getName());
//				Log.i("시작시간", String.valueOf(callInfoPreferenceManager
//						.getStartDate()));
//				Log.i("통화시간", callInfoPreferenceManager.getCallTime());
//				Log.i("발신여부", callInfoPreferenceManager.getSending());
//				break;
//			}
//			// else if (state == TelephonyManager.CALL_STATE_RINGING &&
//			// callState == CALLING) {
//			// idleCall = true;
//			// }else if (state == TelephonyManager.CALL_STATE_IDLE && idleCall
//			// == true){
//			// idleCall = false;
//			// }
//			callInfoPreferenceManager.setCallState(IDLE);
//			pState = state;
//			}
//		}
//	}
//}
