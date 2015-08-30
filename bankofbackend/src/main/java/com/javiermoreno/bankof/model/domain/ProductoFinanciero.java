package com.javiermoreno.bankof.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


/* 
 INSERT INTO "APP"."PRODUCTOFINANCIERO" (DTYPE,CODIGO,INTERESANUAL,SALDO,PENALIZADO) VALUES ('PF','1000',0.03,1000.0,null);
 INSERT INTO "APP"."PRODUCTOFINANCIERO" (DTYPE,CODIGO,INTERESANUAL,SALDO,PENALIZADO) VALUES ('CC','2000',0.03,1000.0,null);
 INSERT INTO "APP"."PRODUCTOFINANCIERO" (DTYPE,CODIGO,INTERESANUAL,SALDO,PENALIZADO) VALUES ('CA','3000',0.05,1000.0,false);
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("PF")
public class ProductoFinanciero implements Comparable<ProductoFinanciero>, Serializable {

    private static final long serialVersionUID = 1L;

    private static final BigDecimal NUMERO_MESES = new BigDecimal("12");

    @Id
    private String codigo;
    private BigDecimal saldo;
    private BigDecimal interesAnual;
    
    public ProductoFinanciero() {
    }

    public ProductoFinanciero(String codigo, BigDecimal saldo, BigDecimal interesAnual) {
        this.codigo = codigo;
        this.saldo = saldo;
        this.interesAnual = interesAnual;
    }

    public void ingresar(BigDecimal importe) {
        if (importe.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(MessageFormat.format("Importe {0} debería de ser superior a 0.", importe));
        }
        saldo = saldo.add(importe);
    }

    public void reintegrar(BigDecimal importe) {
        if (importe.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(MessageFormat.format("Importe {0} debería de ser superior a 0.", importe));
        }
        saldo = saldo.subtract(importe);
    }

    public void actualizarInteresMensual() {
        saldo = saldo.add(saldo.multiply(interesAnual.divide(NUMERO_MESES)));
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getInteresAnual() {
        return interesAnual;
    }

    public void setInteresAnual(BigDecimal interesAnual) {
        this.interesAnual = interesAnual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductoFinanciero other = (ProductoFinanciero) obj;
        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }

    

    public int compareTo(ProductoFinanciero o) {
        return this.codigo.compareTo(o.codigo);
    }

}
