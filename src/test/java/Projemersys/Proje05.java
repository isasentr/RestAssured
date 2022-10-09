package Projemersys;

import Projemersys.Base.Base;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

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

    String addPositionName;
    String addPositionSName;
    String addPositionID;
    String addPositionTenantID="5fe0786230cc4d59295712cf";

    @Test
    public void addPosition() {
        addPositionName = getRandomName();
        addPositionSName = getRandomSName();

        Base addPosition = new Base();
        addPosition.setName(addPositionName);
        addPosition.setSname(addPositionSName);
        addPosition.setTenid(addPositionTenantID);

        addPositionID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(addPosition)

                        .when()
                        .post("school-service/api/employee-position")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")

        ;
    }


    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(7).toLowerCase();
    }

    public String getRandomSName() {
        return RandomStringUtils.randomAlphabetic(6).toLowerCase();
    }
}
