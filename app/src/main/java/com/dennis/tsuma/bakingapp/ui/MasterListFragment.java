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
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.adapters.StepAdapter;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.dennis.tsuma.bakingapp.models.Step;
import com.dennis.tsuma.bakingapp.viewmodels.StepDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class MasterListFragment extends Fragment implements StepAdapter.StepClickListener {
    private List<Step> stepList;
    private StepAdapter mAdapter;
    private OnStepClickHandler mCallback;

    public MasterListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickHandler) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + "Host Activity must implement OnStepClickHandler");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_step, container, false);
        RecyclerView stepsRvItem = rootView.findViewById(R.id.stepsRvItem);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        StepDetailViewModel viewModel = ViewModelProviders.of(getActivity()).get(StepDetailViewModel.class);
        stepList = new ArrayList<>();
        mAdapter = new StepAdapter(stepList, this);
        stepsRvItem.setLayoutManager(manager);
        stepsRvItem.setAdapter(mAdapter);
        viewModel.getRecipe().observe(this, this::onRecipeReceived);

        CardView ingredientsCard = rootView.findViewById(R.id.ingredients_card);
        ingredientsCard.setOnClickListener(v -> mCallback.onIngredientsClicked());
        return rootView;


    }

    private void onRecipeReceived(Recipe recipe) {
        if (recipe != null) {
            stepList.addAll(recipe.getSteps());
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onStepClicked(Step step) {
        mCallback.onStepClicked(step);
    }

    public interface OnStepClickHandler {
        void onStepClicked(Step step);

        void onIngredientsClicked();
    }

}
