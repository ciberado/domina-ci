package com.javiermoreno.bankof;

import com.javiermoreno.bankof.model.GestionProductosServ;
import com.javiermoreno.bankof.model.domain.Movimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ciberado
 */
@RestController
@RequestMapping("/transferencias")
public class TransferenciasCtrl {

    private final GestionProductosServ servicio;

    @Autowired
    public TransferenciasCtrl(GestionProductosServ servicio) {
        this.servicio = servicio;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Movimiento transferir(@RequestBody TransferenciaDTO dto) {
        Movimiento mov = servicio.transferir(dto.origen, dto.destino, dto.importe);
        return mov;
    }

}
