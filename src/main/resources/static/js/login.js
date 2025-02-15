async function iniciarSesion() {
  let datos = {
    email: document.getElementById('txtEmail').value,
    password: document.getElementById('txtPassword').value
  };

  const request = await fetch('api/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json' 
    },
    body: JSON.stringify(datos)
  });

  if (!request.ok) {
    alert("Las credenciales son incorrectas");
    return;
  }

  const respuesta = await request.json(); // Recibirá un objeto con el token

  // Guardar el token en localStorage
  localStorage.setItem("token", respuesta.token);
  
  // Redireccionar al usuario a la página de usuarios
  window.location.href = 'usuarios.html';
}