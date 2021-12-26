package com.example.paprika.API.Apis;

import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.ProductService;

public class ProductAPI {

    public static final String URL_001 = "http://192.168.1.50:9090/";

    public static ProductService getProductService(){
        //return RetrofitService.getRetrofit(URL_001).create(ProductService.class);
        return null;
    }
}
