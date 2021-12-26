package com.example.paprika.API.Service;

import com.example.paprika.Model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("api/v1/category/list")
    Call<List<Category>> getCategories();
}
