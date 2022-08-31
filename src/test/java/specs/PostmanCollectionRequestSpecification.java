package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import com.postman.models.PostmanCollectionModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostmanCollectionRequestSpecification {

    String collectionUid = "";
    String workspaceUid = "5d4314c7-9615-435e-8463-388bf356b0ee";
    String apiKey = "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6";
    RequestSpecBuilder requestBuilder;
    static RequestSpecification requestSpec;

    ResponseSpecBuilder responseBuilder;
    static ResponseSpecification  responseSpec;

    @BeforeClass
    public void setup() {
        requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://api.getpostman.com");
        requestBuilder.setBasePath("/collections");
        requestBuilder.addQueryParam("workspace", workspaceUid);
        requestBuilder.addHeader("X-API-Key", apiKey);
        requestSpec = requestBuilder.build();

        responseBuilder = new ResponseSpecBuilder();
//        responseBuilder.expectStatusCode(200);
//        responseBuilder.expectBody("collections[0].name", equalTo("Coctail"));
        responseSpec = responseBuilder.build();

    }

    @Test
    public void postmanCollectionPostE2E(){
        Map<String, String> infoMap = new HashMap<String, String>();
        infoMap.put("name", "TestCollection09");
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
                .spec(requestSpec).body(collection)
//            .queryParam("workspace", "5d4314c7-9615-435e-8463-388bf356b0ee").header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6").body(collection)
        .when()
            .post("")
        .then()
                .log().body();
//            .statusCode(200)
//                .body("collection.name", Matchers.containsString("TestCollection04")).contentType(ContentType.JSON)

    }

    @Test(dependsOnMethods = {"postmanCollectionPostE2E"})
    public void postmanCollectionGetExtractHardAssertion(){
        given()
            .header("X-API-Key", "PMAK-63035d0a63863d6f23a90f64-ee47e6e2af6dda75ec0f85b20b91d6eca6")
        .when()
            .get("/" + collectionUid)
        .then()
            .statusCode(200);
    }

    @Test
    public void postmanCollectionGetAllCollections(){
        given()
                .spec(requestSpec)
        .when()
                .get("")
        .then().log().all()
                .spec(responseSpec);
    }
}
