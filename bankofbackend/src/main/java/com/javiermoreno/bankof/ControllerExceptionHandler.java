package com.javiermoreno.bankof;

import com.javiermoreno.bankof.framework.ExceptionDTO;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author ciberado
 */
@ControllerAdvice
public class ControllerExceptionHandler{ 

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> gestionarExcepcion(EntityNotFoundException exc) {
        // No se ha recuperado la entidad requerida
        ExceptionDTO dto = new ExceptionDTO(exc.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
    
}
