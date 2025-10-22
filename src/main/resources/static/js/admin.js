document.addEventListener('DOMContentLoaded', () => {
    const departamentoSelect = document.getElementById('departamento');
    const municipioSelect = document.getElementById('municipio');
    const localidadForm = document.getElementById('localidadForm');
    const tablaLocalidades = document.getElementById('tablaLocalidades').querySelector('tbody');
    const nuevoArtistaForm = document.getElementById('nuevoArtistaForm');
    const selectArtista = document.getElementById('selectArtista');
    const btnAgregarArtista = document.getElementById('btnAgregarArtista');
    const tablaArtistasEvento = document.getElementById('tablaArtistasEvento').querySelector('tbody');
    const btnGuardarEvento = document.getElementById('btnGuardarEvento');
    const eventoForm = document.getElementById('eventoForm');

    // Cargar municipios al cambiar departamento
    departamentoSelect.addEventListener('change', () => {
        const departamentoId = departamentoSelect.value;
        municipioSelect.innerHTML = '<option value="">Cargando...</option>';
        if (departamentoId) {
            fetch(`/api/municipios?departamentoId=${departamentoId}`)
                .then(response => response.json())
                .then(data => {
                    municipioSelect.innerHTML = '<option value="">Selecciona un municipio</option>';
                    data.forEach(municipio => {
                        const option = document.createElement('option');
                        option.value = municipio.id;
                        option.textContent = municipio.municipio;
                        municipioSelect.appendChild(option);
                    });
                    municipioSelect.disabled = false;
                })
                .catch(error => {
                    console.error('Error al cargar municipios:', error);
                    municipioSelect.innerHTML = '<option value="">Error al cargar</option>';
                });
        } else {
            municipioSelect.innerHTML = '<option value="">Primero elige un departamento</option>';
            municipioSelect.disabled = true;
        }
    });

    // Agregar localidad a la tabla
    localidadForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const nombre = document.getElementById('nombreLocalidad').value;
        const precio = document.getElementById('precioLocalidad').value;
        const cantidad = document.getElementById('cantidadLocalidad').value;

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${nombre}</td>
            <td>${precio}</td>
            <td>${cantidad}</td>
            <td><button type="button" class="btn small danger btn-eliminar">Eliminar</button></td>
        `;
        tablaLocalidades.appendChild(row);
        localidadForm.reset();
    });

    // Eliminar fila de tabla
    document.addEventListener('click', (e) => {
        if (e.target.classList.contains('btn-eliminar')) {
            e.target.closest('tr').remove();
        }
    });

    // Registrar nuevo artista y agregarlo al select
    nuevoArtistaForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const nombre = document.getElementById('nombreNuevoArtista').value;
        const genero = document.getElementById('generoNuevoArtista').value;
        const ciudad = document.getElementById('ciudadNuevoArtista').value;

        const artistaData = { nombre: nombre, generoMusical: genero, ciudadNatal: ciudad };

        fetch('/api/artista', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(artistaData)
        })
        .then(response => response.json())
        .then(artista => {
            const option = document.createElement('option');
            option.value = artista.id;
            option.textContent = `${artista.nombre} - ${artista.generoMusical}`;
            selectArtista.appendChild(option);
            nuevoArtistaForm.reset();
            alert('Artista agregado a la lista!');
        })
        .catch(error => console.error('Error al crear artista:', error));
    });

    // Agregar artistas seleccionados a la tabla del evento
    btnAgregarArtista.addEventListener('click', () => {
        const opcionesSeleccionadas = Array.from(selectArtista.selectedOptions);
        opcionesSeleccionadas.forEach(option => {
            // Evitar duplicados
            if (!document.querySelector(`#tablaArtistasEvento tr[data-id='${option.value}']`)) {
                const [nombre, genero] = option.textContent.split(' - ');
                const row = document.createElement('tr');
                row.dataset.id = option.value;
                row.innerHTML = `
                    <td>${nombre}</td>
                    <td>${genero || ''}</td>
                    <td></td> <!-- Ciudad no está en el texto, se puede mejorar -->
                    <td><button type="button" class="btn small danger btn-eliminar">Quitar</button></td>
                `;
                tablaArtistasEvento.appendChild(row);
            }
        });
    });

    // Guardar el evento completo
    btnGuardarEvento.addEventListener('click', () => {
        const eventoData = {
            nombreEvento: document.getElementById('nombreEvento').value,
            descripcionEvento: document.getElementById('descripcionEvento').value,
            municipioId: document.getElementById('municipio').value,
            fechaInicio: document.getElementById('fechaInicio').value,
            horaInicio: document.getElementById('horaInicio').value,
            fechaFin: document.getElementById('fechaFin').value,
            horaFin: document.getElementById('horaFin').value,
            administradorId: document.querySelector('input[name="administradorId"]').value,
            localidades: [],
            artistas: [],
            cantidadBoletas: []
        };

        // Recolectar localidades
        tablaLocalidades.querySelectorAll('tr').forEach(row => {
            const cells = row.querySelectorAll('td');
            eventoData.localidades.push({
                localidad: cells[0].textContent,
                // No se envía precio aquí, se asocia en Cantidad_Boletas
            });
            eventoData.cantidadBoletas.push({
                precio: parseFloat(cells[1].textContent),
                cantidad: parseInt(cells[2].textContent, 10)
            });
        });

        // Recolectar artistas
        tablaArtistasEvento.querySelectorAll('tr').forEach(row => {
            eventoData.artistas.push(parseInt(row.dataset.id, 10));
        });

        // Validaciones
        if (!eventoData.nombreEvento || !eventoData.municipioId || !eventoData.fechaInicio) {
            alert('Por favor, completa los datos generales del evento.');
            return;
        }
        if (eventoData.localidades.length === 0) {
            alert('Debes agregar al menos una localidad.');
            return;
        }
        if (eventoData.artistas.length === 0) {
            alert('Debes agregar al menos un artista.');
            return;
        }

        // Enviar datos al backend
        fetch(eventoForm.action, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(eventoData)
        })
        .then(response => {
            if (response.ok) {
                alert('¡Evento guardado con éxito!');
                window.location.href = '/'; // O a la página de gestión de eventos
            } else {
                response.text().then(text => alert('Error al guardar el evento: ' + text));
            }
        })
        .catch(error => {
            console.error('Error en la petición:', error);
            alert('Ocurrió un error de red. Inténtalo de nuevo.');
        });
    });
});
