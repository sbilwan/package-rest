package com.exercise.rest.service;

import com.exercise.rest.model.ProductPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The service massages the package before either storing the package to the DB or before sending them to response.
 *
 */
public interface SanitizePackageService {

    /**
     * Update the package price before storing in the DB>
     *
     * @param productPackage
     */
    void correctPackagePrice(ProductPackage productPackage);

    /**
     * Update the price in the currency that is asked.
     *
     * @param currency
     * @param productPackage
     */
    void applyForexRate(String currency, ProductPackage productPackage);

    /**
     * Display packages page wise.
     *
     * @param pageable
     * @return
     */
    Page<ProductPackage> listPackagesByPage(Pageable pageable);
}
