package com.exercise.rest.repository;

import com.exercise.rest.model.ProductPackage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface PackageRepository extends MongoRepository<ProductPackage, String> {

    @Override
    void delete(ProductPackage entity);
}
