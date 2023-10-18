//VARIABLES GLOBALES
var table;
var tableReporte;
var rutaoperacion = '../operacion';
var rutareceptor = '../receptor';
var rutareporte = '../reporte';
var rutafondo = '../fondo';
var itemsCargados = 0;
var destinatarios = [];
var idReporte = 0;
$(function() {
	ConfListaroperacion();
	ConfCargarReceptores();
	ConfModificarDestinatario();
	ConfAgregarCorreo();
	ValidaFormularioCorreo();
	ConfRechazarReporte();
	ConfListaReportes()
	ConfCargarListaFondo();
	ValidarFormularioGeneraArchivo();
	ConfGeneraArchivos();
	$('#frmRegObs').validate();
});
var objPie;

function ValidarFormularioGeneraArchivo() {
	$("#frmGenerarArchivo").validate({
		submitHandler : function(form) {
			alert('Procesando');
			console.log('enviado form');
			return false;
		},
		rules : {
			cboFondo : {
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

function ConfCargarListaFondo() {
	try {
		ConfListaSelect(rutafondo, '', 'GET', 'cboFondo', 'nombre', 'idFondo');
	} catch (e) {
		console.log(e);
	}

}

function ConfCargarReceptores() {
	var receptoresMail = [];
	ConfAjax(rutareceptor + '/4',/* FASE FINAL */
	'', 'GET', '', function(resp) {
		resp.data.forEach(function(item, num) {
			destinatarios.push(item.correo);
		});
		$('#txtEmail').val(destinatarios.toString());
		console.log(resp);
	});
}

function ConfListaroperacion() {
	try {
		table = $('#operacion').DataTable({
			// "ajax" : rutaoperacion,
			// serverSide : true,
			"sAjaxSource" : rutaoperacion,
			"lengthMenu" : [ [ 20, 40, 100, -1 ], [ 20, 40, 100, "All" ] ],
			"scrollY" : "350px",
			"scrollCollapse" : true,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				var api = this.api(), data;
				objPie = $(api.column(3).footer());
				$.ajax({
					headers : {
						'Accept' : '*/*',
						'X-CSRF-TOKEN' : $('[name=_csrf]').val()
					},
					url : sSource + '/' + idReporte,
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

			},
			"columns" : [ {
				"data" : "codigo"
			}, {
				"data" : "tipoOperacion.nombre"
			}, {
				"data" : "escritura"
			}, {
				"data" : "monto"
			}, {
				"data" : "fondo.nombre"
			}, {
				"data" : "observacion"
			}, {
				"data" : "dni"
			}, {
				"data" : "cliente"
			} ]
		});
		
		
	
		
	} catch (e) {
		console.log(e);
	}
}

function ConfListaReportes() {
	try {
		tableReporte = $('#reporteOperacion')
				.DataTable(
						{
							// "ajax" : rutaoperacion,
							// serverSide : true,
							"sAjaxSource" : rutareporte,
							"lengthMenu" : [ [ 20, 40, 100, -1 ],
									[ 20, 40, 100, "All" ] ],
							"scrollY" : "350px",
							"scrollCollapse" : true,
							"fnServerData" : function(sSource, aoData,
									fnCallback) {
								var api = this.api(), data;
								// objPie = $(api.column(3).footer());
								$.ajax({
									headers : {
										'Accept' : '*/*',
										'X-CSRF-TOKEN' : $('[name=_csrf]')
												.val()
									},
									url : sSource,
									type : "GET",
									success : function(data) {
										data.data.forEach(function() {
											itemsCargados++;
										});
										// objPie.html(' S/.' + data.sumatoria)
										fnCallback(data);
									},
									error : function(err) {
										console.log('err->' + err);
									}
								});

							},
							"columns" : [
									{
										"class" : "",
										"orderable" : false,
										"data" : null,
										"defaultContent" : "<button class='btn btn-primary btn-xs'  data-tipo='editar'>Ver Detalle</button>"
									}, {
										"data" : "idReporteRecaudacion"
									}, {
										"data" : "fechaRegistro"
									}, {
										"data" : "estado"
									} ]
						});
		$('#reporteOperacion tbody').on('click', 'button', function() {
			var data = tableReporte.row($(this).parents('tr')).data();
			var tipo = this.getAttribute("data-tipo");
			if (tipo === 'editar') {
				idReporte = data['idReporteRecaudacion'];
				table.ajax.reload(null, false);
			}
			console.log(data);
		});

	} catch (e) {
		console.log(e);
	}
}

function ConfGeneraArchivos() {
	$('#btnEnviarReporte').click(function() {
		if (!$('#frmGenerarArchivo').valid()) {
			return false;
		}
		location.href = rutareporte+'/file/' + $('#cboFondo').val();
	});
}

function ConfRechazarReporte() {
	$('#btnRechazarReporte').click(function() {
		ConfAjax(rutaoperacion + '/devuelveenvio', '', 'POST', {
			idOperacion : 0
		}, function(resp) {
			table.ajax.reload(null, false);
			tableReporte.ajax.reload(null, false);
			console.log(resp);
		});
	});
}

function ConfModificarDestinatario() {
	$('#btnModificarDestinatario')
			.click(
					function() {
						$('#listaCorreos').empty();
						destinatarios = [];
						ConfAjax(
								rutareceptor + '/4',/* FASE SECUNDARIA */
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
								idFase : 4
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

function EliminarDestinatario(me) {
	try {
		ConfAjax(rutareceptor + '/actualizacion', '', 'POST', {
			correo : $(me).attr("data-value"),
			idFase : 4
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
