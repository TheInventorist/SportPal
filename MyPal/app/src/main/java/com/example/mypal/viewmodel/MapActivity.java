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


import com.google.android.gms.maps.model.Marker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ValueEventListener;


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

    private DatabaseReference mDataBase;

    public int estadoActividad = 0;
    String nombre;
    String descrip;
    int integrantes;
    String latitud;
    String longitud;

    int clickeado;

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

        mDataBase = FirebaseDatabase.getInstance().getReference();




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);


        //Log.d("mapas","prepara el mapa");
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

/*

        Intent intent = getIntent();

        estadoActividad = intent.getIntExtra("estado",0);
        nombre = intent.getStringExtra("nombre");
        descrip = intent.getStringExtra("descrip");
        integrantes = intent.getIntExtra("integrantes", 0);
        latitud = intent.getDoubleExtra("latitud",0);
        longitud = intent.getDoubleExtra("longitud",0);


        if(estadoActividad == 1){
            estadoActividad = 0;
            LatLng activ = new LatLng(latitud,longitud);
            googleMap.addMarker(new MarkerOptions()
                    .position(activ).snippet(descrip + ", integrantes: " + integrantes)
                    .title(nombre)).showInfoWindow();
        }
        */

        mDataBase.child("Actividades").child("usuarioLider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    //UserLocation userLoc = new UserLocation(snapshot.getValue(UserLocation.class));

                    //String descripcion = userLoc.getDescription();
                    integrantes = dataSnapshot.child("integrantes").getValue(int.class);
                    latitud = dataSnapshot.child("latitud").getValue(String.class);
                    longitud = dataSnapshot.child("longitud").getValue(String.class);
                    nombre = dataSnapshot.child("nombreActividad").getValue(String.class);
                    descrip = dataSnapshot.child("description").getValue(String.class);
                    /*
                    String latitud = userLoc.getLatitud();
                    String longitud = userLoc.getLongitud();
                    String nombre = userLoc.getNombreActividad();
                    */
                }





                if(integrantes > 0){
                    //MarkerOptions markerOptions = new MarkerOptions();
                    LatLng activ = new LatLng(Double.valueOf(latitud),Double.valueOf(longitud));
                    mMap.addMarker(new MarkerOptions()
                            .position(activ).snippet(descrip + ", integrantes: " + integrantes)
                            .title(nombre)).showInfoWindow();
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            UserLocation ul = new UserLocation();

                            String user = "usuarioLider";

                            if(clickeado == 0){
                                integrantes = integrantes + 1;
                                clickeado = 1;
                            }

                            ul.setUser(user);
                            ul.setNombreActividad(nombre);
                            ul.setLatitud(latitud);
                            ul.setLongitud(longitud);
                            ul.setDescription(descrip);
                            ul.setIntegrantes(integrantes);

                            dataBaseReference.child("Actividades").child(user).setValue(ul);
                        }
                    });



                }
                else{
                    mMap.clear();
                    Log.d("GPS","se borra");
                }

                Log.d("GPS","Llegamos aqui");




                }
            //}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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
            default:
                break;
            case R.id.nav_ajustes:
                editarActividadx();
                break;
            case R.id.nav_perfil:
                usuariox();
                break;
            case R.id.nav_crear_actividad:
                //crearDatosGps();
                crearActividad();
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

    private void editarActividadx() {
        Intent intentEditar =new Intent(MapActivity.this, editarActividad.class);
        intentEditar.putExtra("integrantes",integrantes);
        intentEditar.putExtra("latitud",latitud);
        intentEditar.putExtra("longitud",longitud);
        startActivity(intentEditar);
    }

    private void usuariox() {
        Intent intent10=new Intent(MapActivity.this, usuario.class);
        startActivity(intent10);
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
        //cambio en la posición del drawer
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        //el drawer se ha abierto completamente
        //Toast.makeText(this, getString(R.string.navigation_drawer_open),
        //        Toast.LENGTH_SHORT).show();
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