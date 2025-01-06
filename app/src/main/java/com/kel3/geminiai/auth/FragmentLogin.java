package com.kel3.geminiai.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kel3.geminiai.activities.MainActivity;


public class FragmentLogin extends Fragment {

    private static final String TAG = "FragmentLogin";
    private static final int RC_SIGN_IN = 9001;

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private TextView googleSignInButton; // Tombol Login with Google
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Konfigurasi Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        // Inisialisasi view
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        registerTextView = view.findViewById(R.id.registerTextView);
        googleSignInButton = view.findViewById(R.id.googleSignInButton); // Inisialisasi tombol


        // Event untuk login
        Log.d(TAG, "loginButton: " + (loginButton != null));
        loginButton.setOnClickListener(v -> loginUser());

        // Event untuk login dengan Google
        googleSignInButton.setOnClickListener(v -> signInWithGoogle());

        // Navigasi ke FragmentRegister
        registerTextView.setOnClickListener(v -> navigateToRegister());

        return view;
    }

    private void loginUser() {
        // Login menggunakan email dan password (untuk login biasa)
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

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Simpan token (UID) ke SharedPreferences
                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_token", auth.getCurrentUser().getUid());
                        editor.apply();

                        Toast.makeText(getContext(), "Login berhasil!", Toast.LENGTH_SHORT).show();

                        // Navigasi ke MainActivity
                        navigateToMainActivity();
                    } else {
                        Toast.makeText(getContext(), "Login gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signInWithGoogle() {
        // Intent untuk memulai Google Sign-In
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Login berhasil
                        if (auth.getCurrentUser() != null) {
                            // Simpan token (UID) ke SharedPreferences
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", getContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_token", auth.getCurrentUser().getUid());
                            editor.apply();

                            Toast.makeText(getContext(), "Login dengan Google berhasil!", Toast.LENGTH_SHORT).show();

                            // Navigasi ke MainActivity
                            navigateToMainActivity();
                        }
                    } else {
                        Toast.makeText(getContext(), "Login dengan Google gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void navigateToRegister() {
        // Navigasi ke FragmentRegister untuk pendaftaran pengguna baru
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FragmentRegister())
                .addToBackStack(null)
                .commit();
    }

    private void navigateToMainActivity() {
        // Navigasi ke MainActivity setelah login berhasil
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
