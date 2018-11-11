package com.dennis.tsuma.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.adapters.IngredientAdapter;
import com.dennis.tsuma.bakingapp.models.Recipe;

public class IngredientsDetailActivity extends AppCompatActivity {
    private Recipe recipe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);
        recipe = getIntent().getParcelableExtra("recipe");
        RecyclerView stepsRvItem = findViewById(R.id.ingredientsRvItem);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        if (recipe != null) {
            IngredientAdapter adapter = new IngredientAdapter(recipe.getIngredients());
            stepsRvItem.setLayoutManager(manager);
            stepsRvItem.setAdapter(adapter);
        }
    }
}
