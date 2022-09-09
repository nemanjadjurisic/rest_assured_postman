package testsuite01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.EmployeeAddModel;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anything;

public class PostEmployeeObject {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

//    @Test
    public void postEmployeeValidateObject(){
        EmployeeAddModel employee = new EmployeeAddModel();
        employee.setName("Pera Peric");
        employee.setJob("Tester");


        given()
            .body(employee)
        .when()
            .post("/users")
        .then()
                .statusCode(201)
                .body("createdAt", Matchers.containsString("2022-08-22"))
                .contentType(ContentType.JSON);
    }

//    @Test
    public void postEmployeePrint(){
        EmployeeAddModel employee = new EmployeeAddModel();
        employee.setName("Pera Peric");
        employee.setJob("Tester");

        Response response =
        given()
                .body(employee)
        .when()
            .post("/users");
        System.out.println(response.body().asPrettyString());


    }
}
