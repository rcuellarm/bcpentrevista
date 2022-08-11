package com.coralogix.calculator.services;

import com.coralogix.calculator.model.ExchangeRate;
import com.coralogix.calculator.model.Fixed;
import com.coralogix.calculator.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeRateService {
    @Autowired
    ExchangeRepository exchangeRepository;
    public ExchangeRate getExchangeRate(String originCurrency, String finalCurrency) {
        String uri = "http://172.22.80.1:9080/fixer/latest?base={originCurrency}&symbols={finalCurrency}";
        Map<String, String> map = new HashMap<>();
        map.put("originCurrency", originCurrency);
        map.put("finalCurrency", finalCurrency);
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        ResponseEntity<Fixed> result = restTemplate.exchange(uri, HttpMethod.GET, null, Fixed.class, map);
        ExchangeRate exchangeRate = exchangeRepository.findByOriginCurrencyAndFinalCurrency(result.getBody().getBase(),finalCurrency);
        if(exchangeRate == null) {
            exchangeRate = new ExchangeRate();
            exchangeRate.setOriginCurrency(originCurrency);
            exchangeRate.setFinalCurrency(finalCurrency);
        }
        exchangeRate.setValue(String.valueOf(result.getBody().getRates().get(finalCurrency)));
        exchangeRate.setDate(result.getBody().getDate());
        return exchangeRepository.save(exchangeRate);
    }

    public List<ExchangeRate> getAllExchangeRate() {
        return (List<ExchangeRate>) exchangeRepository.findAll();
    }
}
