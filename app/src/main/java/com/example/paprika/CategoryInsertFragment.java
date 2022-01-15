package com.example.paprika;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.CategoryService;
import com.example.paprika.Model.Category;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryInsertFragment extends Fragment {

    TextInputLayout category_name, category_description, category_url_image;
    TextInputEditText category_name_edit, category_description_edit, category_image_edit;

    MaterialButton btn;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_category_insert, parent, false);

        category_name = view.findViewById(R.id.text_input_category_name);
        category_description = view.findViewById(R.id.text_input_category_description);
        category_url_image = view.findViewById(R.id.text_input_category_url_image);
        category_name_edit = view.findViewById(R.id.edit_text_category_name);
        category_description_edit = view.findViewById(R.id.edit_text_category_description);
        category_image_edit = view.findViewById(R.id.edit_text_category_url_image);
        btn = view.findViewById(R.id.button_register_category);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Category category = new Category();
                category.setName(category_name_edit.getText().toString());
                category.setDescription(category_description_edit.getText().toString());
                category.setUrl_image(category_image_edit.getText().toString());
                InsertCategory(category);
            }
        });

        return view;
    }


    public void InsertCategory(Category c){

        CategoryService categoryService = RetrofitService.getRetrofit().create(CategoryService.class);
        Call<Category> call = categoryService.insertCategories(c);

        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {

                if (response.isSuccessful()){

                    Toast.makeText(getContext(), "Registrado", Toast.LENGTH_SHORT).show();
                }

                else{

                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
