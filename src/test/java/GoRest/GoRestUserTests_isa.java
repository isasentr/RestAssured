package GoRest;

import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUserTests_isa {

    @BeforeClass
    void Setup() {
        baseURI = "https://gorest.co.in/public/v2/"; // buradaki veriyi direk   .post("users") buraya atabiliriz.
    }

       int userID=0;
    @Test
    public void createUserObject() {

        User newUser = new User();
        newUser.setName(getRandomName());
        newUser.setGender("male");
        newUser.setEmail(getRandomEmail());
        newUser.setStatus("active");

        userID =
                given()
                        // api metoduna gitmeden önceki hazırlıklar: token,gidecek body, parametreleri

                        .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().body()
                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");

        System.out.println("userID = " + userID);
    }

    @Test(dependsOnMethods = "createUserObject")
    public void UpdateUserObject() {

        Map<String,String> updateUser=new HashMap<>();
        User newUser = new User();
        updateUser.put("name","isa şen");

                given()
                        // api metoduna gitmeden önceki hazırlıklar: token,gidecek body, parametreleri
                        .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                        .contentType(ContentType.JSON)
                        .body(updateUser)
                        .log().body()
                        .pathParam("userID", userID)

                        .when()
                        .put("users/{userID}")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("name",equalTo("isa şen"));


    }

    @Test(enabled = false)
    public void createUser() {

        int userID =
                given()
                        // api metoduna gitmeden önceki hazırlıklar: token,gidecek body, parametreleri

                        .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                        .contentType(ContentType.JSON)
                        .body("{\"name\":\"" + getRandomName() + "\", \"gender\":\"male\", \"email\":\"" + getRandomEmail() + "\", \"status\":\"active\"}")

                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
//                        .extract().path("id")
                        .extract().jsonPath().getInt("id");
        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
        System.out.println("userID = " + userID);
    }

    public String getRandomName() {
        return RandomStringUtils.randomAlphanumeric(8).toLowerCase();

    }

    public String getRandomEmail() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase() + "@gmail.com";

    }


    @Test(enabled = false)
    public void createUserMap() {

        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", getRandomName());
        newUser.put("gender", "male");
        newUser.put("email", getRandomEmail());
        newUser.put("status", "active");

        int userID =
                given()
                        // api metoduna gitmeden önceki hazırlıklar: token,gidecek body, parametreleri

                        .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().body()
                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");

        System.out.println("userID = " + userID);
    }



}




class User1 {
    private String name;
    private String gender;
    private String email;
    private String status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
