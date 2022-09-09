package com.cocktails.cocktails_tests;

import com.cocktails.common.RestUtilities;
import com.cocktails.constants.EndPoints;
import com.cocktails.constants.Path;
import com.cocktails.constants.Uid;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class GetCocktail {

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;

    @BeforeClass(groups = {"Group1", "Group2"})
    public void setup() {
        reqSpec = RestUtilities.getRequestSpecification();
        reqSpec.basePath(Path.COCKTAIL);

        resSpec = RestUtilities.getResponseSpecification();
    }

    @Test(groups = {"Group1"})
    public void getCocktailByName() {
        given()
            .spec(reqSpec).queryParam("s", "margarita")
        .when()
            .get(EndPoints.RECEPIE_BY_NAME)
        .then().log().all()
            .body("drinks[0].idDrink", equalTo("11007"))
            .spec(resSpec);
    }

    @Test(groups = {"Group2"})
    public void getCocktailsByIngredient() {
        given()
            .spec(reqSpec).queryParam("i", "vodka")
        .when()
            .get(EndPoints.FILTER_BY_INGREDIENT)
        .then().log().all()
            .body("drinks[0].strDrink", equalTo("155 Belmont"))
            .spec(resSpec);
    }
}
