function Piatto(id, prezzo){
	this.id = id;
	this.prezzo = prezzo;
}

piatti = new Object();
const maxPrice = 34;
//alert(maxPrice);
var ristDaAggiungere = new Array();
var ristDaRimuovere = new Array();

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
		
		var newRist = {
			"nome": nome,
			"descrizione" : descrizione,
			"ubicazione" : ubicazione
		}
		ristDaAggiungere.push(newRist);
		
		
		var newTr = document.createElement("tr");
		var newTdVuoto = document.createElement("td");
		var newTdNome = document.createElement("td");
		var newTdDescrizione = document.createElement("td");
		var newTdUbicazione = document.createElement("td");
		var txNome = document.createTextNode(nome);
		var txDescrizione = document.createTextNode(descrizione);
		var txUbicazione = document.createTextNode(ubicazione);
		
		newTdNome.appendChild(txNome);
		newTdDescrizione.appendChild(txDescrizione);
		newTdUbicazione.appendChild(txUbicazione);
		
		newTr.appendChild(newTdVuoto);
		newTr.appendChild(newTdNome);
		newTr.appendChild(newTdDescrizione);
		newTr.appendChild(newTdUbicazione);
		
		newTr.style="font-weight: bold;"
		
		var table = document.querySelector("table");
		table.appendChild(newTr);
	});
	
	var butRemove = document.getElementById("rimuovi");
	butRemove.addEventListener("click", function(event){
		var ristSelezionati = document.querySelectorAll(".ristorante:checked");
		ristSelezionati.forEach(function (chkRistSel){
			//chkRistSel.style = "text-decoration: line-through";
			var idRist = chkRistSel.getAttribute("value");
			var idRiga = "riga_" + idRist;
			var riga = document.querySelector("#" + idRiga);
			riga.style = "text-decoration: line-through";
			
			ristDaRimuovere.push(idRist);
		});
	});
	
	var butSave = document.getElementById("salva");
	butSave.addEventListener("click", function(event){
		var json = JSON.stringify(ristDaAggiungere);
		$.ajax({
			"method": "post",
			"url": "/newRistorante",
			"data": json,
			"contentType" : "application/json",
			"success": function(risposta){
				console.log(risposta);
				//JSON.parse(risposta)
				if (risposta.message === "OK"){
					var allTrs = document.querySelectorAll("tr");
					allTrs.forEach(function(elem){
						elem.style.removeProperty("font-weight");
					});
				}
			}
		});
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
		}
		//else{
			//let numPiatti = Object.keys(piatti).length;
		//	canSubmit = confirm("Il prezzo totale dei piatti è: " + somma 
		//							+ "\nLa media è: " + somma / numPiatti
		//							+ "\nContinuare?");
		//}
	}
	if (canSubmit){
		let nomeRist = txtNome.value.trim();
		ev.preventDefault();
		$.ajax({
			url: "/checkRistorante",
			data: {"nome": nomeRist},
			type: "post",
			success : function(risposta){
				if (risposta.trim() === 'true'){
					alert("Mi dispiace il ristorante esiste già");
				}else{
					let form = document.querySelector("form");
					form.submit();
				}
			}
		});
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