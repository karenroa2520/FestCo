package com.senasoft.ferias.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senasoft.ferias.Entity.Tipo_Documento;
import com.senasoft.ferias.Entity.Usuario;
import com.senasoft.ferias.Repository.Tipo_Documento_Repository;
import com.senasoft.ferias.service.Usuario_Service;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioComtroller {

    @Autowired
    private Usuario_Service usuarioService;

    @Autowired
    private Tipo_Documento_Repository tipoDocumentoRepository;

    //Registro de usuario
    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrarUsuario(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Extraer datos del request
            Long numDocumento = Long.valueOf(request.get("numDocumento").toString());
            Long tipoDocumentoId = Long.valueOf(request.get("tipoDocumentoId").toString());
            String nombres = request.get("nombres").toString();
            String apellidos = request.get("apellidos").toString();
            String correo = request.get("correo").toString();
            String contrasena = request.get("contrasena").toString();
            Long numTelefonico = Long.valueOf(request.get("numTelefonico").toString());

            // Validaciones
            if (usuarioService.existeUsuarioPorDocumento(numDocumento)) {
                response.put("success", false);
                response.put("message", "Ya existe un usuario con este número de documento");
                return ResponseEntity.badRequest().body(response);
            }

            if (usuarioService.existeUsuarioPorCorreo(correo)) {
                response.put("success", false);
                response.put("message", "Ya existe un usuario con este correo electrónico");
                return ResponseEntity.badRequest().body(response);
            }

            // Validar que el tipo de documento existe
            Optional<Tipo_Documento> tipoDocumento = tipoDocumentoRepository.findById(tipoDocumentoId);
            if (!tipoDocumento.isPresent()) {
                response.put("success", false);
                response.put("message", "El tipo de documento especificado no existe");
                return ResponseEntity.badRequest().body(response);
            }

            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNumDocumento(numDocumento);
            nuevoUsuario.setTipoDocumento(tipoDocumento.get());
            nuevoUsuario.setNombres(nombres);
            nuevoUsuario.setApellidos(apellidos);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setNumTelefonico(numTelefonico);

            // Guardar usuario
            Usuario usuarioGuardado = usuarioService.saveUsuario(nuevoUsuario);

            response.put("success", true);
            response.put("message", "Usuario registrado exitosamente");
            response.put("usuario", Map.of(
                "numDocumento", usuarioGuardado.getNumDocumento(),
                "nombres", usuarioGuardado.getNombres(),
                "apellidos", usuarioGuardado.getApellidos(),
                "correo", usuarioGuardado.getCorreo(),
                "numTelefonico", usuarioGuardado.getNumTelefonico()
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al registrar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Inicio de sesión
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> iniciarSesion(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String correo = request.get("correo").toString();
            String contrasena = request.get("contrasena").toString();

            // Validar credenciales
            if (usuarioService.validarCredenciales(correo, contrasena)) {
                Optional<Usuario> usuario = usuarioService.getUsuarioByCorreo(correo);
                
                if (usuario.isPresent()) {
                    response.put("success", true);
                    response.put("message", "Inicio de sesión exitoso");
                    response.put("usuario", Map.of(
                        "numDocumento", usuario.get().getNumDocumento(),
                        "nombres", usuario.get().getNombres(),
                        "apellidos", usuario.get().getApellidos(),
                        "correo", usuario.get().getCorreo(),
                        "numTelefonico", usuario.get().getNumTelefonico(),
                        "tipoDocumento", usuario.get().getTipoDocumento().getDocumento()
                    ));
                    return ResponseEntity.ok(response);
                }
            }

            response.put("success", false);
            response.put("message", "Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al iniciar sesión: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // Obtener usuario por id
    @GetMapping("/{numDocumento}")
    public ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable Long numDocumento) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Usuario> usuario = usuarioService.getUsuarioById(numDocumento);
            
            if (usuario.isPresent()) {
                response.put("success", true);
                response.put("usuario", Map.of(
                    "numDocumento", usuario.get().getNumDocumento(),
                    "nombres", usuario.get().getNombres(),
                    "apellidos", usuario.get().getApellidos(),
                    "correo", usuario.get().getCorreo(),
                    "numTelefonico", usuario.get().getNumTelefonico(),
                    "tipoDocumento", usuario.get().getTipoDocumento().getDocumento()
                ));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // Obtener tipos de documento
    @GetMapping("/tipos-documento")
    public ResponseEntity<Map<String, Object>> obtenerTiposDocumento() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            response.put("success", true);
            response.put("tiposDocumento", tipoDocumentoRepository.findAll());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener tipos de documento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
