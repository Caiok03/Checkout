package org.example.controller;

import org.example.models.Usuario;
import org.example.repository.UsuarioRepository;

public class LoginController {
    private UsuarioRepository usuarioRepository;

    public LoginController() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public boolean realizarLogin(String nomeUsuario, String senha) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorNome(nomeUsuario);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            return true;
        }

        return false;
    }
}



