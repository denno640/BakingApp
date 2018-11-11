package com.dennis.tsuma.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dennis.tsuma.bakingapp.adapters.RecipeAdapter;
import com.dennis.tsuma.bakingapp.data.JSONData;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.dennis.tsuma.bakingapp.ui.StepActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.CardListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Gson gson = new Gson();
        RecyclerView recipesRecyclerView = findViewById(R.id.recipesRvItem);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recipesRecyclerView.setLayoutManager(manager);
        Type recipesType = new TypeToken<Collection<Recipe>>(){}.getType();
        Collection<Recipe> recipeCollection = gson.fromJson(JSONData.recipes, recipesType);
        //collection converted to array
        Recipe[] recipes = recipeCollection.toArray(new Recipe[recipeCollection.size()]);
        RecipeAdapter mAdapter = new RecipeAdapter(recipes,this);
        recipesRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onCardClicked(Recipe recipe) {
       Intent intent = new Intent(this, StepActivity.class);
       intent.putExtra("recipe",recipe);
       startActivity(intent);
    }
}
