package com.github.samuel_bot.ms_pagamento.controller;


import com.github.samuel_bot.ms_pagamento.dto.PagamentoDTO;
import com.github.samuel_bot.ms_pagamento.service.PagamentoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>>getAll(){
        List<PagamentoDTO> dto = service.getAll();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Long id){
        PagamentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> createPagamento(
            @RequestBody @Valid PagamentoDTO dto){
        dto = service.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> updatePagamento(
            @PathVariable Long id,
            @RequestBody @Valid PagamentoDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
