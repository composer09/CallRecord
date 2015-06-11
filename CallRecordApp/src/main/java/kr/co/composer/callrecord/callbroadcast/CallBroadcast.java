package kr.co.composer.callrecord.callbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.composer.callrecord.dao.record.CallDAO;
import kr.co.composer.callrecord.format.CallFormatter;
import kr.co.composer.callrecord.recorder.CallRecordService;
import kr.co.composer.callrecord.sharedpref.CallInfoPreferenceManager;
import kr.co.composer.callrecord.sharedpref.ConfigPreferenceManager;

public class CallBroadcast extends BroadcastReceiver {

    CallInfoPreferenceManager callInfoPreferenceManager;
    ConfigPreferenceManager configPreferenceManager;
    //////////////////////////////////////////////
    private static int pState = TelephonyManager.CALL_STATE_IDLE;
    private static boolean IDLE = true;
    private static boolean CALLING = false;
    private static int REFUSE = 1;
    private static int ACCEPT = 2;
    private int callState;
    private String[] projection;
    private CallDAO callDAO;
    private Intent sIntent;
    private Intent intent;
    private Uri uri;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        callState = 0;
        callInfoPreferenceManager = CallInfoPreferenceManager.getInstance();
        configPreferenceManager = ConfigPreferenceManager.getInstance();

        TelephonyManager telManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (configPreferenceManager.getAutoRecord()) {
            if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                Log.i("RINGING", "발신");
                callInfoPreferenceManager.setPhoneNumber(intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
                callInfoPreferenceManager.setSending(String.valueOf(true));
            }
        }
//		PhoneListener phoneListener = new PhoneListener(context, intent);
//		telManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
        telManager.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
//				callInfoPreferenceManager = CallInfoPreferenceManager.getInstance();
//				configPreferenceManager = ConfigPreferenceManager.getInstance();
                callDAO = new CallDAO(context);
                if (configPreferenceManager.getAutoRecord()) {
                    if (state != pState) {
                        if (state == TelephonyManager.CALL_STATE_OFFHOOK && callInfoPreferenceManager.getCallState()) {
                            Log.i("OFFHOOK", "OFFHOOK상태");


                            uri = Uri.withAppendedPath(
                                    ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                                    Uri.encode(callInfoPreferenceManager.getPhoneNumber()));
                            projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                            };

                            // Query the filter URI
                            Cursor cursor = context.getContentResolver().query(uri,
                                    projection, null, null, null);
                            if (cursor != null) {
                                if (cursor.moveToFirst()) {
//								callInfoPreferenceManager.setName(cursor.getString(0));
                                    callInfoPreferenceManager.setName(cursor.getString(cursor.getColumnIndex
                                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                                }else {
                                    callInfoPreferenceManager.setName("알수없음");
                                }
                            }
                            cursor.close();

                            callInfoPreferenceManager.setCallState(CALLING);
                            callInfoPreferenceManager.setStartDate(DateFormat.format(
                                    CallFormatter.START_DATE_FORMAT,
                                    System.currentTimeMillis()).toString());

                            callInfoPreferenceManager.setStartTime(System
                                    .currentTimeMillis());

                            // record서비스시작
                            sIntent = new Intent(context.getApplicationContext(),
                                    CallRecordService.class);
                            context.startService(sIntent);

                            Toast.makeText(
                                    context,
                                    callInfoPreferenceManager.getName() + " "
                                            + callInfoPreferenceManager.getPhoneNumber()
                                            + "\n" + "님과 통화녹음 시작", Toast.LENGTH_SHORT).show();

                        } else if (state == TelephonyManager.CALL_STATE_RINGING && callInfoPreferenceManager.getCallState()) {
//						callInfoPreferenceManager.setPhoneNumber(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
                            callInfoPreferenceManager.setPhoneNumber(incomingNumber);
                            Log.i("RINGING", "수신");
                            callInfoPreferenceManager.setSending(String.valueOf(false));

                        } else if (state == TelephonyManager.CALL_STATE_IDLE && callInfoPreferenceManager.getCallState() == CALLING) {
                            Log.i("Phone", "대기상태");
                            Toast.makeText(context, "통화녹음 완료", Toast.LENGTH_SHORT).show();
                            long endTime = System.currentTimeMillis();
                            long startTime = callInfoPreferenceManager.getStartTime();
                            long callTime = endTime - startTime;
                            String totalTime = new SimpleDateFormat(
                                    CallFormatter.TIME_FORMAT).format(new Date(
                                    callTime - 32400000));
//						callInfoPreferenceManager.setCallTime(totalTime);
                            callDAO.insert(callInfoPreferenceManager.getName(),
                                    callInfoPreferenceManager.getPhoneNumber(),
                                    callInfoPreferenceManager.getStartDate(),
                                    totalTime,
                                    callInfoPreferenceManager.getSending(),
                                    callInfoPreferenceManager.getStartDate()
                                            + configPreferenceManager.getPathFormat());
                            callDAO.close();

                            // record서비스종료
                            sIntent = new Intent(context.getApplicationContext(),
                                    CallRecordService.class);
                            context.stopService(sIntent);
                            callInfoPreferenceManager.setCallState(IDLE);
                            Log.i("번호", callInfoPreferenceManager.getPhoneNumber());
                            Log.i("이름", callInfoPreferenceManager.getName());
                            Log.i("시작시간", String.valueOf(callInfoPreferenceManager
                                    .getStartDate()));
                            Log.i("통화시간", callInfoPreferenceManager.getCallTime());
                            Log.i("발신여부", callInfoPreferenceManager.getSending());
                        } else if (state == TelephonyManager.CALL_STATE_RINGING &&
                                callInfoPreferenceManager.getCallState() == CALLING) {
                            callState = REFUSE;
                        } else if (state == TelephonyManager.CALL_STATE_OFFHOOK && callInfoPreferenceManager.getCallState()
                                == CALLING) {
                            callState = ACCEPT; //정상적인 TelephonyManager.CALL_STATE_IDLE이 호출
                        } else if (state == TelephonyManager.CALL_STATE_IDLE && callState == REFUSE) {
                            callInfoPreferenceManager.setCallState(IDLE);
                        }
                        pState = state;
                    }
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }//onReceive

}
