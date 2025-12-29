package api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LoadTests extends ItemApiTest {

    @Test
    void TC22_loadCreate100Ads() {

        for (int i = 0; i < 100; i++) {
            given()
                    .contentType(ContentType.JSON)
                    .body("""
                            {
                              "sellerId": %d,
                              "name": "load_item_%d",
                              "price": 10,
                              "statistics": { "likes":1, "viewCount":1, "contacts":1 }
                            }
                            """.formatted(600000 + i, i))
                    .post("/api/1/item")
                    .then()
                    .statusCode(200);
        }
    }
}
