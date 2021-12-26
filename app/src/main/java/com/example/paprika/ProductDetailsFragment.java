package com.example.paprika;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.android.volley.toolbox.NetworkImageView;
import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.ProductService;
import com.example.paprika.Model.Product;
import com.example.paprika.Network.ImageRequester;

import java.text.DateFormat;
import java.text.SimpleDateFormat;;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsFragment extends Fragment {

    private TextView id;

    private TextView name;
    private TextView price;
    private NetworkImageView image;
    private TextView stock;
    private TextView expiration_date;
    private TextView description;
    private TextView mark;


    /*private static String id_product;
    private static String name_product;
    private static Double price_product;*/


    @Override
    public void onCreate(@Nullable Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        getParentFragmentManager().setFragmentResultListener("params", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                //id_product = bundle.getString("product_id");
                /*name_product = bundle.getString("product_name");
                price_product = bundle.getDouble("product_price");*/
                //bundle.clear();
                getProductById(bundle.getString("product_id"));
                /*name.setText(name_product);
                price.setText(price_product.toString());*/
            }
        });

        id = view.findViewById(R.id.text_product_id);
        name = view.findViewById(R.id.text_product_name);
        price = view.findViewById(R.id.text_product_price);
        image = view.findViewById(R.id.image_product);
        mark = view.findViewById(R.id.text_product_mark);
        stock = view.findViewById(R.id.text_product_stock);
        expiration_date = view.findViewById(R.id.text_product_expiration_date);
        description = view.findViewById(R.id.text_product_description);


        return view;
    }

   public void getProductById(String id_product) {
        ProductService productService = RetrofitService.getRetrofit().create(ProductService.class);
        Call<Product> call = productService.getProductById(id_product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product obj = response.body();
                    id.setText(obj.getId_product());
                    name.setText(obj.getName());
                    mark.setText(obj.getMark());
                    price.setText(obj.getPrice().toString());
                    description.setText(obj.getDescription());

                    //SimpleDateFormat d = new SimpleDateFormat("dd-MM-yy");
                    Date date = obj.getExpiration_date();
                    expiration_date.setText(DateFormat.getDateInstance().format(date));
                    stock.setText(obj.getStock().toString());

                    ImageRequester imageRequester = ImageRequester.getInstance();
                    imageRequester.setImageFromUrl(image, obj.getUrl_image());
                    Log.e("RESPONSE BODY",""+response.body());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("ERROR", "" + t.getMessage());
            }
        });
    }
}
