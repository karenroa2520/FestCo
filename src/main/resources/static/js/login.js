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

  // Opcional: Validación básica al enviar
  document.querySelectorAll('form').forEach(form => {
    form.addEventListener('submit', (e) => {
      e.preventDefault();
      alert(`Formulario de "${form.id.replace('-form', '')}" enviado.`);
      // Aquí iría tu lógica real (fetch, etc.)
    });
  });
});