package com.sidray.live_rute_10.ui.favoritos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sidray.live_rute_10.MapaActivity;
import com.sidray.live_rute_10.R;

import java.util.ArrayList;

public class FavoritosFragment extends Fragment {

    private FavoritosViewModel favoritosViewModel;

    private ListView listView;
    private ArrayList favo;
    private ArrayAdapter adapter;
    private SharedPreferences shared;
    private String favv, favv2, item, ruta_s;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritosViewModel =
                new ViewModelProvider(this).get(FavoritosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favoritos, container, false);

        listView = root.findViewById(R.id.listV);

        cargarPreferencias();

        favo = new ArrayList();

        if (favv.equals("1")) {
            favo.add("RUTA 1: TOLEDO PLATA");
        }
        if (favv2.equals("2")) {
            favo.add("RUTA 2: SANTO DOMINGO");
        }

        adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, favo);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();

                if (item.equals("RUTA 2: SANTO DOMINGO")) {
                    ruta_s="2";
                    Intent intent = new Intent(getActivity(), MapaActivity.class);
                    intent.putExtra("ruta", ruta_s);
                    startActivity(intent);

                }else if (item.equals("RUTA 1: TOLEDO PLATA")){
                    ruta_s="1";
                    Intent intent = new Intent(getActivity(), MapaActivity.class);
                    intent.putExtra("ruta", ruta_s);
                    startActivity(intent);
                    getExitTransition();
                }
            }
        });


        return root;
    }

    private void cargarPreferencias(){
        SharedPreferences shared = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        favv = shared.getString("favorito","no");
        favv2 = shared.getString("favorito2","no");
    }

    /*private void refresh( int miliseconds){

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                contenido();
            }
        };
        handler.postDelayed(runnable, miliseconds);
    }*/


}