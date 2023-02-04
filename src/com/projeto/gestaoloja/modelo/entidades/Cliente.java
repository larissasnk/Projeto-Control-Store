package com.projeto.gestaoloja.modelo.entidades;

import com.projeto.gestaoloja.dao.ClienteDao;
import java.util.List;

public class Cliente extends Pessoa{

    private String cpf;
    private String email;

    public Cliente() {
        super();
    }

    public Cliente(Long id, String nome, String telefone, String endereco, String cpf, String email) {
        super(id, nome, telefone, endereco);
        this.cpf = cpf;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void cadastrarCliente(Cliente cliente){
        new ClienteDao().salvar(cliente);
    }

    public void editarCliente(Cliente cliente){
        new ClienteDao().editar(cliente);
    }

// Aqui há um polimorfismo de sobreescrita, herdando da classe pai (Pessoa) o método deletar;
    @Override
    public void deletar(Long id) {
        new ClienteDao().deletar(id);
    }

    public List<Cliente> listarCliente(){
       return new ClienteDao().listarCliente();
    }

    public Cliente buscarClienteNome(String nome){
        return new ClienteDao().buscarClienteNome(nome);
    }

    public Long listarClienteVenda(Long id){
        return new ClienteDao().listarClienteVenda(id);
    }
    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
