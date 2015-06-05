package kr.co.composer.callrecord.recorder;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import kr.co.composer.callrecord.sharedpref.ConfigPreferenceManager;

public class AudioRecorder {
	
	String fileName;
	
	MediaRecorder mediaRecorder;
	
	ConfigPreferenceManager configPreferenceManager;
	
	public AudioRecorder(String startDate){
		configPreferenceManager = ConfigPreferenceManager.getInstance();
		this.fileName = courseGet(startDate);
		mediaRecorder = new MediaRecorder();
//		System.out.println("겟패스포맷확인 : ~~~~~~~~~~"+configPreferenceManager.getPathFormat());
//	  	System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath() + "/callrecord/" 
//				+fileName+configPreferenceManager.getPathFormat());
	}
	
	public AudioRecorder(){
		configPreferenceManager = ConfigPreferenceManager.getInstance();
	}
	
	  public void start() throws Exception {
		    String state = Environment.getExternalStorageState();
		    if(!state.equals(Environment.MEDIA_MOUNTED))  {
		        throw new IOException("SD Card is not mounted.  It is " + state + ".");
		    }

		    // make sure the directory we plan to store the recording in exists
		    File directory = new File(fileName).getParentFile();
		    if (!directory.exists() && !directory.mkdirs()) {
		      throw new IOException("Path to file could not be created.");
		    }

		    mediaRecorder.setAudioSource(configPreferenceManager.getRecordType());
		    mediaRecorder.setOutputFormat(configPreferenceManager.getRecordFormat());
		    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		    mediaRecorder.setOutputFile(fileName);
		    mediaRecorder.prepare();
		    mediaRecorder.start();
		    Log.d("TAG", "rec stared");
		  }
	
	  public void stop() throws Exception {
		  mediaRecorder.stop();
		  mediaRecorder.reset();
		  mediaRecorder.release();
		  mediaRecorder = null;
		    Log.d("TAG", "rec stoped");
		  }
	  
	  public String courseGet(String startDate){
			return Environment.getExternalStorageDirectory().getAbsolutePath() + "/callrecord/" 
					+startDate+configPreferenceManager.getPathFormat();
		}
	  
	  public String excuteFile(String fileName){
		  return Environment.getExternalStorageDirectory().getAbsolutePath() + "/callrecord/" 
				  +fileName;
	  }
	  
	  public String getFileName(){
		  return this.fileName;
	  }
}
