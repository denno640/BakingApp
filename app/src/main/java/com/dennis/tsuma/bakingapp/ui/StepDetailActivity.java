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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dennis.tsuma.bakingapp.MainActivity;
import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.dennis.tsuma.bakingapp.viewmodels.StepDetailViewModel;

public class StepDetailActivity extends AppCompatActivity {

    private Recipe recipe;
    private int mIndex = 0;
    private StepDetailViewModel viewModel;
    private PhoneFragment phoneFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        recipe = getIntent().getParcelableExtra("recipe");
        if (savedInstanceState == null) {
            phoneFragment = new PhoneFragment();
            phoneFragment.setRecipe(recipe);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.container, phoneFragment)
                    .commit();

        } else {
            mIndex = savedInstanceState.getInt("mIndex");
            recipe = savedInstanceState.getParcelable("recipe");
        }
        viewModel = ViewModelProviders.of(this).get(StepDetailViewModel.class);
        if (recipe != null) {
            viewModel.setStepMutableLiveData(recipe.getSteps().get(mIndex));


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
        getSupportFragmentManager().putFragment(outState, "phoneFragment", phoneFragment);
        outState.putInt("mIndex", mIndex);
        outState.putParcelable("recipe", recipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        phoneFragment = (PhoneFragment) getSupportFragmentManager().getFragment(savedInstanceState, "phoneFragment");
    }



}
