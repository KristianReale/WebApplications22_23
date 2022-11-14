function Piatto(id, prezzo){
	this.id = id;
	this.prezzo = prezzo;
}



piatti = new Object();

function validateLogin(username, password, e){
	if (username.value == ""){
		alert("Inserisci username")
		e.preventDefault();
	}
	
}

function selezionaPiatto(idPiatto, prezzo){
	var piatto = new Piatto(idPiatto, prezzo);
	piatti[idPiatto] = piatto;
}

function validateRistorante(txtNome, txtDescrizione, txtUbicazione, ev){
	var canSubmit = true;
	console.log(txtNome.value);
	if (txtNome.value.trim() == ""){
		alert("Inserisci username")
		canSubmit = false;
	}
	if (txtDescrizione.value.trim() == ""){
		alert("Inserisci descrizione")
		canSubmit = false;
	}else{
		if (txtDescrizione.value.trim().length < 10){
			alert("La descrizione deve essere di almeno 10 caratteri")
			canSubmit = false;
		}
	}
	
	if (txtUbicazione.value.trim() == ""){
		alert("Inserisci cap")
		canSubmit = false;
	}
	if (!canSubmit){
		ev.preventDefault();
	}
	
}