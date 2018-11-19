package com.dennis.tsuma.bakingapp.utils;
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

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dennis.tsuma.bakingapp.R;
import com.dennis.tsuma.bakingapp.data.JSONData;
import com.dennis.tsuma.bakingapp.models.Ingredient;
import com.dennis.tsuma.bakingapp.models.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static ArrayList<Recipe> recipeLists;
    public static int recipeNameId;
    private Context mContext;
    private int mAppWidgetId;


    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public static void getUpdate(int id) {
        id = recipeNameId;
    }

    @Override
    public void onCreate() {
        recipeLists = new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        //String jsonResultConvertedToString = sharedPreferences.getString(JSON_KEY, "");

        //SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences();
        //recipeNameId = sharedPreferences.getInt(RecipeDetailFragment.RECIPE_NAME, -1);

        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>() {
        }.getType();
        recipeLists = gson.fromJson(JSONData.recipes, type);
    }

    @Override
    public void onDestroy() {
        recipeLists.clear();
    }

    @Override
    public int getCount() {
        if (recipeLists != null) {
            return recipeLists.size();
        } else {
            return 1;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);
        ArrayList<Integer> list = new ArrayList<>();
        getUpdate(recipeNameId);

        //The switch statement re orders the list so that the StackView can display the
        // desired recipe first before displaying others
        switch (recipeNameId) {
            case 0:
                list.add(0);
                list.add(1);
                list.add(2);
                list.add(3);
                break;
            case 1:
                list.add(1);
                list.add(0);
                list.add(2);
                list.add(3);
                break;
            case 2:
                list.add(2);
                list.add(1);
                list.add(0);
                list.add(3);
                break;
            case 3:
                list.add(3);
                list.add(1);
                list.add(2);
                list.add(0);
                break;
            default:
                list.add(0);
                list.add(1);
                list.add(2);
                list.add(3);
                break;
        }

        if (position <= getCount()) {
            if (recipeLists != null) {
                Recipe recipe = recipeLists.get(position);
                StringBuilder s = new StringBuilder();
                List<Ingredient> ingredients = recipeLists.get(list.get(position)).getIngredients();
                if (ingredients != null) {
                    s.append(recipeLists.get(list.get(position)).getName() + "\n\n");
                    for (int j = 0; j < ingredients.size(); j++) {
                        s.append(ingredients.get(j).getIngredient() + ": "
                                + ingredients.get(j).getQuantity() + " "
                                + ingredients.get(j).getMeasure() + "\n");
                    }
                    s.append("\n");
                }
                String listOfIngredients = s.toString();
                rv.setTextViewText(R.id.widget_text_view1, listOfIngredients);

                Bundle extras = new Bundle();
                extras.putInt(JSONData.EXTRA_ID, recipe.getId());
                Intent fillIntent = new Intent();
                fillIntent.putExtras(extras);
                rv.setOnClickFillInIntent(R.id.widget_list_item, fillIntent);
            }
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
