import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class zippoTest {
    // http://api.zippopotam.us/us/90210

    @Test
    public  void  test(){
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
}
