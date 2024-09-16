package com.example;

import com.example.jupiter.meta.Todo;
import com.example.model.TodoJson;
import com.example.service.TodosService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TodosTest {

    private final TodosService todosService = new TodosService();

    @Test
    @Todo(title = "my-todo")
    void verifyTodoCanBeRetrievedById(TodoJson todo) {
        assertThat(todosService.getTodoTitle(todo.id())).isEqualTo(todo.title());
    }

    @Test
    @Todo(title = "my-todo")
    void verifyAllTodosCanBeRetrieved(TodoJson todo) {
        assertThat(todosService.getTodos()).contains(todo);
    }
}
