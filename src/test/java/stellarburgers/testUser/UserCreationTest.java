package stellarburgers.testUser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.usefulData.Steps;
import static stellarburgers.usefulData.Steps.*;
import static stellarburgers.usefulData.UsefulData.*;

public class UserCreationTest {

    private String login;
    private String password;
    private String firstName;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;

        // Генерация случайных данных для курьера перед каждым тестом
        login = generateRandomLogin();
        password = generateRandomPassword();
        firstName = generateRandomFirstName();
    }

    @Test
    @DisplayName("Проверка создания юзера с валидными данными")
    public void createUniqueUser() {
        //создание юзера
        Response response = createUser(login, password, firstName);
        validateResponseOk(response, 200, true);
    }

    @Test
    @DisplayName("Проверка создания юзера, аналогичного уже существующему")
    public void createDuplicateUser() {
        Response response1 = createUser(login, password, firstName);
        String accessToken1 = response1.jsonPath().getString("accessToken");

        Response response2 = createUser(login, password, firstName);
        // Проверка ответа по второму юзеру - дубликату
        validateResponseMistake(response2, 403, "User already exists");

        setAccessToken(accessToken1);
    }

    @Test
    @DisplayName("Проверка создания юзера без логина")
    public void createUserWithMissingLogin() {
        Response response = createUser("", password, firstName);
        validateResponseMistake(response, 403, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Проверка создания юзера без пароля")
    public void createUserWithMissingPassword() {
        Response response = createUser(login, "", firstName);
        validateResponseMistake(response, 403, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Проверка создания юзера без имени")
    public void createUserWithMissingFirstName() {
        Response response = createUser(login, password, "");
        validateResponseMistake(response, 403, "Email, password and name are required fields");
    }

    @After
    public void tearDown() {
        Steps.deleteUser(accessToken);
    }

}