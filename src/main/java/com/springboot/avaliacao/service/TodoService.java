package com.springboot.avaliacao.service;

import javax.validation.ConstraintViolationException;

import com.springboot.avaliacao.Exeception.TodoCollectionExeception;
import com.springboot.avaliacao.model.TodoDTO;



public interface TodoService {

    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionExeception;
    
}