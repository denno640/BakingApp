package com.dennis.tsuma.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dennis.tsuma.bakingapp.adapters.RecipeAdapter;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.dennis.tsuma.bakingapp.ui.StepActivity;
import com.dennis.tsuma.bakingapp.utils.SimpleIdlingResource;
import com.dennis.tsuma.bakingapp.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.CardListener {
    private List<Recipe> recipeList;
    private RecipeAdapter mAdapter;
    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipeList = new ArrayList<>();
        RecyclerView recipesRecyclerView = findViewById(R.id.recipesRvItem);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recipesRecyclerView.setLayoutManager(manager);
        mAdapter = new RecipeAdapter(recipeList, this);
        recipesRecyclerView.setAdapter(mAdapter);
        MainActivityViewModel viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.getRecipeList().observe(this, this::onRecipesReceived);

    }

    @Override
    public void onCardClicked(Recipe recipe) {
       Intent intent = new Intent(this, StepActivity.class);
       intent.putExtra("recipe",recipe);
       startActivity(intent);
    }

    private void onRecipesReceived(List<Recipe> recipes) {
        if (recipes != null) {
            if (recipes.size() > 0) {
                recipeList.addAll(recipes);
                mAdapter.notifyDataSetChanged();

            } else {
                Toast.makeText(this, "list of recipes is empty!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
