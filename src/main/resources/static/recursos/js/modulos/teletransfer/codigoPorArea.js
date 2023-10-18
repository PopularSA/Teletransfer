//VARIABLES GLOBALES
var table;
var rutaarea = '../area';
var rutacodigoarea = '../codigoArea';


$(function() {
	confBotones();
	ConfCargarListaArea();
	ValidaFormularioArea();
	ConfListaroperacion();
	ConfEliminarCodigo();
});

function confBotones() {
	$('#btnlimpiarArea').click(function() {
		ResetearF('frmRegistraArea');
	});
	
	$('#btnRegistrarArea').click(function() {
		//GrabarCodigo();
		$('#frmRegistraArea').submit()
	});
}

function ConfCargarListaArea() {
	try {
		ConfListaSelect(rutaarea, '', 'GET', 'cboArea', 'nombre', 'idArea');
	} catch (e) {
		console.log(e);
	}
}

function ValidaFormularioArea() {

	$("#frmRegistraArea").validate({
		submitHandler : function(form) {
			GrabarCodigo();
		},
		rules : {
			cboArea : {
				required : true
			},
			txtCodigo : {
				required : true
			},
			txtDescripcion : {
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

function ConfListaroperacion() {
	try {
		table = $('#listaCodigos')
				.DataTable(
						{
							"deferLoading" : 57,
							"sAjaxSource" : rutacodigoarea,
							"lengthMenu" : [ [ 20, 40, 100, -1 ],
									[ 20, 40, 100, "All" ] ],
							"scrollY" : "300px",
							"scrollCollapse" : true,
							"fnServerData" : function(sSource, aoData,
									fnCallback) {

								$.ajax({
									headers : {
										'Accept' : '*/*',
										'X-CSRF-TOKEN' : $('[name=_csrf]')
												.val()
									},
									url : sSource,
									type : "GET",
									success : function(data) {
										/*
										 * data.data.forEach(function() {
										 * itemsCargados++; });
										 */
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
										"defaultContent" : "<button class='btn btn-primary btn-xs'  data-tipo='editar'>Editar</button> <button class='btn btn-primary btn-xs' data-tipo='eliminar'>Eliminar</button>"
									}, {
										"data" : "idCodigoArea"
									}, {
										"data" : "area.nombre"
									}, {
										"data" : "codigoArea"
									}, {
										"data" : "detalle"
									} ]
						});
		$('#listaCodigos tbody').on(
				'click',
				'button',
				function() {
					var data = table.row($(this).parents('tr')).data();
					var tipo = this.getAttribute("data-tipo");
					if (tipo === 'editar') {
						$('#txtCodigo').attr('data-id', data['idCodigoArea']);
						$('#cboArea').val(data['area']['idArea']);
						$('#txtCodigo').val(data['codigoArea']);
						$('#txtDescripcion').val(data['detalle']);
						
					} else {
						$('#txtCodigo').attr('data-id', data['idCodigoArea']);
						$('#divEliminarContenido').text(
								'¿Desea eliminar el Codigo '
										+ data['idCodigoArea'] + ' ?');
						$("#popEliminarCodigo").dialog("open");
						//console.log('eliminar');
					}
					console.log(data);
				});

	} catch (e) {
		console.log(e);
	}
}

function ConfEliminarCodigo() {
	$("#popEliminarCodigo").dialog({
		autoOpen : false,
		modal : true,
		title : "Eliminar Codigo",
		buttons : {
			'SI' : function() {
				ConfAjax(rutacodigoarea + '/eliminacion', '', 'POST', {
					idCodigoArea : $('#txtCodigo').attr('data-id')
				}, function(resp) {
					table.ajax.reload(null, false);
					$('#txtCodigo').attr('data-id', "0");
					$("#popEliminarCodigo").dialog('close');
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

function GrabarCodigo() {
	try {
		if ($('#txtCodigo').attr('data-id') === "0") {// NUEVO
			ConfAjax(rutacodigoarea + '/registro', '', 'POST', {
				idCodigoArea : $('#txtCodigo').attr('data-id'),
				area : {
					idArea : $('#cboArea').val()
				},
				codigoArea : $('#txtCodigo').val().toUpperCase(),
				detalle : $('#txtDescripcion').val().toUpperCase()
			}, function(resp) {
				table.ajax.reload(null, false);
				ResetearF('frmRegistraArea');
				console.log('insertar');
			});
		} else {// EDITAR
			ConfAjax(rutacodigoarea + '/actualizacion', '', 'POST', {
				idCodigoArea :$('#txtCodigo').attr('data-id'),
				area : {
					idArea : $('#cboArea').val()
				},
				codigoArea : $('#txtCodigo').val().toUpperCase(),
				detalle : $('#txtDescripcion').val().toUpperCase()
			}, function(resp) {
				table.ajax.reload(null, false);
				ResetearF('frmRegistraArea');
				console.log(resp);
			});
			console.log('editar');
		}
		$('#txtCodigo').attr('data-id', "0");
		document.forms[1].reset();
	} catch (e) {
		console.log('grabar codigo ' + e);
	}
}



