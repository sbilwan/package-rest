package com.exercise.rest.repository;

import com.exercise.rest.model.ProductPackage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PackageRepository extends MongoRepository<ProductPackage, String> {

    @Override
    <S extends ProductPackage> S insert(S entity);
}
