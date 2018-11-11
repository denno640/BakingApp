package com.dennis.tsuma.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.adapters.StepAdapter;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.dennis.tsuma.bakingapp.models.Step;

public class StepActivity extends AppCompatActivity implements StepAdapter.StepClickListener {
    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ActionBar actionBar = getSupportActionBar();
        recipe = getIntent().getParcelableExtra("recipe");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (recipe != null) {
                actionBar.setTitle(recipe.getName());
            }
        }

        RecyclerView stepsRvItem = findViewById(R.id.stepsRvItem);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        if (recipe != null) {
            StepAdapter adapter = new StepAdapter(recipe.getSteps(), this);
            stepsRvItem.setLayoutManager(manager);
            stepsRvItem.setAdapter(adapter);
        }
    }

    @Override
    public void onStepClicked(Step step) {
        if(recipe != null){
            Intent intent = new Intent(this,StepDetailActivity.class);
            intent.putExtra("recipe",recipe);
            startActivity(intent);
        }
    }

    public void onIngredientsClick(View view) {
        if(recipe != null){
            Intent intent = new Intent(this,IngredientsDetailActivity.class);
            intent.putExtra("recipe",recipe);
            startActivity(intent);
        }
    }
}
