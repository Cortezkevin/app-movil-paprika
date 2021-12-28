package com.example.paprika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SignUpActivity extends AppCompatActivity {

    private MaterialButton button_back;
    List<User> listUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextInputLayout text_username = findViewById(R.id.text_user_name);
        final TextInputLayout text_email = findViewById(R.id.text_user_email);
        final TextInputLayout text_password = findViewById(R.id.text_user_password);
        final TextInputLayout text_confirm_password = findViewById(R.id.text_user_confirm_password);

        final TextInputEditText username = findViewById(R.id.user_name);
        final TextInputEditText email = findViewById(R.id.user_email);
        final TextInputEditText password = findViewById(R.id.user_password);
        final TextInputEditText confirm_password = findViewById(R.id.user_password_confirm);

        MaterialButton button_register = findViewById(R.id.button_save);

        button_back = findViewById(R.id.button_back_to_login);
        getUsers();
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((username.getText().toString().equals("")) && (email.getText().toString().equals(""))
                        && (password.getText().toString().equals("")) && (confirm_password.getText().toString().equals(""))) {
                    text_username.setError("Campo obligatorio");
                    text_email.setError("Campo obligatorio");
                    text_password.setError("Campo obligatorio");
                    text_confirm_password.setError("Campo obligatorio");
                } else if (username.getText().toString().equals("")) {
                    text_username.setError("Campo obligatorio");
                } else if (email.getText().toString().equals("")) {
                    text_email.setError("Campo obligatorio");
                } else if (password.getText().toString().equals("")) {
                    text_password.setError("Campo obligatorio");
                } else if (confirm_password.getText().toString().equals("")) {
                    text_confirm_password.setError("Campo obligatorio");
                } else {
                    text_username.setError(null);
                    text_email.setError(null);
                    text_password.setError(null);
                    text_confirm_password.setError(null);
                    User user = new User();
                    user.setName(username.getText().toString());
                    user.setId_rol("R0002");
                    if (password.getText().toString().equals(confirm_password.getText().toString())) {
                        user.setImage(null);
                        for (User u : listUser) {
                            if (!(u.getEmail().equals(email.getText().toString()))
                            && !(u.getPassword().equals(password.getText().toString()))) {
                                user.setEmail(email.getText().toString());
                                user.setPassword(password.getText().toString());
                                registerUser(user);
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else if ((u.getEmail().equals(email.getText().toString()))) {
                                text_email.setError("El email ingresado ya existe");
                            }else if ((u.getPassword().equals(password.getText().toString()))) {
                                text_password.setError("El password ingresado ya existe");
                            }
                        }

                    } else {
                        Toast.makeText(SignUpActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void registerUser(User user) {
        UserService userService = RetrofitService.getRetrofit().create(UserService.class);
        Call<User> call = userService.insertUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Se registro su cuenta correctamente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void getUsers() {
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