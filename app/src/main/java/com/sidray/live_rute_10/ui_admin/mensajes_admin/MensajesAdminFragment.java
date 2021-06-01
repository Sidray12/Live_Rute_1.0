package com.sidray.live_rute_10.ui_admin.mensajes_admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sidray.live_rute_10.databinding.FragmentMensajesAdminBinding;

public class MensajesAdminFragment extends Fragment {

    private MensajesAdminViewModel mensajesAdminViewModel;
    private FragmentMensajesAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mensajesAdminViewModel =
                new ViewModelProvider(this).get(MensajesAdminViewModel.class);

        binding = FragmentMensajesAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}