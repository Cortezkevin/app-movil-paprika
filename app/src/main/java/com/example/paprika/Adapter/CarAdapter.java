package com.example.paprika.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ProductViewHolder> {

    Context context;
    TextView text_total;
    //List<Product> list_product_car_shop;
    Double total = 0.0;

    List<ProductCar> list_test_product;


    private ImageRequester imageRequester;
    //private Fragment catalogueFragment;


    /*public CarAdapter(Context context, TextView text_total, List<Product> list_product_car_shop) {
        this.context = context;
        this.text_total = text_total;
        this.list_product_car_shop = list_product_car_shop;
        imageRequester = ImageRequester.getInstance();

            for(int i = 0; i< list_product_car_shop.size(); i++){
                total = total + Double.parseDouble(""+list_product_car_shop.get(i).getPrice());
            }

        text_total.setText(""+ total);
    }
*/

    public CarAdapter(Context context, TextView text_total, List<ProductCar> list_test_product) {
        this.context = context;
        this.text_total = text_total;
        this.list_test_product = list_test_product;
        imageRequester = ImageRequester.getInstance();

        for(int i = 0; i< list_test_product.size(); i++){
            total = total + Double.parseDouble(""+(list_test_product.get(i).getPrice()*list_test_product.get(i).getAmount()));
        }

        text_total.setText(""+ total);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_shop,parent, false);

        return new ProductViewHolder(viewLayout);
    }
/*
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if(list_product_car_shop != null && position < list_product_car_shop.size()){
            Product product = list_product_car_shop.get(position);
            holder.product_name.setText(product.getName());
            holder.product_mark.setText(product.getMark());
            holder.product_price.setText("S/."+product.getPrice().toString());
            imageRequester.setImageFromUrl(holder.product_image, product.getUrl_image());

            holder.product_amount_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.product_amount.setText(""+(Integer.parseInt(holder.product_amount.getText().toString().trim()) + 1 ));
                    text_total.setText(""+(total += product.getPrice()));

                }
            });

            holder.product_amount_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int amount_product = Integer.parseInt(holder.product_amount.getText().toString().trim());
                    if(amount_product >= 1){
                        holder.product_amount.setText(""+ (amount_product - 1 ));
                        text_total.setText(""+(total -= product.getPrice()));
                    }else if(amount_product >= 0){
                        list_product_car_shop.remove(product);
                    }

                }
            });
        }
    }
*/

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if(list_test_product != null && position < list_test_product.size()){
            ProductCar product = list_test_product.get(position);
            holder.product_name.setText(product.getName());
            holder.product_mark.setText(product.getMark());
            holder.product_price.setText("S/."+product.getPrice().toString());
            imageRequester.setImageFromUrl(holder.product_image, product.getUrl_image());

            holder.product_amount_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.product_amount.setText(""+(Integer.parseInt(holder.product_amount.getText().toString().trim()) + 1 ));
                    text_total.setText(""+(total += product.getPrice()));
                    product.setAmount(product.getAmount()+1);
                }
            });

            holder.product_amount_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int amount_product = Integer.parseInt(holder.product_amount.getText().toString().trim());
                    if(amount_product >= 2){
                        holder.product_amount.setText(""+ (amount_product - 1 ));
                        text_total.setText(""+(total -= product.getPrice()));
                    }else if(amount_product >= 1){
                        list_test_product.remove(product);
                        list_test_product.size();
                    }

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list_test_product.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        public NetworkImageView product_image;
        public TextView product_name;
        public TextView product_mark;
        public TextView product_price;

        public TextView product_amount;
        public MaterialButton product_amount_increase;
        public MaterialButton product_amount_decrease;

        ProductViewHolder(View itemView){
            super(itemView);

            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_mark = itemView.findViewById(R.id.product_mark);
            product_price = itemView.findViewById(R.id.product_price);

            product_amount = itemView.findViewById(R.id.text_product_amount);
            product_amount_increase = itemView.findViewById(R.id.product_amount_increase);
            product_amount_decrease = itemView.findViewById(R.id.product_amount_decrease);
        }
    }

}

