package com.github.samuel_bot.ms_pagamento.service;


import com.github.samuel_bot.ms_pagamento.dto.PagamentoDTO;
import com.github.samuel_bot.ms_pagamento.entity.Pagamento;
import com.github.samuel_bot.ms_pagamento.entity.Status;
import com.github.samuel_bot.ms_pagamento.repository.PagamentoRepository;
import com.github.samuel_bot.ms_pagamento.service.exceptions.DatabaseException;
import com.github.samuel_bot.ms_pagamento.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Transactional(readOnly = true)
    public List<PagamentoDTO> getAll() {
        List<Pagamento> pagamentos = repository.findAll();

        return pagamentos.stream().map(PagamentoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findById(Long id) {
        Pagamento entity = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Recurso não encontrado. Id: " + id)
        );
        return new PagamentoDTO(entity);
    }

    @Transactional
    public PagamentoDTO create(PagamentoDTO dto) {
        Pagamento entity = new Pagamento();
        copyDtoToEntity(dto, entity);
        entity.setStatus(Status.CRIADO);
        entity = repository.save(entity);
        return new PagamentoDTO(entity);
    }

    @Transactional
    public PagamentoDTO update(Long id, PagamentoDTO dto) {
        try {
            Pagamento entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity.setStatus(dto.getStatus());
            entity = repository.save(entity);
            return new PagamentoDTO(entity);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possivel deletar o Pagamento.");
        }
    }

    private void copyDtoToEntity(PagamentoDTO dto, Pagamento entity){
    entity.setValor(dto.getValor());
    entity.setNome(dto.getNome());
    entity.setNumeroDoCartao(dto.getNumeroDoCartao());
    entity.setValidade(dto.getValidade());
    entity.setCodigoDoCartao(dto.getCodigoDoCartao());
    entity.setStatus(dto.getStatus());
    entity.setPedidoId(dto.getPedidoId());
    entity.setFormaDePagamento(dto.getFormaDePagamento());
    }
}
