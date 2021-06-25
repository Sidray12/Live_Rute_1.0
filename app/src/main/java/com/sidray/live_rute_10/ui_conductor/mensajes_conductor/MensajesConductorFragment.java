package com.sidray.live_rute_10.ui_conductor.mensajes_conductor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sidray.live_rute_10.R;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Modelo.Conductor;

public class MensajesConductorFragment extends Fragment {

    private Button env_coment;
    private EditText et_comentario;

    FirebaseAuth mauth;
    DatabaseReference data;
    String cel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_mensajes_conductor, container, false);

        env_coment = root.findViewById(R.id.btn_comentario_cond);
        et_comentario = root.findViewById(R.id.text_comentario_cond);

        mauth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();

        DateFormat df = new SimpleDateFormat("HH:mm");
        String time = df.format(Calendar.getInstance().getTime());
        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String date= df2.format(Calendar.getInstance().getTime());

        FirebaseUser currentUser = mauth.getInstance().getCurrentUser();
        String uid= currentUser.getUid();


        data.child("Conductor").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Conductor c = snapshot.getValue(Conductor.class);
                cel = c.getCelular();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), "error " + error, Toast.LENGTH_SHORT).show();
            }
        });


        env_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_comentario.getText().toString().equals("")) {
                    et_comentario.setError("Campo Requerido");
                }else {

                    FirebaseUser currentUser = mauth.getInstance().getCurrentUser();
                    //String RegisteredUserID = currentUser.getUid();
                    String emau = currentUser.getEmail();
                    String comenta = et_comentario.getText().toString().trim();

                    Map<String, Object> map = new HashMap<>();
                    map.put("correo", emau);
                    map.put("comentario", comenta);
                    map.put("hora", time);
                    map.put("fecha", date);
                    map.put("celular", cel);


                    data.child("Comentario_cond").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Comentario Enviado", Toast.LENGTH_SHORT).show();
                                et_comentario.setText("");
                            } else {
                                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }






            }
        });

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}