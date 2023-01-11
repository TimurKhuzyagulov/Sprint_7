import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//тест для создания заказа
@RunWith(Parameterized.class)
public class createOrderTest {

    static OrderApi orderApi;
    final private String firstNameParam;
    final private String lastNameParam;
    final private String addressParam;
    final private String metroStationParam;
    final private String phoneParam;
    final private int rentTimeParam;
    final private String deliveryDateParam;
    final private String commentParam;
    final private String colorParam;

    public createOrderTest(String firstNameParam, String lastNameParam, String addressParam, String metroStationParam, String phoneParam, int rentTimeParam, String deliveryDateParam, String commentParam, String colorParam) {
        this.firstNameParam = firstNameParam;
        this.lastNameParam = lastNameParam;
        this.addressParam = addressParam;
        this.metroStationParam = metroStationParam;
        this.phoneParam = phoneParam;
        this.rentTimeParam = rentTimeParam;
        this.deliveryDateParam = deliveryDateParam;
        this.commentParam = commentParam;
        this.colorParam = colorParam;

    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2} {3} {4} {5} {6} {7}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Ivan", "Petrov", "str.Lenina", "Frunzinskya", "+79194965999", 5, "2023-01-16", "No comments", "Black"},
                {"Pavel", "Semenov", "str.Puchkina", "Sokol'skaya", "+79124965999", 5, "2023-01-15", "comment 1", "Grey"},
                {"Anton", "Gorodov", "str.Popova", "MoskvaCitu", "+79024965999", 5, "2023-01-14", "comment 2", "Blue"},
        };
    }


    @Before
    public void setUp() {
        orderApi = new OrderApi();
        //  RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка создания заказа") // имя теста
    public void createOrderTest() {
        Order order = new Order(firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentParam, colorParam);

        ValidatableResponse response = orderApi.create(order);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
    }

    @Test
    @DisplayName("Проверка создания заказа c одним цветом") // имя теста
    public void createOrderOneColor() {
        Order orderOneColor = new Order(firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentParam, colorParam);

        ValidatableResponse response = orderApi.create(orderOneColor);
        int statusCode = response.extract().statusCode();
        int valTrack = response.extract().path("track");
        assertEquals(SC_CREATED, statusCode);
        assertNotNull(valTrack);
    }

    @Test
    @DisplayName("Проверка создания заказа c несколькими цветами") // имя теста
    public void createOrderManyColor() {
        ArrayList<String> manyColor = new ArrayList<>();
        manyColor.add("Black");
        manyColor.add("Grey");
        Order orderManyColor = new Order(firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentParam, manyColor);

        ValidatableResponse response = orderApi.create(orderManyColor);
        int statusCode = response.extract().statusCode();
        int valTrack = response.extract().path("track");
        assertEquals(SC_CREATED, statusCode);
        assertNotNull(valTrack);
    }

    @Test
    @DisplayName("Проверка создания заказа без цвета") // имя теста
    public void createOrderWithOutColor() {
        Order orderWithOutColor = new Order(firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentParam);
        ValidatableResponse response = orderApi.create(orderWithOutColor);
        int statusCode = response.extract().statusCode();
        int valTrack = response.extract().path("track");
        assertEquals(SC_CREATED, statusCode);
        assertNotNull(valTrack);

    }

}
