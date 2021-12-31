package com.example.paprika;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paprika.Adapter.CarAdapter;
import com.example.paprika.Adapter.ProductRecyclerAdapter;
import com.example.paprika.Model.Product;
import com.example.paprika.Model.ProductCar;
import com.example.paprika.Model.ProductCarConvert;
import com.example.paprika.R;

import java.util.List;

public class CarShopFragment extends Fragment {

    private RecyclerView recycler_car_shop;
    CarAdapter carAdapter;

    TextView text_total;
    List<Product> list_product_car_shop;


    List<ProductCar> list_test_product;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedStateInstance){
        View view = inflater.inflate(R.layout.fragment_car_shop, container, false);

        getParentFragmentManager().setFragmentResultListener("product_car_list", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                list_product_car_shop = (List<Product>) result.getSerializable("list_car_products");

                list_test_product = ProductCarConvert.convertProduct(list_product_car_shop);
                carAdapter = new CarAdapter(getContext(), text_total, list_test_product);
                recycler_car_shop.setAdapter(carAdapter);
            }
        });

        text_total = view.findViewById(R.id.text_total);
        recycler_car_shop = view.findViewById(R.id.recycler_car_shop);
        recycler_car_shop.setHasFixedSize(true);
        recycler_car_shop.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));

        return view;
    }

}

