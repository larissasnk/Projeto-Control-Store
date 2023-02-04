package com.projeto.gestaoloja.controller;

import com.projeto.gestaoloja.modelo.entidades.Usuario;
import com.projeto.gestaoloja.modelo.entidades.Venda;

import java.util.List;

public class UsuarioController {

    public int cadastrarUsuario(Usuario usuario){
        usuario.cadastrarUsuario(usuario);
        return 1;
    }

    public int editarUsuario(Usuario usuario){
        usuario.editarUsuario(usuario);
        return 1;
    }

    public int deletar(Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.deletar(id);
        return 1;
    }

    public List<Usuario> listarUsuario(){
        return new Usuario().listarUsuario();
    }

    public Usuario buscarUsuarioNome(String nome){
        return new Usuario().buscarUsuarioNome(nome);
    }

    public Long listarUsuarioVenda(Long id){
        return new Usuario().listarUsuarioVenda(id);
    }

    public int vender(Venda venda){
        new Usuario().vender(venda);
        return 1;
    }
}
