package com.sidray.live_rute_10.ui_admin.rutas_admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RutasAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RutasAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}