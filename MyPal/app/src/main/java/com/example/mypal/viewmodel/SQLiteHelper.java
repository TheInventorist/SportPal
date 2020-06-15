package com.example.mypal.viewmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE datosUsuario (idUsuario int primary key, nombreUsuario TEXT, correoUsuario TEXT, telUsuario TEXT, fecnacUsuario TEXT, descUsuario TEXT)";
    String sqlLatCreate = "CREATE TABLE datosActividad (idDato int primary key, lat TEXT, long TEXT)";

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Usuario nuevo => Corregir onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        db.execSQL(sqlLatCreate);
        db.execSQL("INSERT INTO datosActividad (idDato,lat,long) VALUES (1, 0, 0)");
        //Agregar busqueda cuando se ingrese una busqueda
    }

    //Usuario antiguo => Copiar onCreate (version 2 en adelante)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Busqueda");
        db.execSQL(sqlCreate);
    }

}

