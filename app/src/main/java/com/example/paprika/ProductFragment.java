package com.example.paprika;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.ProductService;
import com.example.paprika.Adapter.ProductCrudAdapter;
import com.example.paprika.Model.Product;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {

    RecyclerView rv_products;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_product, parent, false);

        rv_products = view.findViewById(R.id.rv_products);
        final MaterialButton btn_insert_product = view.findViewById(R.id.btn_insert_product);

        getProducts();

        btn_insert_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new ProductInsertFragment(), true);
            }
        });

        return view;
    }

    public void getProducts(){

        ProductService productService = RetrofitService.getRetrofit().create(ProductService.class);
        Call<List<Product>> call = productService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    rv_products.setHasFixedSize(true);
                    rv_products.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
                    ProductCrudAdapter productCrudAdapter = new ProductCrudAdapter(getContext(),response.body(), ProductFragment.this);
                    rv_products.setAdapter(productCrudAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("ERROR GET PRODUCTS", t.getMessage());
            }
        });
    }
}
