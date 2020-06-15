package com.example.mypal.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.ContentValues;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mypal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;



public class MapActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {
    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private DrawerLayout drawerLayout;

    public LatLng latLng;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fireBaseDataBase;
    private DatabaseReference dataBaseReference;

/*
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    */








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("mapas","se carga el OnCreate2");
        setContentView(R.layout.activity_submenu);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        fireBaseDataBase = FirebaseDatabase.getInstance();
        dataBaseReference = fireBaseDataBase.getReference();


        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

        drawerLayout.addDrawerListener(this);

        /*View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.header_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, getString(R.string.title_click),
                        Toast.LENGTH_SHORT).show();
            }
        });*/






    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);


        Log.d("mapas","prepara el mapa");
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                try {
                    Log.d("mapas","logra el try");
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    //mMap.addMarker(new MarkerOptions().position(latLng).title("hola"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    Log.d("GPS",String.valueOf(latLng));
                    Log.d("GPS",String.valueOf(latLng.latitude));
                    Log.d("GPS",String.valueOf(latLng.longitude));

                    //Toast.makeText(MapActivity.this,  "ubicacion cambio", Toast.LENGTH_LONG).show(); //Correcto
                } catch (SecurityException e) {
                    //Log.d("mapas","pasa al catch");

                    e.printStackTrace();
                }
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Log.d("mapas","continua");
        try {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

/*
    private void crearDatosGps(){
        UserLocation ul = new UserLocation();
        User u = new User();
        String user = "usuarioLider";

        ul.setLatLng(latLng);

        dataBaseReference.child("Actividades").child(user).setValue(ul);

        //Log.d("usuario","se tiene: " + user + latLng); //configurando los datos gps y cosas para crear la actividad en un punto latlng y se lo paso para que crear actividad lo suba a DB
    }

    /*public LatLng enviarCoodenadas(){
        return latLng;
    }*/

    private void crearActividad(){
        almacenarLatLng();
        Intent intent2 =new Intent(MapActivity.this, crearActividad.class);
        startActivity(intent2);
    }

    private void almacenarLatLng(){

        Log.d("GPS",String.valueOf(latLng));
        Log.d("GPS",String.valueOf(latLng.latitude));
        Log.d("GPS",String.valueOf(latLng.longitude));


        String latitud = String.valueOf(latLng.latitude);
        String longitud = String.valueOf(latLng.longitude);

        SQLiteHelper admin = new SQLiteHelper(this,"DBAdministracion",null,1);
        SQLiteDatabase basedatos = admin.getWritableDatabase();

        ContentValues datos = new ContentValues();
        datos.put("lat",latitud);
        datos.put("long",longitud);

        //db.insert("datosUsuario", null, usuario);
        basedatos.update("datosActividad",datos,"idDato = 1",null);

        basedatos.close();
        Log.d("tag","Lat y long guardados");

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int title;
        switch (menuItem.getItemId()) {
            case R.id.nav_perfil:
                Intent intent=new Intent(MapActivity.this, usuario.class);
                startActivity(intent);
                break;
            case R.id.nav_crear_actividad:
                //crearDatosGps();
                crearActividad();
                break;
            case R.id.nav_ajustes:
                Intent intent3 =new Intent(MapActivity.this, editarActividad.class);
                startActivity(intent3);
                break;
            case R.id.nav_cerrar:
                Intent intent4 =new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent4);
                break;

        }

        /*Fragment fragment = HomeContentFragment.newInstance(getString(title));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();

        setTitle(getString(title));

        drawerLayout.closeDrawer(GravityCompat.START);*/


        return true;
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
        //cambio en la posición del drawer
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        //el drawer se ha abierto completamente
        Toast.makeText(this, getString(R.string.navigation_drawer_open),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        //el drawer se ha cerrado completamente
    }

    @Override
    public void onDrawerStateChanged(int i) {
        //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
    }
}