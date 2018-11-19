package com.dennis.tsuma.bakingapp.ui;
/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.dennis.tsuma.bakingapp.models.Step;
import com.dennis.tsuma.bakingapp.viewmodels.StepDetailViewModel;
import com.google.android.exoplayer2.C;
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

public class PhoneFragment extends Fragment {
    private PlayerView playerView;
    private TextView description;
    private StepDetailViewModel viewModel;
    private SimpleExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    private Recipe recipe;
    private int mIndex;
    private ImageButton leftButton;
    private ImageButton rightButton;
    private TextView navigate;


    public PhoneFragment() {
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phone, container, false);
        playerView = rootView.findViewById(R.id.player);
        description = rootView.findViewById(R.id.description);
        leftButton = rootView.findViewById(R.id.left_btn);
        rightButton = rootView.findViewById(R.id.right_btn);
        navigate = rootView.findViewById(R.id.navigate);
        viewModel = ViewModelProviders.of(getActivity()).get(StepDetailViewModel.class);
        viewModel.getStepMutableLiveData().observe(this, this::onStepReceived);
        viewModel.getRecipe().observe(this, this::onRecipeReceived);
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong("position");
            currentWindow = savedInstanceState.getInt("current");
            mIndex = savedInstanceState.getInt("mIndex");
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            recipe = savedInstanceState.getParcelable("recipe");
        }
        playerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (description.getVisibility() == View.VISIBLE) {
                    description.setVisibility(View.GONE);
                    leftButton.setVisibility(View.GONE);
                    rightButton.setVisibility(View.GONE);
                    navigate.setVisibility(View.GONE);
                } else {
                    description.setVisibility(View.VISIBLE);
                    leftButton.setVisibility(View.VISIBLE);
                    rightButton.setVisibility(View.VISIBLE);
                    navigate.setVisibility(View.VISIBLE);
                }
            }
            return false;
        });
        leftButton.setOnClickListener(v -> {
            if (mIndex > 0) {
                mIndex--;

                onStepReceived(recipe.getSteps().get(mIndex));
                if (mIndex == 0) leftButton.setVisibility(View.INVISIBLE);
                if (mIndex < recipe.getSteps().size() - 1) rightButton.setVisibility(View.VISIBLE);
            }
        });
        rightButton.setOnClickListener(v -> {
            if (mIndex != recipe.getSteps().size() - 1) {
                mIndex++;
                if (mIndex > 0) leftButton.setVisibility(View.VISIBLE);
                onStepReceived(recipe.getSteps().get(mIndex));
                if (mIndex == recipe.getSteps().size() - 1)
                    rightButton.setVisibility(View.INVISIBLE);
            }
        });
        if (mIndex == 0) leftButton.setVisibility(View.INVISIBLE);

        return rootView;
    }

    private void onRecipeReceived(Recipe recipe) {
        if (recipe != null) {
            this.recipe = recipe;
        }
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
        Uri uri = Uri.parse(mediaUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        if (playbackPosition > 0 && playbackPosition != C.TIME_UNSET) {
            player.seekTo(playbackPosition);
            player.setPlayWhenReady(playWhenReady);
        } else {
            player.setPlayWhenReady(playWhenReady);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("baking_app")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
        }


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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("position", playbackPosition);
        outState.putInt("current", currentWindow);
        outState.putBoolean("playWhenReady", playWhenReady);
        outState.putInt("mIndex", mIndex);
        if (recipe != null)
            outState.putParcelable("recipe", recipe);

    }

}
