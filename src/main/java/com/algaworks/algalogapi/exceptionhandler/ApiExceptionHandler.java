package com.algaworks.algalogapi.exceptionhandler;

import com.algaworks.algalogapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algalogapi.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Problema problema = new Problema();
        problema.setStatus(status.value());
        problema.setDataHora(LocalDateTime.now());
        problema.setTitulo("Um ou mais campos estão inválidos! Faça o preenchimento correto e tente novamente!");
        problema.setCampos(this.gerarCampos(ex));

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private List<Campo> gerarCampos(MethodArgumentNotValidException ex){
        List<Campo> campos = new ArrayList<>();
        ex.getAllErrors().stream().forEach(error ->{
            Campo campo = new Campo(((FieldError) error).getField(), messageSource.getMessage(error, LocaleContextHolder.getLocale()));
            campos.add(campo);
        });

        return campos;
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Problema problema = new Problema();

        problema.setStatus(status.value());
        problema.setDataHora(LocalDateTime.now());
        problema.setTitulo(ex.getMessage());

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(NegocioException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problema problema = new Problema();

        problema.setStatus(status.value());
        problema.setDataHora(LocalDateTime.now());
        problema.setTitulo(ex.getMessage());

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

}
