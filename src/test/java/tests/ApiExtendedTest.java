package tests;

import model.LoginBodyModel;
import model.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpec;

public class ApiExtendedTest {

    @Test
    void loginTest() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseModel loginResponse =
                step("Get authorization data", () ->
                        given(loginRequestSpec)
                            .body(loginBody)
                            .when()
                            .post("/login")
                            .then()
                            .spec(loginResponseSpec)
                            .extract().as(LoginResponseModel.class));
        step("Verify authorization response", () ->
            assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }
}
