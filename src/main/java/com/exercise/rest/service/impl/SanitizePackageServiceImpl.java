package com.exercise.rest.service.impl;

import com.exercise.rest.model.Product;
import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.service.ISanitizePackageService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SanitizePackageServiceImpl implements ISanitizePackageService {

    @Override
    public void sanitizePackagePrice(ProductPackage productPackage) {
        if ( productPackage.getProducts() !=null ) {

            BigDecimal totalProductPrice = productPackage.getProducts().stream().map(Product::getPrice).reduce((x, y) -> x.add(y)).get();
            if (totalProductPrice.compareTo( productPackage.getPrice()) == 1) {
                productPackage.setPrice(totalProductPrice);
            }
        }
    }

    @Override
    public void applyForexRate(String currency) {

    }
}
