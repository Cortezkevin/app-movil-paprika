package com.example.paprika.API.Service;

import com.example.paprika.Model.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SupplierService {

    @GET("api/v1/supplier/list")
    Call<List<Supplier>> getSuppliers();
}
