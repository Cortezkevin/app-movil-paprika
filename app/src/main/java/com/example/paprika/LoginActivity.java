package com.example.paprika;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 2500; //--> 5000 milisegundos
    //inicializamos los componentes
    private MaterialButton button_login;
    private MaterialButton buttom_register;

    //variables que tendran la transicion
    private ImageView logo_image;
    private TextView logo_text;
    private TextView logo_desc;
    private TextInputLayout email_tran;
    private TextInputLayout password_tran;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //enlamos el boton con la vista
        button_login = findViewById(R.id.login_button);
        buttom_register = findViewById(R.id.register_button);

        //referenciamos los componentes
        logo_image = findViewById(R.id.logo_image);
        logo_text = findViewById(R.id.logo_name);
        logo_desc = findViewById(R.id.slogan_name);
        email_tran = findViewById(R.id.username);
        password_tran = findViewById(R.id.password);

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

        buttom_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*new Handler().post(new Runnable() {
                    @Override
                    public void run() {*/

                        Intent intent= new Intent(LoginActivity.this, SignUpActivity.class);

                        Pair[] pairs = new Pair[7];
                        pairs[0] = new Pair<View, String>(logo_image, "logo_image");
                        pairs[1] = new Pair<View, String>(logo_text, "logo_text");
                        pairs[2] = new Pair<View, String>(logo_desc, "logo_desc");
                        pairs[3] = new Pair<View, String>(logo_desc, "email_address_tran");
                        pairs[4] = new Pair<View, String>(logo_desc, "password_tran");
                        pairs[5] = new Pair<View, String>(logo_desc, "button_tran");
                        pairs[6] = new Pair<View, String>(logo_desc, "login_signup_tran");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);

                        startActivity(intent, options.toBundle());

                        }
                   // });
           // }
        });

        //falta especificar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}