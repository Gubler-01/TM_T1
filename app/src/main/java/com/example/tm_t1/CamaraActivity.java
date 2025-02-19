package com.example.tm_t1;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;

public class CamaraActivity extends AppCompatActivity {

    private ImageView imagenVista;
    private Button btnTomarFoto;
    private ImageButton btnBack;

    private static final int REQUEST_IMAGE_CAPTURE = 1;  // Código de solicitud para la cámara
    private Uri imageUri;  // Variable para almacenar el Uri de la imagen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        imagenVista = findViewById(R.id.imagenVista);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);

        // Llamar al contrato de la cámara
        btnTomarFoto.setOnClickListener(v -> abrirCamara());

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(CamaraActivity.this, Principal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Elimina actividades anteriores
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });
    }

    private void abrirCamara() {
        // Preparar ContentValues para la imagen
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "imagen.jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        // Obtener el Uri para el almacenamiento
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Abrir la cámara con un Uri para guardar la foto en alta resolución
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);  // Especificar el Uri para guardar
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Recibe el resultado de la cámara (cuando se toma una foto)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                // Decodificar la imagen desde el Uri obtenido
                Bitmap imageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                // Mostrar la imagen en ImageView
                imagenVista.setImageBitmap(imageBitmap);

                // Ya no es necesario guardar manualmente la imagen en la galería
                // saveImageToGallery(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveImageToGallery(Bitmap bitmap) {
        // Prepara los valores para la imagen
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "imagen_guardada.jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        // Obtener el Uri de almacenamiento externo
        Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try (OutputStream outputStream = getContentResolver().openOutputStream(imageUri)) {
            // Escribir la imagen en el OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Toast.makeText(this, "Imagen guardada exitosamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CamaraActivity.this, Principal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Evita que se acumulen instancias de actividades
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }
}
