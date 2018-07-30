package com.exercise.rest.service;

import com.exercise.rest.model.ProductPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface SanitizePackageService {

    void correctPackagePrice(ProductPackage productPackage);

    void applyForexRate(String currency, ProductPackage productPackage);

    Page<ProductPackage> listPackagesByPage(Pageable pageable);
}
