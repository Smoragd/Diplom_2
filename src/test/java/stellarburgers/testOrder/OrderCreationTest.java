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

public class OrderCreationTest {

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
    @DisplayName("Проверка создания заказа с валидными ингредиентами и с авторизацией")
    public void createOrderWithValidIngredientsAndWithAuthorization() {
        createUser(login, password, firstName);

        List<String> validIngredients = getValidIngredients();
        ingredients = new String[]{validIngredients.get(0), validIngredients.get(1)};

        Response response = createOrder(ingredients, accessToken);
        validateResponseOk(response, 200, true);
    }

    @Test
    @DisplayName("Проверка создания заказа с невалидными ингредиентами, но с авторизацией")
    public void createOrderWithInvalidIngredientsAndWithAuthorization() {
        createUser(login, password, firstName);

        ingredients = new String[]{"invalid_ingredient_1", "invalid_ingredient_2"};

        Response response = createOrder(ingredients, accessToken);
        validateResponseMistake500(response, 500);
    }

    @Test
    @DisplayName("Проверка создания заказа с валидными ингредиентами, но без авторизации")
    public void createOrderWithValidIngredientsAndWithoutAuthorization() {
        createUser(login, password, firstName);
        List<String> validIngredients = getValidIngredients();
        ingredients = new String[]{validIngredients.get(0), validIngredients.get(1)};

        Response response = createOrder(ingredients, "");
        //validateResponseMistake(response, 401, "You should be authorised");
        validateResponseOk(response, 200, true);
    }

    @Test
    @DisplayName("Проверка создания заказа без ингредиентов, но с авторизацией")
    public void createOrderWithoutIngredientsAndWithAuthorization() {
        createUser(login, password, firstName);

        ingredients = new String[]{};

        Response response = createOrder(ingredients, accessToken);
        validateResponseMistake(response, 400, "Ingredient ids must be provided");
    }

    @After
    public void tearDown() {
        deleteUser(accessToken);
    }

}
