package com.projeto.gestaoloja.exception;

public class NegocioException extends RuntimeException{

// para mensagem de exceções no programa, assim posibilita uma mensagem de erro para o usuario, caso ele utilize o sistema de maneira errada.
    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
