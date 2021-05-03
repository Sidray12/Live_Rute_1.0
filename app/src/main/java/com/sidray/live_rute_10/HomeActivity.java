package com.sidray.live_rute_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button btncerrar;

    private FirebaseAuth aut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        aut = FirebaseAuth.getInstance();

        btncerrar = findViewById(R.id.cerrar);

        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aut.signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}