package com.sidray.live_rute_10.ui_conductor.mensajes_conductor;

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

import com.sidray.live_rute_10.databinding.FragmentMensajesConductorBinding;

public class MensajesConductorFragment extends Fragment {

    private MensajesConductorViewModel mensajesConductorViewModel;
private FragmentMensajesConductorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        mensajesConductorViewModel =
                new ViewModelProvider(this).get(MensajesConductorViewModel.class);

    binding = FragmentMensajesConductorBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        mensajesConductorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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