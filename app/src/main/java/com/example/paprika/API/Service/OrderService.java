package com.example.paprika.API.Service;

import com.example.paprika.Model.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {

    @POST("api/v1/order")
    Call<Order> insertOrder(@Body Order order);
}
