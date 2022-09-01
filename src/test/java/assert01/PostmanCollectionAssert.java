package assert01;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostmanCollectionAssert {

    String collectionUid = "";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.getpostman.com";
        RestAssured.basePath = "/com/postman/collections_tests";
        RestAssured.rootPath = "collection.info";
    }

//    @Test
//    public void postmanCollectionPostE2E(){
//        Map<String, String> infoMap = new HashMap<String, String>();
//        infoMap.put("name", "TestCollection04");
//        infoMap.put("schema", "https://test.com");
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
//            .post("")
//        .then()
//            .statusCode(200)
////                .body("collection.name", Matchers.containsString("TestCollection04")).contentType(ContentType.JSON)
//            .extract().response();
//
//        collectionUid = response.path("collection.uid");
//        System.out.println("Collection Uid is: " + collectionUid);
//    }

    @Test(dependsOnMethods = {"postmanCollectionPostE2E"})
    public void postmanCollectionGetExtractHardAssertion(){
        given()
            .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
        .when()
            .get("/" + collectionUid)
        .then()
            .statusCode(200)
                .body("collection.info.name", equalTo("TestCollection04"))
                .body("collection.info.updatedAt", containsString("2022"));
    }

    @Test(dependsOnMethods = {"postmanCollectionPostE2E"})
    public void postmanCollectionGetExtractRootPath(){
        given()
                .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
        .when()
                .get("/" + collectionUid)
        .then()
                .statusCode(200)
                .log().body()
//                .rootPath("collection.info")
                .body("name", equalTo("TestCollection04"),
                    "updatedAt", containsString("2022"));
    }
}
