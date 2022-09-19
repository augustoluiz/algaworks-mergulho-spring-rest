package com.algaworks.algalogapi.domain.service;

import com.algaworks.algalogapi.domain.exception.NegocioException;
import com.algaworks.algalogapi.domain.model.Cliente;
import com.algaworks.algalogapi.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CatalogoClienteService {

    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
                .stream()
                .anyMatch(clienteExistente -> clienteExistente.getId() != cliente.getId());

        if(emailEmUso){
            throw new NegocioException("Já existe um cliente cadastrado com esse e-mail");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void excluir(Long idCliente){
        clienteRepository.deleteById(idCliente);
    }

    public Cliente buscar(Long clienteId){
        return clienteRepository.findById(clienteId).orElseThrow(() -> new NegocioException("Cliente não encontrado"));
    }

}
