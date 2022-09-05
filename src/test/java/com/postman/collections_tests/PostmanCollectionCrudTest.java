package com.postman.collections_tests;

import com.postman.common.RestUtilities;
import com.postman.constants.Path;
import com.postman.constants.Uid;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import com.postman.models.PostmanCollectionModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanCollectionCrudTest {
    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    String collectionUid = "";

    String collectionName = "Collection_" + RandomStringUtils.randomAlphanumeric(5);
    String collectionUpdated = "Collection_" + RandomStringUtils.randomAlphanumeric(5);


    @BeforeClass(groups = {"Group2"})
    public void setup() {
        reqSpec = RestUtilities.getRequestSpecification();
        reqSpec.basePath(Path.COLLECTIONS);
        resSpec = RestUtilities.getResponseSpecification();
    }

    @Test
    public void postmanCollectionCreate(){
        Map<String, String> infoMap = new HashMap<String, String>();
        infoMap.put("name", collectionName);
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

        Response response =
        given()
            .spec(reqSpec).queryParam("workspace", Uid.WORKSPACE_MYWORKSPACE).body(collection)
        .when()
            .post("")
        .then().log().all()
            .body("collection.name", equalTo(collectionName))
            .spec(resSpec)
        .extract().response();
        collectionUid = response.path("collection.uid");
        System.out.println("Collection UID is: " + collectionUid);

    }

    @Test(dependsOnMethods = {"postmanCollectionCreate"})
    public void postmanCollectionGet(){

        Response response =
        given()
            .spec(reqSpec)
        .when()
            .get(collectionUid)
        .then().log().all()
            .body("collection.info.name", equalTo(collectionName))
            .spec(resSpec)
            .extract().response();

        String name = response.path("collection.info.name");
        System.out.println("Name of the collection is: " + name);
    }

    @Test(dependsOnMethods = {"postmanCollectionCreate", "postmanCollectionGet"})
    public void postmanCollectionUpdate(){
        Map<String, String> infoMap = new HashMap<String, String>();
        infoMap.put("name", collectionUpdated);
        infoMap.put("schema", "https://updated.com");

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
            .spec(reqSpec).body(collection)
        .when()
            .put(collectionUid)
        .then().log().all()
            .body("collection.name", equalTo(collectionUpdated))
            .spec(resSpec);
    }

    @Test(dependsOnMethods = {"postmanCollectionCreate", "postmanCollectionGet", "postmanCollectionUpdate"})
    public void postmanCollectionGetUpdated(){
        Response response =
        given()
            .spec(reqSpec)
        .when()
            .get(collectionUid)
        .then().log().all()
            .body("collection.info.name", equalTo(collectionUpdated))
            .spec(resSpec)
            .extract().response();

        String name = response.path("collection.info.name");
        System.out.println("Name of the collection is: " + name);
    }

    @Test(dependsOnMethods = {"postmanCollectionCreate", "postmanCollectionGet", "postmanCollectionUpdate", "postmanCollectionGetUpdated"})
    public void postmanCollectionDelete(){
        given()
            .spec(reqSpec)
        .when()
            .delete(collectionUid)
        .then()
                .log().body()
            .spec(resSpec).body("collection.uid", equalTo(collectionUid));
    }

}
