package com.dennis.tsuma.bakingapp.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.models.Step;
import com.dennis.tsuma.bakingapp.viewmodels.StepDetailViewModel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainFragment extends Fragment {

    private PlayerView playerView;
    private TextView description;
    private StepDetailViewModel viewModel;
    private SimpleExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;


    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        playerView = rootView.findViewById(R.id.player);
        description = rootView.findViewById(R.id.description);
        viewModel = ViewModelProviders.of(getActivity()).get(StepDetailViewModel.class);
        viewModel.getStepMutableLiveData().observe(this, this::onStepReceived);
        return rootView;
    }

    private void onStepReceived(Step step) {
        if (step != null) {
            releasePlayer();
            if (step.getVideoURL() != null) {
                if (!step.getVideoURL().isEmpty()) {
                    playerView.setVisibility(View.VISIBLE);
                    initializePlayer(step.getVideoURL());

                } else {
                    if (step.getThumbnailURL() != null) {
                        if (!step.getThumbnailURL().isEmpty()) {
                            playerView.setVisibility(View.VISIBLE);
                            initializePlayer(step.getThumbnailURL());

                        } else {
                            playerView.setVisibility(View.GONE);
                        }
                    } else {
                        playerView.setVisibility(View.GONE);
                    }
                }
            } else {
                if (step.getThumbnailURL() != null) {
                    if (!step.getThumbnailURL().isEmpty()) {
                        playerView.setVisibility(View.VISIBLE);
                        initializePlayer(step.getThumbnailURL());

                    } else {
                        playerView.setVisibility(View.GONE);
                    }
                } else {
                    playerView.setVisibility(View.GONE);
                }
            }
            if (step.getDescription() != null) {
                description.setText(step.getDescription());
            }
        }
    }

    private void initializePlayer(String mediaUrl) {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        Uri uri = Uri.parse(mediaUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("baking_app")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (Util.SDK_INT > 23) {
            initializePlayer("");
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();

    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

}
