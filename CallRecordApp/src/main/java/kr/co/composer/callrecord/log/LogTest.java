package kr.co.composer.callrecord.log;

import android.util.Log;

public class LogTest {
	public static void i(Object tag, Object msg) {
		Log.i(String.valueOf(tag), msg.toString());
	}
	
	public static void i(String tag, String msg) {
		Log.i(tag, msg);
	}

	public static void i(String tag, int msg) {
		Log.i(tag, String.valueOf(msg));
	}

	public static void i(String tag, double msg) {
		Log.i(tag, String.valueOf(msg));
	}
	
	public static void i(String tag, boolean msg) {
		Log.i(tag, String.valueOf(msg));
	}
}