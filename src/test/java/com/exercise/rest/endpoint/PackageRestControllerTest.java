package com.exercise.rest.endpoint;

import com.exercise.rest.PackageRestApplication;
import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class PackageRestControllerTest extends BaseTest {

    @Autowired
    private PackageRepository packageRepository;


    @Test
    public void testPostEndpoint() {
        //Post test package.
        String id = postTestPackage(PACKAGE_END_POINT, testPackage, String.class);
        setTestPackageId(id);
        assertNotNull(id);

        //Get test bin.
        final ProductPackage fetchedPackage = getTestPackage(PACKAGE_END_POINT + "/" + id, ProductPackage.class);
        assertEquals(" Couldn't fetch the test bin ", getTestPackageId(), fetchedPackage.getId());

        //Modify test bin.
        testPackage.setName("UpdateTestPackage");
        final ProductPackage modifyPackage = modifyTestPackage(PACKAGE_END_POINT + "/" + id, ProductPackage.class, testPackage, id);
        final ProductPackage fetchedModifiedPackage = getTestPackage(PACKAGE_END_POINT + "/" + modifyPackage.getId(), ProductPackage.class);
        assertEquals(" Failed to update the package", testPackage.getName(), fetchedModifiedPackage.getName());

        //Delete test bin
        deleteTestPackage(PACKAGE_END_POINT,getTestPackageId());
        final ResponseEntity<ProductPackage> response = restTemplate.getForEntity(PACKAGE_END_POINT, ProductPackage.class);
        assertNull(response.getBody().getId());


    }

}
