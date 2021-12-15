package com.abdalkarimalbiekdev.movietube;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StreamVideo extends AppCompatActivity {

    PlayerView mPlayerView;

    private SimpleExoPlayer mPlayer;
    private String mVideoUrl;

    private long mCurrentMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_video);

        //For Hide the notifications bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Hide soft-key bar on Android phone
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //Change soft-key Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        if (getIntent().getExtras().getString("video_path") != null)
            mVideoUrl = getIntent().getExtras().getString("video_path");

//        if (getIntent().getExtras().getInt("is_trailer") == 1)  //Is Trailer


        mPlayerView = findViewById(R.id.full_screen_video);
        startPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlayer();
    }

    @Override
    protected void onPause() {
        release();
        super.onPause();
    }


    private void startPlayer() {
        if (mPlayer != null) {
            return;
        }
        mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                new DefaultTrackSelector());
        mPlayerView.setPlayer(mPlayer);

        DefaultDataSourceFactory dataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "player"));
        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mVideoUrl));

        boolean isResuming = mCurrentMillis != 0;
        mPlayer.prepare(extractorMediaSource, isResuming, false);
        mPlayer.setPlayWhenReady(true);
        if (isResuming) {
            mPlayer.seekTo(mCurrentMillis);
        }

    }

    private void release() {
        if (mPlayer == null) {
            return;
        }
        mCurrentMillis = mPlayer.getCurrentPosition();
        mPlayer.release();
        mPlayer = null;
    }
}
