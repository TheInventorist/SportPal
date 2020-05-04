package com.example.mypal;

import com.example.mypal.viewmodel.MainActivity;

import org.junit.Test;

import static org.junit.Assert.*;


public class MainActivityUnitTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void onStart() {
    }

    @Test
    public void onClick() {
    }

    @Test
    public void login() {
        String email = "marillion@live.cl";
        String pass = "123456789";

        MainActivity mainActivity = new MainActivity();

        try {
            mainActivity.login(email,pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}