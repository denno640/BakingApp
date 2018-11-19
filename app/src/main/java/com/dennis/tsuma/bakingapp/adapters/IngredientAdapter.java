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
import android.widget.TextView;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.models.Ingredient;
import com.dennis.tsuma.bakingapp.models.Step;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{
    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
       this.ingredientList=ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ingredient_item
                , parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(ingredientList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
    public interface StepClickListener{
        void onStepClicked(Step step);
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final TextView ingredientText, quantity,measure;

        public IngredientViewHolder(View view) {
            super(view);
            ingredientText = view.findViewById(R.id.ingredient);
            quantity = view.findViewById(R.id.quantity);
            measure=view.findViewById(R.id.measure);
        }

        private void bind(final Ingredient ingredient) {


           if(ingredient.getMeasure() != null){
               measure.setText("Measure: "+ingredient.getMeasure());
           }
           if(ingredient.getIngredient() != null){
               ingredientText.setText("Ingredient: "+ingredient.getIngredient());
           }
           quantity.setText("Quantity: "+ingredient.getQuantity());



        }

    }
}
