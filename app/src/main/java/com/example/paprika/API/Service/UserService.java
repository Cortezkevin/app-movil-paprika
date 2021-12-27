package com.example.paprika.API.Service;

import com.example.paprika.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("api/v1/user/list")
    Call<List<User>> getUsers();

    @GET("api/v1/user/findById/{id}")
    Call<User> getUserById(@Path("id") String id);
}
