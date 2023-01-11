import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

//Тесты для создания курьера
public class createCourierTest {


    static int id;
    Courier courier = new Courier("qaTestHTF_001", "0526", "Khuzyagulov");
    static CourierApi courierApi;

    @Before
    public void setUp() {

        courierApi = new CourierApi();
    }

    @AfterClass
    public static void cleanUp() {
        courierApi.delete(id);
    }

    @Test
    @DisplayName("Проверка создания курьера") // имя теста
    @Description("Проверка того, что курьер успешно создается") // описание теста
    public void createCourierAnswerTest() {

        ValidatableResponse response = courierApi.create(courier);
        int statusCode = response.extract().statusCode();
        boolean valOk = response.extract().path("ok");
        assertEquals(SC_CREATED, statusCode);
        assertEquals(true, valOk);

        ValidatableResponse responseLogin = courierApi.login(courier);
        id = responseLogin.extract().path("id");
    }


    @Test
    @DisplayName("Проверка создания второго одинакового курьера") // имя теста
    @Description("Проверка создания второго одинакового курьера с существующим логином") // описание теста
    public void createDoubleCourierTest() {

        Courier courierDouble = new Courier("qaja4test", "1234", "qa_java");
        ValidatableResponse response = courierApi.create(courierDouble);

        int statusCode = response.extract().statusCode();
        String valMessage = response.extract().path("message");
        assertEquals(SC_CONFLICT, statusCode);
        assertEquals("Этот логин уже используется. Попробуйте другой.", valMessage);
    }

    @Test
    @DisplayName("Проверка создания курьера без логина") // имя теста
    @Description("Проверка создания курьера без логина") // описание теста
    public void createCourierWithOutLoginTest() {
        Courier courierWithOutLogin = new Courier("", "1234", "courierWithOutLogin");
        ValidatableResponse response = courierApi.create(courierWithOutLogin);

        int statusCode = response.extract().statusCode();
        String valMessage = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals("Недостаточно данных для создания учетной записи", valMessage);
    }

    @Test
    @DisplayName("Проверка создания курьера без пароля") // имя теста
    @Description("Проверка создания курьера без пароля") // описание теста
    public void createCourierWithOutPasswordTest() {
        Courier courierWithOutPassword = new Courier("TestQA", "", "courierWithOutPassword");
        ValidatableResponse response = courierApi.create(courierWithOutPassword);

        int statusCode = response.extract().statusCode();
        String valMessage = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals("Недостаточно данных для создания учетной записи", valMessage);
    }


}
