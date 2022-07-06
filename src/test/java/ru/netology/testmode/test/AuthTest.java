package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.UserInfo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.activeUser;
import static ru.netology.testmode.data.DataGenerator.Registration.blockedUser;
import static ru.netology.testmode.data.DataGenerator.getLogin;


class AuthTest {

    @BeforeEach
    void setup() {

        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        UserInfo activeInfo = activeUser();
        $("[data-test-id='login'] input").setValue(activeInfo.getLogin());
        $("[data-test-id='password'] input").setValue(activeInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $(withText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldGetErrorIfNotRegisteredUser() {
        UserInfo blockedInfo = blockedUser();
        $("[data-test-id='login'] input").setValue(blockedInfo.getLogin());
        $("[data-test-id='password'] input").setValue(blockedInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    void shouldGetErrorIfInvalidLogin() {
        String invalidLoginInfo = getLogin();
        UserInfo activeInfo = activeUser();
        $("[data-test-id='login'] input").setValue(invalidLoginInfo);
        $("[data-test-id='password'] input").setValue(activeInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);

    }

    @Test
    void shouldGetErrorIfInvalidPassword() {
        String invalidPasswordInfo = getLogin();
        UserInfo activeInfo = activeUser();
        $("[data-test-id='login'] input").setValue(activeInfo.getLogin());
        $("[data-test-id='password'] input").setValue(invalidPasswordInfo);
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

}
