package com.example.tm_t1;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Principal extends AppCompatActivity {

    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        // Establecer los márgenes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar CameraManager
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        try {
            // Obtener el id de la cámara (generalmente la cámara trasera)
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        // Botón "Acerca de..."
        ImageButton btnAcercaDe = findViewById(R.id.btnAcercaDe);
        btnAcercaDe.setOnClickListener(v -> {
            // Mostrar un AlertDialog con información "Acerca de..."
            AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
            builder.setTitle("Acerca de...");
            builder.setMessage("Desarrollado por: \nGustavo David Salinas Rojas \nMateria: \nTenología Móvil")
                    .setPositiveButton("Aceptar", (dialog, id) -> dialog.dismiss())
                    .create()
                    .show();
        });

        // Botón "Televisión"
        ImageButton btnTelevision = findViewById(R.id.btnTelevision);
        btnTelevision.setOnClickListener(v -> {
            Intent intent = new Intent(Principal.this, TelevisionActivity.class);
            startActivity(intent);
        });

        // Botón "Galería"
        ImageButton btnGaleria = findViewById(R.id.btnGaleria);
        btnGaleria.setOnClickListener(v -> {
            Intent intent = new Intent(Principal.this, GaleriaActivity.class);
            startActivity(intent);
        });

        // Botón "Cámara"
        ImageButton btnCamara = findViewById(R.id.btnCamara);
        btnCamara.setOnClickListener(v -> {
            Intent intent = new Intent(Principal.this, CamaraActivity.class);
            startActivity(intent);
        });

        // Botón "Linterna"
        ImageButton btnLinterna = findViewById(R.id.btnLinterna);
        btnLinterna.setOnClickListener(v -> {
            try {
                if (isFlashOn) {
                    // Apagar linterna
                    cameraManager.setTorchMode(cameraId, false);
                    isFlashOn = false;
                    Toast.makeText(Principal.this, "Linterna apagada", Toast.LENGTH_SHORT).show();
                } else {
                    // Encender linterna
                    cameraManager.setTorchMode(cameraId, true);
                    isFlashOn = true;
                    Toast.makeText(Principal.this, "Linterna encendida", Toast.LENGTH_SHORT).show();
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        });

        // Botón "Pestañas"
        ImageButton btnPestanias = findViewById(R.id.btnPestanias);
        btnPestanias.setOnClickListener(v -> {
            Intent intent = new Intent(Principal.this, PestaniasActivity.class);
            startActivity(intent);
        });

        // Botón "Salir"
        ImageButton btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(v -> {
            Toast.makeText(Principal.this, "Terminando Aplicación", Toast.LENGTH_SHORT).show();
            finishAffinity(); // Cierra toda la app
        });
    }
}