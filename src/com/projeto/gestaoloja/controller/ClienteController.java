package com.projeto.gestaoloja.controller;

import com.projeto.gestaoloja.modelo.entidades.Cliente;
import java.util.List;

public class ClienteController {

    public int cadastrarCliente(Cliente cliente){
        cliente.cadastrarCliente(cliente);
        return 1;
    }

    public int editarCliente(Cliente cliente){
        cliente.editarCliente(cliente);
        return 1;
    }

    public int deletar(Long id){
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.deletar(id);
        return 1;
    }

    public List<Cliente> listarClientes(){
        return new Cliente().listarCliente();
    }

    public Cliente buscarClienteNome(String nome){
        return new Cliente().buscarClienteNome(nome);
    }

    public Long listarClienteVenda(Long id){
        return new Cliente().listarClienteVenda(id);
    }
}
