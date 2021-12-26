package com.example.paprika.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.paprika.Model.Product;
import com.example.paprika.NavigationHost;
import com.example.paprika.Network.ImageRequester;
import com.example.paprika.ProductDetailsFragment;
import com.example.paprika.R;

import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    private List<Product> productlist;
    private ImageRequester imageRequester;
    private Fragment catalogueFragment;


    public ProductRecyclerAdapter(List<Product> productList, Fragment fragment){
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
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if(productlist != null && position < productlist.size()){
            Product product = productlist.get(position);
            holder.product_name.setText(product.getName());
            holder.product_mark.setText(product.getMark());
            holder.product_price.setText("S/."+product.getPrice().toString());

            imageRequester.setImageFromUrl(holder.product_image, product.getUrl_image());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle param = new Bundle();
                    param.putString("product_id", product.getId_product());
                    /*param.putString("product_name", product.getName());
                    param.putDouble("product_price", product.getPrice());*/

                    catalogueFragment.getParentFragmentManager().setFragmentResult("params", param);

                    ((NavigationHost) catalogueFragment.getActivity()).navigateTo(new ProductDetailsFragment(), true);
                    //param.clear();
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

        ProductViewHolder(View itemView){
            super(itemView);

            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_mark = itemView.findViewById(R.id.product_mark);
            product_price = itemView.findViewById(R.id.product_price);
        }
    }

}
