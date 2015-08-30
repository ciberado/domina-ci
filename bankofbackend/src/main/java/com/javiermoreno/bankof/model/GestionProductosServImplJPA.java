package com.javiermoreno.bankof.model;

import com.javiermoreno.bankof.model.domain.CuentaCorriente;
import com.javiermoreno.bankof.model.domain.Movimiento;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javiermoreno.bankof.model.domain.ProductoFinanciero;
import com.javiermoreno.dominaspring.framework.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GestionProductosServImplJPA implements GestionProductosServ {

    static final BigDecimal MAXIMO_PERMITIDO = new BigDecimal("1500.00");
    static final BigDecimal IMPORTE_COMISION = new BigDecimal("3.00");

    private ProductoFinancieroRepository repositorioPF;
    private MovimientoRepository respositorioMov;

    public GestionProductosServImplJPA() {
    }

    @Autowired
    public GestionProductosServImplJPA(ProductoFinancieroRepository repositorioPF, MovimientoRepository respositorioMov) {
        this.repositorioPF = repositorioPF;
        this.respositorioMov = respositorioMov;
    }

    @Transactional
    @Override
    public Movimiento ingresar(String codigoDestino, BigDecimal importe) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Transactional
    @Override
    public Movimiento reintegrar(String codigoOrigen, BigDecimal importe) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Transactional
    @Override
    public ProductoFinanciero obtenerProducto(String codigo) {
        ProductoFinanciero productoFinanciero = repositorioPF.findOne(codigo);
        if (productoFinanciero == null) {
            throw new javax.persistence.EntityNotFoundException(MessageFormat.format("Destino {0} no encontrado.", codigo));
        }
        return productoFinanciero;
    }

    @Transactional
    @Override
    public Movimiento transferir(String codigoOrigen, String codigoDestino, BigDecimal importe) {
        if (importe.compareTo(MAXIMO_PERMITIDO) > 0) {
            throw new BusinessException(
                    String.format("Importe {0} supera el máximo {1} permitido.", importe, MAXIMO_PERMITIDO));            
        }

        CuentaCorriente origen = (CuentaCorriente) repositorioPF.findOne(codigoOrigen);
        if (origen.getSaldo().compareTo(IMPORTE_COMISION) < 0) {
            throw new BusinessException(
                String.format("La cuenta corriente {0} no dispone de saldo para pagar la comisión {1}.", codigoOrigen, IMPORTE_COMISION));
        }

        ProductoFinanciero destino = obtenerProducto(codigoDestino);

        origen.setSaldo(origen.getSaldo().subtract(IMPORTE_COMISION));
        Movimiento movimientoComision = new Movimiento(0, origen, new Date(), IMPORTE_COMISION);
        respositorioMov.save(movimientoComision);
        
        origen.transferir(destino, importe);

        Movimiento movimientoTransferencia = new Movimiento(0, origen, new Date(), importe);
        respositorioMov.save(movimientoTransferencia);

        return movimientoTransferencia;
    }

}
