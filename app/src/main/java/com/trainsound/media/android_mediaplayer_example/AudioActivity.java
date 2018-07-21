package com.trainsound.media.android_mediaplayer_example;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AudioActivity extends AppCompatActivity
        implements  MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    MediaPlayer mMediaPlayer;
    Button mPlayButton;
    Button mPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_main);

        // Get hold of the [Play] and [Pause] buttons.
        mPlayButton  = (Button) findViewById(R.id.button_play);
        mPauseButton = (Button) findViewById(R.id.button_pause);

        // Create a MediaPlayer instance and register this activity for events which are
        // of relevance for us: OnPrepared, OnCompletion and OnError.
        // (We'll release and nullify the MediaPlayer in the activity's onStop() method ).
        mMediaPlayer = MediaPlayer.create(this, R.raw.park_boo);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);

        // Attach an OnClickListener to the [Play] button.
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MediaPlayer is either newly created and in the prepared state, or
                // paused or completed --> start playing now.
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
                // If we don't have a living MediaPlayer as of now, then there's nothing to do here...
                if (null == mMediaPlayer) {
                    Toast.makeText(AudioActivity.this, "mMediaPlayer is null ?!?",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // If a song is currently playing, pause it now, disable the [Pause] button
                // and enable the [Play] button.
                if (mMediaPlayer.isPlaying() ) {
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
    public void onPrepared(MediaPlayer mediaPlayer) {
        Toast.makeText(this, "onPrepared() has been called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        String msg;
        if (i == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
            msg = "An unknown error has occurred";
        } else {
            msg = "Error: MediaPlayer server has died";
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        if (mediaPlayer != null) { mediaPlayer.reset();}
        return true;
    } // close onError()


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Toast.makeText(this, "Playback completed", Toast.LENGTH_SHORT).show();
        // We're done with the sound file, so disable the [Pause] button and re-enable the
        // [Play] button.
        mPauseButton.setEnabled(false);
        mPlayButton.setEnabled(true);
    }

} // close class AudioActivity
