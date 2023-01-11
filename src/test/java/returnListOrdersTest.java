import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//тесты для списка закозов
public class returnListOrdersTest {
    static OrderApi orderApi;

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Проверка возврата списка заказов") // имя теста
    @Description("Проверка возврата списка заказов") // описание теста
    public void returnListOrdersTest() {
        ValidatableResponse response = orderApi.return_list();
        int statusCode = response.extract().statusCode();
        ArrayList valOrders = response.extract().path("orders");
        assertEquals(SC_OK, statusCode);
        assertNotNull(valOrders);


    }
}
