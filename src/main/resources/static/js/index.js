document.addEventListener('DOMContentLoaded', () => {
  // Verificar si hay un usuario logueado
  const usuario = localStorage.getItem('usuario');
  
  if (usuario) {
    const datosUsuario = JSON.parse(usuario);
    mostrarUsuarioLogueado(datosUsuario);
  }
});

function mostrarUsuarioLogueado(usuario) {
  // Crear elemento para mostrar información del usuario
  const nav = document.querySelector('nav');
  if (nav) {
    // Remover enlace de "Iniciar Sesión" si existe
    const enlaceLogin = nav.querySelector('a[href="/usuario"]');
    if (enlaceLogin) {
      enlaceLogin.remove();
    }
    
    // Agregar información del usuario
    const usuarioInfo = document.createElement('div');
    usuarioInfo.className = 'usuario-info';
    usuarioInfo.innerHTML = `
      <span>¡Hola, ${usuario.nombres}!</span>
      <button onclick="cerrarSesion()" class="btn-cerrar-sesion">Cerrar Sesión</button>
    `;
    
    // Estilos para la información del usuario
    usuarioInfo.style.cssText = `
      display: flex;
      align-items: center;
      gap: 10px;
      margin-left: auto;
    `;
    
    nav.appendChild(usuarioInfo);
    
    // Estilos para el botón de cerrar sesión
    const btnCerrar = usuarioInfo.querySelector('.btn-cerrar-sesion');
    if (btnCerrar) {
      btnCerrar.style.cssText = `
        background-color: #f44336;
        color: white;
        border: none;
        padding: 8px 16px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
      `;
    }
  }
}

function cerrarSesion() {
  // Remover usuario del localStorage
  localStorage.removeItem('usuario');
  
  // Recargar la página
  window.location.reload();
}



    function verDetalles(eventoId) {
      // Implementar lógica para ver detalles del evento
      alert('Ver detalles del evento: ' + eventoId);
    }

    function comprarBoletas(eventoId) {
      // Implementar lógica para comprar boletas
      alert('Comprar boletas para el evento: ' + eventoId);
    }