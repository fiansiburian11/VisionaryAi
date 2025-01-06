package com.kel3.geminiai.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aigemini.R;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentRegister extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button registerButton;
    private TextView loginTextView;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        auth = FirebaseAuth.getInstance();

        // Inisialisasi view
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        registerButton = view.findViewById(R.id.registerButton);
        loginTextView = view.findViewById(R.id.loginTextView);

        // Event untuk register
        registerButton.setOnClickListener(v -> registerUser());

        // Navigasi ke FragmentLogin
        loginTextView.setOnClickListener(v -> navigateToLogin());

        return view;
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Email tidak boleh kosong");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password tidak boleh kosong");
            passwordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password minimal 6 karakter");
            passwordEditText.requestFocus();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                        navigateToLogin();
                    } else {
                        Toast.makeText(getContext(), "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToLogin() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FragmentLogin())
                .addToBackStack(null) // Tambahkan ke backstack agar dapat kembali
                .commit();
    }
}

