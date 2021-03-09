package com.springboot.avaliacao.repository;

import java.util.Optional;

import com.springboot.avaliacao.model.TodoDTO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * TodoRepository
 */

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO, String> {

    @Query("{'todo: ?0'}")
    Optional<TodoDTO> findByTodo(String todo);
}