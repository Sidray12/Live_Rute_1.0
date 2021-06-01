package com.sidray.live_rute_10.ui_conductor.rutas_conductor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sidray.live_rute_10.MapaActivity;
import com.sidray.live_rute_10.R;
import com.sidray.live_rute_10.databinding.FragmentRutasConductorBinding;

import org.jetbrains.annotations.NotNull;

public class RutasConductorFragment extends Fragment {

    private RutasConductorViewModel rutasConductorViewModel;
    private FragmentRutasConductorBinding binding;

    private Button btn_mr;
    private String rutaa;

    private FirebaseAuth auth;
    private DatabaseReference data;
    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        rutasConductorViewModel =
                new ViewModelProvider(this).get(RutasConductorViewModel.class);

        View root = inflater.inflate(R.layout.fragment_rutas_conductor, container, false);

        auth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();
        user = auth.getInstance().getCurrentUser();

        btn_mr = root.findViewById(R.id.btn_mirut);

        String id = user.getUid();

        data.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                rutaa = snapshot.child("ruta").getValue().toString();

                if (rutaa.equals("1")){
                    btn_mr.setText("RUTA 1: TOLEDO PLATA");
                }else if (rutaa.equals("2")){
                    btn_mr.setText("RUTA 2: SANTO DOMINGO");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btn_mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rutaa.equals("1")){
                    Intent intent = new Intent(getActivity(), MapaActivity.class);
                    intent.putExtra("ruta", rutaa);
                    startActivity(intent);
                }else if (rutaa.equals("2")){
                    Intent intent = new Intent(getActivity(), MapaActivity.class);
                    intent.putExtra("ruta", rutaa);
                    startActivity(intent);
                }
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