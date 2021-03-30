package org.acme.endpoint;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.acme.dto.ItemDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class ItemEndpointTest {

    private static final Logger LOG = LoggerFactory.getLogger(ItemEndpointTest.class.getName());

    @Test
    public void createItemSuccess() {
        
        LOG.info("createItemSuccess()");

        ItemDto request = new ItemDto();

        request.setName("Any coffee");
        request.setPrice(BigDecimal.valueOf(9.9));

        ItemDto response = 
            given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/item")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(ItemDto.class);

        assertTrue(response.getId()>0,"Item Id < 0");
        
    }

    @Test
    public void listItemSuccess() {
        
        LOG.info("listItemSuccess()");

        ItemDto[] response = 
            given()
            .when()
            .get("/item")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(ItemDto[].class);

        assertTrue(response.length!=0,"Array is empty");
        
    }

    @Test
    public void updateItemSuccess() {
        
        LOG.info("updateItemSuccess()");

        ItemDto request = new ItemDto();

        request.setId(1);
        request.setName("Any coffee updated");
        request.setPrice(BigDecimal.valueOf(8.8));

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put("/item")
            .then()
            .statusCode(200);
        
    }

    @Test
    public void deleteItemSuccess() {
        
        LOG.info("deleteItemSuccess()");

        given()
            .when()
            .get("/item?id=1")
            .then()
            .statusCode(200);
        
    }
    
}
