package com.example.paprika.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.paprika.Model.Category;
import com.example.paprika.Network.ImageRequester;
import com.example.paprika.R;

import java.util.List;
import java.util.zip.Inflater;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private ImageRequester imageRequester;

    public CategoryRecyclerAdapter(List<Category> categoryList){
        this.categoryList = categoryList;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);

        return new CategoryViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if(categoryList != null && position < categoryList.size()){
            Category category = categoryList.get(position);
            holder.category_name.setText(category.getName());
            holder.category_description.setText(category.getDescription());
            imageRequester.setImageFromUrl(holder.category_image, category.getUrl_image());
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        public NetworkImageView category_image;
        public TextView category_name;
        public TextView category_description;

        CategoryViewHolder(View itemView){
            super(itemView);

            category_image = itemView.findViewById(R.id.category_image);
            category_description = itemView.findViewById(R.id.category_description);
            category_name = itemView.findViewById(R.id.category_name);
        }
    }
}
