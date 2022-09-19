package com.algaworks.algalogapi.controller;

import com.algaworks.algalogapi.assembler.OcorrenciaAssembler;
import com.algaworks.algalogapi.domain.model.Entrega;
import com.algaworks.algalogapi.domain.model.Ocorrencia;
import com.algaworks.algalogapi.domain.service.BuscaEntregaService;
import com.algaworks.algalogapi.domain.service.RegistroOcorrenciaService;
import com.algaworks.algalogapi.model.OcorrenciaModel;
import com.algaworks.algalogapi.model.input.OcorrenciaInput;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{entregaId}/ocorrencias")
public class OcorrenciaController {

    private RegistroOcorrenciaService registroOcorrenciaService;
    private OcorrenciaAssembler ocorrenciaAssembler;
    private BuscaEntregaService buscaEntregaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaModel regsitrar(@PathVariable Long entregaId, @Valid @RequestBody OcorrenciaInput ocorrenciaInput){
        Ocorrencia ocorrenciaRegistrada = registroOcorrenciaService.registrar(entregaId, ocorrenciaInput.getDescricao());
        return ocorrenciaAssembler.toModel(ocorrenciaRegistrada);
    }

    @GetMapping
    public List<OcorrenciaModel> listar(@PathVariable Long entregaId){
        Entrega entrega = buscaEntregaService.buscar(entregaId);
        return ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
    }

}
