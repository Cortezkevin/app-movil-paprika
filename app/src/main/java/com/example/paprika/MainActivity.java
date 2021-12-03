package com.example.paprika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    //tiempo de duracion de la animacion - tiempo de retraso de la ejecucion del runnable
    private static int SPLASH_SCREEN = 5000; //--> 5000 milisegundos

    //Variables
    private ImageView image;
    private TextView logo;
    private TextView slogan;

    //variables de animacion
    Animation topAnin, bottomAnin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //Animaciones -- enlazamos las instancias a las animaciones creadas
        topAnin = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnin = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //instanciamos las variables con los componentes de la vista(layout - xml)
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        //Le añadimos las animaciones a los componentes
        image.setAnimation(topAnin);
        logo.setAnimation(bottomAnin);
        slogan.setAnimation(bottomAnin);

        //Handler (Un controlador que permite enviar y procesar)
        //Handler -> sirve para programar mensajes o ejecutables para que se ejecuten en algún momento en el futuro
        //Handler().postDelayed() -> hace que lo que pongamos en "Runnable" se ejecute después de que transcurra una cantidad de tiempo especificado
        new Handler().postDelayed(new Runnable() { //handler().postDelayed(new Runnable -> que vamos a ejecutar , long delayMillis -> (cuanto tiempo de retraso tendra esa ejecucion)))
            // al crear el runnable se define un método sin argumentos llamado run, donde colocaremos lo que queremso ejecutar
            @Override
            public void run() {
                //direccionamos al loginActivity con un intent
                                  //Intent("donde estas", "a donde vas")
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //Pair<F , S> -> contenedor que nos sirve para enviar dos objetos
                Pair[] pairs = new Pair[2];
                                                //Enviamos el componente(image) y el nombre de la transicion(logo_image) - que esta en el xml ->(android:transitionName="logo_image")
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logo, "logo_text");

                //agregamos opciones adicionales de como se debe ejecutar el activity al redireccionar
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                //iniciamos el intent - ejecutamos el intent y le agregamos la transicion(animaciones) con el options.toBundle()
                startActivity(intent, options.toBundle());
            }
        },/*tiempo de retraso*/ SPLASH_SCREEN );

    }

}