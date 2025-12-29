package api;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;

public class CreateItemNegativeTests extends ItemApiTest {

    @Test
    void TC03_missingName() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "sellerId": 515515,
                          "price": 100,
                          "statistics": { "likes":1, "viewCount":1, "contacts":1 }
                        }
                        """)
                .post("/api/1/item")
                .then()
                .statusCode(400);
    }

    @Test
    void TC04_missingSellerId() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "no seller",
                          "price": 50,
                          "statistics": { "likes":1, "viewCount":1, "contacts":1 }
                        }
                        """)
                .post("/api/1/item")
                .then()
                .statusCode(400);
    }

    @Test
    void TC04_1_charactersellerId() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "sellerId": adsada,
                          "name": "no seller",
                          "price": 50,
                          "statistics": { "likes":1, "viewCount":1, "contacts":1 }
                        }
                        """)
                .post("/api/1/item")
                .then()
                .statusCode(400);
    }

    @Test
    void TC05_statisticsStringsInsteadOfNumbers() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "sellerId": 616515,
                          "name": "bad stats",
                          "price": 100,
                          "statistics": { "likes":"a", "viewCount":"b", "contacts":"c" }
                        }
                        """)
                .post("/api/1/item")
                .then()
                .statusCode(400);
    }

    @Test
    void TC06_negativePrice() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "sellerId": 717515,
                          "name": "minus",
                          "price": -10,
                          "statistics": { "likes":1, "viewCount":1, "contacts":1 }
                        }
                        """)
                .post("/api/1/item")
                .then()
                .statusCode(200);
    }

    @Disabled
    @Test
    void TC07_sellerIdOutOfRange() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "sellerId": 10,
                          "name": "bad seller",
                          "price": 10,
                          "statistics": { "likes":1, "viewCount":1, "contacts":1 }
                        }
                        """)
                .post("/api/1/item")
                .then()
                .statusCode(400);
    }
    @Disabled
    @Test
    void TC07_1_sellerNegativeId() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "sellerId": -10,
                          "name": "negative seller",
                          "price": 10,
                          "statistics": { "likes":1, "viewCount":1, "contacts":1 }
                        }
                        """)
                .post("/api/1/item")
                .then()
                .statusCode(400);
    }

    @Test
    void TC07_2_sellerBigId() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "sellerId": 99999999999999999999999,
                          "name": "big seller",
                          "price": 10,
                          "statistics": { "likes":1, "viewCount":1, "contacts":1 }
                        }
                        """)
                .post("/api/1/item")
                .then()
                .statusCode(400);
    }

    @Test
    void TC08_emptyJson() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .post("/api/1/item")
                .then()
                .statusCode(400);
    }

    @Test
    void TC09_parallelCreate() {

        for (int i = 0; i < 20; i++) {

            int seller = 300000 + new Random().nextInt(600000);

            given()
                    .contentType(ContentType.JSON)
                    .body("""
                        {
                          "sellerId": %d,
                          "name": "bulk_%d",
                          "price": 1,
                          "statistics": { "likes":2, "viewCount":2, "contacts":2 }
                        }
                        """.formatted(seller, i))
                    .post("/api/1/item")
                    .then()
                    .statusCode(200);

            try {
                Thread.sleep(60);
            } catch (InterruptedException ignored) {}
        }
    }

    @Test
    void TC16_invalidsellerId() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "sellerId": %$$%,
                          "name": "invalid seller",
                          "price": 10,
                          "statistics": { "likes":1, "viewCount":1, "contacts":1 }
                        }
                        """)
                .post("/api/1/item")
                .then()
                .statusCode(400);
    }



}
