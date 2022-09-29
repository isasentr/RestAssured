import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class zippoTest {
    // http://api.zippopotam.us/us/90210

    @Test
    public void test() {
        given()
                //hazırlık işlemlerini yapacağız.(token,send body ve parametreler)
                .when()
                // linki ve metodu veriyoruz
                .then();
        //assertion ve verileri ele alma
    }

    @Test
    public  void  statusCodetest(){
        given()
                //hazırlık işlemlerini yapacağız.(token,send body ve parametreler)
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body() // log.all() bütün responsu gösterir.
                .statusCode(200); // status kontrolü
    }

    @Test
    public  void  contentTypetest(){
        given()
                //hazırlık işlemlerini yapacağız.(token,send body ve parametreler)
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body() // log.all() bütün responsu gösterir.
                .statusCode(200) // status kontrolü
                .contentType(ContentType.JSON) // hatalı durumu kontrolü yapılır
        ;
    }

    @Test
    public void checkStateInResponseBody() {
        given()
                //hazırlık işlemlerini yapacağız.(token,send body ve parametreler)
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
//                .log().body() // log.all() bütün responsu gösterir.
                .body("country", equalTo("United States")) // body.country == United States ?
                .statusCode(200) // status kontrolü

        ;
    }
    //  body.country -->body("country"),
//body.'post code' -->body("post code"),
//body.'country abbreviation'-->body("country abbreviation")
//body.places[0].'place name'-->body("body.places[0].'place name'")
//body.places[0].state-->body("body.places[0].state")


//        "post code": "90210",
//        "country": "United States",
//        "country abbreviation": "US",
//        "places": [
//        {
//        "place name": "Beverly Hills",
//        "longitude": "-118.4065",
//        "state": "California",
//        "state abbreviation": "CA",
//        "latitude": "34.0901"

    @Test
    public void bodyJsonPathTest2() {
        given()
                //hazırlık işlemlerini yapacağız.(token,send body ve parametreler)
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
//                .log().body() // log.all() bütün responsu gösterir.
                .body("places[0].state", equalTo("California")) // body.country == United States ?
                .body("places[0].latitude", equalTo("34.0901"))
                .statusCode(200) // status kontrolü
        ;
    }

    @Test
    public void bodyJsonPathTest3() {
        given()
                //hazırlık işlemlerini yapacağız.(token,send body ve parametreler)
                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body() // log.all() bütün responsu gösterir.
                .body("places.'place name'", hasItem("Dervişler Köyü"))// bir index verilmezse dizinin bütün elemanlarında
                .statusCode(200) // status kontrolü
        //"places.'place name'" bu bilgiler "Dervişler Köyü" bu iteme sahip mi
        ;
    }

    @Test
    public void bodyArrayHsaSizeTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log.all() bütün responsu gösterir.
                .body("places", hasSize(1)) // verilen pat daki listin size kontrolü
                .statusCode(200)
        ;
    }

    @Test
    public void combiningTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
//                .log().body() // log.all() bütün responsu gösterir.
                .body("places", hasSize(1)) // verilen pat daki listin size kontrolü
                .body("places.state", hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)
        ;
    }

    @Test
    public void pathParamTest() {
        given()
                .pathParam("Country", "us")
                .pathParam("ZipKod", "90210")
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipKod}")

                .then()
                .log().body() // log.all() bütün responsu gösterir.

                .statusCode(200)
        ;
    }

    @Test
    public void pathParamTest2() {
        //90210 dan 90250 ye kadar test sonuçlarında places sizen hepsinde 1 geldiğini doğrulayınız.
        for (int i = 90210; i <= 90213; i++) {

            given()
                    .pathParam("Country", "us")
                    .pathParam("ZipKod", i)
                    .log().uri()

                    .when()
                    .get("http://api.zippopotam.us/{Country}/{ZipKod}")

                    .then()
                    .body("places", hasSize(1))
                    .log().body()

                    .statusCode(200)
            ;
        }
    }

    @Test
    public void queryParamTest() {

        given()
                .param("page", 1)

                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(1))
                .statusCode(200)
        ;
    }

    @Test
    public void queryParamTest2() {
        for (int i = 1; i <= 10; i++) {

            given()
                    .param("page", i)

                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(i))
                    .statusCode(200)
            ;
        }
    }

    RequestSpecification requestOzellikleri;
    ResponseSpecification responsOzellikleri;

    @BeforeClass
    void Setup(){
        // restasssured kendi statik değişkeni tanımlı değer atanıyor.
        baseURI="https://gorest.co.in/public/v1";
        requestOzellikleri=new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responsOzellikleri =new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();

    }

    @Test
    public void requestResponseSpecification() {
        //https://gorest.co.in/public/v1/users?page=1

        given()
                .param("page", 1)
                .spec(requestOzellikleri)

                .when()
                .get("/users") // url nin başında http yoksa baseurideli dğer otomatik atanır.

                .then()
                .body("meta.pagination.page", equalTo(1))
                .spec(responsOzellikleri)
        ;
    }

    @Test
    public void extracingJsonPath() {
        String placeName=
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log.all() bütün responsu gösterir.
                .statusCode(200) // status kontrolü
                .extract().path("places[0].'longitude'")
        //Extract metodu ile given ile başlayan satır, bir dğer döndürür hale geldi,en sonda extract olmalı.
        ;
        System.out.println("placeName = " + placeName);
    }

}


