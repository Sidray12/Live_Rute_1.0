package com.sidray.live_rute_10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sidray.live_rute_10.ui_usuario.rutas_usuario.RutasUsuarioFragment;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComentarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComentarioFragment extends Fragment {

    private Button env_coment;
    private EditText et_comentario;

    FirebaseAuth mauth;
    DatabaseReference data;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComentarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComentarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComentarioFragment newInstance(String param1, String param2) {
        ComentarioFragment fragment = new ComentarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_comentario, container, false);

        env_coment = root.findViewById(R.id.btn_comentario);
        et_comentario = root.findViewById(R.id.text_comentario);

        mauth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();

        DateFormat df = new SimpleDateFormat("HH:mm");
        String time = df.format(Calendar.getInstance().getTime());
        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String date= df2.format(Calendar.getInstance().getTime());



        env_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_comentario.getText().toString().equals("")) {
                    et_comentario.setError("Campo Requerido");
                }else {

                    FirebaseUser currentUser = mauth.getInstance().getCurrentUser();
                    String emau = currentUser.getEmail();
                    String comenta = et_comentario.getText().toString().trim();

                    Map<String, Object> map = new HashMap<>();
                    map.put("correo", emau);
                    map.put("comentario", comenta);
                    map.put("hora", time);
                    map.put("fecha", date);


                    data.child("Comentario").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Comentario Enviado", Toast.LENGTH_SHORT).show();
                                RutasUsuarioFragment rutasUsuarioFragment = new RutasUsuarioFragment();
                                FragmentTransaction fragmentTransaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, rutasUsuarioFragment);
                                fragmentTransaction.commit();
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
}