package stellarburgers.testOrder;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static stellarburgers.usefulData.Steps.*;
import static stellarburgers.usefulData.UsefulData.*;
import static stellarburgers.usefulData.UsefulData.generateRandomFirstName;

public class UserOrdersTest {

    private String login;
    private String password;
    private String firstName;
    static String[] ingredients;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;

        // Генерация случайных данных для курьера перед каждым тестом
        login = generateRandomLogin();
        password = generateRandomPassword();
        firstName = generateRandomFirstName();
    }

    @Test
    @DisplayName("Проверка получения списка заказов юзера с авторизацией")
    public void getUserOrdersWithAuthorization() {
        createUser(login, password, firstName); // создание юзера
        List<String> validIngredients = getValidIngredients(); // получение валидных ингредиентов
        ingredients = new String[]{validIngredients.get(0), validIngredients.get(1)}; // выбор первых двух ингредиентов
        createOrder(ingredients, accessToken); // создание заказа

        Response response = getUserOrders(accessToken); // получение заказов юзера
        validateResponseOk(response, 200, true);
    }

    @Test
    @DisplayName("Проверка получения списка заказов юзера без авторизации")
    public void getUserOrdersWithoutAuthorization() {
        Response response = getUserOrders("");
        validateResponseMistake(response, 401, "You should be authorised");
    }

    @After
    public void tearDown() {
        deleteUser(accessToken);
    }

}