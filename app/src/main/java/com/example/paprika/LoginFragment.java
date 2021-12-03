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

    //variables
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //instanciamos o enlazamos los componentes con la vista
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);

        //mostramos las opciones del menu
        setHasOptionsMenu(true);

        //agregamos el toolbar a la vista
        setUpToolbar(view);

        //instanciamos el toggle -> icono de hamburguesa
        toggle = setUpDrawerToggle();

        //agregamos el toggle
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState(); //mostrar el toggle

        return view;
    }

    //metodo que agrega el toolbar a la vista
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

    //inflamos o agregamos el menu que creamos al menu del toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) { //creamos estado inicial del menu
        //inflamos o agregamos el menu que creamos al menu del toolbar
        menuInflater.inflate(R.menu.toolbar_menu, menu);  //inflamos el menu con el xml
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    //metodo que se ejecuta cuando seleccionamos un item del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //abrimos y cerramos el drawerLayout con el toggle(icono de hamburguesa)
        if(toggle.onOptionsItemSelected(item)){//si el icono de hamburguesa es seleccionado
            return true;        //true -> se abre el menu lateral(drawermenu - drawerlayout)
        }
        return super.onOptionsItemSelected(item);// de lo contrario false
    }
}