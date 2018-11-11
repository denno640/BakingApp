package com.dennis.tsuma.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dennis.tsuma.bakingapp.BakingAppApplication;
import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Recipe[] recipes;
    private CardListener mCardListener;

    public RecipeAdapter(Recipe[] recipes, CardListener mCardListener) {
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
        holder.bind(recipes[position]);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return recipes.length;
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
            if (recipe.getImage() != null) {
                if (!recipe.getImage().isEmpty()) {
                    BakingAppApplication.getApp()
                            .getPicassoWithCache()
                            .load(recipe.getImage())
                            .priority(Picasso.Priority.HIGH)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .into(image, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError() {
                                    BakingAppApplication.getApp()
                                            .getPicassoWithCache()
                                            .load(recipe.getImage())
                                            .priority(Picasso.Priority.HIGH)
                                            // .networkPolicy(NetworkPolicy.OFFLINE)
                                            .into(image, new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                }

                                                @Override
                                                public void onError() {

                                                }
                                            });
                                }
                            });
                }
            }
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
            mCardListener.onCardClicked(recipes[getAdapterPosition()]);
        }
    }
}
