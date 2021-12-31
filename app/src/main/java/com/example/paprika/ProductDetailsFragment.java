package com.example.paprika;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.android.volley.toolbox.NetworkImageView;
import com.example.paprika.API.RetrofitService;
import com.example.paprika.API.Service.OrderDetailService;
import com.example.paprika.API.Service.ProductService;
import com.example.paprika.Model.Order;
import com.example.paprika.Model.OrderDetails;
import com.example.paprika.Model.Product;
import com.example.paprika.Network.ImageRequester;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    private TextView discount;
    private TextView subtotal_price;

    String product_id;


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


    //CARRITO
    Product product_add_shop;
    List<Product> list_car_shop = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        getParentFragmentManager().setFragmentResultListener("params", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                getProductById(bundle.getString("product_id"));
                product_id = bundle.getString("product_id");
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


        discount = view.findViewById(R.id.discount_product);
        subtotal_price = view.findViewById(R.id.subtotal_price_product);

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
                    product_add_shop = response.body();

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

    public void addCarDB(OrderDetails orderDetails){
        OrderDetailService orderDetailService = RetrofitService.getRetrofit().create(OrderDetailService.class);
        Call<OrderDetails> call = orderDetailService.insertOrderDetail(orderDetails);
        call.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                if(response.isSuccessful()){
                    Toast.makeText(new MenuActivity(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
            }
        });
    }

    public void createOrder(Order order){

    }
}
