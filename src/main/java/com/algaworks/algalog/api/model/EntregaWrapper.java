package com.algaworks.algalog.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algalog.domain.model.StatusEntrega;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaWrapper {

    private Long id;
    private ClienteWrapper cliente;
    private DestinatarioWrapper destinatario;
    private BigDecimal taxa;
    private StatusEntrega statusEntrega;
    private OffsetDateTime dataPedido;
    private OffsetDateTime dataFinalizacao;
    
}
