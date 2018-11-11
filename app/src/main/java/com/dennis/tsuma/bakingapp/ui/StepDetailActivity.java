package com.dennis.tsuma.bakingapp.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.dennis.tsuma.bakingapp.viewmodels.StepDetailViewModel;

public class StepDetailActivity extends AppCompatActivity {
    private ImageButton leftButton;
    private ImageButton rightButton;
    private Recipe recipe;
    private int mIndex=0;
    private StepDetailViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        recipe = getIntent().getParcelableExtra("recipe");
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.container, new MainFragment())
                .commit();
        leftButton=findViewById(R.id.left_btn);
        rightButton=findViewById(R.id.right_btn);
        viewModel= ViewModelProviders.of(this).get(StepDetailViewModel.class);
        if(recipe !=null){
            viewModel.setStepMutableLiveData(recipe.getSteps().get(mIndex));
            mIndex++;
            leftButton.setVisibility(View.INVISIBLE);
        }

    }

    public void onBackClicked(View view) {
        if(recipe !=null) {
            if (mIndex > 0) {
                mIndex--;
                viewModel.setStepMutableLiveData(recipe.getSteps().get(mIndex));
                if (mIndex == 0) leftButton.setVisibility(View.INVISIBLE);
                if (mIndex < recipe.getSteps().size() - 1) rightButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onNextClicked(View view) {
        if (recipe != null) {
            if (mIndex != recipe.getSteps().size() - 1) {
                mIndex++;
                if (mIndex > 0) leftButton.setVisibility(View.VISIBLE);
                viewModel.setStepMutableLiveData(recipe.getSteps().get(mIndex));
                if (mIndex == recipe.getSteps().size() - 1)
                    rightButton.setVisibility(View.INVISIBLE);
            }
        }
    }
}
