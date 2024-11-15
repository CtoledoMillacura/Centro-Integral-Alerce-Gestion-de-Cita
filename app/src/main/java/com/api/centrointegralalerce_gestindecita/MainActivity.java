package com.api.centrointegralalerce_gestindecita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Inicializa el botón de cerrar sesión
        logoutButton = findViewById(R.id.logout_button);

        // Configura el listener para cerrar sesión al hacer clic en el botón
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cierra sesión
                auth.signOut();
                Toast.makeText(MainActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

                // Redirige al LoginActivity después de cerrar sesión
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // Finaliza MainActivity
            }
        });
    }
}
