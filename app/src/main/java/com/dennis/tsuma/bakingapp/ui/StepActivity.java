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
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dennis.tsuma.bakingapp.MainActivity;
import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.dennis.tsuma.bakingapp.models.Step;
import com.dennis.tsuma.bakingapp.viewmodels.StepDetailViewModel;

public class StepActivity extends AppCompatActivity implements MasterListFragment.OnStepClickHandler {
    private Recipe recipe;
    private boolean mTwoPane;
    private FragmentManager fm;
    private MainFragment mainFragment;
    private IngredientsFragment ingredientsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_fragment_holder);
        ActionBar actionBar = getSupportActionBar();
        recipe = getIntent().getParcelableExtra("recipe");
        StepDetailViewModel viewModel = ViewModelProviders.of(this).get(StepDetailViewModel.class);
        viewModel.setStepMutableLiveData(recipe.getSteps().get(0));
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (recipe != null) {
                actionBar.setTitle(recipe.getName());
            }
        }
        if (findViewById(R.id.fragment_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                mainFragment = new MainFragment();
                mainFragment.setRecipe(recipe);
                fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .add(R.id.fragment_container, mainFragment)
                        .commit();
            } else {
                mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mainfragment");
                ingredientsFragment = (IngredientsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ingredientsFragment");
            }

        } else {
            mTwoPane = false;
        }
        viewModel.setRecipe(recipe);

    }


    @Override
    public void onStepClicked(Step step) {
        if (!mTwoPane) {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        } else {
            mainFragment = new MainFragment();
            mainFragment.setRecipe(recipe);
            fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, mainFragment)
                    .commit();
        }
    }

    @Override
    public void onIngredientsClicked() {
        if (!mTwoPane) {
            if (recipe != null) {
                Intent intent = new Intent(this, IngredientsDetailActivity.class);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
            }
        } else {
            ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setRecipe(recipe);
            if (fm != null) {
                fm.beginTransaction()
                        .replace(R.id.fragment_container, ingredientsFragment)
                        .commit();
            } else {
                mainFragment = null;
                fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .add(R.id.fragment_container, ingredientsFragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.step_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mainFragment != null) {
            getSupportFragmentManager().putFragment(outState, "mainfragment", mainFragment);
        }
        if (ingredientsFragment != null) {
            getSupportFragmentManager().putFragment(outState, "ingredientsFragment", ingredientsFragment);
        }

    }
}
