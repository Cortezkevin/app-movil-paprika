package com.example.paprika.Adapter;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.paprika.CarShopFragment;
import com.example.paprika.Model.Product;
import com.example.paprika.NavigationHost;
import com.example.paprika.Network.ImageRequester;
import com.example.paprika.ProductDetailsFragment;
import com.example.paprika.R;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    Context context;
    TextView text_cant_products;
    MaterialButton show_car_shop;
    List<Product> car_shop_list;

    private List<Product> productlist;
    private ImageRequester imageRequester;
    private Fragment catalogueFragment;


    public ProductRecyclerAdapter(Context context, TextView text_cant_products, MaterialButton show_car_shop, List<Product> car_shop_list, List<Product> productList, Fragment fragment){
        this.context = context;
        this.text_cant_products =  text_cant_products;
        this.show_car_shop = show_car_shop;
        this.car_shop_list = car_shop_list;
        this.productlist = productList;
        this.catalogueFragment = fragment;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent, false);

        return new ProductViewHolder(viewLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        if(productlist != null && position < productlist.size()){
            Product product = productlist.get(position);
            holder.product_name.setText(product.getName());
            holder.product_mark.setText(product.getMark());
            holder.product_price.setText("S/."+product.getPrice().toString());
            holder.add_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(holder.add_product.isChecked() == true){
                        int cantProducts = Integer.parseInt(text_cant_products.getText().toString().trim()) + 1;
                        text_cant_products.setText(""+cantProducts);
                        car_shop_list.add(product);
                    }else if(holder.add_product.isChecked() == false){
                        text_cant_products.setText(""+(Integer.parseInt(text_cant_products.getText().toString().trim())-1));
                        car_shop_list.remove(product);
                    }
                }
            });

            imageRequester.setImageFromUrl(holder.product_image, product.getUrl_image());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle param = new Bundle();
                    param.putString("product_id", product.getId_product());

                    catalogueFragment.getParentFragmentManager().setFragmentResult("params", param);

                    ((NavigationHost) catalogueFragment.getActivity()).hideShowFragment(catalogueFragment, new ProductDetailsFragment(), "PRODUCT_DETAILS");
                }
            });

            show_car_shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle product_car_param = new Bundle();
                    product_car_param.putSerializable("list_car_products", (Serializable) car_shop_list);

                    catalogueFragment.getParentFragmentManager().setFragmentResult("product_car_list", product_car_param);

                    ((NavigationHost) catalogueFragment.getActivity()).hideShowFragment(catalogueFragment, new CarShopFragment(), "CAR_SHOP");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        public NetworkImageView product_image;
        public TextView product_name;
        public TextView product_mark;
        public TextView product_price;
        public TextView product_discount;
        public CheckBox add_product;

        ProductViewHolder(View itemView){
            super(itemView);

            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_mark = itemView.findViewById(R.id.product_mark);
            product_price = itemView.findViewById(R.id.product_price);

            add_product = itemView.findViewById(R.id.add_product);
        }
    }

}
