function validateLogin(username, password, e){
	if (username.value == ""){
		alert("Inserisci username")
		e.preventDefault();
	}
	
}

function validateRistorante(txtNome, txtDescrizione, txtUbicazione, ev){
	var canSubmit = true;
	if (txtNome.value.trim() == ""){
		alert("Inserisci username")
		canSubmit = false;
	}
	if (txtDescrizione.value.trim() == ""){
		alert("Inserisci descrizione")
		canSubmit = false;
	}
	if (txtUbicazione.value.trim() == ""){
		alert("Inserisci cap")
		canSubmit = false;
	}
	if (!canSubmit){
		ev.preventDefault();
	}
	
}