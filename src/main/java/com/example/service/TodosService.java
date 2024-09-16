package com.example.service;

import com.example.model.GraphQlQuery;
import com.example.model.Todo;
import io.restassured.response.ValidatableResponse;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TodosService {

    private static final String BEARER_TOKEN = "";
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

    public Todo createTodo(String title) {
        final GraphQlQuery query = new GraphQlQuery("graphql/insert_todo.graphql")
                .setVariables(Map.of("title", title));

        return graphQlRequest(query)
                .extract()
                .body()
                .jsonPath()
                .getObject("data.insert_todos.returning[0]", Todo.class);
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
