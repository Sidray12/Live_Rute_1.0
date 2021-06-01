package com.sidray.live_rute_10.ui_usuario.favoritos_usuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoritosUsuarioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FavoritosUsuarioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}