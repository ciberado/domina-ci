var OrdenTransferencia = function (origen, destino, importe) {
    this.origen = origen;
    this.destino = destino;
    this.importe = importe;
    this.status = OrdenTransferencia.status.NO_VALIDADA;
};

OrdenTransferencia.status = {
    NO_VALIDADA: 0, VALIDADA_OK: 1, VALIDADA_ERROR: 2, PROCESADA_OK: 3, PROCESADA_ERROR: 4};
OrdenTransferencia.ENDPOINT_CREAR = 'http://localhost:8080/transferencias';

OrdenTransferencia.prototype.validar = function () {
    if (this.origen === this.destino) {
        this.status = OrdenTransferencia.status.VALIDADA_ERROR;
        throw new Error('No es posible enviar una transferencia a la cuenta de origen.: '
                + this.origen + ', ' + this.destino + '.');
    }
    this.status = OrdenTransferencia.status.VALIDADA_OK;
};

OrdenTransferencia.prototype.procesar = function () {
    var self = this;

    if (this.status !== OrdenTransferencia.status.VALIDADA_OK) {
        throw new Error('No es posible procesar una transferencia no validada.');
    }

    var promesaHttp = $.ajax({
        type: 'POST',
        data: JSON.stringify(this),
        dataType: "JSON",
        contentType: "application/json; charset=utf-8",
        url: OrdenTransferencia.ENDPOINT_CREAR,
        headers: {}
    });
    var promesaResultado = promesaHttp.then(function (respuesta) {
        self.status = OrdenTransferencia.status.PROCESADA_OK;
        return respuesta;
    }, function (error) {
        self.status = OrdenTransferencia.status.PROCESADA_ERROR;
        return error;
    });
    return promesaResultado;
};