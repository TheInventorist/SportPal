
package com.example.mypal.viewmodel;


import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;

import com.example.mypal.R;


public class MainActivity extends AppCompatActivity {
 public EditText emailLogin;
 public EditText claveLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       emailLogin = (EditText) findViewById(R.id.emailLogin);
       claveLogin = (EditText) findViewById(R.id.claveLogin);
    }

    public void onButtonClick(View v){
        Intent myIntent = new Intent(getBaseContext(), registro.class);
        startActivity(myIntent);
    }
    public void onButtonLogin(View v){
        Intent myIntent3 = new Intent(getBaseContext(), usuario.class);
        startActivity(myIntent3);
    }
}
