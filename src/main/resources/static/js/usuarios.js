// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuarios();
});

async function cargarUsuarios() {
    const request = await fetch('api/usuarios', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    const usuarios = await request.json();
  
    console.log(usuarios);

    let listadoHTML = '';

    for (let usuario of usuarios) {
        let botonEliminar = '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
        let usuariosHTML = '<tr><td>' + usuario.id + '</td><td>' + usuario.nombre + ' ' + usuario.apellido + '</td><td>' 
        + usuario.email + '</td><td>' + usuario.telefono 
        + '</td><td>' + botonEliminar + '</td></tr>';
      
        listadoHTML += usuariosHTML;
    }

    document.querySelector('#usuarios tbody').outerHTML = listadoHTML;

    // Iniciar DataTable después de que se hayan cargado los usuarios
    $('#usuarios').DataTable();
}

	async function eliminarUsuario(id) {
		/*alert(id);*/
		
    if (!confirm('¿Desea eliminar este usuario?')) {
        return;
    }

    const request = await fetch('api/usuarios/' + id, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    if (request.ok) {
        // Recargar la lista de usuarios después de eliminar
        cargarUsuarios();
    } else {
        alert('Error al eliminar el usuario');
    }
	
	//location.reload();
}

