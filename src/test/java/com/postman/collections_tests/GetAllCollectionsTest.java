package com.postman.collections_tests;

import com.postman.common.RestUtilities;
import com.postman.constants.EndPoints;
import com.postman.constants.Path;
import com.postman.constants.Uid;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class GetAllCollectionsTest {

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;

    @BeforeClass(groups = {"Group1"})
    public void setup() {
        reqSpec = RestUtilities.getRequestSpecification();
        reqSpec.basePath(Path.COLLECTIONS);

        resSpec = RestUtilities.getResponseSpecification();
    }

    @Test(groups = {"Group1"})
    public void getAllCollections1() {
        given()
            .spec(reqSpec).queryParam("workspace", Uid.WORKSPACE_MYWORKSPACE)
        .when()
            .get("")
        .then().log().all()
            .body("collections[0].name", equalTo("Coctail"))
            .spec(resSpec);
    }

    @Test
    public void getAllCollections2() {
        given()
            .spec(RestUtilities.createQueryParam(reqSpec, "workspace", Uid.WORKSPACE_MYWORKSPACE))
        .when()
            .get("")
        .then().log().all()
                .body("collections[0].name", equalTo("Coctail"))
                .spec(resSpec);
    }

//    @Test
//    public void getAllCollections3() {
//        RestUtilities.setEndPoint(EndPoints.WORKSPACE_EP);
//        Response res = RestUtilities.getResponse(RestUtilities.createQueryParam(reqSpec, "workspace", Uid.WORKSPACE_MYWORKSPACE), "get");
//        System.out.println(res);
//    }

}
