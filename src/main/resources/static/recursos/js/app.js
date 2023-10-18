/*COMPONENTES COMUNES*/
var pleaseWaitDiv = $('<div class="modal-backdrop  in"><label id="messageLoading">Procesando ...</label><img src="../recursos/images/loading.gif" id="loading"/></div>');
// LLENAR LISTA
var cabeceraDefecto = {
	'Accept' : 'application/json',
	'Content-Type' : 'application/json',
	'X-CSRF-TOKEN' : $('[name=_csrf]').val()
};
var cabeceraFile = {
	'Accept' : '*/*',
	'X-CSRF-TOKEN' : $('[name=_csrf]').val()
};

function ConfListaSelect(ruta, cabecera, metodo, control, etiqueta, valor) {

	if (cabecera === '') {
		cabecera = cabeceraDefecto;
	}
	$.ajax({
		headers : cabecera,
		type : metodo,
		url : ruta,
		dataType : "json",
		success : function(response) {
			$('#' + control).empty();
			$('#' + control).append($('<option>', {
				value : ''
			}).text('::SELECCIONE::'));
			$.each(response.data, function(key, value) {
				$('#' + control).append($('<option>', {
					value : eval('value.' + valor)
				}).text(eval('value.' + etiqueta)));
			});
		}
	});
	console.log('listado');
}

function ConfAjax(ruta, cabecera, metodo, datos, funcion) {
	try {
		if (cabecera === '') {
			cabecera = cabeceraDefecto;
		}
		if (metodo === '') {
			metodo = 'POST';
		}
		pleaseWaitDiv.modal();
		$.ajax({
			headers : cabecera,
			type : metodo,
			url : ruta,
			data : (metodo=='GET'?'': JSON.stringify(datos)),
			dataType : "json",
			success : function(response) {
				funcion(response);
				 pleaseWaitDiv.modal('hide');
			}
		});
	} catch (e) {
		cosnsole.log(e);
	}								
}

function ConfAjaxFile(ruta, cabecera, metodo, datos, funcion) {
	try {

		if (cabecera === '') {
			cabecera = cabeceraFile;
		}
		pleaseWaitDiv.modal();
		$.ajax({
			headers : cabecera,
			url : ruta,
			type : metodo,
			data : datos,
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			success :function(response){
				funcion(response);
				pleaseWaitDiv.modal('hide');
			} ,
			error : function(resp) {
				console.log(resp);
			}
		});

	} catch (e) {
		console.log(e);
	}
}

function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {
	// If JSONData is not an object then JSON.parse will parse the JSON string
	// in an Object
	var arrData = typeof JSONData !== 'object' ? JSON.parse(JSONData)
			: JSONData;

	var CSV = '';
	// Set Report title in first row or line

	// CSV += ReportTitle + '\r\n\n';

	CSV += "<table border='1'><tr style='background-color:#3668A7;'><td colspan=8>"
			+ ReportTitle + '\r\n\n' + " </td></tr>"

	// This condition will generate the Label/Header
	if (ShowLabel) {
		var row = "<table border='1'><tr style='background-color:#83A6CC;'>";

		// This loop will extract the label from 1st index of on array
		var contC = 0;
		for ( var index in arrData[0]) {
			contC++;
			// Now convert each value to string and comma-seprated
			/*
			 * if(contC!==7){ row += '<td>' + index + '</td>'; }
			 */
			switch (contC) {
			case 1:
				row += '';
				break;
			case 2:
				row += '<td>Operacion</td>';
				break;
			case 3:
				row += '<td>Monto</td>';
				break;
			case 4:
				row += '<td>Fecha Registro</td>';
				break;
			case 5:
				row += '<td>Fondo</td>';
				break;
			case 6:
				row += '<td>Tipo Operacion</td>';
				break;
			case 7:
				row += '';
				;// ESTE CAMPO ES DE LA TABLA PERSONA
				break;
			case 8:
				row += '';
				break;
			case 9:
				row += '<td>Fecha Escritura</td>';
				break;
			case 10:
				row += '';
				break;
			case 11:
				row += '';
				break;
			case 12:
				row += '';
				break;
			case 13:
				row += '';
				break;
			case 14:
				row += '<td>DNI</td>';
				break;
			case 15:
				row += '<td>Cliente</td>';
				break;
			default:
				row += '<td>' + index + '</td>';
				break;
			}
		}
		row += '</tr>';
		// row = row.slice(0, -1);

		// append Label row with line break
		CSV += row + '\r\n';
	}

	// 1st loop is to extract each row
	for (var i = 0; i < arrData.length; i++) {
		var row = "<tr style='background-color:#BDD9F1;'>";

		// 2nd loop will extract each column and convert it in string
		// comma-seprated
		var cont = 0;
		for ( var index in arrData[i]) {
			cont++;
			switch (cont) {
			case 1:
				row += '';
				break;
			case 5:
				row += '<td>' + arrData[i][index].nombre + '</td>';
				break;
			case 6:
				row += '<td>' + arrData[i][index].nombre + '</td>';
				break;
			case 7:
				row += '';// ESTE CAMPO ES DE LA TABLA PERSONA
				break;
			case 8:
				row += '';
				break;
			case 10:
				row += '';
				break;
			case 11:
				row += '';
				break;
			case 12:
				row += '';
				break;
			case 13:
				row += '';
				break;
			default:
				row += '<td>' + arrData[i][index] + '</td>';
				break;
			}

		}
		row += '</tr>';
		// row.slice(0, row.length - 1);

		// add a line break after each row
		CSV += row + '\r\n';
	}
	row += '</table>'
	if (CSV === '') {
		alert("Invalid data");
		return;
	}

	// Generate a file name
	var fileName = "";
	// this will remove the blank-spaces from the title and replace it with an
	// underscore
	fileName += ReportTitle.replace(/ /g, "_");

	// Initialize file format you want csv or xls
	var uri = 'data:application/vnd.ms-excel,' + escape(CSV);

	// Now the little tricky part.
	// you can use either>> window.open(uri);
	// but this will not work in some browsers
	// or you will not get the correct file extension

	// this trick will generate a temp <a /> tag
	var link = document.createElement("a");
	link.href = uri;

	// set the visibility hidden so it will not effect on your web-layout
	link.style = "visibility:hidden";
	link.download = fileName + ".XLS";

	// this part will append the anchor tag and remove it after automatic click
	document.body.appendChild(link);
	link.click();
	document.body.removeChild(link);
}
/*
 * function ConfAjaxFile(ruta, cabecera, metodo, datos, funcion) { try {
 * 
 * if (cabecera === '') { cabecera = cabeceraFile; }
 * 
 * $.ajax({ headers : cabecera, url : ruta, type : metodo, data : datos, enctype :
 * 'multipart/form-data', processData : false, contentType : false, cache :
 * false, success : funcion, error : function(resp) { console.log(resp); } }); }
 * catch (e) { console.log(e); } }
 */
/* COMPONENTES COMUNES */



function ResetearF(form) {
	document.getElementById(form).reset();
	$('#' + form).find('input[type=hidden]').each(function(num, val) {
		$(val).val('');
	});
}

function ResetearTodosForms() {
	$(document.forms).each(function(num, item) {
		//console.log(item)
		$(item).find('input[type=hidden]').each(function(num, val) {
			$(val).val('');
		});
		item.reset();
	});
}