package com.sidray.live_rute_10.ui_admin.mensajes_admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MensajesAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MensajesAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}