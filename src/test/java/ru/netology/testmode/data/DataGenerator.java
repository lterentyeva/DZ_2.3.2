package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.testmode.data.UserInfo;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));
    private static UserInfo user;

    private DataGenerator() {
    }

    private static void sendRequest(UserInfo user) {
        DataGenerator.user = user;
        given()
         .spec(requestSpec)
         .body(user)
         .when()
         .post("/api/system/users")
         .then()
         .statusCode(200);
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
       return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo activeUser() {
            UserInfo user = new UserInfo(getRandomLogin(),getRandomPassword(),"active");
            sendRequest(user);
            return user;
        }

        public static UserInfo blockedUser() {
          UserInfo user = new UserInfo(getRandomLogin(), getRandomPassword(), "blocked");
          sendRequest(user);
          return user;
        }
    }

}
