package com.example.fleming.learnresource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment fragment = new MainFragment();
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }
}
