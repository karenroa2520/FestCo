const form = document.getElementById("formBoleta");
const cantidadInput = document.getElementById("cantidad");
const precioInput = document.getElementById("precio");
const totalSpan = document.getElementById("total");
const mensajeDiv = document.getElementById("mensaje");

// Actualiza el total automáticamente al cambiar la cantidad
cantidadInput.addEventListener("input", () => {
  const cantidad = parseInt(cantidadInput.value) || 0;
  const precio = parseFloat(precioInput.value) || 0;
  const total = cantidad * precio;
  totalSpan.textContent = `$${total.toLocaleString()}`;
});

// Simula el envío de compra
form.addEventListener("submit", (e) => {
  e.preventDefault();

  const evento = document.getElementById("idEvento").value;
  const cantidad = cantidadInput.value;
  const total = cantidad * precioInput.value;

  if (!evento || cantidad <= 0) {
    mostrarMensaje("Por favor completa todos los campos correctamente.", "error");
    return;
  }

  // Aquí iría la llamada al backend (fetch POST /boletas/comprar)
  // Simulación de compra exitosa:
  setTimeout(() => {
    mostrarMensaje(`Compra realizada con éxito 🎉 Total pagado: $${total.toLocaleString()}`, "exito");
    form.reset();
    totalSpan.textContent = "$0";
  }, 800);
});

function mostrarMensaje(texto, tipo) {
  mensajeDiv.textContent = texto;
  mensajeDiv.className = `mensaje ${tipo}`;
  mensajeDiv.style.display = "block";
}
