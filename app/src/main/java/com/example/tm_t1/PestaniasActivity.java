package com.example.tm_t1;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PestaniasActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pestanias);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        ImageButton btnBack = findViewById(R.id.btnBack);

        // Configurar ViewPager con el Adapter
        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Sincronizar TabLayout con ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Progreso");
                    break;
                case 1:
                    tab.setText("Ubicación");
                    break;
                case 2:
                    tab.setText("Hora");
                    break;
            }
        }).attach();

        // Acción del botón de regreso
        btnBack.setOnClickListener(view -> finish());
    }
}
