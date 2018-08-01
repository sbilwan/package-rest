package com.exercise.rest.endpoint;

import com.exercise.rest.model.Product;
import com.exercise.rest.model.ProductPackage;
import com.exercise.rest.repository.PackageRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    protected static final String PACKAGE_END_POINT = "/packages";

    @Autowired
    protected TestRestTemplate restTemplate;

    protected ProductPackage testPackage;

    protected String testPackageId;

    @LocalServerPort
    private int port;

    private void createTestPackage() {
        testPackage = new ProductPackage();

        testPackage.setPrice(new BigDecimal(100.0));
        testPackage.setName("Dummy");
        testPackage.setProducts(createTestProducts());
    }

    private List<Product> createTestProducts(){
        List<Product> products = new ArrayList<>();
        Product x = new Product();
        Product y = new Product();
        x.setId("1");
        x.setName("X");
        x.setPrice(new BigDecimal(200.0));
        y.setId("2");
        y.setName("Y");
        y.setPrice(new BigDecimal(300.0));
        products.add(x);
        products.add(y);
        return products;
    }

    @Before
    public void setUp(){
        createTestPackage();
    }

    protected final <T, K> K postTestPackage(final String endPoint, final T object, final Class<K> primaryKeyClass) {

        final HttpEntity<T> entity = new HttpEntity<>(object);
        final ResponseEntity<K> response = restTemplate.exchange(createURLWithPort(endPoint), HttpMethod.POST, entity,
                primaryKeyClass);
        checkHttpStatus(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getHeaders().get(HttpHeaders.LOCATION) != null);
        assertTrue(response.getHeaders().get(HttpHeaders.LOCATION).size() > 0);
        final String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
        final K id = response.getBody();
        assertTrue(id != null);
        final String[] tokens = actual.split("/");
        assertTrue(tokens[tokens.length - 1].equals(id.toString()));
        if (id.getClass() == Integer.class) {
            assertTrue((Integer) id > 0);
        }
        return id;
    }

    protected final <T> T getTestPackage(final String endPoint, final Class<T> cls) {
        final ResponseEntity<T> response = restTemplate.getForEntity(endPoint, cls);
        checkHttpStatus(response.getStatusCode());
        final T entity = response.getBody();
        return entity;
    }

    protected final <T> T modifyTestPackage(final String endPoint, final Class<T> cls, final T object, final Object id) {
        final HttpEntity<T> entity = new HttpEntity<>(object);
        final ResponseEntity<T> response = restTemplate.exchange(createURLWithPort(endPoint), HttpMethod.PUT, entity,
                cls);
        checkHttpStatus(response.getStatusCode());
        return response.getBody();
    }

    protected final void deleteTestPackage(final String endPoint, final Object id) {
        restTemplate.delete(endPoint + "/" + id);
    }

    protected final void checkHttpStatus(final HttpStatus statusToCheck, final HttpStatus responseStatus) {
        assertEquals(statusToCheck, responseStatus);
    }

    private String createURLWithPort(final String uri) {
        return "http://localhost:" + port + uri;
    }

    protected final void checkHttpStatus(final HttpStatus responseStatus) {
        checkHttpStatus(HttpStatus.OK, responseStatus);
    }

    public String getTestPackageId() {
        return testPackageId;
    }

    public void setTestPackageId(String testPackageId) {
        this.testPackageId = testPackageId;
    }



}
