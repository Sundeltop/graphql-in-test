package com.example.service;

import com.example.model.GraphQlQuery;
import io.restassured.response.ValidatableResponse;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TodosService {

    private static final String BEARER_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY2ZTZmOTU2NGRmZmY4Nzg5NDE4MjQwYSJ9LCJuaWNrbmFtZSI6InN1bmRlbHRvcDMyMiIsIm5hbWUiOiJzdW5kZWx0b3AzMjJAZ21haWwuY29tIiwicGljdHVyZSI6Imh0dHBzOi8vcy5ncmF2YXRhci5jb20vYXZhdGFyLzBlMWFmMzE1YjFjNjJmMDMxZDk3NWE1MTc1OThkZjY3P3M9NDgwJnI9cGcmZD1odHRwcyUzQSUyRiUyRmNkbi5hdXRoMC5jb20lMkZhdmF0YXJzJTJGc3UucG5nIiwidXBkYXRlZF9hdCI6IjIwMjQtMDktMTVUMTU6MTI6MjMuMTY5WiIsImlzcyI6Imh0dHBzOi8vZ3JhcGhxbC10dXRvcmlhbHMuYXV0aDAuY29tLyIsImF1ZCI6IlAzOHFuRm8xbEZBUUpyemt1bi0td0V6cWxqVk5HY1dXIiwiaWF0IjoxNzI2NDcwOTMwLCJleHAiOjE3MjY1MDY5MzAsInN1YiI6ImF1dGgwfDY2ZTZmOTU2NGRmZmY4Nzg5NDE4MjQwYSIsImF0X2hhc2giOiJ5ZW5KQWF0YWZqSmNhVno5bjNjeXRBIiwic2lkIjoidGhoZnRvQ19nTFZtWjYycUxqZ3Jsb2g0cmVTQkRodUIiLCJub25jZSI6ImREZVpUN0Jyd1BxOVRGeWRjZjZkalNCZmVHS0h1alpnIn0.Sn6W6GWlQTvTVDSEYUrTzcf6Q9g3PovxG3sILfks00YQo7X0H12ohSy2IhriMRZrtvRV9QO9i9Mg3-w8heKFVW5r8dLTux_q0fBAtd_Oh4PcMZrJmgGU-qlcaO_hOoK0oh9teZMFO2aQz_v7eVAICqSiPtSDbhw6s3NtHEWP5bhM7LE5x-Si0J4TUQO8IrDETq2QAIi5YFkUF4J2d4fYrpBNPXEYrYPRQkbQvz_Ho8o3PDsKHYGZat1v260lYIm-jCv0lBZxQotr8rAAcyswUER0jAlOUxioJe9qO76Mlp1PYKVfWuhv746pzklJNzqJQ9xMB36igk8k79hWH_pv3Q";
    private static final String URL = "https://hasura.io/learn/graphql";

    public TodosService() {
    }

    public List<String> getTodos() {
        return graphQlRequest(new GraphQlQuery("graphql/todos.graphql"))
                .extract()
                .body()
                .path("data.todos.id");
    }

    public String getTodoTitle(int id) {
        final GraphQlQuery query = new GraphQlQuery("graphql/todos_by_pk.graphql")
                .setVariables(Map.of("id", id));

        return graphQlRequest(query)
                .extract()
                .body()
                .path("data.todos_by_pk.title");
    }

    private ValidatableResponse graphQlRequest(GraphQlQuery query) {
        return given()
                .auth()
                .oauth2(BEARER_TOKEN)
                .body(query)
                .when()
                .post(URL)
                .then();
    }
}
