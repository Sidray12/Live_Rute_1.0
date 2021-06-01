package com.sidray.live_rute_10.ui_usuario.rutas_usuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RutasUsuarioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RutasUsuarioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}