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

public class UserUpdateTest {

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
    @DisplayName("Проверка изменения логина юзера с авторизацией")
    public void updateUserLoginWithAuthorization() {
        createUser(login, password, firstName);

        Response response = updateUser(1+login, password, firstName, accessToken);
        validateResponseOk(response, 200, true);
    }

    @Test
    @DisplayName("Проверка изменения пароля юзера с авторизацией")
    public void updateUserPasswordWithAuthorization() {
        createUser(login, password, firstName);

        Response response = updateUser(login, 1+password, firstName, accessToken);
        validateResponseOk(response, 200, true);
    }

    @Test
    @DisplayName("Проверка изменения имени юзера с авторизацией")
    public void updateUserFirstNameWithAuthorization() {
        createUser(login, password, firstName);

        Response response = updateUser(login, password, 1+firstName, accessToken);
        validateResponseOk(response, 200, true);
    }

    @Test
    @DisplayName("Проверка изменения логина юзера без авторизациии")
    public void updateUserLoginWithoutAuthorization() {
        createUser(login, password, firstName);

        Response response = updateUser(1+login, password, firstName, "");
        validateResponseMistake(response, 401, "You should be authorised");
    }

    @Test
    @DisplayName("Проверка изменения пароля юзера без авторизациии")
    public void updateUserPasswordWithoutAuthorization() {
        createUser(login, password, firstName);

        Response response = updateUser(login, 1+password, firstName, "");
        validateResponseMistake(response, 401, "You should be authorised");
    }

    @Test
    @DisplayName("Проверка изменения имени юзера без авторизациии")
    public void updateUserFirstNameWithoutAuthorization() {
        createUser(login, password, firstName);

        Response response = updateUser(login, password, 1+firstName, "");
        validateResponseMistake(response, 401, "You should be authorised");
    }

    @After
    public void tearDown() {
        deleteUser(accessToken);
    }

}
