package com.example.paprika.API.Service;

import com.example.paprika.Model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryService {

    @GET("api/v1/category/list")
    Call<List<Category>> getCategories();

    @POST("api/v1/category/insert")
    Call<Category> insertCategories(@Body Category c);

}
