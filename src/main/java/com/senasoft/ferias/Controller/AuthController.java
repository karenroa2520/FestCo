package com.senasoft.ferias.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senasoft.ferias.Entity.Tipo_Documento;
import com.senasoft.ferias.Entity.Usuario;
import com.senasoft.ferias.Repository.Tipo_Documento_Repository;
import com.senasoft.ferias.Repository.Usuario_Repository;

@Controller
public class AuthController {

    @Autowired
    private Usuario_Repository usuarioRepository;
    
    @Autowired
    private Tipo_Documento_Repository tipoDocumentoRepository;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("tiposDocumento", tipoDocumentoRepository.findAll());
        return "usuario/usuario";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password,
                       RedirectAttributes redirectAttributes) {
        
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(email);
        
        if (usuario.isPresent() && usuario.get().getContrasena().equals(password)) {
            // Login exitoso - aquí podrías crear una sesión
            redirectAttributes.addFlashAttribute("success", "Login exitoso");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
            return "redirect:/login";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String docType,
                          @RequestParam Long docNumber,
                          @RequestParam String fullName,
                          @RequestParam String email,
                          @RequestParam Long phone,
                          @RequestParam String password,
                          RedirectAttributes redirectAttributes) {
        
        try {
            // Verificar si el usuario ya existe
            if (usuarioRepository.existsById(docNumber)) {
                redirectAttributes.addFlashAttribute("error", "El número de documento ya está registrado");
                return "redirect:/login";
            }
            
            if (usuarioRepository.findByCorreo(email).isPresent()) {
                redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está registrado");
                return "redirect:/login";
            }

            // Crear nuevo usuario
            Usuario usuario = new Usuario();
            usuario.setNumDocumento(docNumber);
            
            // Buscar tipo de documento
            Optional<Tipo_Documento> tipoDoc = tipoDocumentoRepository.findById(Long.parseLong(docType));
            tipoDoc.ifPresent(usuario::setTipoDocumento);
            
            // Separar nombre y apellidos
            String[] nombres = fullName.split(" ", 2);
            usuario.setNombres(nombres[0]);
            if (nombres.length > 1) {
                usuario.setApellidos(nombres[1]);
            }
            
            usuario.setCorreo(email);
            usuario.setContrasena(password);
            usuario.setNumTelefonico(phone);
            
            usuarioRepository.save(usuario);
            
            redirectAttributes.addFlashAttribute("success", "Registro exitoso. Ya puedes iniciar sesión.");
            return "redirect:/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error en el registro: " + e.getMessage());
            return "redirect:/login";
        }
    }
}
