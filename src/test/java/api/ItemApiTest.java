package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemApiTest {

    public static String createdItemId;
    private static final int sellerId = 717515;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://qa-internship.avito.com";
    }

    @Test
    @Order(1)
    void TC01_createItem() {

        String responseStatus =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body("""
                    {
                      "sellerId": 717515,
                      "name": "Nothing phone 3a",
                      "price": 50000,
                      "statistics": {
                        "likes": 5,
                        "viewCount": 55,
                        "contacts": 5
                      }
                    }
                    """)
                        .when()
                        .post("/api/1/item")
                        .then()
                        .statusCode(200)
                        .body("status", notNullValue())
                        .extract()
                        .path("status");



        String createdId = responseStatus.split(" - ")[1];

        System.out.println("Создан ID: " + createdId);

        assertNotNull(createdId);
        assertTrue(createdId.length() > 10);

        this.createdItemId = createdId;
    }



    @Test
    @Order(2)
    void TC10_getItemById() {

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/1/item/" + createdItemId)
                .then()
                .statusCode(200)
                .body("[0].id", equalTo(createdItemId))
                .body("[0].sellerId", equalTo(sellerId));
    }

    @Test
    @Order(3)
    void TC14_getItemsBySeller() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/1/" + sellerId + "/item")
                .then()
                .statusCode(200)
                .body("sellerId", everyItem(equalTo(sellerId)));
    }

    @Test
    @Order(4)
    void TC18_getStatistics() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/1/statistic/" + createdItemId)
                .then()
                .statusCode(200)
                .body("[0].likes", greaterThanOrEqualTo(0))
                .body("[0].viewCount", greaterThanOrEqualTo(0))
                .body("[0].contacts", greaterThanOrEqualTo(0));
    }
}
