package com.sidray.live_rute_10.ui_usuario.home_usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sidray.live_rute_10.R;

public class HomeUsuarioFragment extends Fragment {

    private HomeUsuarioViewModel homeUsuarioViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeUsuarioViewModel =
                new ViewModelProvider(this).get(HomeUsuarioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_usuario, container, false);
        return root;
    }
}