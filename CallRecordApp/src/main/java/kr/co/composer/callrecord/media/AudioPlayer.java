package kr.co.composer.callrecord.media;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import kr.co.composer.callrecord.R;


public class AudioPlayer extends Activity {


    private android.media.MediaPlayer mediaPlayer;
    private TextView fileName, duration;
    private ImageButton playBtn;
    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 2000, backwardTime = 2000;
    private Handler durationHandler = new Handler();
    private SeekBar seekbar;
    private boolean wasPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_audio_player);

        //바깥 영역 누름 방지
        setFinishOnTouchOutside(false);
        //initialize views
        initializeViews();
    }

    public void initializeViews() {
        Intent intent = getIntent();
        String fName = intent.getStringExtra("fileName");
        fileName = (TextView) findViewById(R.id.fileName);
        duration = (TextView) findViewById(R.id.songDuration);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        playBtn = (ImageButton) findViewById(R.id.media_play);
        try {
            mediaPlayer = new android.media.MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(fName);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileName.setText(fName.substring(fName.lastIndexOf("/") + 1));
        finalTime = mediaPlayer.getDuration();
        seekbar.setOnSeekBarChangeListener(mOnSeek);
        seekbar.setMax((int) finalTime);
        seekbar.setClickable(true);
        mediaPlayer.start();
        timeElapsed = mediaPlayer.getCurrentPosition();
        seekbar.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateSeekBarTime, 200);
    }

    // play mp3 song
    public void play(View view) {
        if (mediaPlayer.isPlaying() == false) {
            mediaPlayer.start();
            playBtn.setImageResource(R.drawable.ic_media_pause);
            timeElapsed = mediaPlayer.getCurrentPosition();
            seekbar.setProgress((int) timeElapsed);
            durationHandler.postDelayed(updateSeekBarTime, 200);
        } else {
            mediaPlayer.pause();
            durationHandler.removeMessages(0);
            playBtn.setImageResource(R.drawable.ic_media_play);
        }
    }

    public void close(View view){
        mediaPlayer.stop();
        this.finish();
    }

    //handler to change seekBarTime
    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            //get current position
            timeElapsed = mediaPlayer.getCurrentPosition();
            //set seekbar progress
            seekbar.setProgress((int) timeElapsed);
            //set time remaing
            double timeRemaining = finalTime - timeElapsed;
            duration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

            //repeat yourself that again in 200 miliseconds
            durationHandler.postDelayed(this, 200);
        }
    };

    // go forward at forwardTime seconds
    public void forward(View view) {
        //check if we can go forward at forwardTime seconds before song endes
        if ((timeElapsed + forwardTime) <= finalTime) {
            timeElapsed = timeElapsed + forwardTime;

            //seek to the exact second of the track
            mediaPlayer.seekTo((int) timeElapsed);
        }
    }

    // go backwards at backwardTime seconds
    public void rewind(View view) {
        //check if we can go back at backwardTime seconds after song starts
        if ((timeElapsed - backwardTime) > 0) {
            timeElapsed = timeElapsed - backwardTime;

            //seek to the exact second of the track
            mediaPlayer.seekTo((int) timeElapsed);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        durationHandler.postDelayed(updateSeekBarTime, 200);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playBtn.setImageResource(R.drawable.ic_media_play);
            durationHandler.removeMessages(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    SeekBar.OnSeekBarChangeListener mOnSeek = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mediaPlayer.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            wasPlaying = mediaPlayer.isPlaying();
            if (wasPlaying) {
                mediaPlayer.start();
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

}