
package com.springboot.avaliacao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.avaliacao.Exeception.TodoCollectionExeception;
import com.springboot.avaliacao.model.TodoDTO;
import com.springboot.avaliacao.repository.TodoRepository;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepo;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionExeception {
        Optional<TodoDTO> todoOptional = todoRepo.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()) {
            throw new TodoCollectionExeception(TodoCollectionExeception.TodoAlreadyExists());
        } else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
        }

    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos = todoRepo.findAll();
        if (todos.size() > 0) {
            return todos;
        } else {
            return new ArrayList<TodoDTO>();
        }
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionExeception {
        Optional<TodoDTO> optionalTodo = todoRepo.findById(id);
        if (!optionalTodo.isPresent()) {
            throw new TodoCollectionExeception(TodoCollectionExeception.NotFoundException(id));
        } else {
            return optionalTodo.get();
        }
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionExeception {
        Optional<TodoDTO> todoWithId = todoRepo.findById(id);
        Optional<TodoDTO> todoWithSameName = todoRepo.findByTodo(todo.getTodo());
        if (todoWithId.isPresent()) {

            if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
                throw new TodoCollectionExeception(TodoCollectionExeception.TodoAlreadyExists());
            }

            TodoDTO todoToUpdate = todoWithId.get();

            todoToUpdate.setTodo(todo.getTodo());
            todoToUpdate.setDescription(todo.getDescription());
            todoToUpdate.setCompleted(todo.getCompleted());
            todoToUpdate.setUpdateAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToUpdate);

        } else {
            throw new TodoCollectionExeception(TodoCollectionExeception.NotFoundException(id));
        }

    }

}
