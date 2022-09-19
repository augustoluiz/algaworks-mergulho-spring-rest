package com.algaworks.algalogapi.assembler;

import com.algaworks.algalogapi.domain.model.Ocorrencia;
import com.algaworks.algalogapi.model.OcorrenciaModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OcorrenciaAssembler {

    private ModelMapper modelMapper;

    public OcorrenciaModel toModel(Ocorrencia ocorrencia){
        return modelMapper.map(ocorrencia, OcorrenciaModel.class);
    }

    public List<OcorrenciaModel> toCollectionModel(List<Ocorrencia> ocorrenciaList){
        return ocorrenciaList.stream().map(this::toModel).collect(Collectors.toList());
    }

}
