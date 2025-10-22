// admin.js – Versión completa para admin-eventos.html

document.addEventListener('DOMContentLoaded', () => {
  if (!document.getElementById('eventoForm')) return;

  // --- DATOS DE COLOMBIA ---
  const departamentosMunicipios = {
    "Amazonas": ["Leticia", "Puerto Nariño"],
    "Antioquia": ["Medellín", "Bello", "Itagüí", "Envigado", "Rionegro", "Santa Fe de Antioquia"],
    "Arauca": ["Arauca", "Saravena", "Tame"],
    "Atlántico": ["Barranquilla", "Soledad", "Baranoa", "Puerto Colombia"],
    "Bolívar": ["Cartagena", "Magangué", "El Carmen de Bolívar", "Turbaco"],
    "Boyacá": ["Tunja", "Duitama", "Sogamoso", "Chiquinquirá"],
    "Caldas": ["Manizales", "La Dorada", "Chinchiná", "Villamaría"],
    "Caquetá": ["Florencia", "San José del Fragua"],
    "Casanare": ["Yopal", "Aguazul", "Paz de Ariporo"],
    "Cauca": ["Popayán", "Santander de Quilichao", "Piendamó"],
    "Cesar": ["Valledupar", "Aguachica", "La Paz"],
    "Chocó": ["Quibdó", "Istmina", "Bahía Solano"],
    "Córdoba": ["Montería", "Cereté", "Sahagún", "Tierralta"],
    "Cundinamarca": ["Bogotá", "Facatativá", "Girardot", "Zipaquirá", "Chía"],
    "Guainía": ["Inírida"],
    "Guaviare": ["San José del Guaviare"],
    "Huila": ["Neiva", "Pitalito", "Garzón"],
    "La Guajira": ["Riohacha", "Maicao", "Uribia"],
    "Magdalena": ["Santa Marta", "Ciénaga", "Fundación", "El Banco"],
    "Meta": ["Villavicencio", "Granada", "Acacías"],
    "Nariño": ["Pasto", "Tumaco", "Ipiales", "Túquerres"],
    "Norte de Santander": ["Cúcuta", "Ocaña", "Pamplona", "Los Patios"],
    "Putumayo": ["Mocoa", "Puerto Asís", "Villagarzón"],
    "Quindío": ["Armenia", "Calarcá", "La Tebaida"],
    "Risaralda": ["Pereira", "Dosquebradas", "Santa Rosa de Cabal"],
    "San Andrés y Providencia": ["San Andrés", "Providencia"],
    "Santander": ["Bucaramanga", "Floridablanca", "Piedecuesta", "Barrancabermeja"],
    "Sucre": ["Sincelejo", "Corozal", "Sampués"],
    "Tolima": ["Ibagué", "Espinal", "Chaparral"],
    "Valle del Cauca": ["Cali", "Buenaventura", "Palmira", "Jamundí", "Tuluá"],
    "Vaupés": ["Mitú"],
    "Vichada": ["Puerto Carreño"]
  };

  const artistasDisponibles = [
    { id: '1', nombre: 'Carlos Vives', genero: 'Vallenato', ciudad: 'Santa Marta' },
    { id: '2', nombre: 'Shakira', genero: 'Pop', ciudad: 'Barranquilla' },
    { id: '3', nombre: 'Silvestre Dangond', genero: 'Vallenato', ciudad: 'Valledupar' }
  ];

  let localidades = [];
  let artistasAsignados = [];
  let proximoIdArtista = 100; // para nuevos artistas

  // --- LLENAR DEPARTAMENTOS ---
  const depSelect = document.getElementById('departamento');
  const munSelect = document.getElementById('municipio');

  Object.keys(departamentosMunicipios).sort().forEach(dep => {
    const opt = document.createElement('option');
    opt.value = dep;
    opt.textContent = dep;
    depSelect.appendChild(opt);
  });

  depSelect.addEventListener('change', () => {
    const dep = depSelect.value;
    munSelect.innerHTML = '<option value="">Selecciona un municipio</option>';
    if (dep) {
      departamentosMunicipios[dep].sort().forEach(mun => {
        const opt = document.createElement('option');
        opt.value = mun;
        opt.textContent = mun;
        munSelect.appendChild(opt);
      });
      munSelect.disabled = false;
    } else {
      munSelect.disabled = true;
    }
  });

  // --- LOCALIDADES ---
  document.getElementById('localidadForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const nombre = document.getElementById('nombreLocalidad').value.trim();
    const precio = parseFloat(document.getElementById('precioLocalidad').value);
    const cantidad = parseInt(document.getElementById('cantidadLocalidad').value);

    if (!nombre || isNaN(precio) || isNaN(cantidad) || precio < 0 || cantidad <= 0) {
      alert('Completa correctamente los campos de la localidad.');
      return;
    }

    localidades.push({ nombre, precio, cantidad });
    renderLocalidades();
    document.getElementById('localidadForm').reset();
  });

  // --- ARTISTAS ---
  function cargarArtistasEnSelect() {
    const select = document.getElementById('selectArtista');
    select.innerHTML = '';
    [...artistasDisponibles].sort((a, b) => a.nombre.localeCompare(b.nombre)).forEach(art => {
      const opt = document.createElement('option');
      opt.value = art.id;
      opt.textContent = `${art.nombre} (${art.genero} - ${art.ciudad})`;
      select.appendChild(opt);
    });
  }

  // Registrar nuevo artista
  document.getElementById('nuevoArtistaForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const nombre = document.getElementById('nombreNuevoArtista').value.trim();
    const genero = document.getElementById('generoNuevoArtista').value.trim();
    const ciudad = document.getElementById('ciudadNuevoArtista').value.trim();

    if (!nombre || !genero || !ciudad) {
      alert('Completa todos los campos del nuevo artista.');
      return;
    }

    const nuevo = {
      id: `nuevo-${proximoIdArtista++}`,
      nombre,
      genero,
      ciudad
    };

    artistasDisponibles.push(nuevo);
    cargarArtistasEnSelect();
    document.getElementById('nuevoArtistaForm').reset();
    alert(`Artista "${nombre}" agregado a la lista.`);
  });

  // Agregar artistas seleccionados al evento
  document.getElementById('btnAgregarArtista').addEventListener('click', () => {
    const select = document.getElementById('selectArtista');
    const selected = Array.from(select.selectedOptions);

    if (selected.length === 0) {
      alert('Selecciona al menos un artista.');
      return;
    }

    selected.forEach(opt => {
      const art = artistasDisponibles.find(a => a.id === opt.value);
      if (art && !artistasAsignados.some(a => a.id === art.id)) {
        artistasAsignados.push({ ...art });
      }
    });

    renderArtistasAsignados();
    // Deseleccionar
    Array.from(select.options).forEach(o => o.selected = false);
  });

  // --- GUARDAR EVENTO ---
  document.getElementById('btnGuardarEvento').addEventListener('click', () => {
    const nombre = document.getElementById('nombreEvento').value.trim();
    const dep = depSelect.value;
    const mun = munSelect.value;
    const fIni = document.getElementById('fechaInicio').value;
    const hIni = document.getElementById('horaInicio').value;
    const fFin = document.getElementById('fechaFin').value;
    const hFin = document.getElementById('horaFin').value;

    if (!nombre || !dep || !mun || !fIni || !hIni || !fFin || !hFin) {
      alert('Por favor, completa todos los datos del evento.');
      return;
    }

    if (new Date(fFin + 'T' + hFin) <= new Date(fIni + 'T' + hIni)) {
      alert('La fecha y hora de fin deben ser posteriores al inicio.');
      return;
    }

    if (localidades.length === 0) {
      alert('Debes agregar al menos una localidad.');
      return;
    }

    if (artistasAsignados.length === 0) {
      alert('Debes asignar al menos un artista al evento.');
      return;
    }

    alert(`🎉 Evento "${nombre}" registrado con éxito!\n📍 ${mun}, ${dep}\n📅 ${fIni} ${hIni} → ${fFin} ${hFin}\n🎟️ ${localidades.length} localidades\n🎤 ${artistasAsignados.length} artistas`);
  });

  // --- RENDERIZADO ---
  function renderLocalidades() {
    const tbody = document.querySelector('#tablaLocalidades tbody');
    tbody.innerHTML = '';
    localidades.forEach((loc, i) => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${loc.nombre}</td>
        <td>$${loc.precio.toLocaleString('es-CO')}</td>
        <td>${loc.cantidad}</td>
        <td><button class="btn small danger" data-index="${i}">Eliminar</button></td>
      `;
      tbody.appendChild(tr);
    });
    tbody.querySelectorAll('.btn.danger').forEach(btn => {
      btn.addEventListener('click', () => {
        const i = btn.dataset.index;
        localidades.splice(i, 1);
        renderLocalidades();
      });
    });
  }

  function renderArtistasAsignados() {
    const tbody = document.querySelector('#tablaArtistasEvento tbody');
    tbody.innerHTML = '';
    artistasAsignados.forEach((art, i) => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${art.nombre}</td>
        <td>${art.genero}</td>
        <td>${art.ciudad}</td>
        <td><button class="btn small danger" data-index="${i}">Eliminar</button></td>
      `;
      tbody.appendChild(tr);
    });
    tbody.querySelectorAll('.btn.danger').forEach(btn => {
      btn.addEventListener('click', () => {
        const i = btn.dataset.index;
        artistasAsignados.splice(i, 1);
        renderArtistasAsignados();
      });
    });
  }

  // --- INICIALIZAR ---
  cargarArtistasEnSelect();

  // Cerrar sesión
  const logoutBtn = document.getElementById('logoutBtn');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
      if (confirm('¿Deseas cerrar sesión?')) {
        alert('Sesión cerrada.');
        // Redirigir en producción
      }
    });
  }
});