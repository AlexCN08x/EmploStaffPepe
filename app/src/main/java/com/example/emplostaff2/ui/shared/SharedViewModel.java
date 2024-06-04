package com.example.emplostaff2.ui.shared;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> extraHours = new MutableLiveData<>();

    public void setExtraHours(Integer hours) {
        extraHours.setValue(hours);
    }

    public LiveData<Integer> getExtraHours() {
        return extraHours;
    }
}
