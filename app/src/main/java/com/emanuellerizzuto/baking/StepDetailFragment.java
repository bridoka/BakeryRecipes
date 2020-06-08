package com.emanuellerizzuto.baking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emanuellerizzuto.baking.data.RecipeParcelable;
import com.emanuellerizzuto.baking.data.RecipeStepParcelable;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetailFragment extends Fragment
        implements ExoPlayer.EventListener {

    private RecipeStepParcelable step;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private final String PLAYER_POSITION = "player_position";
    private final String PLAY_WHEN_READY = "play_when_ready";
    private Long positionExoPlayer = null;
    private boolean playWhenReady = true;
    private boolean twoPainels = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        Context context = getContext();
        twoPainels = getActivity().findViewById(R.id.two_painels) != null;
        if (savedInstanceState != null && savedInstanceState.getBundle("bundle") != null) {
            Bundle bundle = savedInstanceState.getBundle("bundle");
            setStep((RecipeStepParcelable) bundle.getParcelable("step"));
            positionExoPlayer = savedInstanceState.getLong(PLAYER_POSITION);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY, true);
        }

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE && twoPainels == false) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

            getActivity().getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN );
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            TextView tv_step_short_description = view.findViewById(R.id.tv_step_short_description);
            if (tv_step_short_description != null) {
                tv_step_short_description.setText(step.getShortDescription());
            }
            TextView tv_step_description = view.findViewById(R.id.tv_step_description);
            if (tv_step_description != null) {
                tv_step_description.setText(step.getDescription());
            }
        }

        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        return view;
    }


    public void setStep(RecipeStepParcelable stepParcelable) {
        step = stepParcelable;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        outState.putBundle("bundle", bundle);
        if (mExoPlayer != null) {
            outState.putLong(PLAYER_POSITION, mExoPlayer.getCurrentPosition());
            outState.putBoolean(PLAY_WHEN_READY, mExoPlayer.getPlayWhenReady());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((RecipeActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //hideSystemUi();
        if (Util.SDK_INT < 24 || mExoPlayer == null) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void initializePlayer() {
        Context context = getContext();
        String videoURL = step.getVideoURL();
        Uri mediaUri = Uri.parse(videoURL);
        if (mExoPlayer == null && !videoURL.isEmpty()) {
            mPlayerView.setVisibility(View.VISIBLE);
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);
            if (positionExoPlayer != null) {
                mExoPlayer.seekTo(positionExoPlayer);
            }
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, "baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            this.clearExoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            this.clearExoPlayer();
        }
    }

    private void clearExoPlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
