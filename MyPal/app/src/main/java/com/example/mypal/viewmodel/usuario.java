package com.example.mypal.viewmodel;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mypal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.*;


import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class usuario extends AppCompatActivity implements View.OnClickListener {

    private TextView mDisplayDate;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fireBaseDataBase;
    private DatabaseReference dataBaseReference;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    int TAKE_IMAGE_CODE = 10001;
    ImageView fotoDePerfil;
    EditText descripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        findViewById(R.id.btnPerfil).setOnClickListener(this);
        fotoDePerfil = findViewById(R.id.vistaImagenPerfil);
        descripcion = findViewById(R.id.campoDescripcion);


        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        fireBaseDataBase = FirebaseDatabase.getInstance();
        dataBaseReference = fireBaseDataBase.getReference();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            if(user.getPhotoUrl() != null){
                Glide.with(usuario.this).load(user.getPhotoUrl()).into(fotoDePerfil);
            }
        }

        mDisplayDate = (TextView) findViewById(R.id.campoFecha);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(usuario.this, android.R.style.Theme_DeviceDefault_Light,mDateSetListener,anio,mes,dia);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                mes = mes +1;
                String fecha = dia + "/" + mes + "/" + anio;
                mDisplayDate.setText(fecha);

            }
        };




    }
    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.btnPerfil:
                modificarDatosPerfil();
                Intent myIntent1 = new Intent(getBaseContext(), PantallaCarga.class);
                startActivity(myIntent1);
                break;
        }
    }

    private void modificarDatosPerfil() {
        String fecha = mDisplayDate.getText().toString();
        String texto = descripcion.getText().toString();
        User u = new User();
        u.setuId(mAuth.getCurrentUser().getUid().toString());
        u.setFecha(fecha);
        u.setDescripcion(texto);
        dataBaseReference.child("MasDatosUsuarios").child(u.getuId()).setValue(u);

    }

   /* public void imagenDePerfil(View view) {

        Intent intentImagenPerfil = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intentImagenPerfil.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intentImagenPerfil, TAKE_IMAGE_CODE);
        }
        Log.d("camara","el boton funciona");
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE){
            switch(requestCode){

                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    fotoDePerfil.setImageBitmap(bitmap);
                    handleUpload(bitmap);
                    Log.d("camara","Saca la fota");
                    break;
            }
        }

    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("Perfiles")
                .child(uid + ".jpeg");
        Log.d("camara","Se sube la foto");

        reference.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getDownloadUrl(reference);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                setUserProfileUrl(uri);
            }
        });
    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(usuario.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(usuario.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
            }
        });
    }




}

