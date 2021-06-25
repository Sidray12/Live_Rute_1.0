package com.sidray.live_rute_10.ui_admin.mensajes_admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sidray.live_rute_10.R;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Modelo.AdapterMesage;
import Modelo.ListMesage;
import Modelo.Mensaje;

public class MensajesAdminFragment extends Fragment {

    private MensajesAdminViewModel mensajesAdminViewModel;

    private RecyclerView recyclerMes;

    private DatabaseReference data;
    private Mensaje listMesage;
    private Spinner spin_cond;

    private List<ListMesage> elem_message;

    private int dias;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mensajesAdminViewModel =
                new ViewModelProvider(this).get(MensajesAdminViewModel.class);

        View root = inflater.inflate(R.layout.fragment_mensajes_admin, container, false);
        data = FirebaseDatabase.getInstance().getReference();
        recyclerMes = root.findViewById(R.id.recycler_mesage);
        spin_cond = root.findViewById(R.id.spin_mensaje);

        elem_message = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("HH:mm");
        String time = df.format(Calendar.getInstance().getTime());

        String[] selc = {"Conductor","Usuario"};

        if (getActivity() != null) {
            ArrayAdapter<String> adapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, selc);
            spin_cond.setAdapter(adapter1);
        }

        spin_cond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    listarDatosCond();
                }else{
                    listarDatos();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //listarDatos();

        return root;
    }

    private void listarDatos() {
        data.child("Comentario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                elem_message.clear();
                String corr, mensa, hor;
                String fech;


                for (DataSnapshot sdata : snapshot.getChildren()){
                    String key=sdata.getKey();

                    listMesage =sdata.getValue(Mensaje.class);
                    mensa=listMesage.getComentario();
                    corr=listMesage.getCorreo();
                    hor = listMesage.getHora();
                    fech = listMesage.getFecha();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                    String date= df2.format(Calendar.getInstance().getTime());

                    Date fechai = null; Date fechaf = null;

                    try {
                        fechai = simpleDateFormat.parse(fech);
                        fechaf = simpleDateFormat.parse(date);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }

                    int difer = getDif(fechai, fechaf);

                    if (difer >= 2){
                        data.child("Comentario").child(key).removeValue();
                    }else {

                        elem_message.add(new ListMesage(corr, mensa + "\n\n" + hor + " - " + fech));

                        AdapterMesage AdapterMe = new AdapterMesage(elem_message, getContext());
                        recyclerMes.setHasFixedSize(true);
                        recyclerMes.setLayoutManager(new LinearLayoutManager(getContext()));

                        recyclerMes.setAdapter(AdapterMe);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), "error: "+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listarDatosCond() {
        data.child("Comentario_cond").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                elem_message.clear();
                String corr, mensa, hor;
                String fech, cel;


                for (DataSnapshot sdata : snapshot.getChildren()){
                    String key=sdata.getKey();

                    listMesage =sdata.getValue(Mensaje.class);
                    mensa=listMesage.getComentario();
                    corr=listMesage.getCorreo();
                    hor = listMesage.getHora();
                    fech = listMesage.getFecha();
                    cel = listMesage.getCelular();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                    String date= df2.format(Calendar.getInstance().getTime());

                    Date fechai = null; Date fechaf = null;

                    try {
                        fechai = simpleDateFormat.parse(fech);
                        fechaf = simpleDateFormat.parse(date);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }

                    int difer = getDif(fechai, fechaf);

                    if (difer >= 2){
                        data.child("Comentario").child(key).removeValue();
                    }else {

                        elem_message.add(new ListMesage(corr, mensa + "\n\n"+ "Contacto: "+ cel+"\n" + hor + " - " + fech));
                        AdapterMesage AdapterMe = new AdapterMesage(elem_message, getActivity());
                        recyclerMes.setHasFixedSize(true);
                        recyclerMes.setLayoutManager(new LinearLayoutManager(getActivity()));


                        recyclerMes.setAdapter(AdapterMe);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), "error: "+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getDif(Date datei, Date datef){

        long diferencia = datef.getTime() - datei.getTime();

        long segsMilli = 1000;
        long minsMilli = segsMilli * 60;
        long horasMilli = minsMilli * 60;
        long diasMilli = horasMilli * 24;

        long diasTranscurridos = diferencia / diasMilli;
        diferencia = diferencia % diasMilli;
        dias = (int) diasTranscurridos;


        return dias;
    }
}