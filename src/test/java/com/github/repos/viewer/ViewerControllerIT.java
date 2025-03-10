package com.github.repos.viewer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class ViewerControllerIT {

    private static final String USERNAME = "willy-it-wonka";

    @Test
    void testHappyPath() {
        RestAssured.given()
                .when()
                .get("/api/v1/users/" + USERNAME + "/repos")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].name", notNullValue())
                .body("[0].owner.login", equalTo(USERNAME))
                .body("[0].branches", not(empty()))
                .body("[0].branches[0].name", notNullValue())
                .body("findAll { it.branches.size() > 0 }.size()", greaterThan(0));
    }
}
