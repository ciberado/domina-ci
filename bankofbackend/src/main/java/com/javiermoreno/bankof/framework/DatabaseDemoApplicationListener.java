package com.javiermoreno.bankof.framework;

import com.javiermoreno.bankof.model.domain.CuentaCorriente;
import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.javiermoreno.bankof.model.domain.ProductoFinanciero;
import org.springframework.stereotype.Component;

@Component
public class DatabaseDemoApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @PersistenceUnit(name = "data")
    private EntityManagerFactory emFactory;

    public void onApplicationEvent(ContextRefreshedEvent evt) {
        EntityManager em = null;

        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();

            ProductoFinanciero pf9999 = new CuentaCorriente("9999", new BigDecimal("1000.0"), new BigDecimal("0.03"));
            em.persist(pf9999);
            ProductoFinanciero cc9998 = new CuentaCorriente("9998", new BigDecimal("2000.0"), new BigDecimal("0.01"));
            em.persist(cc9998);
            ProductoFinanciero ca9997 = new CuentaCorriente("9997", new BigDecimal("5000.0"), new BigDecimal("0.05"));
            em.persist(ca9997);

            em.getTransaction().commit();
        } catch (Exception exc) {
            if (em.getTransaction().isActive() == true) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

}
