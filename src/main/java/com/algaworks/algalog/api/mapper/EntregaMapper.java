package com.algaworks.algalog.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.api.model.EntregaWrapper;
import com.algaworks.algalog.api.model.request.EntregaRequest;
import com.algaworks.algalog.domain.model.Entrega;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EntregaMapper {
    
    private ModelMapper modelMapper;
    
    public EntregaWrapper toMapper(Entrega entrega) {
        return modelMapper.map(entrega, EntregaWrapper.class);
    }

    public List<EntregaWrapper> toCollectionWrapper(List<Entrega> entregas) {
        return entregas.stream().map(this::toMapper).collect(Collectors.toList());
    }

    public Entrega toEntity(EntregaRequest entregaRequest) {
        return modelMapper.map(entregaRequest, Entrega.class);
    }

}
