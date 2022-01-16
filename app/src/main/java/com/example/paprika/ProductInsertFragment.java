package com.example.paprika;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.CategoryService;
import com.example.paprika.API.Service.ProductService;
import com.example.paprika.API.Service.SupplierService;
import com.example.paprika.Model.Category;
import com.example.paprika.Model.Product;
import com.example.paprika.Model.Supplier;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInsertFragment extends Fragment {

    Spinner spinner_categories, spinner_suppliers;

    List<Category> categoryList = new ArrayList<>();
    List<Supplier> supplierList = new ArrayList<>();

    HashMap<String,String> categories = new HashMap<>();
    HashMap<String,String> suppliers = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_product_insert, parent, false);

        spinner_categories = view.findViewById(R.id.spinner_categories);
        spinner_suppliers = view.findViewById(R.id.spinner_suppliers);

        final TextInputEditText edit_text_product_name = view.findViewById(R.id.edit_text_product_name);
        final TextInputEditText edit_text_product_mark = view.findViewById(R.id.edit_text_product_mark);
        final TextInputEditText edit_text_product_description = view.findViewById(R.id.edit_text_product_description);
        final TextInputEditText edit_text_product_url = view.findViewById(R.id.edit_text_product_url_image);
        final TextInputEditText edit_text_product_expiration = view.findViewById(R.id.edit_text_product_expiration_date);
        final TextInputEditText edit_text_product_price = view.findViewById(R.id.edit_text_product_price);
        final TextInputEditText edit_text_product_stock = view.findViewById(R.id.edit_text_product_stock);

        final MaterialButton button_register_product = view.findViewById(R.id.button_register_product);

        button_register_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formato2 = new SimpleDateFormat(
                        "dd-MM-yyyy"/*, Locale.getDefault()*/);
                Date fecha_hoy = new Date();
                String fecha = formato2.format(fecha_hoy);
                Log.e("ITEM SPINNER CATEGORY", ""+spinner_categories.getSelectedItem() +" "+fecha);
                Log.e("ITEM SPINNER ID CATEGORY", ""+categories.get(spinner_categories.getSelectedItem().toString()));
                Product product = new Product();
                product.setName(edit_text_product_name.getText().toString());
                product.setMark(edit_text_product_mark.getText().toString());
                product.setDescription(edit_text_product_description.getText().toString());
                product.setUrl_image(edit_text_product_url.getText().toString());
                product.setExpiration_date(edit_text_product_expiration.getText().toString());
                product.setPrice(Double.parseDouble(edit_text_product_price.getText().toString()));
                product.setStock(Integer.parseInt(edit_text_product_stock.getText().toString()));

                product.setId_category(categories.get(spinner_categories.getSelectedItem().toString()));
                product.setId_supplier(suppliers.get(spinner_suppliers.getSelectedItem().toString()));
                registerProduct(product);
            }
        });

        getCategories();
        getSuppliers();

        return view;
    }

    public void registerProduct(Product product){
        ProductService productService = RetrofitService.getRetrofit().create(ProductService.class);
        Call<Product> call = productService.insertProduct(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "PRODUCTO REGISTRADO", Toast.LENGTH_SHORT).show();
                    ((NavigationHost)getActivity()).navigateTo(new ProductFragment(), true);
                }else{
                    Toast.makeText(getContext(), "ERROR AL REGISTRAR", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void getCategories(){
        CategoryService categoryService = RetrofitService.getRetrofit().create(CategoryService.class);
        Call<List<Category>> call = categoryService.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    for (Category c: response.body()) {
                        categories.put(c.getName().toString(), c.getId_category().toString());
                    }
                    categoryList = response.body();
                    ArrayAdapter<Category> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, categoryList);
                    spinner_categories.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void getSuppliers(){
        SupplierService supplierService = RetrofitService.getRetrofit().create(SupplierService.class);
        Call<List<Supplier>> call = supplierService.getSuppliers();
        call.enqueue(new Callback<List<Supplier>>() {
            @Override
            public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                if(response.isSuccessful()){
                    for (Supplier s: response.body()) {
                        suppliers.put(s.getName(), s.getId_supplier());
                    }

                    supplierList = response.body();
                    ArrayAdapter<Supplier> adapterSupplier = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, supplierList);
                    spinner_suppliers.setAdapter(adapterSupplier);
                }
            }

            @Override
            public void onFailure(Call<List<Supplier>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }


}
