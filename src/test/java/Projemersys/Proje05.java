package Projemersys;

import Projemersys.Base.Base;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

public class Proje05 {
    Cookies cookies;

 @BeforeTest
    public void loginCampusMersys() {

        baseURI = "https://demo.mersys.io/";

        Map<String, String> login = new HashMap<>();
        login.put("username", "richfield.edu");
        login.put("password", "Richfield2020!");
        login.put("rememberMe", "true");

        cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(login)

                        .when()
                        .post("auth/login")

                        .then()
//                        .log().body()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;
    }

    String addPositionName = getRandomName( 8);
    String addPositionSName = getRandomSName( 6);
    String addPositionID;
    String addTenderId = getTanderId();

@Test
    public void addPosition() {
        Base addPosition = new Base(addPositionName, addPositionSName);

        addPositionID =
                given()
                        .cookies(cookies)
                        .body(addPosition)
//                        .log().body()
                        .contentType(ContentType.JSON)


                        .when()
//                        .log().body()
                        .post("school-service/api/employee-position")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")

        ;
    System.out.println("addPosition = " + addPosition);
    }

    public String getRandomName(int value) {return RandomStringUtils.randomAlphabetic(value).toLowerCase();}

    public String getRandomSName(int value) {
        return RandomStringUtils.randomAlphabetic(value).toLowerCase();
    }


    public String getTanderId() {
        return RandomStringUtils.randomAlphanumeric(6);
    }
}
