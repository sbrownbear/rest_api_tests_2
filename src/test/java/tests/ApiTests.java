package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests {

        /*
        1. Make request to https://selenoid.autotests.cloud/status
        2. Get response { total: 20, used: 0, queued: 0, pending: 0, browsers: { android: { 8.1: { } },
        chrome: { 100.0: { }, 99.0: { } }, chrome-mobile: { 86.0: { } }, firefox: { 97.0: { }, 98.0: { } },
        opera: { 84.0: { }, 85.0: { } } } }
        3. Check total is 20
        */

    @Test
    @DisplayName("Check total is 20 and browser chrome version is 100.0")
    void checkTotalAndBrowser() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20))
                .body("browsers.chrome", hasKey("100.0"));
    }

        /*
        1. Make request to https://selenoid.autotests.cloud/wd/hub/status, log: user1, pass: 1234
        2. Get response { value: { message: "Selenoid 1.10.7 built at 2021-11-21_05:46:32AM", ready: true } }
        3. Check value.ready is true
        */

    @Test
    @DisplayName("Check value.ready is true with auth basic")
    void checkValueReadyIsTrue() {
        given()
                .log().uri()
                .auth().basic("user1", "1234")
                .when()
                .post("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }

        /*
        1. Make POST request to https://reqres.in/api/login
                with body { "email": "eve.holt@reqres.in", "password": "cityslicka" }
        2. Get response { "token": "QpwL5tke4Pnpja7X4" }
        3. Check token is QpwL5tke4Pnpja7X4
        */

    @Test
    @DisplayName("Check token is right")
    void loginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
