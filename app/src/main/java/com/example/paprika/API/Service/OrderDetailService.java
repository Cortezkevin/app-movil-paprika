package com.example.paprika.API.Service;

import com.example.paprika.Model.OrderDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderDetailService {

    @POST("api/v1/orderDetails")
    Call<OrderDetails> insertOrderDetail(@Body OrderDetails orderDetails);

}
