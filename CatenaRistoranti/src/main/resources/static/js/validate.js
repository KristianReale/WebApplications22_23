function Piatto(id, prezzo){
	this.id = id;
	this.prezzo = prezzo;
}

piatti = new Object();
const maxPrice = 34;
//alert(maxPrice);

window.addEventListener("load", function(){
	var elForm = document.getElementsByTagName("form")[0];
	elForm.addEventListener("submit", function(event){
		var txtNome = document.getElementById("nome"); 
		var txtDescrizione = document.getElementById("descrizione"); 
		var txtUbicazione = document.getElementById("ubicazione"); 
		validateRistorante(txtNome, txtDescrizione, txtUbicazione, event);
	});
	
	var butAdd = document.getElementById("aggiungi");
	butAdd.addEventListener("click", function(event){
		var txtNome = document.getElementById("nome"); 
		var txtDescrizione = document.getElementById("descrizione"); 
		var txtUbicazione = document.getElementById("ubicazione");
		var nome = txtNome.value;
		var descrizione = txtDescrizione.value;
		var ubicazione = txtUbicazione.value;
		
		var newTr = document.createElement("tr");
		var newTdNome = document.createElement("td");
		var newTdDescrizione = document.createElement("td");
		var newTdUbicazione = document.createElement("td");
		var txNome = document.createTextNode(nome);
		var txDescrizione = document.createTextNode(descrizione);
		var txUbicazione = document.createTextNode(ubicazione);
		
		newTdNome.appendChild(txNome);
		newTdDescrizione.appendChild(txDescrizione);
		newTdUbicazione.appendChild(txUbicazione);
		
		newTr.appendChild(newTdNome);
		newTr.appendChild(newTdDescrizione);
		newTr.appendChild(newTdUbicazione);
		
		var table = document.querySelector("table");

		
		table.appendChild(newTr);
	});
});


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
	
	for (let piattoId in piatti){
		sommaPrezzi += (piatti[piattoId].prezzo);
	}
	return sommaPrezzi;
}