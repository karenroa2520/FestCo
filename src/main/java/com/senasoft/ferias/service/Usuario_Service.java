package com.senasoft.ferias.service;

import java.util.List;
import java.util.Optional;

import com.senasoft.ferias.Entity.Usuario;

public interface Usuario_Service {

    // Gestión de Usuarios
    Usuario saveUsuario(Usuario usuario);
    List<Usuario> getAllUsuarios();
    Optional<Usuario> getUsuarioById(Long numDocumento);
    Optional<Usuario> getUsuarioByCorreo(String correo);
    boolean validarCredenciales(String correo, String contrasena);
    boolean existeUsuarioPorCorreo(String correo);
    boolean existeUsuarioPorDocumento(Long numDocumento);
    void deleteUsuario(Long numDocumento);
}
