var TransferenciasCtrl = function($elem)  {
    this.$elem = $elem;
};

TransferenciasCtrl.prototype.inicializar = function() {
    var self = this;
    this.$elem.submit(function(evt) {
        evt.preventDefault();
        self.procesarTransferencia();
    });
    this.$elem.find('#formulario').addClass('activa');
};

TransferenciasCtrl.prototype.procesarTransferencia = function() {
    var self = this;
    var orden = new OrdenTransferencia(
            this.$elem.find('#origen').val(),
            this.$elem.find('#destino').val(),
            this.$elem.find('#importe').val());
    orden.validar();
    var promesa = orden.procesar();
    promesa.done(function(resultado) {
        self.mostrarResultadoOperacion(resultado);
    });
};

TransferenciasCtrl.prototype.mostrarResultadoOperacion = function(resultado) {
    this.$elem.find('#saldo').text(resultado.origen.saldo);
    this.$elem.find('#formulario').removeClass('activa');
    this.$elem.find('#resultados').addClass('activa');
};