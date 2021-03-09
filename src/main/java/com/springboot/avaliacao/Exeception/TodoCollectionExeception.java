
package com.springboot.avaliacao.Exeception;

public class TodoCollectionExeception extends Exception {

    private static final long serialVersionUID = 1L;

    public TodoCollectionExeception(String messege) {
        super(messege);
    }

    public static String NotFoundException(String id) {
        return "Todo with" + id + "Not Found!";
    }

    public static String TodoAlreadyExists() {
        return "Todo with given name already exists";
    }
}
