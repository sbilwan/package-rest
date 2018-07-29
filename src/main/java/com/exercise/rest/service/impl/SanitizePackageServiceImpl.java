package com.exercise.rest.service.impl;

import com.exercise.rest.model.Product;
import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
import com.exercise.rest.service.SanitizePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SanitizePackageServiceImpl implements SanitizePackageService {

    private PackageRepository packageRepository;

    @Autowired
    SanitizePackageServiceImpl(PackageRepository packageRepository){
        this.packageRepository = packageRepository;
    }



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

    @Override
    public Page<ProductPackage> listPackagesByPage(Pageable pageable) {
        return packageRepository.findAll(pageable);
    }
}
