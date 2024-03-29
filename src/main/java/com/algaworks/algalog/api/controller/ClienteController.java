package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;
    private CatalogoClienteService catalogoClienteService;    

    public ClienteController(ClienteRepository clienteRepository, CatalogoClienteService catalogoClienteService) {
        this.clienteRepository = clienteRepository;
        this.catalogoClienteService = catalogoClienteService;
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
        return clienteRepository.findById(clienteId)
                .map(cliente -> ResponseEntity.ok(cliente))
                .orElse(ResponseEntity.notFound().build());

        // Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        
        // if (clienteOpt.isPresent()) {
        //     return ResponseEntity.ok(clienteOpt.get());
        // } else {
        //     return ResponseEntity.notFound().build();
        // }

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
        return catalogoClienteService.salvar(cliente);
    }
    
    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteId);
        cliente = clienteRepository.save(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        // clienteRepository.deleteById(clienteId);
        catalogoClienteService.excluir(clienteId);

        return ResponseEntity.noContent().build();
    }

}
