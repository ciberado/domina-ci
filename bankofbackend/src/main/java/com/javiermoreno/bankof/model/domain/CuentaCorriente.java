package com.javiermoreno.bankof.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.javiermoreno.dominaspring.framework.BusinessException;

@Entity
@DiscriminatorValue("CC")
public class CuentaCorriente extends ProductoFinanciero implements Serializable {

    private static final long serialVersionUID = 1L;

    public CuentaCorriente() {
        super();
    }

    public CuentaCorriente(String codigo, BigDecimal saldo, BigDecimal interesAnual) {
        super(codigo, saldo, interesAnual);
    }

    public void transferir(ProductoFinanciero destino,
            BigDecimal importe) {
        if (this.equals(destino) == true) {
            throw new IllegalArgumentException("Transferencia entre una única cuenta.");
        }

        if (importe.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(String.format("Importe {0} debería de ser superior a 0.", importe));
        }

        if (importe.compareTo(this.getSaldo()) > 0) {
            throw new BusinessException(String.format("Saldo insuficiente para transferir el importe {0}.", importe));
        }

        this.setSaldo(this.getSaldo().subtract(importe));
        destino.setSaldo(destino.getSaldo().add(importe));
    }

}
