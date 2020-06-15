package com.example.mypal.viewmodel;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserLocation {

    private LatLng latLng;
    private String user;
    private String description;
    private int integrantes;
    private String nombreActividad;

    public UserLocation(LatLng latLng, String user, String description, int integrantes, String nombreActividad) {
        this.latLng = latLng;
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

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
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

    @Override
    public String toString() {
        return "UserLocation{" +
                "geoPoint=" + latLng +
                ", timeStramp='" +
                ", user=" + user +
                '}';
    }
}
