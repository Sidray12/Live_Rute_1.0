package com.sidray.live_rute_10.ui_admin.conductor_admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sidray.live_rute_10.R;
import com.sidray.live_rute_10.databinding.FragmentConductorAdminBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Modelo.Usuarios;

public class ConductorAdminFragment extends Fragment {

    private ConductorAdminViewModel conductorAdminViewModel;
    private FragmentConductorAdminBinding binding;

    private EditText e_nom, e_cor, e_rut, e_pas;
    private Button b_gua, b_ac, b_el;
    private ListView l_cond;
    private String nom, cor, pas, rut, ver="";

    private List<Usuarios> list_data = new ArrayList<Usuarios>();
    private ArrayAdapter<Usuarios> adapter;
    private Usuarios usu_select;

    private FirebaseAuth mauth;
    private DatabaseReference data;
    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        conductorAdminViewModel =
                new ViewModelProvider(this).get(ConductorAdminViewModel.class);
        View root = inflater.inflate(R.layout.fragment_conductor_admin, container, false);


        FirebaseApp.initializeApp(getContext());
        mauth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();

        e_nom = root.findViewById(R.id.et_nombree);
        e_cor = root.findViewById(R.id.et_correo);
        e_rut = root.findViewById(R.id.et_ruta);
        e_pas = root.findViewById(R.id.et_pass);
        b_gua = root.findViewById(R.id.btn_guarda);
        b_ac = root.findViewById(R.id.btn_actualiza);
        b_el = root.findViewById(R.id.btn_elimina);
        l_cond = root.findViewById(R.id.listViii);

        user = mauth.getInstance().getCurrentUser();



        listarDatos();

        l_cond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usu_select = (Usuarios) parent.getItemAtPosition(position);

                e_nom.setText(usu_select.getNombre());
                e_cor.setText(usu_select.getCorreo());
                e_rut.setText(usu_select.getRuta());
                e_pas.setText(usu_select.getContraseña());
                ver = usu_select.getUid();
            }
        });


        b_gua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nom = e_nom.getText().toString();
                cor = e_cor.getText().toString();
                rut = e_rut.getText().toString();
                pas = e_pas.getText().toString();

                if (!ver.isEmpty()){
                    Toast.makeText(getContext(),"Actualice o Elimine",Toast.LENGTH_SHORT).show();
                }else if (nom.equals("") || cor.equals("") || rut.equals("") || pas.equals("")){
                    validacion();
                }else{
                    registrarusuario();
                    limpiar();
                }

            }
        });

        b_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom = e_nom.getText().toString();
                cor = e_cor.getText().toString();
                rut = e_rut.getText().toString();
                pas = e_pas.getText().toString();

                if (ver.isEmpty()){
                    Toast.makeText(getContext(),"seleccione un conductor",Toast.LENGTH_SHORT).show();
                }else if (nom.equals("") || cor.equals("") || rut.equals("") || pas.equals("")){
                    validacion();
                }else{
                    Usuarios u = new Usuarios();
                    u.setUid(usu_select.getUid());
                    u.setNombre(e_nom.getText().toString().trim());
                    u.setCorreo(e_cor.getText().toString().trim());
                    u.setRuta(e_rut.getText().toString().trim());
                    u.setContraseña(e_pas.getText().toString().trim());
                    u.setRol(usu_select.getRol());
                    data.child("Usuarios").child(u.getUid()).setValue(u);

                    limpiar();

                    Toast.makeText(getContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                    ver="";
                }
            }
        });

        b_el.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom = e_nom.getText().toString();
                cor = e_cor.getText().toString();
                rut = e_rut.getText().toString();
                pas = e_pas.getText().toString();

                if (ver.isEmpty()){
                    Toast.makeText(getContext(), "seleccione un conductor", Toast.LENGTH_SHORT).show();
                }else{
                    Usuarios u = new Usuarios();
                    u.setUid(ver);
                    data.child("Usuarios").child(u.getUid()).removeValue();
                    limpiar();
                    ver="";
                }
            }
        });


        return root;
    }

    private void listarDatos(){
        data.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list_data.clear();

                for (DataSnapshot snap : snapshot.getChildren()){
                    Usuarios u = snap.getValue(Usuarios.class);
                    String rol = u.getRol();

                    if (rol.equals("conductor")){
                        list_data.add(u);

                        adapter = new ArrayAdapter<Usuarios>(getContext(), android.R.layout.simple_list_item_1, list_data);
                        l_cond.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void validacion(){

        if (nom.equals("")){
            e_nom.setError("Requerido");
        }
        if (cor.equals("")){
            e_cor.setError("Requerido");
        }
        if (rut.equals("")){
            e_rut.setError("Requerido");
        }
        if (pas.equals("")){
            e_pas.setError("Requerido");
        }
    }

    private void limpiar(){
        e_nom.setText("");
        e_cor.setText("");
        e_rut.setText("");
        e_pas.setText("");
    }

    private void registrarusuario(){
        mauth.createUserWithEmailAndPassword(cor, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> map= new HashMap<>();
                    map.put("nombre", nom);
                    map.put("correo", cor);
                    map.put("contraseña", pas);
                    map.put("rol", "conductor");
                    map.put("ruta", rut);
                    String id = mauth.getCurrentUser().getUid();
                    map.put("uid", id);

                    data.child("Usuarios").child(id).setValue(map);
                    Toast.makeText(getContext(), "Guardado con exito", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(getContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}