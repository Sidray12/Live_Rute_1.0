package com.sidray.live_rute_10.ui_conductor.rutas_conductor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RutasConductorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RutasConductorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}