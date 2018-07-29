package com.exercise.rest.controller;

import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
import com.exercise.rest.service.ISanitizePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductPackage getPackageById(@PathVariable("id") String id, @PathVariable("cur") String currency){
        if (StringUtils.hasText(currency)){

        }
        return packageRepository.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProductPackage> deletePackage(@PathVariable String id) {
        ProductPackage aPackage = packageRepository.findOne(id);
        packageRepository.delete(aPackage);
        return ResponseEntity.ok(aPackage);
    }
}
