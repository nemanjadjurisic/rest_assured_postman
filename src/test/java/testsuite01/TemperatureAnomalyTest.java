package testsuite01;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TemperatureAnomalyTest {

    String collectionUid = "";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://www.ncdc.noaa.gov";
        RestAssured.basePath = "/cag";
    }

    @Test
    public void anualAverageTemperature(){
        Response response =
                given()
                    .queryParam("base_prd", "true")
                    .queryParam("begbaseyear", "2009")
                    .queryParam("endbaseyear", "2010")
                    .pathParam("from", "2009")
                    .pathParam("to", "2010")
                .when()
                    .get("/national/time-series/110-tavg-ytd-12-{from}-{to}.xml" )
                .then()
                    .extract().response();
        String responseString = response.asString();
        System.out.println(responseString);

//        String value = response.path("datacollection.description.units");
//        System.out.println("Unit is: " + value);

        XmlPath xmlPath = new XmlPath(responseString);
        String valuePath = xmlPath.get("datacollection.description.units");
        System.out.println("Unit is: " + valuePath);

    }
}
