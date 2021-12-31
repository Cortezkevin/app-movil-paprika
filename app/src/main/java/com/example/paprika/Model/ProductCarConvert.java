package com.example.paprika.Model;

import java.util.ArrayList;
import java.util.List;

public class ProductCarConvert {

    public static List<ProductCar> convertProduct(List<Product> listProduct) {
        List<ProductCar> productCars = new ArrayList<>();
        for (Product p : listProduct) {
            productCars.add(new ProductCar(p));
        }

        return  productCars;
    }
}
