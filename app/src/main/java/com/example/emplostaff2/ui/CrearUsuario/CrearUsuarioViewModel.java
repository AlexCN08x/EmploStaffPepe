package com.example.emplostaff2.ui.CrearUsuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CrearUsuarioViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CrearUsuarioViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}