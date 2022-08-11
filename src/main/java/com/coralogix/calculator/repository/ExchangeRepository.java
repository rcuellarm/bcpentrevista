package com.coralogix.calculator.repository;

import com.coralogix.calculator.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends CrudRepository<ExchangeRate, Long> {
    ExchangeRate findByOriginCurrencyAndFinalCurrency(String originCurrency, String finalCurrency);
}
