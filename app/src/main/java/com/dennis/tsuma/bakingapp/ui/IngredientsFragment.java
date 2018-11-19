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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.adapters.IngredientAdapter;
import com.dennis.tsuma.bakingapp.models.Ingredient;
import com.dennis.tsuma.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {
    IngredientAdapter mAdapter;
    private List<Ingredient> ingredientList = new ArrayList<>();

    public IngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_ingredient_detail, container, false);
        RecyclerView stepsRvItem = rootView.findViewById(R.id.ingredientsRvItem);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new IngredientAdapter(ingredientList);
        stepsRvItem.setLayoutManager(manager);
        stepsRvItem.setAdapter(mAdapter);
        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        if (recipe != null) {
            ingredientList.addAll(recipe.getIngredients());
            if (mAdapter != null)
                mAdapter.notifyDataSetChanged();
        }
    }
}
