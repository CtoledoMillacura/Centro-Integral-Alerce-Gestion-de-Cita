package com.santotomas.centrointegralalerce_gestindecitas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private TextView signupRedirectText, forgotPasswordText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar si el usuario ya ha iniciado sesión
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        // Inicializar componentes
        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        // Iniciar sesión
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString().trim();
                String pass = loginPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    loginEmail.setError("El correo no puede estar vacío");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    loginEmail.setError("Ingrese un correo válido");
                } else if (pass.isEmpty()) {
                    loginPassword.setError("La contraseña no puede estar vacía");
                } else {
                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                    // Guardar sesión persistente en SharedPreferences
                                    SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Error al iniciar sesión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // Redirigir a la pantalla de registro
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        // Redirigir a la pantalla de recuperación de contraseña
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }
}
