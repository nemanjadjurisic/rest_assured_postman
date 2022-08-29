package testsuite01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.PostmanWorkspaceAddModel;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanWorkspaceCrud {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.getpostman.com";
        RestAssured.basePath = "/workspaces";
    }

    @Test
    public void postmanWorkspacePost(){
        Map<String, String> workspaceMap = new HashMap<String, String>();
        workspaceMap.put("name", "TestWorkspace02");
        workspaceMap.put("description", "Bla bla");
        workspaceMap.put("type", "personal");

        PostmanWorkspaceAddModel workspace = new PostmanWorkspaceAddModel();
        workspace.setWorkspace(workspaceMap);


        given()
            .body(workspace)
        .when()
            .post("")
        .then()
                .statusCode(201)
                .body("createdAt", Matchers.containsString("2022-08-22"))
                .contentType(ContentType.JSON);
    }

    @Test
    public void postEmployeePrint(){
        Map<String, String> workspaceMap = new HashMap<>();
        workspaceMap.put("name", "TestWorkspace02");
        workspaceMap.put("description", "Bla bla");
        workspaceMap.put("type", "personal");

        PostmanWorkspaceAddModel workspace = new PostmanWorkspaceAddModel();
        workspace.setWorkspace(workspaceMap);

        Response response =
        given()
                .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
                .body(workspace)
        .when()
            .post("");
        System.out.println(response.body().asPrettyString());


    }

    @Test
    public void getWorkspace(){
        Response response =
                given()
                        .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
                .when()
                    .get("/b6745607-2203-4e81-925b-c2556d657db6");

        System.out.println(response.body().asPrettyString());
    }


    @Test
    public void deleteWorkspacePrint(){
        Response response =
        given()
                .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
                .when()
                .delete("d2ca21ab-0ce5-4ed2-af5e-20e3f13c9a65");
        System.out.println(response.body().asPrettyString());

    }

    @Test
    public void deleteWorkspaceValidate(){
                given()
                        .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
                .when()
                        .delete("/c39d53aa-2653-40e7-8cf8-1b86127c49a4")
                .then()
                        .statusCode(200)
                        .body("workspace.id", equalTo("c39d53aa-2653-40e7-8cf8-1b86127c49a4"))
                        .contentType(ContentType.JSON);
    }
}
