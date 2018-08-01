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
import java.math.RoundingMode;

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
            if (totalProductPrice.compareTo( productPackage.getPrice()) > 0) {
                productPackage.setPrice(totalProductPrice);
            }
            BigDecimal conversionFactor = forexApiConsumer.latestRate("USD");
            // Converting the price in EUROs.
            productPackage.setPrice(productPackage.getPrice().divide(conversionFactor, RoundingMode.HALF_UP));
        }
    }

    @Override
    public void applyForexRate(String currency, ProductPackage productPackage) {
        BigDecimal conversionFactor = new BigDecimal(1.0);
        if( StringUtils.isEmpty(currency) || "EUR".equals(currency.toUpperCase())) {
            // Default convert to USD.
            conversionFactor = forexApiConsumer.latestRate("USD");
        } else {
                String askedCurrency = currency.toUpperCase();
            if (SupportedCurrencies.getInstance().getCurrencies().get(askedCurrency) != null) {
                conversionFactor = forexApiConsumer.latestRate(askedCurrency);
            } else {
                throw new BusinessValidationException("Either not a valid currency or not supported by service", currency);
            }
        }
        productPackage.setPrice(productPackage.getPrice().multiply(conversionFactor) );
        productPackage.setPrice(productPackage.getPrice().setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public Page<ProductPackage> listPackagesByPage(Pageable pageable) {
        return packageRepository.findAll(pageable);
    }
}
