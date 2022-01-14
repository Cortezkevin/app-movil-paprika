package com.example.paprika.API.Service;

import com.example.paprika.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductService {

    @GET("api/v1/product/list")
    Call<List<Product>> getProducts();

    @GET("api/v1/product/findById/{id}")
    Call<Product> getProductById(@Path("id") String id);

    @POST("api/v1/product/insert")
    Call<Product> insertProduct(@Body Product product);
}
