package com.exercise.rest.service.impl;

import com.exercise.rest.apiConsumer.ForexApiConsumer;
import com.exercise.rest.exception.BusinessValidationException;
import com.exercise.rest.model.Product;
import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.model.SupportedCurrencies;
import com.exercise.rest.repository.PackageRepository;
import com.exercise.rest.service.SanitizePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Service
public class SanitizePackageServiceImpl implements SanitizePackageService {

    private PackageRepository packageRepository;

    private ForexApiConsumer forexApiConsumer;

    @Autowired
    SanitizePackageServiceImpl(PackageRepository packageRepository, ForexApiConsumer forexApiConsumer){
        this.packageRepository = packageRepository;
        this.forexApiConsumer = forexApiConsumer;
    }



    @Override
    public void correctPackagePrice(ProductPackage productPackage) {
        if ( productPackage.getProducts() !=null ) {
            BigDecimal totalProductPrice = productPackage.getProducts().stream().map(Product::getPrice).reduce((x, y) -> x.add(y)).get();
            if (totalProductPrice.compareTo( productPackage.getPrice()) == 1) {
                productPackage.setPrice(totalProductPrice);
            }
        }
    }

    @Override
    public void applyForexRate(String currency, ProductPackage productPackage) {
        BigDecimal conversionFactor = new BigDecimal(1.0);
        if( StringUtils.isEmpty(currency)) {
            // Default convert ot USD.
            conversionFactor = forexApiConsumer.latestRate("USD");
        }
        if ( SupportedCurrencies.getInstance().getCurrencies().get(currency) !=null ) {
            conversionFactor = forexApiConsumer.latestRate(currency);
        }else {
            throw new BusinessValidationException("Either not a valid currency or not supported by service");
        }
        productPackage.setPrice(productPackage.getPrice().multiply(conversionFactor) );
    }

    @Override
    public Page<ProductPackage> listPackagesByPage(Pageable pageable) {
        return packageRepository.findAll(pageable);
    }
}
