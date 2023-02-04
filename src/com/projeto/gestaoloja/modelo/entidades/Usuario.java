package com.projeto.gestaoloja.modelo.entidades;

import com.projeto.gestaoloja.dao.UsuarioDao;
import com.projeto.gestaoloja.dao.VendaDao;
import java.util.List;

public class Usuario extends Pessoa{

    private String senha;
    private String usuario;
    private String cargo;

    public Usuario() {
        super();
    }

    public Usuario(Long id, String nome, String telefone, String endereco, String senha, String usuario, String cargo) {
        super(id, nome, telefone, endereco);
        this.senha = senha;
        this.usuario = usuario;
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void cadastrarUsuario(Usuario usuario){
        new UsuarioDao().salvar(usuario);
    }

    public void editarUsuario(Usuario usuario){
        new UsuarioDao().editar(usuario);
    }

// Aqui há um polimorfismo de sobreescrita, herdando da classe pai (Pessoa) o método deletar;
    @Override
    public void deletar(Long id) {
        new UsuarioDao().deletar(id);
    }

    public List<Usuario> listarUsuario(){
        return new UsuarioDao().listarUsuario();
    }

    public Usuario buscarUsuarioNome(String nome){
         return new UsuarioDao().buscarUsuarioNome(nome);
    }

    public void vender(Venda venda){
        new VendaDao().salvar(venda);
    }

    public Long listarUsuarioVenda(Long id){
        return new UsuarioDao().listarUsuarioVenda(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "senha='" + senha + '\'' +
                ", usuario='" + usuario + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
