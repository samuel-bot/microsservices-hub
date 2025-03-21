package com.github.samuel_bot.ms_pagamento.dto;


import com.github.samuel_bot.ms_pagamento.entity.Pagamento;
import com.github.samuel_bot.ms_pagamento.entity.Status;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class PagamentoDTO {

    private Long id;
    @NotNull (message = "Campo Obrigatório")
    @Positive(message = "O valor do pagamento deve ser um número positivo")
    private BigDecimal valor;

    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 19, message = "0 cartão deve ter no máximo 19 caracteres")
    private String numeroDoCartao;

    @Size(max = 5, message = "A validade do cartão deve ter no máximo 5 caracteres")
    private String validade;

    @Size(min = 3, max = 3, message = "O código de segurança deve ter 3 caracteres")
    private String codigoDoCartao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Pedido ID é obrigatório")
    private Long pedidoId;

    @NotNull(message = "Forma de pagamento ID é obrigatório")
    private Long formaDePagamento;

    public PagamentoDTO(Pagamento entity){
    id = entity.getId();
    valor = entity.getValor();
    nome = entity.getNome();
    numeroDoCartao = entity.getNumeroDoCartao();
    validade = entity.getValidade();
    codigoDoCartao = entity.getCodigoDoCartao();
    status = entity.getStatus();
    pedidoId = entity.getPedidoId();
    formaDePagamento = entity.getFormaDePagamento();

    }

}
