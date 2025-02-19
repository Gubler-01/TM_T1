package com.example.tm_t1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GaleriaActivity extends AppCompatActivity {

    private static final int MIN_IMAGES = 10;
    private static final int MAX_IMAGES = 15;

    private Button btnOpenGallery;
    private ImageButton btnBack;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Uri> imageUris = new ArrayList<>();

    // Lanzador de actividad para seleccionar imágenes
    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    List<Uri> selectedImages = new ArrayList<>();
                    if (result.getData().getClipData() != null) {
                        int count = result.getData().getClipData().getItemCount();
                        if (count >= MIN_IMAGES && count <= MAX_IMAGES) {
                            for (int i = 0; i < count; i++) {
                                selectedImages.add(result.getData().getClipData().getItemAt(i).getUri());
                            }
                            imageUris.clear();
                            imageUris.addAll(selectedImages);
                            imageAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "Debes seleccionar entre 10 y 15 imágenes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_galeria);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(GaleriaActivity.this, Principal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Elimina actividades anteriores
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        btnOpenGallery = findViewById(R.id.btnOpenGallery);
        recyclerView = findViewById(R.id.recyclerView);

        // Configurar el RecyclerView con un GridLayout
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageAdapter = new ImageAdapter(imageUris);
        recyclerView.setAdapter(imageAdapter);

        btnOpenGallery.setOnClickListener(v -> openGallery());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GaleriaActivity.this, Principal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Evita que se acumulen instancias de actividades
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        galleryLauncher.launch(intent);
    }

}
