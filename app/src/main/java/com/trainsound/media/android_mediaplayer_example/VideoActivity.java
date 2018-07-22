package com.trainsound.media.android_mediaplayer_example;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    MediaPlayer mMediaPlayer;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    Button mPlayButton;
    Button mPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_main);

        // Get hold of the [Play] and [Pause] buttons.
        mPlayButton = (Button) findViewById(R.id.btnStart);
        mPauseButton = (Button) findViewById(R.id.btnStop);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceID);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        // Create a MediaPlayer instance and register this activity for events which are
        // of relevance for us: OnPrepared, OnCompletion and OnError.
        // (We'll release and nullify the MediaPlayer in the activity's onStop() method ).

        if(Configs.default_videoplay_mode.equals("localfileplay")){
            mMediaPlayer = MediaPlayer.create(this, R.raw.park_boo_video);
        }else if(Configs.default_videoplay_mode.equals("network")){
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.setDataSource(Configs.video_url);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);

        // Attach an OnClickListener to the [Play] button.
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMediaPlayer.start();

                // As the song is now playing, disable the [Play] button and
                // enable the [Pause] button.
                if (mMediaPlayer.isPlaying()) {
                    mPauseButton.setEnabled(true);
                    mPlayButton.setEnabled(false);
                }
            } // close onClick()
        });

        // Attach an OnClickListener to the [Pause] button.
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If a song is currently playing, pause it now, disable the [Pause] button
                // and enable the [Play] button.
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mPauseButton.setEnabled(false);
                    mPlayButton.setEnabled(true);
                }
            } // close onClick()
        });
    } // close onCreate()


    @Override
    protected void onStop() {
        super.onStop();

        // Release and nullify the MediaPlayer
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    } // close onStop()

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("VideoActivity", "surfaceCreate");
        mMediaPlayer.setDisplay(mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Toast.makeText(this, "It have been prepared", Toast.LENGTH_SHORT).show();
    }
} // close class AudioActivity
