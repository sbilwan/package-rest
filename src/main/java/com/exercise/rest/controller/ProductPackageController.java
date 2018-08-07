package com.exercise.rest.controller;

import com.exercise.rest.exception.EntityNotFoundException;
import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
import com.exercise.rest.service.SanitizePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

/**
 * Controller that intercepts all the REST calls.
 *
 */
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

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createPackage(@Valid @RequestBody ProductPackage productPackage){
        sanitizePackage.correctPackagePrice(productPackage);
        productPackage.setId(UUID.randomUUID().toString());
        ProductPackage aPackage = packageRepository.insert(productPackage);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(aPackage.getId()).toUri();
        return ResponseEntity.created(location).body(aPackage.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductPackage> updatePackage(@PathVariable String id, @Valid @RequestBody ProductPackage productPackage) {
        ProductPackage fetchedPackage = packageRepository.findOne(id);
        if (fetchedPackage == null){
            throw new EntityNotFoundException(id, "No Package exists with this id");
        }
        productPackage.setId(fetchedPackage.getId());
        ProductPackage savedPackage = packageRepository.save(productPackage);
        return ResponseEntity.ok(savedPackage);
    }
}
