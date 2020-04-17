<<<<<<< HEAD:MyPal/app/src/main/java/com/example/mypal/view/MainActivity.java
package com.example.mypal.view;
=======
package com.example.mypal.viewmodel;
>>>>>>> desarrollo_UI:MyPal/app/src/main/java/com/example/mypal/viewmodel/MainActivity.java

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;

import com.example.mypal.R;
<<<<<<< HEAD:MyPal/app/src/main/java/com/example/mypal/view/MainActivity.java

public class MainActivity extends AppCompatActivity {
=======
>>>>>>> desarrollo_UI:MyPal/app/src/main/java/com/example/mypal/viewmodel/MainActivity.java

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
