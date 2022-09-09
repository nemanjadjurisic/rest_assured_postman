package testsuite01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class GetCoctails {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://www.thecocktaildb.com";
        RestAssured.basePath = "/api";
    }

//    @Test
    public void margaritaStatusCode(){
        given()
            .param("s", "margarita")
        .when()
            .get("/json/v1/1/search.php")
        .then()
            .statusCode(200);
    }

//    @Test
    public void filterIngrediantsResponse(){
        Response response =
        given()
            .param("i", "Bitter lemon")
            .param("i", "Kiwi liqueur")
        .when()
            .get("/json/v1/1/filter.php");

        System.out.println(response.body().asPrettyString());
    }

//    @Test
    public void filterIngrediantsResponseValiodation(){
        given()
            .param("i", "Bitter lemon")
            .param("i", "Kiwi liqueur")
        .when()
            .get("/json/v1/1/filter.php")
        .then()
            .statusCode(200)
            .body("drinks[0].strDrink", equalTo("Kiwi Lemon"))
            .contentType(ContentType.JSON);
    }
}
