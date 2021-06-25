package com.sidray.live_rute_10.ui_usuario.favoritos_usuario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sidray.live_rute_10.MapaActivity;
import com.sidray.live_rute_10.R;

import java.util.ArrayList;
import java.util.List;

import Modelo.ListAdapter;
import Modelo.ListFavoritos;

public class FavoritosUsuarioFragment extends Fragment {

    private FavoritosUsuarioViewModel favoritosUsuarioViewModel;

    private String favv, favv2, favv3, favv4, ruta_s, nn;

    private FirebaseAuth auth;
    private DatabaseReference data;
    private FirebaseUser user;
    private RecyclerView recyclerView;

    private List<ListFavoritos> elementos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritosUsuarioViewModel =
                new ViewModelProvider(this).get(FavoritosUsuarioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favoritos_usuario, container, false);

        auth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();
        user = auth.getInstance().getCurrentUser();

        nn = user.getEmail();

        cargarPreferencias();

        elementos = new ArrayList<>();

        if (favv.equals("1")){
            elementos.add(new ListFavoritos("RUTA 1: TOLEDO PLATA"));
        }
        if (favv2.equals("2")){
            elementos.add(new ListFavoritos("RUTA 2: SANTO DOMINGO"));
        }
        if (favv3.equals("3")) {
            elementos.add(new ListFavoritos("RUTA 3: ESCOBAL"));
        }
        if (favv4.equals("4")) {
            elementos.add(new ListFavoritos("RUTA 4: PORVENIR"));
        }

        ListAdapter listAdapter = new ListAdapter(elementos, getContext());
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "seleccion: "+ elementos.get(recyclerView.getChildAdapterPosition(v)).getRuta(), Toast.LENGTH_SHORT).show();
                String select= elementos.get(recyclerView.getChildAdapterPosition(v)).getRuta();

                if (select.equals("RUTA 1: TOLEDO PLATA")){
                    ruta_s="1";
                    Intent intent = new Intent(getActivity(), MapaActivity.class);
                    intent.putExtra("ruta", ruta_s);
                    startActivity(intent);
                }else if (select.equals("RUTA 2: SANTO DOMINGO")){
                    ruta_s="2";
                    Intent intent = new Intent(getActivity(), MapaActivity.class);
                    intent.putExtra("ruta", ruta_s);
                    startActivity(intent);
                }else if (select.equals("RUTA 3: ESCOBAL")){
                    ruta_s="3";
                    Intent intent = new Intent(getActivity(), MapaActivity.class);
                    intent.putExtra("ruta", ruta_s);
                    startActivity(intent);
                }else if (select.equals("RUTA 4: PORVENIR")){
                    ruta_s="4";
                    Intent intent = new Intent(getActivity(), MapaActivity.class);
                    intent.putExtra("ruta", ruta_s);
                    startActivity(intent);
                }

            }
        });

        recyclerView.setAdapter(listAdapter);

        return root;
    }

    private void cargarPreferencias(){
        SharedPreferences shared = getContext().getSharedPreferences(nn, Context.MODE_PRIVATE);

        favv = shared.getString("favorito","no");
        favv2 = shared.getString("favorito2","no");
        favv3 = shared.getString("favorito3","no");
        favv4 = shared.getString("favorito4","no");
    }


}