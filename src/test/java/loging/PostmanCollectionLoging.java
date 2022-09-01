package loging;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.postman.models.PostmanCollectionModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanCollectionLoging {

    String collectionUid = "";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.getpostman.com";
        RestAssured.basePath = "/com/postman/collections_tests";
    }

    @Test
    public void postmanCollectionPostLoging(){
        Map<String, String> infoMap = new HashMap<String, String>();
        infoMap.put("name", "TestCollection03");
        infoMap.put("schema", "https://test.com");

        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("request", "");

        ArrayList<Map> itemArray = new ArrayList<>();
        itemArray.add(requestMap);

        Map<String, Object> collectionMap = new HashMap<String, Object>();
        collectionMap.put("info", infoMap);
        collectionMap.put("item", itemArray);

        PostmanCollectionModel collection = new PostmanCollectionModel();
        collection.setCollection(collectionMap);

        given()
//            .log()
//                .all()
//                .headers()
//                .body()
//                .parameters()
//                .ifValidationFails()
            .queryParam("workspace", "5d4314c7-9615-435e-8463-388bf356b0ee").header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6").body(collection)
        .when()
            .post("")
        .then()
//            .log()
//                .all()
//                .headers()
//                .body()
//                .parameters()
//                .ifValidationFails()
            .statusCode(200);
    }

    @Test(dependsOnMethods = {"postmanCollectionPostE2E"})
    public void postmanCollectionGetExtractE2E(){
        Response response =
        given()
                .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
        .when()
            .get("/" + collectionUid)
        .then()
            .extract().response();

        String name = response.path("collection.info.name");
        System.out.println("Name of the collection is: " + name);
    }

//    @Test(dependsOnMethods = {"postmanCollectionPostE2E", "postmanCollectionGetExtractE2E"})
//    public void postmanCollectionPutExtractE2E(){
//        Map<String, String> infoMap = new HashMap<String, String>();
//        infoMap.put("name", "UpdatedCollection03");
//        infoMap.put("schema", "https://updated.com");
//
//        Map<String, Object> requestMap = new HashMap<String, Object>();
//        requestMap.put("request", "");
//
//        ArrayList<Map> itemArray = new ArrayList<>();
//        itemArray.add(requestMap);
//
//        Map<String, Object> collectionMap = new HashMap<String, Object>();
//        collectionMap.put("info", infoMap);
//        collectionMap.put("item", itemArray);
//
//        PostmanCollectionModel collection = new PostmanCollectionModel();
//        collection.setCollection(collectionMap);
//
//        Response response =
//        given()
//            .queryParam("workspace", "5d4314c7-9615-435e-8463-388bf356b0ee").header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6").body(collection)
//        .when()
//            .put("/" + collectionUid)
//        .then()
//            .extract().response();
//
//        System.out.println(response.body().asPrettyString());
//
//        String name = response.path("collection.name");
//        System.out.println("Updated name is: " + name);
//    }

    @Test(dependsOnMethods = {"postmanCollectionPostE2E", "postmanCollectionGetExtractE2E", "postmanCollectionPutExtractE2E"})
    public void postmanCollectionDeleteExtract(){
        Response response =
        given()
            .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
        .when()
            .delete(collectionUid)
        .then()
            .statusCode(200).body("collection.uid", equalTo(collectionUid)).contentType(ContentType.JSON)
            .extract().response();

        System.out.println(response.body().asPrettyString());
    }

    @Test(dependsOnMethods = {"postmanCollectionPostE2E", "postmanCollectionGetExtractE2E", "postmanCollectionPutExtractE2E"})
    public void postmanCollectionDeleteExtractPath(){
        Response response =
            given()
                .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
                    .pathParam("uid", collectionUid)
            .when()
                .delete("{uid}")
            .then()
                .statusCode(200).body("collection.uid", equalTo(collectionUid)).contentType(ContentType.JSON)
                .extract().response();

        System.out.println(response.body().asPrettyString());
    }
}
