package com.example.emplostaff2.ui.ModificarUsuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ModificarUsuarioViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ModificarUsuarioViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
    }
