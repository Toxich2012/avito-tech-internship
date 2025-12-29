package api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetStatisticsNegativeTests extends ItemApiTest {

    @Test
    void TC19_statisticsNotFound() {
        given()
                .accept(ContentType.JSON)
                .get("/api/1/statistic/NON_EXISTING_ID_123")
                .then()
                .statusCode(400);
    }


    @Test
    void TC20_invalidIdFormat() {
        given()
                .accept(ContentType.JSON)
                .get("/api/1/statistic/***")
                .then()
                .statusCode(400);
    }

    @Test
    void TC21_fieldTypesAreInteger() {
        given()
                .accept(ContentType.JSON)
                .get("/api/1/statistic/" + createdItemId)
                .then()
                .statusCode(200)
                .body("[0].likes", isA(Integer.class))
                .body("[0].viewCount", isA(Integer.class))
                .body("[0].contacts", isA(Integer.class));
    }
}
