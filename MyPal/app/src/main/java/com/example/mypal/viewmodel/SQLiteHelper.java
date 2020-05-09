package com.example.mypal.viewmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

//Registro
public class SQLiteHelper extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE datosUsuario (idUsuario int primary key, nombreUsuario TEXT, correoUsuario TEXT, telUsuario TEXT)";

    public SQLiteHelper(@Nullable registro context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
        db.execSQL("DROP TABLE IF EXISTS DatosUsuario");
        //db.execSQL();     //Dentro del parentesis: CREATE TABLE datosUsuarios con la(s) nueva(s) columna(s)
    }
}
