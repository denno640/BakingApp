package com.dennis.tsuma.bakingapp.adapters;
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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;
    private CardListener mCardListener;
    private int[] drawables = {R.mipmap.nutella_pie, R.mipmap.brownie, R.mipmap.yellow_cake, R.mipmap.cheesecake};

    public RecipeAdapter(List<Recipe> recipes, CardListener mCardListener) {
        this.recipes = recipes;
        this.mCardListener= mCardListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_item
                , parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
    public interface CardListener{
        void onCardClicked(Recipe recipe);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView image;
        private final TextView name, servings,steps,ingredients;

        public RecipeViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.photo);
            name = view.findViewById(R.id.name);
            servings = view.findViewById(R.id.servings);
            steps=view.findViewById(R.id.steps);
            ingredients=view.findViewById(R.id.ingredients);
            view.setOnClickListener(this);
        }

        private void bind(final Recipe recipe) {
            image.setImageResource(drawables[getAdapterPosition()]);

            if(recipe.getName() != null){
                name.setText(String.format("Name: %s", recipe.getName()));
            }
            if(recipe.getIngredients() != null){
                ingredients.setText(String.format("Ingredients: %d", recipe.getIngredients().size()));
            }
            if(recipe.getSteps() != null){
                steps.setText(String.format("Steps: %d", recipe.getSteps().size()));
            }
            servings.setText(String.format("Servings: %d", recipe.getServings()));
        }


        @Override
        public void onClick(View v) {
            mCardListener.onCardClicked(recipes.get(getAdapterPosition()));
        }
    }
}
