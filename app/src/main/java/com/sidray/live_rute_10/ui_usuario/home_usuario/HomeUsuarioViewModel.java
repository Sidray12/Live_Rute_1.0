package com.sidray.live_rute_10.ui_usuario.home_usuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeUsuarioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeUsuarioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}