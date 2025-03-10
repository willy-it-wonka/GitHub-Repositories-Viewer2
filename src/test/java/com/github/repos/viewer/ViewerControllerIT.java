package com.github.repos.viewer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class ViewerControllerIT {

    @Test
    void testHappyPath() {
        String testUsername = "willy-it-wonka";

        RestAssured.given()
                .when()
                .get("/api/v1/users/" + testUsername + "/repos")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].name", notNullValue())
                .body("[0].owner.login", notNullValue())
                .body("[0].branches", not(empty()))
                .body("[0].branches[0].name", notNullValue())
                .body("[0].branches[0].lastCommitSha", notNullValue());
    }
}
