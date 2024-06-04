


package com.example.emplostaff2.ui.Workforce;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkforceViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WorkforceViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
