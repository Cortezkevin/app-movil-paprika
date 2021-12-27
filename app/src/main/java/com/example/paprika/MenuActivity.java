package com.example.paprika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import java.util.zip.Inflater;

public class MenuActivity extends AppCompatActivity implements NavigationHost, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //si no hay nada en el activity
        if(savedInstanceState == null){
            // agregamos un fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CatalogueFragment())
                    .commit();
        }
        //instanciamos o enlazamos los componentes con la vista
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);
        //instanciamos el toggle -> icono de hamburguesa
        toggle = setUpDrawerToggle();

        //agregamos el toggle
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState(); //mostrar el toggle
    }


    //Metodo navigateTo implementado de la interfaz NavigationHost
    //sirve para redireccionarnos entre fagmentos
    //recibe como parametros un fragment (que fragmento vamos a mostrar) y un boolean( si se podra regresar a la vista anterior)
    @Override
    public void navigateTo(Fragment fragment, boolean addToBackStack) {
        //craemos un objeto de tipo fragmentTransaccion
        FragmentTransaction transaction = getSupportFragmentManager() //usamos el getSupportFragmentManager() para configurar la transaccion
                .beginTransaction() //iniciamos las operaciones de la transaccion que pueden ser add, replace o remove
                .replace(R.id.container, fragment); ////usamos el replace para reemplazar el fragment actual por el nuevo, y nos cambia de vista
                //replace("el contenedor donde estan los fragmentos", "el fragmento que vamos a colocar")

        // si es true se puede regresar o volver
        if(addToBackStack == true){
            transaction.addToBackStack(null); //permite que se regrese a la vista anterior
        }

        transaction.commit();//aplicamos la configuracion del transaccion
    }

    public ActionBarDrawerToggle setUpDrawerToggle(){  //agregamos el toggle(icono de hamburguesa al toolbar)
        return new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
    }

    //metodo que se ejecuta cuando seleccionamos un item del menu de barra
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //abrimos y cerramos el drawerLayout con el toggle(icono de hamburguesa)
        if(toggle.onOptionsItemSelected(item)){//si el icono de hamburguesa es seleccionado
            return true;        //true -> se abre el menu lateral(drawermenu - drawerlayout)
        }
        return super.onOptionsItemSelected(item);// de lo contrario false
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //programar cambio de fragmentos

        return false;
    }



}