//VARIABLES GLOBALES
var table;
var rutaoperacion = '../operacion';
var rutatipooperacion = '../tipooperacion';
var rutafondo = '../fondo';
var rutareceptor = '../receptor';
var itemsCargados = 0;
var objPie;
var destinatarios = [];

var myApp;
$(function() {
	ConfListaroperacion();
	ConfCargarListaTipoOperacion();
	ConfCargarListaFondo();
	ConfGrabarOpeacion();
	ConfEliminarOperacion();
	ConfCargarMasivamente();
	ConfEnviarReporte();
	ValidarFormulario();
	ValidarFormularioMasivo();
	SetearFecha();
	ValidaFormularioCorreo();
	ConfAgregarCorreo();
	ConfModificarDestinatario();
	//ConfCargarReceptores();
	ConfExportarExcel();
	MostrarUltimoEnvio();
});

function MostrarUltimoEnvio() {
	ConfAjax('../reporte', '', 'GET', '', function(resp) {
		$('#spUltEnvio').text(resp.data[0].fechaRegistro);
		console.log(resp);
	});

}

function ConfCargarReceptores() {
	var receptoresMail = [];
	ConfAjax(rutareceptor + '/1',/* FASE INICIAL */
	'', 'GET', '', function(resp) {
		resp.data.forEach(function(item, num) {
			destinatarios.push(item.correo);
		});
		$('#txtEmail').val(destinatarios.toString());
		console.log(resp);
	});
}

function ValidaFormularioCorreo() {
	jQuery.validator.addMethod("correoRepetido", function(value, element) {
		if (destinatarios.indexOf(value) !== (-1)) {
			console.log('registro correo error');
			return false;
		} else {
			return true;
		}
	}, "mensaje de repetidos");

	$("#frmAgregarCorreo")
			.validate(
					{
						submitHandler : function(form) {
							var correo = $('#txtCorreoDestinatario').val();
							destinatarios.push(correo);
							$("#listaCorreos")
									.append(
											"<li class='list-group-item'><button data-value="
													+ correo
													+ " class='btn btn-primary' onclick='EliminarDestinatario(this)'"
													+ "'>Eliminar</button>  <span style='color:black'> "
													+ correo + "</span></li>");
							document.getElementById('frmAgregarCorreo').reset();
							ConfAjax(rutareceptor + '/registro', '', 'POST', {
								correo : correo,
								idFase : 1
							/* SETEADO PARA LA FASE INICIAL */
							}, function(resp) {
								$('#txtEmail').val(destinatarios.toString());
								console.log(resp);
							});
						},
						rules : {
							txtCorreoDestinatario : {
								required : true,
								email : true,
								correoRepetido : true
							},
						},
						highlight : function(element) {
							$(element).closest('.form-group').addClass(
									'has-error');
						},
						unhighlight : function(element) {
							$(element).closest('.form-group').removeClass(
									'has-error');
						},
						errorElement : 'span',
						errorClass : 'help-block',
						errorPlacement : function(error, element) {
							if (element.parent('.input-group').length) {
								error.insertAfter(element.parent());
							} else {
								error.insertAfter(element);
							}
						}
					});
}

function SetearFecha() {
	$("#txtFechaEscritura").datepicker({
		dateFormat : "dd/mm/yy"
	}).on('change', function() {
		$(this).valid();
	});
}
function ValidarFormulario() {
	$("#frmExcel").validate({
		submitHandler : function(form) {
			GrabarOperacion();
		},
		rules : {
			txtCodigo : {
				required : true,
				maxlength : 14,
			},
			cboTipoOperacion : {
				required : true
			},
			txtFechaEscritura : {
				required : true
			},
			txtPrestamo : {
				required : true,
				number : true,
				maxlength : 10,
			},
			cboFondo : {
				required : true
			},
			txtDocumento : {
				required : true,
				maxlength : 8,
				minlength : 8,
				number : true
			},
			txtCliente : {
				required : true,
				maxlength : 70,
			},
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		},
		errorElement : 'span',
		errorClass : 'help-block',
		errorPlacement : function(error, element) {
			if (element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
}

function ValidarFormularioMasivo() {
	$("#frmExcelMasivo").validate({
		submitHandler : function(form) {
			console.log('enviado form');
			return false;
		},
		rules : {
			filArchivo : {
				required : true
			},
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		},
		errorElement : 'span',
		errorClass : 'help-block',
		errorPlacement : function(error, element) {
			if (element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
}

var filtrados;
function ConfListaroperacion() {
	try {
		table = $('#operacion').DataTable({
			// "ajax" : rutaoperacion,
			// serverSide : true,
			"deferLoading" : 57,
			"sAjaxSource" : rutaoperacion,
			"lengthMenu" : [ [ -1, 40, 100, 20 ], [ "All", 40, 100, 20 ] ],
			"scrollY" : "300px",
			"scrollCollapse" : true,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				var api = this.api(), data;
				objPie = $(api.column(1).footer());// 4
				if (retornoExcel.length != 0) {
					data = retornoExcel;
					data.data.forEach(function() {
						itemsCargados++;
					});
					objPie.html(' S/.' + data.sumatoria)
					fnCallback(data);
					retornoExcel = [];
				} else {
					$.ajax({
						headers : {
							'Accept' : '*/*',
							'X-CSRF-TOKEN' : $('[name=_csrf]').val()
						},
						url : sSource,
						type : "GET",
						success : function(data) {
							data.data.forEach(function() {
								itemsCargados++;
							});
							objPie.html(' S/.' + data.sumatoria)
							fnCallback(data);
						},
						error : function(err) {
							console.log('err->' + err);
						}
					});
				}

			},
			"columns" : [
			/*
			 * { "class" : "", "orderable" : false, "data" : null,
			 * "defaultContent" : "<button class='btn btn-primary btn-xs'
			 * data-tipo='editar'>Editar</button> <button class='btn
			 * btn-primary btn-xs' data-tipo='eliminar'>Eliminar</button>" },
			 */{
				"data" : "codigo"
			}, /*
				 * { "data" : "tipoOperacion.nombre" }, { "data" : "escritura"
				 * },{ "data" : "monto" },
				 */{
				"data" : "fondo.nombre"
			},/*
				 * { "data" : "observacion" }, { "data" : "dni" },
				 */{
				"data" : "cliente"
			} ]
		});
		$('#operacion tbody').on(
				'click',
				'button',
				function() {
					var data = table.row($(this).parents('tr')).data();
					var tipo = this.getAttribute("data-tipo");
					if (tipo === 'editar') {
						$('#txtCodigo').val(data['codigo']);
						$('#txtCodigo').attr('data-id', data['idOperacion']);
						$('#txtPrestamo').val(data['monto']);
						$('#cboFondo').val(data['fondo']['idFondo']);
						$('#cboTipoOperacion').val(
								data['tipoOperacion']['idTipoOperacion']);
						$('#txtFechaEscritura').val(data['escritura']);
						$('#txtDocumento').val(data['dni']);
						$('#txtCliente').val(data['cliente']);
					} else {
						$('#txtCodigo').attr('data-id', data['idOperacion']);
						$('#divEliminarContenido').text(
								'¿Desea eliminar la Operación '
										+ data['codigo'] + ' ?');
						$("#popEliminarOperacion").dialog("open");
						console.log('eliminar');
					}
					console.log(data);
				});

		$('#operacion').on('draw.dt', function() {
			var filteredrows = $("#operacion").dataTable()._('tr', {
				"filter" : "applied"
			});
			filtrados = filteredrows;// prueba
			var sumadorParcialMonto = 0;
			for (var i = 0; i < filteredrows.length; i++) {
				// sumadorParcialMonto +=
				// Number(filteredrows[i].monto.toFixed(2));
				// sumadorParcialMonto=Number(sumadorParcialMonto.toFixed(2));
			}
			// var table1 = $('#operacion').DataTable();
			// var column = table1.column(1); //4
			// $(column.footer()).html(sumadorParcialMonto.toString());
		});

	} catch (e) {
		console.log(e);
	}
}

function ConfExportarExcel() {
	$('#btnExportarExcel').click(
			function() {
				JSONToCSVConvertor(filtrados,
						"Popular Sistemas-Reporte Cobros SAFI", true);
			});
}

function ConfLimpiaroperacion() {
	$('#btnLimpiarOperacion').click(function() {
		$('#txtOperacion').attr('data-id', 0);
		document.forms[1].reset();
	});
}

var retornoExcel = [];
function ConfCargarMasivamente() {
	$('#btnCargarMasiva').click(
			function() {
				if (!$("#frmExcelMasivo").valid()) {
					return false;
				}
				ConfAjaxFile(rutaoperacion + "/upload", '', 'POST',
						new FormData($("#frmExcelMasivo")[0]), function(data) {
							retornoExcel = data;
							objPie.html(' S/.' + data.sumatoria);
							table.ajax.reload(null, false);
							document.getElementById('frmExcelMasivo').reset();
							location.reload();
						});

			});
}
function ConfCargarListaTipoOperacion() {
	try {
		ConfListaSelect(rutatipooperacion, '', 'GET', 'cboTipoOperacion',
				'nombre', 'idTipoOperacion');
	} catch (e) {
		console.log(e);
	}

}

function ConfCargarListaFondo() {
	try {
		ConfListaSelect(rutafondo, '', 'GET', 'cboFondo', 'nombre', 'idFondo');
	} catch (e) {
		console.log(e);
	}

}

function GrabarOperacion() {
	try {
		if ($('#txtCodigo').attr('data-id') === "0") {// NUEVO
			ConfAjax(rutaoperacion + '/registro', '', 'POST', {
				codigo : $('#txtCodigo').val(),
				tipoOperacion : {
					idTipoOperacion : $('#cboTipoOperacion').val()
				},
				escritura : $('#txtFechaEscritura').val(),
				monto : $('#txtPrestamo').val(),
				fondo : {
					idFondo : $('#cboFondo').val()
				},
				dni : $('#txtDocumento').val(),
				cliente : $('#txtCliente').val().toUpperCase()
			}, function(resp) {
				table.ajax.reload(null, false);
				console.log('insertar');
			});
		} else {// EDITAR
			ConfAjax(rutaoperacion + '/actualizacion', '', 'POST', {
				idOperacion : $('#txtCodigo').attr('data-id'),
				codigo : $('#txtCodigo').val(),
				tipoOperacion : {
					idTipoOperacion : $('#cboTipoOperacion').val()
				},
				escritura : $('#txtFechaEscritura').val(),
				monto : $('#txtPrestamo').val(),
				fondo : {
					idFondo : $('#cboFondo').val()
				},
				dni : $('#txtDocumento').val(),
				cliente : $('#txtCliente').val().toUpperCase()
			}, function(resp) {
				table.ajax.reload(null, false);
				console.log(resp);
			});
			console.log('editar');
		}
		$('#txtCodigo').attr('data-id', "0");
		document.forms[1].reset();
	} catch (e) {
		console.log('grabar operacion ' + e);
	}
}

function ConfEliminarOperacion() {
	$("#popEliminarOperacion").dialog({
		autoOpen : false,
		modal : true,
		title : "Eliminar de Operación",
		buttons : {
			'SI' : function() {
				ConfAjax(rutaoperacion + '/eliminacion', '', 'POST', {
					idOperacion : $('#txtCodigo').attr('data-id')
				}, function(resp) {
					table.ajax.reload(null, false);
					$('#txtCodigo').attr('data-id', "0");
					$("#popEliminarOperacion").dialog('close');
					console.log(resp);
				});
			},
			'NO' : function() {
				$('#txtCodigo').attr('data-id', "0");
				$(this).dialog('close');
			}
		}
	});
}

function ConfAgregarCorreo() {
	$("#popModificarDestinatario").dialog({
		autoOpen : false,
		modal : true,
		title : "Modificar Destinatarios",
		height : 400,
		buttons : {
			'CERRAR' : function() {
				$(this).dialog('close');
			},
		}
	});
}

function ConfGrabarOpeacion() {
	$('#btnInsertarOperacion').click(function() {
		$('#frmExcel').submit();
	});
}

function ConfModificarDestinatario() {
	$('#btnModificarDestinatario')
			.click(
					function() {
						$('#listaCorreos').empty();
						destinatarios = [];
						ConfAjax(
								rutareceptor + '/1',/* FASE INICIAL */
								'',
								'GET',
								'',
								function(resp) {
									resp.data
											.forEach(function(item, num) {
												destinatarios.push(item.correo);
												$("#listaCorreos")
														.append(
																"<li class='list-group-item'><button data-value="
																		+ item.correo
																		+ " class='btn btn-primary' onclick='EliminarDestinatario(this)'"
																		+ "'>Eliminar</button>  <span style='color:black'> "
																		+ item.correo
																		+ "</span></li>");

											});
									$("#popModificarDestinatario").dialog(
											'open');
									console.log(resp);
								});
					});
}

function ConfEnviarReporte() {
	$('#btnEnviarReporte').click(function() {
		/*if (itemsCargados === 0 || destinatarios.length === 0) {
			alert('Registre Codigos para el envio');
			return false;
		}*/
		var r = confirm("¿Esta seguro de realizar el envio?");
		if (r == true) {
			ConfAjax(rutaoperacion + '/registraenvio', '', 'POST', {
				idOperacion : 0
			}, function(resp) {
				table.ajax.reload(null, false);
				console.log(resp);
			});
			setTimeout(function() {
				pleaseWaitDiv.modal('hide');
				alert('¡El envio de los archivos CREP fue satisfactorio!');
				location.reload();
			}, 1000);
		}
	});
}

function EliminarDestinatario(me) {
	try {
		ConfAjax(rutareceptor + '/actualizacion', '', 'POST', {
			correo : $(me).attr("data-value"),
			idFase : 1
		/* SETEADO PARA LA FASE INICIAL */
		}, function(resp) {
			console.log(resp);
		});
		destinatarios
				.splice(destinatarios.indexOf($(me).attr("data-value")), 1);
		$('#txtEmail').val(destinatarios.toString());
		$(me).parent().remove();

	} catch (e) {
		// TODO: handle exception
	}

}

console.log('modulo de tranferencias cargo correctamente');
