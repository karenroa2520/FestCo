package com.senasoft.ferias.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senasoft.ferias.Entity.Usuario;
import com.senasoft.ferias.Repository.Usuario_Repository;
import com.senasoft.ferias.service.Usuario_Service;

@Service
@Transactional
public class Usuario_ServiceImpl implements Usuario_Service {

    @Autowired
    private Usuario_Repository usuarioRepository;


    // Gestión de usuarios
    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(Long numDocumento) {
        return usuarioRepository.findById(numDocumento);
    }

    @Override
    public Optional<Usuario> getUsuarioByCorreo(String correo) {
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getCorreo().equals(correo))
                .findFirst();
    }

    @Override
    public boolean validarCredenciales(String correo, String contrasena) {
        Optional<Usuario> usuario = getUsuarioByCorreo(correo);
        if (usuario.isPresent()) {
            return usuario.get().getContrasena().equals(contrasena);
        }
        return false;
    }

    @Override
    public boolean existeUsuarioPorCorreo(String correo) {
        return getUsuarioByCorreo(correo).isPresent();
    }

    @Override
    public boolean existeUsuarioPorDocumento(Long numDocumento) {
        return usuarioRepository.existsById(numDocumento);
    }

    @Override
    public void deleteUsuario(Long numDocumento) {
        usuarioRepository.deleteById(numDocumento);
    }
}
