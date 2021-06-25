package com.sidray.live_rute_10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private EditText txtnom;
    private EditText txtem;
    private EditText txtpass;
    private Button btnreg;
    private String nombre="";
    private String email="";
    private String password="";

    FirebaseAuth mauth;
    DatabaseReference data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mauth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();

        txtnom = (EditText) findViewById(R.id.txtnombre);
        txtem = (EditText) findViewById(R.id.txtcorreo);
        txtpass = (EditText) findViewById(R.id.txtcontra);
        btnreg = (Button) findViewById(R.id.btnregistro);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=txtnom.getText().toString().trim();
                email=txtem.getText().toString().trim();
                password=txtpass.getText().toString().trim();

                if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()){

                    if (password.length() >=6){
                        registrarusuario();
                    }else {
                        Toast.makeText(RegistroActivity.this, "La contraseña debe contener minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegistroActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    if (nombre.isEmpty()){
                        txtnom.setError("Campo Requerido");
                    }if (email.isEmpty()){
                        txtem.setError("Campo Requerido");
                    }if (password.isEmpty()){
                        txtpass.setError("Campo Requerido");
                    }
                }

            }
        });
    }

    private void registrarusuario(){
        mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> map= new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("correo", email);
                    map.put("contraseña", password);
                    map.put("rol", "usuario");
                    String id = mauth.getCurrentUser().getUid();
                    map.put("uid", id);



                    data.child("Conductor").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(RegistroActivity.this, MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(RegistroActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else{
                    String resul = String.valueOf(task.getException());
                    Toast.makeText(RegistroActivity.this, "No se pudo registrar"+ resul , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}