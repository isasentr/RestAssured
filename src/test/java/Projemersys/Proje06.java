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

public class Proje06 {
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

    public String getRandom() {
        return RandomStringUtils.randomNumeric(1);
    }




    
}
