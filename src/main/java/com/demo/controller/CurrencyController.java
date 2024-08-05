
package com.demo.controller;

import com.demo.model.Currency;
import com.demo.repository.CurrencyRepository;
import com.demo.service.CoindeskService;
import com.demo.service.CurrencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    @Autowired
    private CoindeskService coindeskService;
    @Autowired
    private CurrencyService currencyService;
    
    @GetMapping("/data")
    public String getCoindeskJson() {
        return coindeskService.getFormattedCoindeskJson(); 
    }
    @GetMapping("/newdata")
    public String getCoindeskData() {
           System.out.println(coindeskService.getCoindeskData());
        return coindeskService.getCoindeskData();
    }
    @GetMapping("/all")
    public List<Currency> getCoindesall() {
        return currencyService.getAllCurrencies();
    }
    @PostMapping("/add")
    public Currency createCurrency(@RequestBody Currency currency) {
         currency = currencyService.addCurrency(currency);
        return currency;
    }

    @GetMapping("/{id}")
    public Currency  getCurrencyById(@PathVariable Long id) {
        Currency  currency = currencyService.getCurrencyById(id);
        return currency;
    }
    
    @PutMapping("/{id}")
    public Currency updateCurrency(@PathVariable Long id, @RequestBody Currency currency) {
        Currency updatedCurrency = currencyService.updateCurrency(id, currency);
        return updatedCurrency;
    }

    @DeleteMapping("/{id}")
    public String deleteCurrency(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return "刪除成功";
    }
    
    
    

}
