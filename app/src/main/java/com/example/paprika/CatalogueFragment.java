package com.example.paprika;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogueFragment extends Fragment {

    List<Product> productList = new ArrayList<>();
    List<Category> categoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saverInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalogue, container, false);

        getProducts(view);
        getCategories(view);

        return view;
    }

    public void getProducts(View view) {
        ProductService productService = RetrofitService.getRetrofit().create(ProductService.class);
        Call<List<Product>> call = productService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();

                RecyclerView recyclerViewProducts = view.findViewById(R.id.recycler_view_products);
                recyclerViewProducts.setHasFixedSize(true);

                recyclerViewProducts.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));

                ProductRecyclerAdapter adapterProducts = new ProductRecyclerAdapter(productList, CatalogueFragment.this);


                recyclerViewProducts.setAdapter(adapterProducts);

                int largePaddingProduct = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing);
                int smallPaddingProduct = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing_small);
                recyclerViewProducts.addItemDecoration(new ProductGridItemDecoration(largePaddingProduct, smallPaddingProduct));

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("ERROR", "" + t.getMessage());
            }
        });
    }

    public void getCategories(View view) {
        CategoryService categoryService = RetrofitService.getRetrofit().create(CategoryService.class);
        Call<List<Category>> callCategory = categoryService.getCategories();
        callCategory.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body();
                    RecyclerView recyclerViewCategories = view.findViewById(R.id.recycler_view_categories);
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
