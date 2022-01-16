package com.example.paprika.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.ProductService;
import com.example.paprika.Model.Product;
import com.example.paprika.NavigationHost;
import com.example.paprika.Network.ImageRequester;
import com.example.paprika.ProductDetailsFragment;
import com.example.paprika.ProductEditFragment;
import com.example.paprika.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCrudAdapter extends RecyclerView.Adapter<ProductCrudAdapter.ProductViewHolder> {

    Context context;
    List<Product> productList;
    ImageRequester imageRequester;
    Fragment crudFragment;

    public ProductCrudAdapter(Context context, List<Product> productList, Fragment fragment) {
        this.context = context;
        this.productList = productList;
        this.crudFragment = fragment;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_crud_card, parent, false);

        return new ProductViewHolder(viewLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if(productList!= null && position < productList.size()){
            Product product = productList.get(position);
            holder.product_name.setText(product.getName());
            holder.product_mark.setText(product.getMark());
            holder.product_price.setText(product.getPrice().toString());
            holder.product_stock.setText(product.getStock().toString());
            holder.product_state.setText(product.getState());
            imageRequester.setImageFromUrl(holder.product_image, product.getUrl_image());


        holder.edit_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle id_product = new Bundle();
                id_product.putString("product_id_edit", product.getId_product());
                crudFragment.getParentFragmentManager().setFragmentResult("param_edit", id_product);

                ((NavigationHost) crudFragment.getActivity()).navigateTo(new ProductEditFragment(), true);
            }
        });
        holder.delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductService productService = RetrofitService.getRetrofit().create(ProductService.class);
                Call<Product> call = productService.deleteProduct(product.getId_product());
                call.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.isSuccessful()){
                            productList.remove(product);
                            Toast.makeText(crudFragment.getContext(), "PRODUCTO ELIMINADO", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(crudFragment.getContext(), "NO SE PUDO ELIMINAR", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.e("ERROR DELETE PRODUCT", t.getMessage());
                    }
                });
            }
        });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        public NetworkImageView product_image;
        public TextView product_name;
        public TextView product_mark;
        public TextView product_price;
        public TextView product_stock;
        public TextView product_state;
        public MaterialButton edit_product;
        public MaterialButton delete_product;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image = itemView.findViewById(R.id.product_image_crud);
            product_name = itemView.findViewById(R.id.product_name_crud);
            product_mark = itemView.findViewById(R.id.product_mark_crud);
            product_price = itemView.findViewById(R.id.product_price_crud);
            product_stock = itemView.findViewById(R.id.product_stock_crud);
            product_state = itemView.findViewById(R.id.product_state_crud);
            edit_product = itemView.findViewById(R.id.btn_edit_product);
            delete_product = itemView.findViewById(R.id.btn_delete_product);
        }
    }
}
