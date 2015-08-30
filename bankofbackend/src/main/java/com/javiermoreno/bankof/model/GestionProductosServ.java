package com.javiermoreno.bankof.model;

import com.javiermoreno.bankof.model.domain.Movimiento;
import java.math.BigDecimal;

import com.javiermoreno.bankof.model.domain.ProductoFinanciero;

public interface GestionProductosServ {

    ProductoFinanciero obtenerProducto(String codigo);

    Movimiento ingresar(String codigoDestino, BigDecimal importe);

    Movimiento reintegrar(String codigoOrigen, BigDecimal importe);

    Movimiento transferir(String codigoOrigen, String codigoDestino, BigDecimal importe);

}
