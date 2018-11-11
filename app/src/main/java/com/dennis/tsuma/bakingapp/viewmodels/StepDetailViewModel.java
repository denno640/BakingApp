package com.dennis.tsuma.bakingapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.dennis.tsuma.bakingapp.models.Step;

public class StepDetailViewModel extends ViewModel {
    private MutableLiveData<Step> stepMutableLiveData;

    public StepDetailViewModel() {
        stepMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<Step> getStepMutableLiveData() {
        return stepMutableLiveData;
    }

    public void setStepMutableLiveData(Step step) {
        stepMutableLiveData.setValue(step);
    }
}
