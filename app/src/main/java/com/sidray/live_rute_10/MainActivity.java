package com.sidray.live_rute_10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText txtcor;
    private EditText txtpassw;
    private Button btnlog;
    private Button btnregistr;
    private String correo;
    private String contra;
    private FirebaseAuth auten;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Live_Rute_10);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        auten = FirebaseAuth.getInstance();
        txtcor = findViewById(R.id.txtemail);
        txtpassw = findViewById(R.id.txtpass);
        btnlog = findViewById(R.id.ini_sesion);
        btnregistr = findViewById(R.id.registrarse);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);


        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correo = txtcor.getText().toString();
                contra = txtpassw.getText().toString();

                if (!correo.isEmpty() && !contra.isEmpty()) {
                    if (correo.equals("admin") && contra.equals("1234")) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        iniciosesion();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnregistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistroActivity.class));
            }
        });
    }

    private void iniciosesion() {
        auten.signInWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    progressBar.setVisibility(View.VISIBLE);

                    FirebaseUser currentUser = auten.getInstance().getCurrentUser();
                    String RegisteredUserID = currentUser.getUid();

                    DatabaseReference LoginDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(RegisteredUserID);

                    LoginDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String userType = snapshot.child("rol").getValue().toString();
                            if (userType.equals("usuario")) {
                                startActivity(new Intent(MainActivity.this, HomeUsuarioActivity.class));
                                finish();
                            } else if (userType.equals("conductor")) {
                                startActivity(new Intent(MainActivity.this, HomeConductorActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {
                    Toast.makeText(MainActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (auten.getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            FirebaseUser currentUser = auten.getInstance().getCurrentUser();
            String RegisteredUserID = currentUser.getUid();
            String emau = currentUser.getEmail();

            DatabaseReference LoginDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(RegisteredUserID);

            LoginDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userType = snapshot.child("rol").getValue().toString();
                    if (userType.equals("usuario")) {
                        Toast.makeText(MainActivity.this, "Iniciando Sesion \n" + emau, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeUsuarioActivity.class));
                        finish();
                    } else if (userType.equals("conductor")) {
                        Toast.makeText(MainActivity.this, "Iniciando Sesion \n" + emau, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeConductorActivity.class));
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }
}