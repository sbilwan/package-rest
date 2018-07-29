package com.exercise.rest.service;

import com.exercise.rest.model.ProductPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SanitizePackageService {

    void sanitizePackagePrice(ProductPackage productPackage);

    void applyForexRate(String currency);

    Page<ProductPackage> listPackagesByPage(Pageable pageable);
}
