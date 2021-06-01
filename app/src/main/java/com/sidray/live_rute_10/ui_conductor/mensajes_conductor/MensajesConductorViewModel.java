package com.sidray.live_rute_10.ui_conductor.mensajes_conductor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MensajesConductorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MensajesConductorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}