package com.example.paprika;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

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

    private TextInputEditText amount;
    private MaterialButton increase;
    private MaterialButton decrease;


    private TextView discount;
    private TextView subtotal_price;


    @Override
    public void onCreate(@Nullable Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
    }
    // contador de cantidad de productos
    int amount_product = 1;
    // descuento del producto por cantidad de unidades
    double dsc = 0.0;
    // cantidad de unidades del producto para hacer el descuento
    int cant = 0;
    //precio unitario del producto
    Double price_unit_product = 0.0;
    // subtotal del pedido, precio por cantidad
    Double subtotal = 0.0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        getParentFragmentManager().setFragmentResultListener("params", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                getProductById(bundle.getString("product_id"));
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

        amount = view.findViewById(R.id.product_amount);
        increase = view.findViewById(R.id.button_increase);
        decrease = view.findViewById(R.id.button_decrease);

        discount = view.findViewById(R.id.discount_product);
        subtotal_price = view.findViewById(R.id.subtotal_price_product);

        amount.setText(amount_product + "");


        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int max_amount = Integer.parseInt(stock.getText().toString());
                if (amount_product < max_amount) {
                    amount_product += 1;
                    amount.setText(amount_product + "");

                    subtotal = subtotal + price_unit_product ;
                    subtotal_price.setText(subtotal + "");
                }else{
                    amount.setText(amount_product+"");
                }
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount_product >= 2) {
                    amount_product -= 1;
                    amount.setText(amount_product + "");

                    subtotal = subtotal - price_unit_product ;
                    subtotal_price.setText(subtotal + "");
                } else {
                    amount.setText(amount_product + "");
                }
            }
        });
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
                    price.setText("S/." + obj.getPrice().toString());

                    subtotal_price.setText(obj.getPrice().toString());
                    price_unit_product = obj.getPrice();
                    subtotal = obj.getPrice();

                    setDiscountCantProducts(obj.getPrice());

                    discount.setText("Descuento de "+Math.round(dsc*100)+"% a partir de "+ cant +" unidades");

                    description.setText(obj.getDescription());

                    Date date = obj.getExpiration_date();
                    expiration_date.setText(DateFormat.getDateInstance().format(date));

                    stock.setText(obj.getStock().toString());

                    ImageRequester imageRequester = ImageRequester.getInstance();
                    imageRequester.setImageFromUrl(image, obj.getUrl_image());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("ERROR", "" + t.getMessage());
            }
        });
    }

    public void setDiscountCantProducts(Double priceUnit){
        if (priceUnit > 1.0 && priceUnit < 8.0) {
            cant = 10;
            dsc = 0.02;
        }else if(priceUnit > 8.0 && priceUnit < 16.0){
            cant = 9;
            dsc = 0.03;
        }else if(priceUnit > 16.0 && priceUnit < 24.0) {
            cant = 8;
            dsc = 0.04;
        }
    }
}
