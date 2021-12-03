package com.example.paprika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    //inicializamos los componentes
    private MaterialButton button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //enlamos el boton con la vista
        button_login = findViewById(R.id.login_button);

        //Evento click del boton
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direccionamos a la otra actividad con el intent
                                  //Intent("donde estas", "a donde vas")
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);

                //iniciamos el intent - ejecutamos el intent
                startActivity(intent);
            }
        });

        //falta especificar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}