package com.exercise.rest.controller;

import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
import com.exercise.rest.service.ISanitizePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/packages")
public class ProductPackageController {

    private PackageRepository packageRepository;

    private ISanitizePackageService sanitizePackage;

    @Autowired
    ProductPackageController(PackageRepository repository, ISanitizePackageService sanitizePackage){
        this.packageRepository = repository;
        this.sanitizePackage = sanitizePackage;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createPackage(@Valid @RequestBody ProductPackage productPackage){
        sanitizePackage.sanitizePackagePrice(productPackage);
        productPackage.setId(UUID.randomUUID().toString());
        ProductPackage aPackage = packageRepository.insert(productPackage);
        return aPackage.getId();
    }
}
