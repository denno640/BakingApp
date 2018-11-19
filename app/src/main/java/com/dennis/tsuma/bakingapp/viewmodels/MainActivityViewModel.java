package com.dennis.tsuma.bakingapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dennis.tsuma.bakingapp.api.ApiMerchant;
import com.dennis.tsuma.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Recipe>> recipeList;

    public MainActivityViewModel() {
        recipeList = new MutableLiveData<>();
        loadRecipes();
    }

    private void loadRecipes() {
        ApiMerchant.getInstance()
                .getMyApiService()
                .getRecipes()
                .enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                        if (response.isSuccessful()) {
                            recipeList.setValue(ApiMerchant.provideRecipeList(response));
                            return;
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                        Log.d("MainActivityViewModel", t.toString());

                    }
                });
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }
}
