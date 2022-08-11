package com.coralogix.calculator;

import com.coralogix.calculator.model.ExchangeRate;
import com.coralogix.calculator.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExchangeController {

    @Autowired
    ExchangeRateService exchangeRateService;

    @GetMapping(path = "/exchange/rate/{originCurrency}/{finalCurrency}")
    public ExchangeRate getExchangeRate(@PathVariable String originCurrency, @PathVariable String finalCurrency) {
        return exchangeRateService.getExchangeRate(originCurrency,finalCurrency);
    }

    @GetMapping(path = "/exchange")
    public List<ExchangeRate> getAllExchangeRate() {
        return exchangeRateService.getAllExchangeRate();
    }

}
