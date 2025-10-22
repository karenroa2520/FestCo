document.addEventListener('DOMContentLoaded', () => {
  const tabButtons = document.querySelectorAll('.tab-btn');
  const forms = document.querySelectorAll('.auth-form');

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

  // Los formularios ahora se envían directamente al servidor
  // No necesitamos prevenir el comportamiento por defecto
});