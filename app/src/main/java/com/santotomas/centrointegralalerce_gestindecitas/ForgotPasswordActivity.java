package com.santotomas.centrointegralalerce_gestindecitas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Inicializar componentes
        emailEditText = findViewById(R.id.email_edit_text);
        resetPasswordButton = findViewById(R.id.reset_password_button);
        auth = FirebaseAuth.getInstance();

        // Enviar correo de restablecimiento
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    emailEditText.setError("El correo no puede estar vacío");
                    return;
                }

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Correo de restablecimiento enviado", Toast.LENGTH_SHORT).show();
                                    finish(); // Regresar al login
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "Error al enviar el correo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
