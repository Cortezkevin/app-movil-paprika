package com.example.paprika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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

public class MenuActivity extends AppCompatActivity implements NavigationHost{

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
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
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

}