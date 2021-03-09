package com.springboot.avaliacao.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.springboot.avaliacao.model.TodoDTO;
import com.springboot.avaliacao.repository.TodoRepository;
import com.springboot.avaliacao.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TodoController
 */

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoRepo.findAll();
        if (todos.size() > 0) {
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
        }

    }

    // criando endpoint para salvar registro no Banco
    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
            return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // localizar atraves do id
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // update
    @PutMapping("/todos/{id}")
    public ResponseEntity<?> UpdateById(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if (todoOptional.isPresent()) {
            TodoDTO todoToSave = todoOptional.get();
            todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToSave.getCompleted());
            todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription(
                    todo.getDescription() != null ? todo.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdateAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToSave);
            return new ResponseEntity<>(todoToSave, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Id não encontrado!" + id, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            todoRepo.deleteById(id);
            return new ResponseEntity<>("id deletado com sucesso!" + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}