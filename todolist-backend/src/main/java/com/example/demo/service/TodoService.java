package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Todo;
import com.example.demo.request.CreateTodoRequest;
import com.example.demo.request.UpdateTodoRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private List<Todo> todos;

    public TodoService() {
        initTodos();
    }

    public void initTodos() {
        todos = new ArrayList<>();
        todos.add(new Todo(1, "Đi đá bóng", false));
        todos.add(new Todo(2, "Làm bài tập", true));
        todos.add(new Todo(3, "Đi chơi với bạn bè", true));
    }

    public List<Todo> getTodos(String status) {
        return switch (status) {
            case "true" -> todos.stream().filter(Todo::isStatus).collect(Collectors.toList());
            case "false" -> todos.stream().filter(p -> !p.isStatus()).collect(Collectors.toList());
            default -> todos;
        };
    }

    public Todo getTodoById(int id) {
        Optional<Todo> todoOptional = findById(id);
        if(todoOptional.isEmpty()) {
            throw new NotFoundException("Not found with id = " + id);
        }
        return todoOptional.get();
    }

    public Todo createTodo(CreateTodoRequest request) {
        // Kiểm tra xem title có trống hay không
        if(request.getTitle() ==  null) {
            throw new BadRequestException("Title can't empty");
        }

        // Tạo todo mới
        Random rd = new Random();
        Todo todo = new Todo(rd.nextInt(1000), request.getTitle(), false);

        todos.add(todo);
        return todo;
    }

    public Todo updateTodo(int id, UpdateTodoRequest request) {
        Optional<Todo> todoOptional = findById(id);
        if(todoOptional.isEmpty()) {
            throw new NotFoundException("Not found with id = " + id);
        }

        Todo todo = todoOptional.get();
        todo.setTitle(request.getTitle());
        todo.setStatus(request.isStatus());

        return todo;
    }

    public void deleteTodo(int id) {
        Optional<Todo> todoOptional = findById(id);
        if(todoOptional.isEmpty()) {
            throw new NotFoundException("Not found with id = " + id);
        }
        todos.removeIf(todo -> todo.getId() == id);
    }

    Optional<Todo> findById(int id) {
        return todos.stream().filter(todo -> todo.getId() == id).findFirst();
    }
}
