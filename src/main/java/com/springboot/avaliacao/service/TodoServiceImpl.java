
package com.springboot.avaliacao.service;

import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.springboot.avaliacao.Exeception.TodoCollectionExeception;
import com.springboot.avaliacao.model.TodoDTO;
import com.springboot.avaliacao.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepo;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionExeception{
        Optional<TodoDTO> todoOptional = todoRepo.findByTodo(todo.getTodo());
        if(todoOptional.isPresent()){
            throw new TodoCollectionExeception(TodoCollectionExeception.TodoAlreadyExists());
        }else{
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
        }

    }

    
    
}
