package kr.co.composer.callrecord.page;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import kr.co.composer.callrecord.R;
import kr.co.composer.callrecord.sharedpref.ConfigPreferenceManager;

public class ConfigurationActivity extends AppCompatActivity {
	ToggleButton autoRecordButton;
	ConfigPreferenceManager preferenceManager;
	Button recordFormatSelect, recordTypeSelect;
	TextView recordTypeText;
	TextView recordFormatText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuratioin);
		preferenceManager = ConfigPreferenceManager.getInstance();
		init();

	}

	private void init() {
		autoRecordButton = (ToggleButton) findViewById(R.id.auto_record);
		recordTypeSelect = (Button) findViewById(R.id.record_type_select);
		recordFormatSelect = (Button) findViewById(R.id.record_format_select);
		recordTypeText = (TextView)findViewById(R.id.record_type_text);
		recordTypeText.setText(preferenceManager.getRecordTypeText());
		recordFormatText = (TextView)findViewById(R.id.record_format_text);
		recordFormatText.setText(preferenceManager.getRecordFormatText());
		CharSequence title = "Configure";
		// 액션바 타이틀변경
		ActionBar ab = this.getSupportActionBar();
		ab.setTitle(title);
		// 액션바 백버튼 생성
		ab.setDisplayHomeAsUpEnabled(true);

		if (preferenceManager.getAutoRecord()) {
			autoRecordButton.setChecked(true);
		}

		autoRecordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (autoRecordButton.isChecked()) {
					preferenceManager.setAutoRecord(true);
					Toast.makeText(getBaseContext(), "자동녹음", Toast.LENGTH_SHORT)
							.show();
				} else if (!autoRecordButton.isChecked()) {
					preferenceManager.setAutoRecord(false);
					System.out.println("확인:"
							+ preferenceManager.getAutoRecord());
					Toast.makeText(getBaseContext(), "자동녹음해제",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		
//		녹음타입 설정
		recordTypeSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(ConfigurationActivity.this)
				.setTitle("녹음 타입을 선택하시오")
				.setItems(R.array.type, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String[] type = getResources().getStringArray(R.array.type);
						System.out.println(type[which]);
						recordTypeText.setText(type[which]);
						switch (type[which]){
						case "전체녹음": 
							preferenceManager.setRecordType(MediaRecorder.AudioSource.VOICE_CALL);
							preferenceManager.setRecordTypeText(type[which]);
							break;
						case "내 목소리만":
							preferenceManager.setRecordType(MediaRecorder.AudioSource.VOICE_UPLINK);
							preferenceManager.setRecordTypeText(type[which]);
							break;
						case "상대방 목소리만":
							preferenceManager.setRecordType(MediaRecorder.AudioSource.VOICE_DOWNLINK);
							preferenceManager.setRecordTypeText(type[which]);
							break;
						}
					}
				})
				.setNegativeButton("취소", null)
				.show();
			
			}
		});
		
//		포맷설정
		recordFormatSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(ConfigurationActivity.this)
				.setTitle("녹음할 포맷을 선택하시오")
				.setItems(R.array.format, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String[] format = getResources().getStringArray(R.array.format);
						System.out.println(format[which]);
						recordFormatText.setText(format[which]);
						preferenceManager.setRecordFormatText(format[which]);
						switch (format[which]){
						case "MP4": preferenceManager.setRecordFormat(MediaRecorder.OutputFormat.MPEG_4);
									preferenceManager.setPathFormat(".mp4");
						
						break;
						case "3GP":	preferenceManager.setRecordFormat(MediaRecorder.OutputFormat.THREE_GPP);
									preferenceManager.setPathFormat(".3gp");
						break;
						}
									
					}
				})
				.setNegativeButton("취소", null)
				.show();
			}
		});
	}

		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			ConfigurationActivity.this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
