
package com.demo.service;

import com.demo.model.Currency;
import com.demo.repository.CurrencyRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency getCurrencyById(Long id) {
        return currencyRepository.findById(id).orElse(null);
    }

    public Currency addCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    public Currency updateCurrency(Long id, Currency currency) {
        if (currencyRepository.existsById(id)) {
            currency.setId(id);
            return currencyRepository.save(currency);
        }
        return null;
    }

    public void deleteCurrency(Long id) {
        currencyRepository.deleteById(id);
    }

}
