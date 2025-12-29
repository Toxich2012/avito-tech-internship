package api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetSellerItemsNegativeTests extends ItemApiTest {

    @Test
    void TC15_sellerHasNoItems() {
        int nonexistentSeller = 1_000_000 + new Random().nextInt(999_999);

        given()
                .accept(ContentType.JSON)
                .get("/api/1/" + nonexistentSeller + "/item")
                .then()
                .statusCode(200)
                .body("sellerId", everyItem(not(equalTo(nonexistentSeller))));
    }


    @Test
    void TC16_invalidSellerId() {
        given()
                .accept(ContentType.JSON)
                .get("/api/1/%%%/item")
                .then()
                .statusCode(400);
    }

    @Test
    void TC17_massItems() {

        int seller = (int)(System.currentTimeMillis() % 800000) + 111111;

        for (int i = 0; i < 50; i++) {
            given()
                    .contentType(ContentType.JSON)
                    .body("""
                    {
                      "sellerId": %d,
                      "name": "mass_%d",
                      "price": 10,
                      "statistics": { "likes":3, "viewCount":2, "contacts":1 }
                    }
                    """.formatted(seller, i))
                    .post("/api/1/item")
                    .then()
                    .statusCode(200);
        }
        try { Thread.sleep(1200); } catch (InterruptedException ignored) {}

        given()
                .accept(ContentType.JSON)
                .get("/api/1/" + seller + "/item")
                .then()
                .statusCode(200)
                .body("size()", org.hamcrest.Matchers.greaterThanOrEqualTo(50));
    }

}
