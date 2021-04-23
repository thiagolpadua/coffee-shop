package org.acme.endpoint;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.acme.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class ProductEndpointTest {

    private static final Logger LOG = LoggerFactory.getLogger(ProductEndpointTest.class.getName());

    @Test
    public void createProductSuccess() {
        
        LOG.info("createProductSuccess()");

        ProductDto request = new ProductDto();

        request.setName("Any coffee");
        request.setPrice(BigDecimal.valueOf(9.9));

        ProductDto response = 
            given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/product")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(ProductDto.class);

        assertTrue(response.getId()>0,"Product Id < 0");
        
    }

    @Test
    public void listProductSuccess() {
        
        LOG.info("listProductSuccess()");

        ProductDto[] response = 
            given()
            .when()
            .get("/product")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(ProductDto[].class);

        assertTrue(response.length!=0,"Array is empty");
        
    }

    @Test
    public void updateProductSuccess() {
        
        LOG.info("updateProductSuccess()");

        ProductDto request = new ProductDto();

        request.setId(1);
        request.setName("Any coffee updated");
        request.setPrice(BigDecimal.valueOf(8.8));

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put("/product")
            .then()
            .statusCode(200);
        
    }

    @Test
    public void deleteProductSuccess() {
        
        LOG.info("deleteProductSuccess()");

        given()
            .when()
            .get("/product?id=1")
            .then()
            .statusCode(200);
        
    }
    
}
