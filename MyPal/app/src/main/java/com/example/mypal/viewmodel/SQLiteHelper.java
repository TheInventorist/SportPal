package com.example.mypal.viewmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE Busqueda (idBusqueda INTEGER, descBusqueda TEXT)";

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Usuario nuevo => Corregir onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        //Agregar busqueda cuando se ingrese una busqueda
    }

    //Usuario antiguo => Copiar onCreate (version 2 en adelante)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Busqueda");
        db.execSQL(sqlCreate);
    }

}

