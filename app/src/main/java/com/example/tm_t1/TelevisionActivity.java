package com.example.tm_t1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;
import android.widget.MediaController;
import androidx.appcompat.app.AppCompatActivity;

public class TelevisionActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button btnPlayVideo1, btnPlayVideo2, btnPlayVideo3;
    private MediaController mediaController;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_television);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(TelevisionActivity.this, Principal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Elimina actividades anteriores
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });

        // Referencias a los elementos de la UI
        videoView = findViewById(R.id.videoView);
        btnPlayVideo1 = findViewById(R.id.btnPlayVideo1);
        btnPlayVideo2 = findViewById(R.id.btnPlayVideo2);
        btnPlayVideo3 = findViewById(R.id.btnPlayVideo3);

        // Crear y asignar el MediaController
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);  // Anclarlo al VideoView
        videoView.setMediaController(mediaController); // Asignarlo al VideoView

        // Asignar eventos a los botones
        btnPlayVideo1.setOnClickListener(v -> playVideo("android.resource://" + getPackageName() + "/" + R.raw.video1));
        btnPlayVideo2.setOnClickListener(v -> playVideo("android.resource://" + getPackageName() + "/" + R.raw.video2));
        btnPlayVideo3.setOnClickListener(v -> playVideo("android.resource://" + getPackageName() + "/" + R.raw.video3));
    }

    // Método para reproducir el video
    private void playVideo(String videoPath) {
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TelevisionActivity.this, Principal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Evita que se acumulen instancias de actividades
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }
}