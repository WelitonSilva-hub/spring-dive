package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.mapper.EntregaMapper;
import com.algaworks.algalog.api.model.EntregaWrapper;
import com.algaworks.algalog.api.model.request.EntregaRequest;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {
    
    private EntregaRepository entregaRepository;
    private SolicitacaoEntregaService solicitacaoEntregaService;
    private EntregaMapper entregaMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntregaWrapper solicitarEntrega(@Valid @RequestBody EntregaRequest entregaRequest) {
        Entrega novEntrega = entregaMapper.toEntity(entregaRequest);
        
        return entregaMapper.toMapper(solicitacaoEntregaService.solicitar(novEntrega));
    }

    @GetMapping
    public List<EntregaWrapper> listar() {
        return entregaMapper.toCollectionWrapper(entregaRepository.findAll());
    }

    @GetMapping("/{entregaId}")
    public ResponseEntity<EntregaWrapper> buscar(@PathVariable Long entregaId) {
        return entregaRepository.findById(entregaId)
                .map(entrega -> ResponseEntity.ok(entregaMapper.toMapper(entrega)))
                .orElse(ResponseEntity.notFound().build());
    }

}
