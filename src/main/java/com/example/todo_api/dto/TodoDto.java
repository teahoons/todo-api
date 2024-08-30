package com.example.todo_api.dto;

import com.example.todo_api.entity.Todo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDto {
    private Long id;
    private String title;
    private boolean complted;

    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.complted = todo.getCompleted();
    }
}
