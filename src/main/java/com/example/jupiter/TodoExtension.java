package com.example.jupiter;

import com.example.jupiter.meta.Todo;
import com.example.model.TodoJson;
import com.example.service.TodosService;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public class TodoExtension implements BeforeEachCallback, ParameterResolver {

    public final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(TodoExtension.class);

    private final TodosService todosService = new TodosService();

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Todo.class)
                .ifPresent(annotation -> {
                    final TodoJson todo = todosService.createTodo(annotation.title());

                    context.getStore(NAMESPACE).put(context.getUniqueId(), todo);
                });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(TodoJson.class)
                && extensionContext.getRequiredTestMethod().isAnnotationPresent(Todo.class);
    }

    @Override
    public TodoJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), TodoJson.class);
    }
}
