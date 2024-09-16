package com.example.jupiter.meta;

import com.example.jupiter.TodoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
@ExtendWith(TodoExtension.class)
public @interface Todo {

    String title();
}
