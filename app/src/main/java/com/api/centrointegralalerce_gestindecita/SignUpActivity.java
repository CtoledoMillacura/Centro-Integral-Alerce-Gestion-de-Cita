package com.api.centrointegralalerce_gestindecita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.api.centrointegralalerce_gestindecita.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, signupName, signupSurname;
    private Button signupButton;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Inicializar FirebaseAuth y las vistas
        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupName = findViewById(R.id.signup_name); // Nombre del usuario
        signupSurname = findViewById(R.id.signup_surname); // Apellidos del usuario
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String name = signupName.getText().toString().trim();
                String surname = signupSurname.getText().toString().trim();

                // Validaciones
                if (email.isEmpty()) {
                    signupEmail.setError("El correo electrónico no puede estar vacío");
                } else if (password.isEmpty()) {
                    signupPassword.setError("La contraseña no puede estar vacía");
                } else if (name.isEmpty()) {
                    signupName.setError("El nombre no puede estar vacío");
                } else if (surname.isEmpty()) {
                    signupSurname.setError("Los apellidos no pueden estar vacíos");
                } else {
                    // Crear usuario con Firebase Authentication
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Obtener el ID del usuario creado
                                        String userId = auth.getCurrentUser().getUid();

                                        // Crear el objeto Usuario con los datos
                                        Usuario usuario = new Usuario(userId, email, name, surname);

                                        // Obtener la referencia a Firebase Realtime Database
                                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                                        // Guardar los datos del usuario en la base de datos
                                        database.child("usuarios").child(userId).setValue(usuario)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(SignUpActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                        } else {
                                                            Toast.makeText(SignUpActivity.this, "Error al guardar los datos del usuario", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Error de registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}
