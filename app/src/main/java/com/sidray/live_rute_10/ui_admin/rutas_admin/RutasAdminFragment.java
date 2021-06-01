package com.sidray.live_rute_10.ui_admin.rutas_admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sidray.live_rute_10.databinding.FragmentRutasAdminBinding;

public class RutasAdminFragment extends Fragment {

    private RutasAdminViewModel rutasAdminViewModel;
    private FragmentRutasAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rutasAdminViewModel =
                new ViewModelProvider(this).get(RutasAdminViewModel.class);

        binding = FragmentRutasAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        rutasAdminViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}