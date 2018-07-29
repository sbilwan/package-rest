package com.exercise.rest.controller;

import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
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

    @Autowired
    ProductPackageController(PackageRepository repository){
        this.packageRepository = repository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createPackage(@Valid @RequestBody ProductPackage productPackage){
        productPackage.setId(UUID.randomUUID().toString());
        ProductPackage aPackage = packageRepository.insert(productPackage);
        return aPackage.getId();
    }
}
