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
import androidx.fragment.app.FragmentResultListener;

import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.CategoryService;
import com.example.paprika.API.Service.ProductService;
import com.example.paprika.API.Service.SupplierService;
import com.example.paprika.Model.Category;
import com.example.paprika.Model.Product;
import com.example.paprika.Model.Supplier;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductEditFragment extends Fragment {

    TextInputLayout il_name_edit, il_mark_edit, il_description_edit,
            il_url_image_edit,il_expiration_date_edit,il_price_edit,il_stock_edit;
    TextInputEditText et_name_edit, et_mark_edit, et_description_edit,
            et_url_image_edit,et_expiration_date_edit,et_price_edit,et_stock_edit;

    MaterialButton btn_edit, btn_cancel;

    Spinner sp_categories, sp_suppliers;

    String product_id;

    List<Category> categoryList = new ArrayList<>();
    List<Supplier> supplierList = new ArrayList<>();

    HashMap<String,String> categories = new HashMap<>();
    HashMap<String,Integer> categories_positions = new HashMap<>();
    HashMap<String,String> suppliers = new HashMap<>();
    HashMap<String,Integer> suppliers_positions = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_product_edit, parent, false);

        getParentFragmentManager().setFragmentResultListener("param_edit", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                product_id = bundle.getString("product_id_edit");
                getProductById(bundle.getString("product_id_edit"));

                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Product product = new Product();
                        product.setName(et_name_edit.getText().toString());
                        product.setMark(et_mark_edit.getText().toString());
                        product.setDescription(et_description_edit.getText().toString());
                        product.setUrl_image(et_url_image_edit.getText().toString());
                        product.setExpiration_date(et_expiration_date_edit.getText().toString());
                        product.setPrice(Double.parseDouble(et_price_edit.getText().toString()));
                        product.setStock(Integer.parseInt(et_stock_edit.getText().toString()));
                        product.setId_category(categories.get(sp_categories.getSelectedItem().toString()));
                        product.setId_supplier(suppliers.get(sp_suppliers.getSelectedItem().toString()));
                        editProduct(product, bundle.getString("product_id_edit"));
                    }
                });
            }
        });

        et_name_edit = view.findViewById(R.id.et_product_name_edit);
        et_mark_edit = view.findViewById(R.id.et_product_mark_edit);
        et_description_edit = view.findViewById(R.id.et_product_description_edit);
        et_url_image_edit = view.findViewById(R.id.et_product_url_image_edit);
        et_expiration_date_edit = view.findViewById(R.id.et_product_expiration_date_edit);
        et_price_edit = view.findViewById(R.id.et_product_price_edit);
        et_stock_edit = view.findViewById(R.id.et_product_stock_edit);

        btn_cancel = view.findViewById(R.id.button_cancel_edit);
        btn_edit = view.findViewById(R.id.button_edit_product);

        sp_categories = view.findViewById(R.id.spinner_categories_edit);
        sp_suppliers = view.findViewById(R.id.spinner_suppliers_edit);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new ProductFragment(), true);
            }
        });

        getCategories();
        getSuppliers();
        return view;
    }

    public void getProductById(String id){
        ProductService productService = RetrofitService.getRetrofit().create(ProductService.class);
        Call<Product> call = productService.getProductById(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product obj = response.body();
                    et_name_edit.setText(obj.getName());
                    et_mark_edit.setText(obj.getMark());
                    et_description_edit.setText(obj.getDescription());
                    et_url_image_edit.setText(obj.getUrl_image());
                    et_expiration_date_edit.setText(obj.getExpiration_date().toString());
                    et_price_edit.setText(obj.getPrice().toString());
                    et_stock_edit.setText(obj.getStock().toString());
                    sp_categories.setSelection(categories_positions.get(obj.getId_category()));
                    sp_suppliers.setSelection(suppliers_positions.get(obj.getId_supplier()));
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("ERROR GET PRODUCT BY ID", t.getMessage());
            }
        });
    }

    public void editProduct(Product product, String id){
        ProductService productService = RetrofitService.getRetrofit().create(ProductService.class);
        Call<Product> call = productService.updateProduct(product, id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Producto Actualizado", Toast.LENGTH_SHORT).show();
                    ((NavigationHost)getActivity()).navigateTo(new ProductFragment(), true);
                }else{
                    Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("ERROR UPDATE PRODUCT", t.getMessage());
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
                    int position = 0;
                    for (Category c: response.body()) {
                        categories.put(c.getName().toString(), c.getId_category().toString());
                        categories_positions.put(c.getId_category().toString(),position);
                        position++;
                    }
                    categoryList = response.body();
                    ArrayAdapter<Category> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, categoryList);
                    sp_categories.setAdapter(adapter);

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
                    int position = 0;
                    for (Supplier s: response.body()) {
                        suppliers.put(s.getName(), s.getId_supplier());
                        suppliers_positions.put(s.getId_supplier().toString(), position);
                        position++;
                    }

                    supplierList = response.body();
                    ArrayAdapter<Supplier> adapterSupplier = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, supplierList);
                    sp_suppliers.setAdapter(adapterSupplier);
                }
            }

            @Override
            public void onFailure(Call<List<Supplier>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }
}

