package com.github.samuel_bot.ms_pagamento.repository;

import com.github.samuel_bot.ms_pagamento.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
