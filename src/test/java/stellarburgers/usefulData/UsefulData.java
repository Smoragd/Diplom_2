package stellarburgers.usefulData;

import java.util.Random;

public class UsefulData {

    // uri ресурса
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    // константы для api
    public static final String USER_REGISTER = "/api/auth/register";
    public static final String USER_LOGIN = "/api/auth/login";
    public static final String USER_UPDATE_DELETE = "/api/auth/user";
    public static final String INGREDIENTS_LIST = "/api/ingredients";
    public static final String ORDER_CREATE_GET = "/api/orders";

    // блок для генерации рандомных тестовых значений:
    // список всех цифр и латинских букв:
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();
    // Генерация случайного логина = email
    public static String generateRandomLogin() {
        return generateRandomString(6) + "@yandex.ru"; // email вида "XXXXXX@yandex.ru"
    }
    // Генерация случайного пароля
    public static String generateRandomPassword() {
        return generateRandomString(6); // Пароль из 6 символов
    }
    // Генерация случайного имени
    public static String generateRandomFirstName() {
        return "FirstName" + generateRandomString(6); // Имя вида "FirstNameXXXXXX"
    }
    // Доп. метод для генерации случайной строки
    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

}