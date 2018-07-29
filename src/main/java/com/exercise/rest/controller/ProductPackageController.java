package com.exercise.rest.controller;

import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
import com.exercise.rest.service.SanitizePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/packages")
public class ProductPackageController {

    private PackageRepository packageRepository;

    private SanitizePackageService sanitizePackage;

    @Autowired
    ProductPackageController(PackageRepository repository, SanitizePackageService sanitizePackage){
        this.packageRepository = repository;
        this.sanitizePackage = sanitizePackage;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<ProductPackage> getPackages(Pageable pageable){
        return sanitizePackage.listPackagesByPage(pageable);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createPackage(@Valid @RequestBody ProductPackage productPackage){
        sanitizePackage.sanitizePackagePrice(productPackage);
        productPackage.setId(UUID.randomUUID().toString());
        ProductPackage aPackage = packageRepository.insert(productPackage);
        return ResponseEntity.ok(aPackage.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductPackage getPackageById(@PathVariable("id") String id, @RequestParam(value ="cur", required = false) String currency){
        if ( StringUtils.hasText(currency)){

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
