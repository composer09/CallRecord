package kr.co.composer.callrecord.recorder;

import java.io.IOException;

import kr.co.composer.callrecord.sharedpref.CallInfoPreferenceManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CallRecordService extends Service {
	
	CallInfoPreferenceManager preferenceManager;
	
	AudioRecorder recorder;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		preferenceManager = CallInfoPreferenceManager.getInstance();
		recorder = new AudioRecorder(preferenceManager.getStartDate());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("service", "onStartCommand");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					recorder.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
//		try {
//			recorder.start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		Log.d("service", "destroy");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					recorder.stop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
//		try {
//			recorder.stop();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}catch (IllegalStateException e){
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		super.onDestroy();
	}
}
