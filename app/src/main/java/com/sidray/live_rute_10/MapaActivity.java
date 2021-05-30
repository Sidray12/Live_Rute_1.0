package com.sidray.live_rute_10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.data.kml.KmlLayer;
import com.sidray.live_rute_10.databinding.ActivityMapaBinding;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapaBinding binding;
    private Boolean favorito = false;
    private String rutaa;
    private String ver;
    private FloatingActionButton b_fav;
    private FloatingActionButton b_bor;
    private SharedPreferences shared;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Live_Rute_10);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rutaa = getIntent().getStringExtra("ruta");

        b_fav = findViewById(R.id.btn_favorito);
        b_bor = findViewById(R.id.btn_border);

        b_fav.setVisibility(View.INVISIBLE);
        b_bor.setVisibility(View.INVISIBLE);

        cargarPreferencias();
        //Toast.makeText(MapaActivity.this, "resul " + ver, Toast.LENGTH_SHORT).show();

        if(rutaa.equals("1")){


            if(ver.equals("1")){
                b_bor.setVisibility(View.INVISIBLE);
                b_fav.setVisibility(View.VISIBLE);

                b_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        borrarPreferencias();
                        Intent intent = new Intent(MapaActivity.this, MapaActivity.class);
                        intent.putExtra("ruta", rutaa);
                        startActivity(intent);
                        Toast.makeText(MapaActivity.this, "Eliminado de Favoritos", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }else if(ver.equals("no")){
                b_bor.setVisibility(View.VISIBLE);
                b_fav.setVisibility(View.INVISIBLE);

                b_bor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guardarPreferencias();
                        Intent intent = new Intent(MapaActivity.this, MapaActivity.class);
                        intent.putExtra("ruta", rutaa);
                        startActivity(intent);
                        Toast.makeText(MapaActivity.this, "Agragado a Favoritos", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }

        if(rutaa.equals("2")){


            if(ver.equals("2")){
                b_bor.setVisibility(View.INVISIBLE);
                b_fav.setVisibility(View.VISIBLE);

                b_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        borrarPreferencias();
                        Intent intent = new Intent(MapaActivity.this, MapaActivity.class);
                        intent.putExtra("ruta", rutaa);
                        startActivity(intent);
                        Toast.makeText(MapaActivity.this, "Eliminado de Favoritos", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }else if(ver.equals("no")){
                b_bor.setVisibility(View.VISIBLE);
                b_fav.setVisibility(View.INVISIBLE);

                b_bor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guardarPreferencias();
                        Intent intent = new Intent(MapaActivity.this, MapaActivity.class);
                        intent.putExtra("ruta", rutaa);
                        startActivity(intent);
                        Toast.makeText(MapaActivity.this, "Agragado a Favoritos", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng cucuta = new LatLng(7.911275, -72.505464);
        //mMap.addMarker(new MarkerOptions().position(cucuta).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cucuta, (float) 13.5));



        KmlLayer layer = null;

        if(rutaa.equals("1")) {

            try {
                layer = new KmlLayer(mMap, R.raw.ruta1, this);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //layer.addLayerToMap();

        }else if(rutaa.equals("2")){
            try {
                layer = new KmlLayer(mMap, R.raw.ruta2, this);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        layer.addLayerToMap();
    }

    private void guardarPreferencias(){

        if (rutaa.equals("1")) {
            SharedPreferences shared = getSharedPreferences
                    ("credenciales", Context.MODE_PRIVATE);

            String fav = rutaa;

            SharedPreferences.Editor editor = shared.edit();
            editor.putString("favorito", fav);
            editor.commit();
        }else if (rutaa.equals("2")){
            SharedPreferences shared = getSharedPreferences
                    ("credenciales", Context.MODE_PRIVATE);

            String fav = rutaa;

            SharedPreferences.Editor editor = shared.edit();
            editor.putString("favorito2", fav);
            editor.commit();
        }
    }

    private void borrarPreferencias(){

        if (rutaa.equals("1")) {
            SharedPreferences shared = getSharedPreferences
                    ("credenciales", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = shared.edit();
            editor.remove("favorito");
            editor.commit();
        }else  if (rutaa.equals("2")){
            SharedPreferences shared = getSharedPreferences
                    ("credenciales", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = shared.edit();
            editor.remove("favorito2");
            editor.commit();
        }
    }

    private void cargarPreferencias(){

        if (rutaa.equals("1")) {
            SharedPreferences shared = getSharedPreferences
                    ("credenciales", Context.MODE_PRIVATE);

            ver = shared.getString("favorito", "no");
        }else if (rutaa.equals("2")){
            SharedPreferences shared = getSharedPreferences
                    ("credenciales", Context.MODE_PRIVATE);

            ver = shared.getString("favorito2", "no");
        }
    }

    /*@RequiresApi(api = Build.VERSION_CODES.M)
    private void setFavoritoIcon(FloatingActionButton btn_f){
        int id;
        if(favorito = true) {
            id = R.drawable.ic_baseline_favorite;
        }else{
            id = R.drawable.ic_favorite_border;
        }

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favoritos, menu);

        return true;
    }

}