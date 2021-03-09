package com.springboot.avaliacao.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.springboot.avaliacao.Exeception.TodoCollectionExeception;
import com.springboot.avaliacao.model.TodoDTO;



public interface TodoService {

    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionExeception;

    public List<TodoDTO> getAllTodos();

    public TodoDTO getSingleTodo(String id) throws TodoCollectionExeception;

    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionExeception;
    
    
}