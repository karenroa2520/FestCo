document.addEventListener('DOMContentLoaded', () => {
  const tabButtons = document.querySelectorAll('.tab-btn');
  const forms = document.querySelectorAll('.auth-form');

  // Manejo de pestañas
  tabButtons.forEach(button => {
    button.addEventListener('click', () => {
      // Remover clase activa de todos
      tabButtons.forEach(btn => btn.classList.remove('active'));
      forms.forEach(form => form.classList.remove('active'));

      // Agregar clase activa al botón clickeado
      button.classList.add('active');

      // Mostrar el formulario correspondiente
      const targetForm = button.dataset.form;
      document.getElementById(`${targetForm}-form`).classList.add('active');
    });
  });

  // Manejo del formulario de login
  const loginForm = document.getElementById('login-form');
  if (loginForm) {
    loginForm.addEventListener('submit', handleLogin);
  }

  // Manejo del formulario de registro
  const registerForm = document.getElementById('register-form');
  if (registerForm) {
    registerForm.addEventListener('submit', handleRegister);
  }
});

// Función para manejar el login
async function handleLogin(e) {
  e.preventDefault();
  
  const email = document.getElementById('login-email').value;
  const password = document.getElementById('login-password').value;
  
  if (!email || !password) {
    alert('Por favor, completa todos los campos');
    return;
  }
  
  try {
    const response = await fetch('/api/usuario/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        correo: email,
        contrasena: password
      })
    });
    
    const data = await response.json();
    
    if (data.success) {
      alert('¡Inicio de sesión exitoso!');
      // Guardar información del usuario en localStorage
      localStorage.setItem('usuario', JSON.stringify(data.usuario));
      // Redirigir a la página principal
      window.location.href = '/';
    } else {
      alert(data.message || 'Error al iniciar sesión');
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error de conexión. Inténtalo de nuevo.');
  }
}

// Función para manejar el registro
async function handleRegister(e) {
  e.preventDefault();
  
  const docType = document.getElementById('doc-type').value;
  const docNumber = document.getElementById('doc-number').value;
  const fullName = document.getElementById('full-name').value;
  const email = document.getElementById('email').value;
  const phone = document.getElementById('phone').value;
  const password = document.getElementById('password').value;
  
  // Validaciones básicas
  if (!docType || !docNumber || !fullName || !email || !phone || !password) {
    alert('Por favor, completa todos los campos');
    return;
  }
  
  if (password.length < 6) {
    alert('La contraseña debe tener al menos 6 caracteres');
    return;
  }
  
  // Separar nombres y apellidos
  const nameParts = fullName.trim().split(' ');
  const nombres = nameParts[0] || '';
  const apellidos = nameParts.slice(1).join(' ') || '';
  
  try {
    const response = await fetch('/api/usuario/registro', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        numDocumento: parseInt(docNumber),
        tipoDocumentoId: parseInt(docType),
        nombres: nombres,
        apellidos: apellidos,
        correo: email,
        contrasena: password,
        numTelefonico: parseInt(phone)
      })
    });
    
    const data = await response.json();
    
    if (data.success) {
      alert('¡Usuario registrado exitosamente!');
      // Limpiar formulario
      document.getElementById('register-form').reset();
      // Cambiar a pestaña de login
      document.querySelector('[data-form="login"]').click();
    } else {
      alert(data.message || 'Error al registrar usuario');
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error de conexión. Inténtalo de nuevo.');
  }
}