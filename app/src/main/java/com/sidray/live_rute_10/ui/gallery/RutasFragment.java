package com.sidray.live_rute_10.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sidray.live_rute_10.R;

public class RutasFragment extends Fragment {

    private RutasViewModel rutasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rutasViewModel =
                new ViewModelProvider(this).get(RutasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rutas, container, false);
        return root;
    }
}