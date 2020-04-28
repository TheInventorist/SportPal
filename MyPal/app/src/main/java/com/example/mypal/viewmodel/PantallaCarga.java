package com.example.mypal.viewmodel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.mypal.R;

public class PantallaCarga extends Activity{
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantallacarga);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(PantallaCarga.this,usuario.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }}

