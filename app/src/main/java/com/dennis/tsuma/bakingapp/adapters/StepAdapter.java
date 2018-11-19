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
import com.dennis.tsuma.bakingapp.models.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<Step> steps;
    private StepClickListener mStepClickListener;

    public StepAdapter(List<Step> steps, StepClickListener mStepClickListener) {
        this.steps = steps;
        this.mStepClickListener= mStepClickListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_detail
                , parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(steps.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
    public interface StepClickListener{
        void onStepClicked(Step step);
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView id, description;

        public StepViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.step_id);
            description = view.findViewById(R.id.description);
            view.setOnClickListener(this);
        }

        private void bind(final Step step) {
                id.setText(itemView.getContext().getString(R.string.step_id)+step.getId());
            if(step.getShortDescription() != null){
                description.setText(String.format("Description: %s", step.getShortDescription()));
            }

        }


        @Override
        public void onClick(View v) {
            mStepClickListener.onStepClicked(steps.get(getAdapterPosition()));
        }
    }
}
