package com.example.millonario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.millonario.dummy.DummyContent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Jugar.OnFragmentInteractionListener, Configuracion.OnFragmentInteractionListener,
        Puntuaciones.OnFragmentInteractionListener , Creditos.OnFragmentInteractionListener, Mapa.OnFragmentInteractionListener, ListaPuntuacionesFragment.OnListFragmentInteractionListener {

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment = new Creditos();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (R.id.content_main == R.id.nav_configuracion){
        }


        Fragment miFragment = null;
        Boolean fSel = false;

        if (id == R.id.nav_jugar) {
            miFragment = new Jugar();
            fSel = true;
        } else if (id == R.id.nav_puntuaciones) {
            miFragment = new Puntuaciones();
            fSel = true;
        } else if (id == R.id.nav_configuracion) {
            miFragment = new Configuracion();
            fSel = true;
        } else if (id == R.id.nav_creditos) {
            miFragment = new Creditos();
            fSel = true;
        }

        if (fSel == true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


        private void LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias",MODE_PRIVATE);

        String lonTxt = sharedPreferences.getString("longitud", "");
        String latTxt = sharedPreferences.getString("latitud", "");
        Integer njk = sharedPreferences.getInt("comodines", 1);
        String usutxt = sharedPreferences.getString("usuario", "");

        TextView lon = (TextView) findViewById(R.id.LongitudTxt);
        TextView lat = (TextView) findViewById(R.id.LatitudTxt);
        TextInputEditText usu = (TextInputEditText) findViewById(R.id.playerName);
        Spinner jk = (Spinner) findViewById(R.id.nJokers);

        lon.setText(lonTxt);
        lat.setText(latTxt);
        jk.setSelection(njk-1);
        usu.setText(usutxt);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
