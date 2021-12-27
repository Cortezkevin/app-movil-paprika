package com.example.paprika;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.UserService;
import com.example.paprika.Model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private TextInputEditText email_text;
    private TextInputEditText password_text;


    List<User> listUser = new ArrayList<>();

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
        email_tran = findViewById(R.id.text_input_email);
        password_tran = findViewById(R.id.text_input_password);

        email_text = findViewById(R.id.text_edit_email);
        password_text = findViewById(R.id.text_edit_password);

        //Evento click del boton
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((email_text.getText().toString().equals(""))
                && (password_text.getText().toString().equals(""))){
                    email_tran.setError("Campo obligatorio");
                    password_tran.setError("Campo obligatio");
                }
                else if(email_text.getText().toString().equals("")){
                    email_tran.setError("Campo obligatorio");
                }else if(password_text.getText().toString().equals("")){
                    password_tran.setError("Campo obligatio");
                }else
                {
                    email_tran.setError(null);
                    password_tran.setError(null);
                    for(User u: listUser){
                        if(u.getEmail().equals(email_text.getText().toString())
                            && u.getPassword().equals(password_text.getText().toString())){
                            //direccionamos a la otra actividad con el intent
                            //Intent("donde estas", "a donde vas")
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);

                            //iniciamos el intent - ejecutamos el intent
                            startActivity(intent);
                        }else if(u.getEmail().equals(email_text.getText().toString())
                                || u.getPassword().equals(password_text.getText().toString())){
                            Toast.makeText(getApplicationContext(), "Email o password invalidos", Toast.LENGTH_SHORT);
                        }else{
                            Toast.makeText(getApplicationContext(), "Email o password invalidos", Toast.LENGTH_SHORT);
                        }
                    }
                }

            }
        });

        getUsers();

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

    private boolean isValidData(@Nullable Editable text){ //metodo que retorna true si la cadena tiene mas de 6 caracteres
        return text != null & text.length() >= 0;
    }

    //metodo que trae la lista de usuarios
    public void getUsers(){
        UserService userService = RetrofitService.getRetrofit().create(UserService.class);
        Call<List<User>> call = userService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    //guardo la lista en la variable listUser
                    listUser = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR", "" + t.getMessage());
            }
        });
    }
}