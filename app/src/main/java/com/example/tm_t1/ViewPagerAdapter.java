package com.example.tm_t1;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.fragment.app.Fragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ProgresoFragment();
            case 1:
                return new UbicacionFragment();
            case 2:
                return new HoraFragment();
            default:
                throw new IllegalStateException("Posición inválida");
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Número total de pestañas
    }
}
