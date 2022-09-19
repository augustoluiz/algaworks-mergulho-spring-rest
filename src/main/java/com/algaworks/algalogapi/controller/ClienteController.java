package com.algaworks.algalogapi.controller;

import com.algaworks.algalogapi.assembler.ClienteAssembler;
import com.algaworks.algalogapi.domain.model.Cliente;
import com.algaworks.algalogapi.domain.repository.ClienteRepository;
import com.algaworks.algalogapi.domain.service.CatalogoClienteService;
import com.algaworks.algalogapi.model.ClienteModel;
import com.algaworks.algalogapi.model.input.ClienteInput;
import lombok.AllArgsConstructor;
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

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private CatalogoClienteService catalogoClienteService;
    private ClienteRepository clienteRepository;
    private ClienteAssembler clienteAssembler;

    @GetMapping()
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteModel> buscar(@PathVariable("idCliente") Long idCliente){
        return clienteRepository.findById(idCliente)
                .map((cliente) -> ResponseEntity.ok(clienteAssembler.toModel(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteModel adicionar(@Valid @RequestBody ClienteInput clienteInput){
        Cliente cliente = clienteAssembler.toEntity(clienteInput);
        return clienteAssembler.toModel(catalogoClienteService.salvar(cliente));
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteModel> atualizar(@PathVariable("idCliente") Long idCliente, @RequestBody Cliente cliente){
        if(!clienteRepository.existsById(idCliente)){
            return ResponseEntity.notFound().build();
        }

        cliente.setId(idCliente);
        return ResponseEntity.ok(clienteAssembler.toModel(catalogoClienteService.salvar(cliente)));
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deletar(@PathVariable("idCliente") Long idCliente){
        if(!clienteRepository.existsById(idCliente)){
            return ResponseEntity.notFound().build();
        }

        catalogoClienteService.excluir(idCliente);
        return ResponseEntity.noContent().build();
    }
}
