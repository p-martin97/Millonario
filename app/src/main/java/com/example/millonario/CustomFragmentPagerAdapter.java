package com.example.millonario;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment result = null;
        switch(position) {
            case 0:

                result = new ListaPuntuacionesFragment();
                break;
            case 1:
                result = new Mapa();

                break;
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return "Puntuaciones";
            case 1:
                return "Mapa";

        }
        return super.getPageTitle(position);
    }
    @Override
    public int getCount() { return 2; }
}
