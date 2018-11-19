package com.dennis.tsuma.bakingapp.viewmodels;
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
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.dennis.tsuma.bakingapp.models.Recipe;
import com.dennis.tsuma.bakingapp.models.Step;

public class StepDetailViewModel extends ViewModel {
    private MutableLiveData<Step> stepMutableLiveData;
    private MutableLiveData<Boolean> shouldOpenIngredients;
    private MutableLiveData<Recipe> recipe;

    public StepDetailViewModel() {
        stepMutableLiveData = new MutableLiveData<>();
        shouldOpenIngredients = new MutableLiveData<>();
        recipe = new MutableLiveData<>();
    }

    public LiveData<Step> getStepMutableLiveData() {
        return stepMutableLiveData;
    }

    public void setStepMutableLiveData(Step step) {
        stepMutableLiveData.setValue(step);
    }

    public LiveData<Boolean> getShouldOpenIngredients() {
        return shouldOpenIngredients;
    }

    public void setShouldOpenIngredients(boolean shouldOpen) {
        shouldOpenIngredients.setValue(shouldOpen);
    }

    public LiveData<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipeObject) {
        recipe.setValue(recipeObject);
    }
}
