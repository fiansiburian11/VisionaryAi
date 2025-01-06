
package com.kel3.geminiai.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.aigemini.R;
import com.google.android.material.tabs.TabLayout;
import com.kel3.geminiai.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tampilkan splash screen selama 2 detik
        setContentView(R.layout.splash_screen);
        int splashDuration = 2000; // Durasi splash screen (dalam milidetik)

        new Handler().postDelayed(() -> {
            // Setelah splash selesai, lanjutkan ke logic utama
            loadMainContent();
        }, splashDuration);
    }

    private void loadMainContent() {
        // Cek token login
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("user_token", null);

        if (token == null || token.isEmpty()) {
            Log.e("MainActivity", "Token login tidak ditemukan!");
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginIntent);
            finish();
            return;
        } else {
            Log.d("MainActivity", "Token login ditemukan: " + token);
        }

        // Tampilkan layout utama
        setContentView(R.layout.activity_main);

        // Inisialisasi ViewPager dan TabLayout
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Set adapter untuk ViewPager dan hubungkan dengan TabLayout
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ImageButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void logoutUser() {
        // Hapus token login
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_token");
        editor.apply();

        // Kembali ke halaman LoginActivity
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
        finish();
    }
}
