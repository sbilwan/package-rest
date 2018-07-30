package com.exercise.rest.controller;

import com.exercise.rest.exception.EntityNotFoundException;
import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
import com.exercise.rest.service.SanitizePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        sanitizePackage.correctPackagePrice(productPackage);
        productPackage.setId(UUID.randomUUID().toString());
        ProductPackage aPackage = packageRepository.insert(productPackage);
        return ResponseEntity.ok(aPackage.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductPackage getPackageById(@PathVariable("id") String id, @RequestParam(value ="cur", required = false) String currency){
        ProductPackage fetchedPackage = packageRepository.findOne(id);
        if( fetchedPackage == null ){
            throw new EntityNotFoundException(id, "No Package exists with this id");
        }
        sanitizePackage.applyForexRate(currency, fetchedPackage);
        return fetchedPackage;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProductPackage> deletePackage(@PathVariable String id) {
        ProductPackage fetchedPackage = packageRepository.findOne(id);
        if (fetchedPackage == null){
            throw new EntityNotFoundException(id, "No Package exists with this id");
        }
        packageRepository.delete(fetchedPackage);
        return ResponseEntity.ok(fetchedPackage);
    }
}
