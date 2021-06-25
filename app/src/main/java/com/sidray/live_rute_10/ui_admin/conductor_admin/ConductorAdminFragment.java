package com.sidray.live_rute_10.ui_admin.conductor_admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sidray.live_rute_10.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Modelo.Conductor;
import Modelo.Devices;
import Modelo.Drivers;
import Modelo.InterfaceAPI;
import Modelo.Usuarios;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConductorAdminFragment extends Fragment {

    private ConductorAdminViewModel conductorAdminViewModel;

    private EditText e_nom, txtBuscar;
    private Button b_ac;
    private ListView l_cond;
    private String nombre="", correo="", contraseña="", celular="", ver="";
    private String rut, nom;
    private Spinner spinner;
    private List list;
    private List<Conductor> list_data = new ArrayList<>();
    private ArrayAdapter adapter;
    private List<Devices> listDevices;
    private List<Drivers> listDrivers;
    private String correo_adm, password_adm;

    private FirebaseAuth mauth;
    private DatabaseReference data;
    private FirebaseUser user;
    private Conductor usu_select, nose;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_conductor_admin, container, false);

            FirebaseApp.initializeApp(getContext());
            mauth = FirebaseAuth.getInstance();
            data = FirebaseDatabase.getInstance().getReference();

            e_nom = root.findViewById(R.id.et_nombre_cond);
            b_ac = root.findViewById(R.id.btn_actualiza);
            spinner = root.findViewById(R.id.spinner);
            l_cond = root.findViewById(R.id.listViii);
            txtBuscar = root.findViewById(R.id.tx_buscar);

            user = mauth.getInstance().getCurrentUser();
            correo_adm=user.getEmail();
            String ua=user.getUid();

            e_nom.setEnabled(false);
            spinner.setEnabled(false);

            data.child("Conductor").child(ua).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    Usuarios u =snapshot.getValue(Usuarios.class);

                    password_adm = u.getContraseña();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            list = new ArrayList();

            String[] spin = {"Seleccione","RUTA 1: TOLEDO PLATA", "RUTA 2: SANTO DOMINGO", "RUTA 3: ESCOBAL", "RUTA 4: PORVENIR"};
            if (getActivity() != null) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, spin);
                spinner.setAdapter(adapter1);
            }

            listarDatos();

            l_cond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    usu_select = (Conductor) parent.getItemAtPosition(position);
                    String ruta = usu_select.getRuta();
                    int sruta = 0;
                    if (ruta.equals("1")){
                        sruta = 1;
                    }else if (ruta.equals("2")){
                        sruta = 2;
                    }else if (ruta.equals("3")){
                        sruta = 3;
                    }else if (ruta.equals("4")){
                        sruta = 4;
                    }

                    spinner.setEnabled(true);

                    e_nom.setText(usu_select.getName());
                    spinner.setSelection(sruta);
                    ver = list.get(position).toString();

                    data.child("Conductor").child(ver).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            nose = snapshot.getValue(Conductor.class);

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Toast.makeText(getContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            b_ac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nom = e_nom.getText().toString().trim();
                    String srut = spinner.getSelectedItem().toString();
                    if (srut.equals("RUTA 1: TOLEDO PLATA")){
                        rut="1";
                    }else if (srut.equals("RUTA 2: SANTO DOMINGO")){
                        rut="2";
                    }else if (srut.equals("RUTA 3: ESCOBAL")){
                        rut="3";
                    }else if (srut.equals("RUTA 4: PORVENIR")){
                        rut="4";
                    }

                    if (ver.isEmpty()) {
                        Toast.makeText(getContext(), "seleccione un conductor", Toast.LENGTH_SHORT).show();
                    } else if (nom.equals("") || srut.equals("Seleccione")) {
                        validacion();
                    } else {

                        Map<String, Object> map = new HashMap<>();
                        map.put("name",nom);
                        map.put("ruta",rut);
                        map.put("uid",ver);
                        map.put("conductor",nose.getConductor());
                        map.put("correo",nose.getCorreo());
                        map.put("password",nose.getPassword());
                        map.put("rol",nose.getRol());
                        map.put("id",nose.getId());
                        map.put("celular",nose.getCelular());

                        data.child("Conductor").child(ver).updateChildren(map);

                        limpiar();
                        spinner.setEnabled(false);

                        Toast.makeText(getContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                        ver = "";
                    }
                }
            });



        return root;
    }

    private void listarDatos(){

        data.child("Conductor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list_data.clear();
                list.clear();

                for (DataSnapshot snap : snapshot.getChildren()){
                    Conductor u = snap.getValue(Conductor.class);
                    list.add(snap.getKey());

                    if (u.getRol().equals("conductor")) {
                        list_data.add(u);
                    }

                    if (getActivity() != null) {

                        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_data);
                        l_cond.setAdapter(adapter);
                    }
                }
                txtBuscar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void cargarDrivers(){
        listDrivers = null;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://admin.4track.com.co/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        InterfaceAPI devicesAPI = retrofit.create(InterfaceAPI.class);
        Call<List<Drivers>> listCall = devicesAPI.getDrivers("Basic RnJvbm9ydGU3Nzc6T3BlcmF0aXYwNzc3");
        listCall.enqueue(new Callback<List<Drivers>>() {
            @Override
            public void onResponse(Call<List<Drivers>> call, Response<List<Drivers>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Codigo: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                listDrivers = response.body();

            }

            @Override
            public void onFailure(Call<List<Drivers>> call, Throwable t) {
                Toast.makeText(getContext(), "error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarConductor(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://admin.4track.com.co/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        InterfaceAPI devicesAPI = retrofit.create(InterfaceAPI.class);
        Call<List<Devices>> listCall = devicesAPI.getDevices("Basic RnJvbm9ydGU3Nzc6T3BlcmF0aXYwNzc3");
        listCall.enqueue(new Callback<List<Devices>>() {
            @Override
            public void onResponse(Call<List<Devices>> call, Response<List<Devices>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Codigo: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                listDevices=response.body();


                /*for (Devices d : listDevices){
                    String id = String.valueOf(d.getId());
                    String[] spl = d.getName().split(" |-");
                    String name = d.getName().trim();
                    String celular = d.getContact();
                    String status = d.getStatus();
                    String correo = (spl[1]+spl[2]+"@gmail.com").toLowerCase();
                    String sad = spl[1]+spl[2];
                    String contras= spl[0]+"Fronorte";
                    //Log.e("correo","contra: "+correo+"  "+contras);

                    for (Drivers ss : listDrivers){


                        String comp = ss.getUniqueId();
                        //if (sad.equals("xvn140")){
                            //Toast.makeText(getContext(), "funciona+"+ sad, Toast.LENGTH_SHORT).show();

                        Log.e("asd","asd"+ sad);

                        String comp2 = spl[0];

                        if (comp.equals(comp2)){
                            mauth.signInWithEmailAndPassword(correo, contras).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(), "funciona", Toast.LENGTH_SHORT).show();
                                        Map<String , Object> map = new HashMap<>();
                                        String uid=mauth.getCurrentUser().getUid();
                                        map.put("uid",uid);
                                        map.put("id", id);
                                        map.put("conductor",ss.getName());
                                        map.put("name", name);
                                        map.put("celular", celular);
                                        map.put("status", status);
                                        map.put("rol", "conductor");
                                        map.put("ruta", "2");
                                        map.put("correo",correo);
                                        map.put("password", contras);

                                        data.child("Conductor").child(uid).setValue(map);
                                        mauth.signOut();
                                    }else {
                                        Toast.makeText(getContext(), "Error "+ correo +" , "+ contras, Toast.LENGTH_SHORT).show();
                                    }
                                    mauth.signInWithEmailAndPassword(correo_adm, password_adm);
                                }
                            });
                        }
                    }*/
            }

            @Override
            public void onFailure(Call<List<Devices>> call, Throwable t) {
                Toast.makeText(getContext(), "error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void validacion(){

        if (spinner.getSelectedItem().toString().equals("Seleccione")){
            Toast.makeText(getContext(), "Por favor, Seleccione una ruta", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiar(){
        e_nom.setText("");
        spinner.setSelection(0);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}