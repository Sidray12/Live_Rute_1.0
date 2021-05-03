package com.sidray.live_rute_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeConductorActivity extends AppCompatActivity {

    private Button btncerrar;

    private FirebaseAuth aut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_conductor);

        aut = FirebaseAuth.getInstance();

        btncerrar = findViewById(R.id.btcerra);

        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aut.signOut();
                startActivity(new Intent(HomeConductorActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}