package com.algaworks.algalogapi.controller;

import com.algaworks.algalogapi.assembler.EntregaAssembler;
import com.algaworks.algalogapi.domain.model.Entrega;
import com.algaworks.algalogapi.domain.repository.EntregaRepository;
import com.algaworks.algalogapi.domain.service.FinalizacaoEntregaService;
import com.algaworks.algalogapi.domain.service.SolicitacaoEntregaService;
import com.algaworks.algalogapi.model.EntregaModel;
import com.algaworks.algalogapi.model.input.EntregaInput;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/entregas")
public class EntregaController {

    private EntregaRepository entregaRepository;
    private SolicitacaoEntregaService solicitacaoEntregaService;
    private FinalizacaoEntregaService finalizacaoentregaService;
    private EntregaAssembler entregaAssembler;

    @PostMapping
    public ResponseEntity<EntregaModel> criar(@Valid @RequestBody EntregaInput entregaInput){
        Entrega entrega = entregaAssembler.toEntity(entregaInput);
        return new ResponseEntity(entregaAssembler.toModel(solicitacaoEntregaService.solicitar(entrega)), HttpStatus.CREATED);
    }

    @GetMapping
    public List<EntregaModel> listar(){
        return entregaAssembler.toCollectionModel(entregaRepository.findAll());
    }

    @GetMapping("/{entregaId}")
    public ResponseEntity<EntregaModel> buscar(@PathVariable("entregaId") Long entregaId){
        return entregaRepository.findById(entregaId)
                .map((entrega -> ResponseEntity.ok(entregaAssembler.toModel(entrega))))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{entregaId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable("entregaId") Long entregaId){
        finalizacaoentregaService.finalizar(entregaId);
    }

}
