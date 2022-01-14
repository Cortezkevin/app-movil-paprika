package com.example.paprika;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.CategoryService;
import com.example.paprika.API.Service.ProductService;
import com.example.paprika.Adapter.CategoryGridItemDecoration;
import com.example.paprika.Adapter.CategoryRecyclerAdapter;
import com.example.paprika.Adapter.ProductGridItemDecoration;
import com.example.paprika.Adapter.ProductRecyclerAdapter;
import com.example.paprika.Model.Category;
import com.example.paprika.Model.Product;
import com.example.paprika.Model.ProductCar;
import com.example.paprika.Model.ProductCarConvert;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogueFragment extends Fragment {

    static List<Product> productList = new ArrayList<>();
    static List<ProductCar> carshopList = new ArrayList<>();

    static List<Category> categoryList = new ArrayList<>();

    TextView text_cant_products;
    MaterialButton show_car_shop;

    RecyclerView recyclerViewCategories;

    RecyclerView recyclerViewProducts;
    ProductRecyclerAdapter adapterProducts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saverInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalogue, container, false);

        text_cant_products = view.findViewById(R.id.text_cant_products);
        show_car_shop = view.findViewById(R.id.show_car_shop);

        recyclerViewProducts = view.findViewById(R.id.recycler_view_products);
        recyclerViewCategories = view.findViewById(R.id.recycler_view_categories);
        getProducts();

        getCategories();

        return view;
    }

    public void getProducts() {
        ProductService productService = RetrofitService.getRetrofit().create(ProductService.class);
        Call<List<Product>> call = productService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    List<ProductCar> productConvertList = ProductCarConvert.convertProduct(productList);
                    recyclerViewProducts.setHasFixedSize(true);

                    recyclerViewProducts.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));

                    adapterProducts = new ProductRecyclerAdapter(getContext(), text_cant_products, show_car_shop, carshopList, productConvertList, CatalogueFragment.this);


                    recyclerViewProducts.setAdapter(adapterProducts);

                    int largePaddingProduct = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing);
                    int smallPaddingProduct = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing_small);
                    recyclerViewProducts.addItemDecoration(new ProductGridItemDecoration(largePaddingProduct, smallPaddingProduct));
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("ERROR", "" + t.getMessage());
            }
        });
    }

    public void getCategories() {
        CategoryService categoryService = RetrofitService.getRetrofit().create(CategoryService.class);
        Call<List<Category>> callCategory = categoryService.getCategories();
        callCategory.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body();
                    recyclerViewCategories.setHasFixedSize(true);
                    recyclerViewCategories.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
                    CategoryRecyclerAdapter adapterCategories = new CategoryRecyclerAdapter(categoryList);
                    recyclerViewCategories.setAdapter(adapterCategories);

                    int largePaddingCategory = getResources().getDimensionPixelSize(R.dimen.category_grid_spacing);
                    int smallPaddingCategory = getResources().getDimensionPixelSize(R.dimen.category_grid_spacing_small);
                    recyclerViewCategories.addItemDecoration(new CategoryGridItemDecoration(largePaddingCategory, smallPaddingCategory));
                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("ERROR", "" + t.getMessage());
            }
        });
    }

}
