package com.algaworks.algalogapi.assembler;

import com.algaworks.algalogapi.domain.model.Cliente;
import com.algaworks.algalogapi.model.ClienteModel;
import com.algaworks.algalogapi.model.input.ClienteInput;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Configuration
public class ClienteAssembler {

    private ModelMapper modelMapper;

    public ClienteModel toModel(Cliente cliente){
        return modelMapper.map(cliente, ClienteModel.class);
    }

    public List<ClienteModel> toCollectionModel(List<Cliente> clientes){
        return clientes.stream().map(this::toModel).collect(Collectors.toList());
    }

    public Cliente toEntity(ClienteInput clienteInput){
        return modelMapper.map(clienteInput, Cliente.class);
    }

}
