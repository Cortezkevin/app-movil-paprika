package com.example.paprika;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;

public class LoginFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
        setHasOptionsMenu(true);
        setUpToolbar(view);

        toggle = setUpDrawerToggle();
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); //mostrar el toggle

        return view;
    }

    private void setUpToolbar(View view) {
        toolbar = view.findViewById(R.id.app_bar); //instaciamos como toolbar al id app_bar


        //instanciamos AppCompactActivity para utilizar funsion para la actividad
        AppCompatActivity activity = (AppCompatActivity) getActivity(); //atrapá el tema que tenemos en la actividad(layout)
        if(activity != null) {
            activity.setSupportActionBar(toolbar); //asignamos la barra herramientas con el toolbar
            //pintamos el activity con el toolbar // configuramos el Action bar de nuestro activity con el toolbar
        }
    }

    public ActionBarDrawerToggle setUpDrawerToggle(){  //agregamos el toggle(icono de hamburguesa al toolbar)
        return new ActionBarDrawerToggle(new MainActivity(),
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) { //creamos estado inicial del menu
        menuInflater.inflate(R.menu.toolbar_menu, menu);  //inflamos el menu con el xml
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //abrimos y cerramos el drawerLayout con el toggle
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}