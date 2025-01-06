package com.kel3.geminiai.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.aigemini.R;
import com.kel3.geminiai.auth.FragmentLogin;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout activity untuk LoginActivity
        setContentView(R.layout.activity_login);

        // Tambahkan FragmentLogin sebagai default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new FragmentLogin())
                    .commit();
        }
    }
}

