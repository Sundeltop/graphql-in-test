package com.example.service;

import com.example.model.GraphQlQuery;
import com.example.model.TodoJson;
import io.restassured.response.ResponseBodyExtractionOptions;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TodosService {

    private static final String BEARER_TOKEN = "";
    private static final String URL = "https://hasura.io/learn/graphql";

    public TodosService() {
    }

    public List<TodoJson> getTodos() {
        return graphQlRequest(new GraphQlQuery("graphql/todos.graphql"))
                .jsonPath()
                .getList("data.todos", TodoJson.class);
    }

    public String getTodoTitle(int id) {
        final GraphQlQuery query = new GraphQlQuery("graphql/todos_by_pk.graphql")
                .setVariables(Map.of("id", id));

        return graphQlRequest(query)
                .path("data.todos_by_pk.title");
    }

    public TodoJson createTodo(String title) {
        final GraphQlQuery query = new GraphQlQuery("graphql/insert_todo.graphql")
                .setVariables(Map.of("title", title));

        return graphQlRequest(query)
                .jsonPath()
                .getObject("data.insert_todos.returning[0]", TodoJson.class);
    }

    private ResponseBodyExtractionOptions graphQlRequest(GraphQlQuery query) {
        return given()
                .auth()
                .oauth2(BEARER_TOKEN)
                .body(query)
                .when()
                .post(URL)
                .then()
                .extract()
                .body();
    }
}
