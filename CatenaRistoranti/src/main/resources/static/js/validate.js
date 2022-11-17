function Piatto(id, prezzo){
	this.id = id;
	this.prezzo = prezzo;
}

piatti = new Object();
const maxPrice = 34;

function validateLogin(username, password, e){
	if (username.value == ""){
		alert("Inserisci username")
		e.preventDefault();
	}
	
}

function selezionaPiatto(id, prezzo, e){
	if (e.currentTarget.checked) {
		var piatto = new Piatto(id, prezzo);
		piatti[id] = piatto;		
	} else {
		delete piatti[id];
	}
	
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
	if (canSubmit){
		let somma = calculateTotalPrice();
		if (somma > maxPrice){
			alert("Hai superato il limite massimo di prezzi che è: " + somma);		
			canSubmit = false;
		}else{
			let numPiatti = Object.keys(piatti).length;
			canSubmit = confirm("Il prezzo totale dei piatti è: " + somma 
									+ "\nLa media è: " + somma / numPiatti
									+ "\nContinuare?");
		}
	}
	if (!canSubmit){
		ev.preventDefault();
	}
}

function calculateTotalPrice(){
	var sommaPrezzi = 0;
	
	for (var piattoId in piatti){
		sommaPrezzi += (piatti[piattoId].prezzo);
	}
	return sommaPrezzi;
}