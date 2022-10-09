package GoRest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUsersTests {

    @BeforeClass
    void Setup() {
        // RestAssured kendi statik değişkeni tanımlı değer atanıyor.
        baseURI = "https://gorest.co.in/public/v2/";
    }

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    public String getRandomEmail() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase() + "@gmail.com";
    }

    int userID = 0;
    User newUser;

    @Test
    public void createUserObject() {
        newUser = new User();
        newUser.setName(getRandomName());
        newUser.setGender("male");
        newUser.setEmail(getRandomEmail());
        newUser.setStatus("active");

        userID =
                given()
                        // api metoduna gitmeden önceki hazırlıklar : token, gidecek body, parametreleri
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
                        //.extract().path("id")
                        .extract().jsonPath().getInt("id")
        ;

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.

        System.out.println("userID = " + userID);
    }

    @Test(dependsOnMethods = "createUserObject", priority = 1)
    public void updateUserObject() {
//        Map<String, String> updateUser=new HashMap<>();
//        updateUser.put("name","ismet temur");

        newUser.setName("isa şen");

        given()
                .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                .contentType(ContentType.JSON)
                .body(newUser)
                .log().body()
                .pathParam("userID", userID)

                .when()
                .put("users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("isa şen"))
        ;
    }

    @Test(dependsOnMethods = "createUserObject", priority = 2)
    public void getUserByID() {
        given()
                .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID", userID)

                .when()
                .get("users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID))
        ;
    }

    @Test(dependsOnMethods = "createUserObject", priority = 3)
    public void deleteUserByID() {
        given()
                .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID", userID)

                .when()
                .delete("users/{userID}")

                .then()
                .log().body()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "deleteUserByID")
    public void deleteUserByIDNegatif() {
        given()
                .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID", userID)

                .when()
                .delete("users/{userID}") // çalışmadı hata veriyor

                .then()
                .log().body()
                .statusCode(404)
        ;
    }

    @Test(enabled = true)
    public void getUsers() {
        Response response =
                given()
                        .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")

                        .when()
                        .get("users")

                        .then()
//                        .log().body()
                        .statusCode(200)
                        .extract().response();

        //TODO 3. user in ID sini alınız. (path ve jhonpath ile ayrı ayrı yapınız.)
        int idUsers3path = response.path("[2].id");
        int idUser3JsonPath = response.jsonPath().getInt("[2].id");

        System.out.println("idUsers3path = " + idUsers3path);
        System.out.println("idUser3JsonPath = " + idUser3JsonPath);


        // TODO Tüm gelen veriyi bir nesneye atınız. (google araştırması gerekir)
        User[] userPath = response.as(User[].class);
        System.out.println("Arrays.toString(userPath) = " + Arrays.toString(userPath));

       List<User> userJsonPath=response.jsonPath().getList("",User.class);
        System.out.println("userJsonPath = " + userJsonPath);
    }


    @Test
    public void deleteUserByIDExract() {
        given()
                .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID", userID)

                .when()
                .delete("users/{userID}")

                .then()
                .log().body()
                .statusCode(204)
        ;
    }

@Test
    public void getUserByIDExract() {
        //TODO GetUserByID testinde dönen user ı bir nesneye atınız
        User user =
                given()
                        .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")
                        .contentType(ContentType.JSON)
                        .pathParam("userID", 3630)

                        .when()
                        .get("users/{userID}")

                        .then()
                        .log().body()
                        .statusCode(200)
//                .extract().as(User.class);
                        .extract().jsonPath().getObject("", User.class);

        System.out.println("user = " + user);
    }

    @Test
    public void getUsersV1() {
        Response response =
                given()
                        .header("Authorization", "Bearer f1a6e484b61f1578ac69f0d4e1077a923ea9f6d244d67f24f7880b7c06d60707")

                        .when()
                        .get("http://gorest.co.in/public/v1/users")

                        .then()
//                        .log().body()
                        .statusCode(200)
                        .extract().response();


//        response.as();  tüm gelen response uygun nesnelerin için tüm clasların yapılması gerekiyor.

        List<User> dataUsers=response.jsonPath().getList("data",User.class);

        System.out.println("dataUsers = " + dataUsers);

        //Jsonpath bir responsen içindeki bir parçayı nesneye dönüştürebiliriz.

        // Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.
        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ise veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.




    }


    @Test(enabled = true)
    public void createUser()
    {
        int userID=
                given()
                        // api metoduna gitmeden önceki hazırlıklar : token, gidecek body, parametreleri
                        .header("Authorization","Bearer 523891d26e103bab0089022d20f1820be2999a7ad693304f560132559a2a152d")
                        .contentType(ContentType.JSON)
                        .body("{\"name\":\""+getRandomName()+"\", \"gender\":\"male\", \"email\":\""+ getRandomEmail()+"\", \"status\":\"active\"}")

                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");
        ;

        System.out.println("userID = " + userID);
    }

    @Test(enabled = false)
    public void createUserMap()
    {
        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",getRandomName());
        newUser.put("gender","male");
        newUser.put("email", getRandomEmail());
        newUser.put("status","active");

        int userID=
                given()
                        // api metoduna gitmeden önceki hazırlıklar : token, gidecek body, parametreleri
                        .header("Authorization","Bearer 523891d26e103bab0089022d20f1820be2999a7ad693304f560132559a2a152d")
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
        ;

        System.out.println("userID = " + userID);
    }

}

class User {

    private int id;
    private String name;
    private String gender;
    private String email;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

