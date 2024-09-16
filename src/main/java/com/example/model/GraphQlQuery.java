package com.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.springframework.core.io.ClassPathResource;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.nio.charset.StandardCharsets.UTF_8;

@Getter
@Setter
@JsonInclude(value = NON_NULL)
public class GraphQlQuery {

    private final String query;

    @Accessors(chain = true)
    private Object variables;

    public GraphQlQuery(String pathToQueryFile) {
        this.query = getQueryByFileName(pathToQueryFile);
    }

    @SneakyThrows
    private String getQueryByFileName(String fileName) {
        return new ClassPathResource(fileName).getContentAsString(UTF_8);
    }
}
