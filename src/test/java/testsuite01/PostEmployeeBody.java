package testsuite01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostEmployeeBody {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://dummy.restapiexample.com";
        RestAssured.basePath = "/api";
    }

    @Test
    public void postEmployeeValidate(){
        given()
            .body("{" +
                    "\"name\":\"sdffgds\"," +
                    "\"salary\":\"222\"," +
                    "\"age\":\"22\"" +
                    "}")
        .when()
            .post("/v1/create")
        .then()
                .statusCode(200)
                .body("message", equalTo("Successfully! Record has been added."))
                .contentType(ContentType.JSON);
    }

    @Test
    public void postEmployeePrint(){
        Response response =
        given()
            .body("{" +
                    "\"name\":\"sdffgds\"," +
                    "\"salary\":\"222\"," +
                    "\"age\":\"22\"" +
                    "}")
        .when()
            .post("/v1/create");
        System.out.println(response.body().asPrettyString());


    }
}
