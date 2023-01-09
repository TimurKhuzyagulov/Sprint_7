import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class createCourierTest {

    static int id;
    Courier courier = new Courier("qaTestHTF_1", "0526", "Khuzyagulov");

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @AfterClass
    public static void cleanUp() {

        given().delete("/api/v1/courier/" + id);
    }

    @Test
    @DisplayName("Проверка создания курьера") // имя теста
    @Description("Проверка того, что курьер успешно создается") // описание теста
    public void createCourierAnswerTest() {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));

        Response responseLogin = given().header("Content-type", "application/json").body(courier).when().post("/api/v1/courier/login");
        id = responseLogin.then().extract().path("id");

    }


    @Test
    @DisplayName("Проверка создания второго одинакового курьера") // имя теста
    @Description("Проверка создания второго одинакового курьера с существующим логином") // описание теста
    public void createDoubleCourierTest() {

        Courier courierDouble = new Courier("qaja4test", "1234", "qa_java");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierDouble)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));


        //System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Проверка создания курьера без логина") // имя теста
    @Description("Проверка создания курьера без логина") // описание теста
    public void createCourierWithOutLoginTest() {
        Courier courierWithOutLogin = new Courier("", "1234", "courierWithOutLogin");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierWithOutLogin)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка создания курьера без пароля") // имя теста
    @Description("Проверка создания курьера без пароля") // описание теста
    public void createCourierWithOutPasswordTest() {
        Courier courierWithOutPassword = new Courier("TestQA", "", "courierWithOutPassword");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierWithOutPassword)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


}
