package com.example.todo_api.controller;


import com.example.todo_api.dto.PageDto;
import com.example.todo_api.dto.TodoDto;
import com.example.todo_api.entity.Todo;
import com.example.todo_api.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/api/todos")
public class TodoController {


    @Autowired
    private TodoService todoService;


    @GetMapping
    public ResponseEntity<?> getAllTodos(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "0") int size,
                                  @RequestParam(defaultValue = "id,desc") String[] sort
                                  ) {
        Sort.Direction direction= Sort.Direction.fromString(sort[1]);
        Sort sortBy = Sort.by(direction, sort[0]);

        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<Todo> todoPage = todoService.getAllTodos(pageable);

        List<TodoDto> todoDtoList = todoPage.getContent().stream()
                .map(item -> new TodoDto(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageDto<>(todoDtoList, todoPage.getNumber(), todoPage.getSize(), todoPage.getTotalElements(), todoPage.getTotalPages()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Todo> getAllTodos(@PathVariable("id") Long id) {

        Todo todo = todoService.getTodoById(id).orElseThrow(() ->  new RuntimeException("Todo not found"));

        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        log.info("createTodo {}", todo.toString());
        return todoService.createTodo(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long id, @RequestBody Todo todoDetails) {
        Todo updateTodo = todoService.updateTodo(id, todoDetails);
        return ResponseEntity.ok(updateTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }

}
