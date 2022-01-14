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
import com.example.paprika.Model.ProductCar;
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
    List<ProductCar> car_shop_list;

    private List<ProductCar> productlist;
    private ImageRequester imageRequester;
    private Fragment catalogueFragment;


    public ProductRecyclerAdapter(Context context, TextView text_cant_products, MaterialButton show_car_shop, List<ProductCar> car_shop_list, List<ProductCar> productList, Fragment fragment){
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
            ProductCar product = productlist.get(position);
            holder.product_name.setText(product.getName());
            holder.product_mark.setText(product.getMark());
            holder.product_price.setText("S/."+product.getPrice().toString());
            holder.product_amount.setText(""+product.getAmount());
            holder.product_stock.setText(""+product.getStock());
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

            holder.product_amount_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.product_amount.setText(""+(Integer.parseInt(holder.product_amount.getText().toString().trim()) + 1 ));
                    product.setAmount(product.getAmount()+1);
                }
            });

            holder.product_amount_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int amount_product = Integer.parseInt(holder.product_amount.getText().toString().trim());
                    if(amount_product >= 2){
                        holder.product_amount.setText(""+ (amount_product - 1 ));
                        product.setAmount(amount_product-1);
                    }else if(amount_product >= 1){
                        holder.product_amount.setText(""+ amount_product);
                        product.setAmount(amount_product);
                        notifyDataSetChanged(); //// actualizar el recycler
                    }

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
        public TextView product_stock;
        public CheckBox add_product;

        public TextView product_amount;
        public MaterialButton product_amount_increase;
        public MaterialButton product_amount_decrease;

        ProductViewHolder(View itemView){
            super(itemView);

            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_mark = itemView.findViewById(R.id.product_mark);
            product_price = itemView.findViewById(R.id.product_price);
            product_stock = itemView.findViewById(R.id.product_stock);

            add_product = itemView.findViewById(R.id.add_product);

            product_amount = itemView.findViewById(R.id.text_product_amount);
            product_amount_increase = itemView.findViewById(R.id.product_amount_increase);
            product_amount_decrease = itemView.findViewById(R.id.product_amount_decrease);
        }
    }

}
