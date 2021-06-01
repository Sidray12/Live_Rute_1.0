package com.sidray.live_rute_10.ui_admin.conductor_admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConductorAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConductorAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}