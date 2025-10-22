// Funcionalidad para el formulario de administrador
document.addEventListener('DOMContentLoaded', function() {
    
    // Variables globales
    let localidades = [];
    let artistas = [];
    let artistasSeleccionados = [];

    // Cargar departamentos al iniciar (ya están cargados por Thymeleaf)
    // Solo necesitamos cargar municipios cuando cambie el departamento
    cargarLocalidades();
    cargarArtistas();

    // Manejar cambio de departamento
    document.getElementById('departamento').addEventListener('change', function() {
        const departamentoId = this.value;
        if (departamentoId) {
            cargarMunicipios(departamentoId);
        } else {
            document.getElementById('municipio').innerHTML = '<option value="">Primero elige un departamento</option>';
            document.getElementById('municipio').disabled = true;
        }
    });

    // Manejar formulario de localidad
    document.getElementById('localidadForm').addEventListener('submit', function(e) {
        e.preventDefault();
        agregarLocalidad();
    });

    // Manejar formulario de nuevo artista
    document.getElementById('nuevoArtistaForm').addEventListener('submit', function(e) {
        e.preventDefault();
        agregarNuevoArtista();
    });

    // Manejar botón de agregar artista seleccionado
    document.getElementById('btnAgregarArtista').addEventListener('click', function() {
        agregarArtistasSeleccionados();
    });

    // Manejar botón principal de guardar evento
    document.getElementById('btnGuardarEvento').addEventListener('click', function() {
        guardarEventoCompleto();
    });

    // Funciones auxiliares

    function cargarMunicipios(departamentoId) {
        fetch(`/api/municipios?departamentoId=${departamentoId}`)
            .then(response => response.json())
            .then(data => {
                const select = document.getElementById('municipio');
                select.innerHTML = '<option value="">Selecciona un municipio</option>';
                data.forEach(municipio => {
                    const option = document.createElement('option');
                    option.value = municipio.id;
                    option.textContent = municipio.municipio;
                    select.appendChild(option);
                });
                select.disabled = false;
            })
            .catch(error => console.error('Error cargando municipios:', error));
    }

    function cargarLocalidades() {
        fetch('/api/localidades')
            .then(response => response.json())
            .then(data => {
                localidades = data;
            })
            .catch(error => console.error('Error cargando localidades:', error));
    }

    function cargarArtistas() {
        // Los artistas ya están cargados por Thymeleaf en el select
        // Solo necesitamos obtener los datos del DOM
        const selectArtista = document.getElementById('selectArtista');
        artistas = Array.from(selectArtista.options).map(option => ({
            id: option.value,
            nombre: option.textContent.split(' - ')[0],
            generoMusical: option.textContent.split(' - ')[1] || '',
            ciudadNatal: '' // No tenemos esta info en el select
        }));
    }

    function actualizarSelectArtistas() {
    const select = document.getElementById('selectArtista');
    select.innerHTML = '';
        artistas.forEach(artista => {
            const option = document.createElement('option');
            option.value = artista.id;
            option.textContent = `${artista.nombre} - ${artista.generoMusical}`;
            select.appendChild(option);
        });
    }

    function agregarLocalidad() {
        const nombre = document.getElementById('nombreLocalidad').value;
        const precio = parseFloat(document.getElementById('precioLocalidad').value);
        const cantidad = parseInt(document.getElementById('cantidadLocalidad').value);

        if (nombre && precio >= 0 && cantidad > 0) {
            const localidad = {
                nombre: nombre,
                precio: precio,
                cantidad: cantidad
            };

            localidades.push(localidad);
            actualizarTablaLocalidades();
            
            // Limpiar formulario
            document.getElementById('localidadForm').reset();
        }
    }

    function actualizarTablaLocalidades() {
        const tbody = document.querySelector('#tablaLocalidades tbody');
        tbody.innerHTML = '';
        
        localidades.forEach((localidad, index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${localidad.nombre}</td>
                <td>$${localidad.precio.toLocaleString()}</td>
                <td>${localidad.cantidad}</td>
                <td><button onclick="eliminarLocalidad(${index})" class="btn small danger">Eliminar</button></td>
            `;
            tbody.appendChild(row);
        });
    }

    function eliminarLocalidad(index) {
        localidades.splice(index, 1);
        actualizarTablaLocalidades();
    }

    function agregarNuevoArtista() {
        const nombre = document.getElementById('nombreNuevoArtista').value;
        const genero = document.getElementById('generoNuevoArtista').value;
        const ciudad = document.getElementById('ciudadNuevoArtista').value;

        if (nombre && genero && ciudad) {
            const nuevoArtista = {
                nombre: nombre,
                generoMusical: genero,
                ciudadNatal: ciudad
            };

            // Enviar al servidor
            fetch('/api/artista', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(nuevoArtista)
            })
            .then(response => response.json())
            .then(data => {
                artistas.push(data);
                actualizarSelectArtistas();
                actualizarTablaArtistasEvento();
                
                // Limpiar formulario
                document.getElementById('nuevoArtistaForm').reset();
            })
            .catch(error => console.error('Error creando artista:', error));
        }
    }

    function agregarArtistasSeleccionados() {
        const select = document.getElementById('selectArtista');
        const opcionesSeleccionadas = Array.from(select.selectedOptions);
        
        opcionesSeleccionadas.forEach(option => {
            const artista = artistas.find(a => a.id == option.value);
            if (artista && !artistasSeleccionados.find(a => a.id === artista.id)) {
                artistasSeleccionados.push(artista);
            }
        });
        
        actualizarTablaArtistasEvento();
    }

    function actualizarTablaArtistasEvento() {
        const tbody = document.querySelector('#tablaArtistasEvento tbody');
        tbody.innerHTML = '';
        
        artistasSeleccionados.forEach((artista, index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${artista.nombre}</td>
                <td>${artista.generoMusical}</td>
                <td>${artista.ciudadNatal}</td>
                <td><button onclick="eliminarArtistaSeleccionado(${index})" class="btn small danger">Eliminar</button></td>
            `;
            tbody.appendChild(row);
        });
    }

    function eliminarArtistaSeleccionado(index) {
        artistasSeleccionados.splice(index, 1);
        actualizarTablaArtistasEvento();
    }

    function guardarEventoCompleto() {
        // Validar formulario principal
        const nombreEvento = document.getElementById('nombreEvento').value;
        const descripcionEvento = document.getElementById('descripcionEvento').value;
        const fechaInicio = document.getElementById('fechaInicio').value;
        const fechaFin = document.getElementById('fechaFin').value;
        const horaInicio = document.getElementById('horaInicio').value;
        const horaFin = document.getElementById('horaFin').value;
        const municipioId = document.getElementById('municipio').value;

        if (!nombreEvento || !descripcionEvento || !fechaInicio || !fechaFin || !horaInicio || !horaFin || !municipioId) {
            alert('Por favor completa todos los campos obligatorios del evento');
      return;
    }

    if (localidades.length === 0) {
            alert('Debes agregar al menos una localidad');
      return;
    }

        if (artistasSeleccionados.length === 0) {
            alert('Debes agregar al menos un artista');
      return;
    }

        // Crear campos ocultos para localidades y artistas
        const form = document.getElementById('eventoForm');
        
        // Limpiar campos ocultos anteriores
        const camposOcultos = form.querySelectorAll('input[type="hidden"][name^="nombresLocalidades"], input[type="hidden"][name^="preciosLocalidades"], input[type="hidden"][name^="cantidadesLocalidades"], input[type="hidden"][name^="artistasIds"]');
        camposOcultos.forEach(campo => campo.remove());

        // Agregar localidades como campos ocultos
        localidades.forEach((localidad, index) => {
            const nombreInput = document.createElement('input');
            nombreInput.type = 'hidden';
            nombreInput.name = 'nombresLocalidades';
            nombreInput.value = localidad.nombre;
            form.appendChild(nombreInput);

            const precioInput = document.createElement('input');
            precioInput.type = 'hidden';
            precioInput.name = 'preciosLocalidades';
            precioInput.value = localidad.precio;
            form.appendChild(precioInput);

            const cantidadInput = document.createElement('input');
            cantidadInput.type = 'hidden';
            cantidadInput.name = 'cantidadesLocalidades';
            cantidadInput.value = localidad.cantidad;
            form.appendChild(cantidadInput);
        });

        // Agregar artistas como campos ocultos
        artistasSeleccionados.forEach((artista, index) => {
            const artistaInput = document.createElement('input');
            artistaInput.type = 'hidden';
            artistaInput.name = 'artistasIds';
            artistaInput.value = artista.id;
            form.appendChild(artistaInput);
        });

        // Enviar formulario
        form.submit();
    }

    // Hacer funciones globales para los botones
    window.eliminarLocalidad = eliminarLocalidad;
    window.eliminarArtistaSeleccionado = eliminarArtistaSeleccionado;
});