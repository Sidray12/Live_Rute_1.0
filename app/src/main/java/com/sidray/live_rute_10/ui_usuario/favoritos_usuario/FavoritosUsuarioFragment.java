package com.sidray.live_rute_10.ui_usuario.favoritos_usuario;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sidray.live_rute_10.MapaActivity;
import com.sidray.live_rute_10.R;

import java.util.ArrayList;

public class FavoritosUsuarioFragment extends Fragment {

    private FavoritosUsuarioViewModel favoritosUsuarioViewModel;

    private ListView listView;
    private ArrayList favo;
    private ArrayAdapter adapter;
    private SharedPreferences shared;
    private String favv, favv2, item, ruta_s, nn;

    private FirebaseAuth auth;
    private DatabaseReference data;
    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritosUsuarioViewModel =
                new ViewModelProvider(this).get(FavoritosUsuarioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favoritos_usuario, container, false);

        auth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();
        user = auth.getInstance().getCurrentUser();

        nn = user.getEmail();

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
                }
            }
        });


        return root;
    }

    private void cargarPreferencias(){
        SharedPreferences shared = getContext().getSharedPreferences(nn, Context.MODE_PRIVATE);

        favv = shared.getString("favorito","no");
        favv2 = shared.getString("favorito2","no");
    }


}