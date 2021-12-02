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

    private static int SPLASH_SCREEN = 5000;

    //Variables
    private ImageView image;
    private TextView logo;
    private TextView slogan;

    Animation topAnin, bottomAnin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //Animaciones -- enlazamos las instancias a las animaciones creadas
        topAnin = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnin = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        //Le añadimos las animaciones a los componentes
        image.setAnimation(topAnin);
        logo.setAnimation(bottomAnin);
        slogan.setAnimation(bottomAnin);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //((NavigationHost) MainActivity.this).navigateTo(new LoginFragment(), true);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logo, "logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREEN);

    }

}