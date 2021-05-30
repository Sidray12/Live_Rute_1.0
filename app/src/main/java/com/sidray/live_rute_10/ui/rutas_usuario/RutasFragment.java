package com.sidray.live_rute_10.ui.rutas_usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sidray.live_rute_10.MapaActivity;
import com.sidray.live_rute_10.R;

public class RutasFragment extends Fragment {

    private Button rutaa_1;
    private Button rutaa_2;
    private String select_ruta;

    private RutasViewModel rutasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rutasViewModel =
                new ViewModelProvider(this).get(RutasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rutas_usuario, container, false);
        rutaa_1= root.findViewById(R.id.ruta_1);
        rutaa_2= root.findViewById(R.id.ruta_2);

        rutaa_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_ruta="1";
                Intent intent = new Intent(getActivity(), MapaActivity.class);
                intent.putExtra("ruta", select_ruta);
                startActivity(intent);

            }
        });

        rutaa_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_ruta="2";
                Intent intent = new Intent(getActivity(), MapaActivity.class);
                intent.putExtra("ruta", select_ruta);
                startActivity(intent);

            }
        });

        return root;
    }
}