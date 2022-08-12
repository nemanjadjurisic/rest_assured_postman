package testsuite01;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BasicTest01 {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://www.thecocktaildb.com";
        RestAssured.basePath = "/api";
    }

    @Test
    public void margaritaStatusCode(){
        given()
            .param("s", "margarita")
        .when()
            .get("/json/v1/1/search.php")
        .then()
            .statusCode(200);
    }

    @Test
    public void filterIngrediantsResponse(){
        given()
            .param("i", "Bitter lemon")
            .param("i", "Kiwi liqueur")
        .when()
            .get("/json/v1/1/filter.php")
        .then()
            .statusCode(200);
    }
}
