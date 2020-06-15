package com.example.mypal.viewmodel;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserLocation {

    private String latitud;
    private String longitud;
    private String user;
    private String description;
    private int integrantes;
    private String nombreActividad;

    public UserLocation(String  latitud, String longitud, String user, String description, int integrantes, String nombreActividad) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.user = user;
        this.description = description;
        this.integrantes = integrantes;
        this.nombreActividad = nombreActividad;
    }

    public UserLocation() {

    }


    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(int integrantes) {
        this.integrantes = integrantes;
    }


}
