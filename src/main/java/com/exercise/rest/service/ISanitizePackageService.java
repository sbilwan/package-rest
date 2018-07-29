package com.exercise.rest.service;

import com.exercise.rest.model.ProductPackage;

public interface ISanitizePackageService {

    void sanitizePackagePrice(ProductPackage productPackage);

    void applyForexRate(String currency);
}
