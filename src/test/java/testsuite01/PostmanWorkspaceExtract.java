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

public class PostmanWorkspaceExtract {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.getpostman.com";
        RestAssured.basePath = "/workspaces";
    }

    @Test
    public void postmanWorkspacePostExtract(){
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
    public void postmanWorkspacePostPrintExtract(){
        Map<String, String> workspaceMap = new HashMap<>();
        workspaceMap.put("name", "TestWorkspace04");
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
    public void postmanWorkspaceGetExtract(){
        Response response =
                given()
                        .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
                .when()
                    .get("/b6745607-2203-4e81-925b-c2556d657db6");

        System.out.println(response.body().asPrettyString());
    }


    @Test
    public void postmanWorkspaceDeletePrintExtract(){
        Response response =
        given()
                .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
                .when()
                .delete("dfa67c07-684d-407e-a1fc-166601d5c029");
        System.out.println(response.body().asPrettyString());

    }

    @Test
    public void postmanWorkspaceDeleteValidateExtract(){
                given()
                        .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
                .when()
                        .delete("/6999f5d6-2966-41b2-a29a-216151145315")
                .then()
                        .statusCode(200)
                        .body("workspace.id", equalTo("6999f5d6-2966-41b2-a29a-216151145315"))
                        .contentType(ContentType.JSON);
    }
}
