package com.example.tm_t1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Aplicar padding por insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos de la UI
        val etUsuario = findViewById<EditText>(R.id.etMNombre)
        val etPassword = findViewById<EditText>(R.id.etMPassword)
        val btnAcceder = findViewById<Button>(R.id.btnIAcceder)
        val btnSalir = findViewById<Button>(R.id.btnISalir)

        btnAcceder.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val password = etPassword.text.toString()

            if (usuario == "TM1p25A" && password.equals("david", ignoreCase = true)) {
                Toast.makeText(this, "Acceso concedido", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Principal::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        btnSalir.setOnClickListener {
            Toast.makeText(this, "¡Hasta pronto!", Toast.LENGTH_SHORT).show()
            finish()
            System.exit(0)
        }
    }
}
