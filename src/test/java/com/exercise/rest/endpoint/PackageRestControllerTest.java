package com.exercise.rest.endpoint;

import com.exercise.rest.PackageRestApplication;
import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertEquals(getTestPackageId(), fetchedPackage.getId());

        //Modify test bin.
        testPackage.setName("UpdateTestPackage");
        final ProductPackage modifyPackage = modifyTestPackage(PACKAGE_END_POINT + "/" + id, ProductPackage.class, testPackage, id);
        final ProductPackage fetchedModifiedPackage = getTestPackage(PACKAGE_END_POINT + "/" + modifyPackage.getId(), ProductPackage.class);
        assertEquals(" Failed to update the package", testPackage.getName(), fetchedModifiedPackage.getName());

        //Delete test bin
        deleteTestPackage(PACKAGE_END_POINT,getTestPackageId());

    }


  /* @After
   public void cleanUp() {
        testPackage.setId(getTestPackageId());
        packageRepository.delete(testPackage);
   }*/
}
