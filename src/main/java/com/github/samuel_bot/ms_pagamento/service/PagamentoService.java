package com.github.samuel_bot.ms_pagamento.service;


import com.github.samuel_bot.ms_pagamento.dto.PagamentoDTO;
import com.github.samuel_bot.ms_pagamento.entity.Pagamento;
import com.github.samuel_bot.ms_pagamento.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public List<PagamentoDTO> getAll(){
        List<Pagamento> pagamentos = repository.findAll();

        return pagamentos.stream().map(PagamentoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public PagamentoDTO finById(Long id){
        Pagamento entity = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Recurso não encontrado. Id: " + id)
        );
        return new PagamentoDTO(entity);
    }

}
