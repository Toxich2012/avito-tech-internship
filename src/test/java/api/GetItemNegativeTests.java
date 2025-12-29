package api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetItemNegativeTests extends ItemApiTest {

    @Test
    void TC11_getNonExisting() {
        given()
                .accept(ContentType.JSON)
                .get("/api/1/item/UNKNOWN_123")
                .then()
                .statusCode(400);
    }

    @Test
    void TC12_invalidIdFormat() {
        given()
                .accept(ContentType.JSON)
                .get("/api/1/item/@@@###")
                .then()
                .statusCode(400);
    }

    @Test
    void TC13_createdAtExists() {
        given()
                .get("/api/1/item/" + createdItemId)
                .then()
                .statusCode(200)
                .body("[0].createdAt", org.hamcrest.Matchers.notNullValue());
    }
}
