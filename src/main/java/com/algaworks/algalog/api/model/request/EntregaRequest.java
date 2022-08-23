package com.algaworks.algalog.api.model.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EntregaRequest {
    
    @Valid
    @NotNull
    private ClienteIdRequest cliente;
    
    @Valid
    @NotNull
    private DestinatarioRequest destinatario;

    @NotNull
    private BigDecimal taxa;

}
