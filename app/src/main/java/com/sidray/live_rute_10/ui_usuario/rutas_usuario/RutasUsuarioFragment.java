package com.sidray.live_rute_10.ui_usuario.rutas_usuario;

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

public class RutasUsuarioFragment extends Fragment {

    private Button rutaa_1;
    private Button rutaa_2;
    private Button rutaa_3;
    private Button rutaa_4;
    private String select_ruta;

    private RutasUsuarioViewModel rutasUsuarioViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rutasUsuarioViewModel =
                new ViewModelProvider(this).get(RutasUsuarioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rutas_usuario, container, false);
        rutaa_1= root.findViewById(R.id.ruta_1);
        rutaa_2= root.findViewById(R.id.ruta_2);
        rutaa_3 = root.findViewById(R.id.ruta_3);
        rutaa_4 = root.findViewById(R.id.ruta_4);

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

        rutaa_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_ruta="3";
                Intent intent = new Intent(getActivity(), MapaActivity.class);
                intent.putExtra("ruta", select_ruta);
                startActivity(intent);

            }
        });

        rutaa_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_ruta="4";
                Intent intent = new Intent(getActivity(), MapaActivity.class);
                intent.putExtra("ruta", select_ruta);
                startActivity(intent);

            }
        });




        return root;
    }
}