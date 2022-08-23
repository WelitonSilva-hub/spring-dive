package com.algaworks.algalog.api.exceptions;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algalog.domain.exceptions.DomainException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ErrorBody.Campo> campos = new ArrayList<>();

        for (ObjectError error: ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            campos.add(new ErrorBody.Campo(nome, mensagem));
        }

        ErrorBody errorBody = new ErrorBody();
        errorBody.setStatus(status.value());
        errorBody.setDataHora(OffsetDateTime.now());
        errorBody.setTitulo("Um ou mais campos inválidos. Faça o preenchimento correto e tente novamente.");
        errorBody.setCampos(campos);

        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorBody errorBody = new ErrorBody();
        errorBody.setStatus(status.value());
        errorBody.setDataHora(OffsetDateTime.now());
        errorBody.setTitulo(ex.getMessage());

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), status, request);
    }
    
}
