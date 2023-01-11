import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//Тест для логина курьера
public class loginCourierTest {

    Courier courier = new Courier("qaTestHTF2", "0526", "Khuzyagulov");
    CourierApi courierApi;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @Test
    @DisplayName("Проверка успешной авторизации курьера") // имя теста
    @Description("Проверка того, в резултате успешной авторизации возвращается не пустой id") // описание теста
    public void authCourierSuccessTest() {

        ValidatableResponse response = courierApi.login(courier);
        int statusCode = response.extract().statusCode();
        int valId = response.extract().path("id");
        assertEquals(SC_OK, statusCode);
        assertNotNull(valId);


    }

    @Test
    @DisplayName("Проверка авторизации курьера без логина") // имя теста
    @Description("Проверка авторизации курьера без указания логина") // описание теста
    public void authCourierWithOutLoginTest() {
        Courier courierWithOutLogin = new Courier("", "1234", "");

        ValidatableResponse response = courierApi.login(courierWithOutLogin);

        int statusCode = response.extract().statusCode();
        String valMessage = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals("Недостаточно данных для входа", valMessage);
    }

    @Test
    @DisplayName("Проверка авторизации курьера без пароля") // имя теста
    @Description("Проверка авторизации курьера без указания пароля") // описание теста
    public void authCourierWithOutPasswordTest() {
        Courier courierWithOutPassword = new Courier("TestQA", "", "");

        ValidatableResponse response = courierApi.login(courierWithOutPassword);

        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с неправильным паролем") // имя теста
    @Description("Проверка авторизации курьера с неправильным паролем") // описание теста
    public void authCourierIncorrectPasswordTest() {
        Courier courierIncorrectPassword = new Courier("qaja", "12345", "qa_java");

        ValidatableResponse response = courierApi.login(courierIncorrectPassword);

        int statusCode = response.extract().statusCode();
        String valMessage = response.extract().path("message");
        assertEquals(SC_NOT_FOUND, statusCode);
        assertEquals("Учетная запись не найдена", valMessage);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с неправильным или не существующим логином") // имя теста
    @Description("Проверка авторизации курьера с неправильным или не существующим логином") // описание теста
    public void authCourierIncorrectLoginTest() {
        Courier courierIncorrectLogin = new Courier("qaja_bad_login", "1234", "qa_java");

        ValidatableResponse response = courierApi.login(courierIncorrectLogin);

        int statusCode = response.extract().statusCode();
        String valMessage = response.extract().path("message");
        assertEquals(SC_NOT_FOUND, statusCode);
        assertEquals("Учетная запись не найдена", valMessage);
    }
}
