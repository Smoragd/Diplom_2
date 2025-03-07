package stellarburgers.testUser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static stellarburgers.usefulData.Steps.*;
import static stellarburgers.usefulData.UsefulData.*;
import static stellarburgers.usefulData.UsefulData.generateRandomFirstName;

public class UserLoginTest {

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
    @DisplayName("Проверка авторизации юзера с валидными данными")
    public void loginWithValidCredentials() {
        createUser(login, password, firstName);
        Response response = loginUser(login, password);
        validateResponseOk(response, 200, true);
    }

    @Test
    @DisplayName("Проверка авторизации юзера с неправильным логином")
    public void loginWithInvalidLogin() {
        createUser(login, password, firstName);
        Response response = loginUser(login+1, password);
        validateResponseMistake(response, 401, "email or password are incorrect");
    }

    @Test
    @DisplayName("Проверка авторизации юзера с неправильным паролем")
    public void loginWithInvalidPassword() {
        createUser(login, password, firstName);
        Response response = loginUser(login, password+1);
        validateResponseMistake(response, 401, "email or password are incorrect");
    }

    @After
    public void tearDown() {
        deleteUser(accessToken);
    }
}