package com.algaworks.algalogapi.domain.service;

import com.algaworks.algalogapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algalogapi.domain.model.Entrega;
import com.algaworks.algalogapi.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BuscaEntregaService {

    private EntregaRepository entregaRepository;

    public Entrega buscar(Long entregaId){
        return entregaRepository.findById(entregaId).orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada"));
    }

}
