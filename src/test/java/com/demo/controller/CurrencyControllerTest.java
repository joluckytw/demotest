package com.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.demo.model.Currency;
import com.demo.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
public class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;
    @InjectMocks
    private CurrencyController currencyController;
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
    }

    @Test
    public void testCreateCurrency() throws Exception {
        Currency currency = new Currency();
        currency.setCode("NTD");
        currency.setName("新台幣");

        when(currencyService.addCurrency(any(Currency.class))).thenReturn(currency);
        mockMvc.perform(post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(currency)))
        .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("NTD"))
                .andExpect(jsonPath("$.name").value("新台幣"));
        System.out.println("呼叫testCreateCurrency"); 
    }
    
    @Test
    public void testGetCurrency() throws Exception {
        Long id = 1L;
        Currency currency = new Currency();
        currency.setId(id);
        currency.setCode("NTD");
        currency.setName("新台幣");
        given(currencyService.getCurrencyById(id)).willReturn(currency);
        mockMvc.perform(get("/api/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.code").value("NTD"))
                .andExpect(jsonPath("$.name").value("新台幣"));
        System.out.println("呼叫testGetCurrencyById"); 
    }
    
    @Test
    public void testUpdateCurrency() throws Exception {
        // Arrange
        Long id = 3L;
        Currency currency = new Currency();
        currency.setId(id);
        currency.setCode("USD");
        currency.setName("美金");
        ObjectMapper objectMapper = new ObjectMapper();
        given(currencyService.updateCurrency(any(Long.class), any(Currency.class)));
        mockMvc.perform(put("/api/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testGetAllCurrencies() throws Exception {

        Currency currency1 = new Currency();
        currency1.setId(1L);
        currency1.setCode("NTD");
        currency1.setName("新台幣");

        Currency currency2 = new Currency();
        currency2.setId(2L);
        currency2.setCode("USD");
        currency2.setName("美金");

        List<Currency> currencies = Arrays.asList(currency1, currency2);
        given(currencyService.getAllCurrencies()).willReturn(currencies);

        mockMvc.perform(get("/api/")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].code").value("NTD"))
                .andExpect(jsonPath("$[0].name").value("新台幣"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].code").value("USD"))
                .andExpect(jsonPath("$[1].name").value("美金"));
        System.out.println("呼叫testGetAllCurrencies");
    }

    
    @Test
    public void testDeleteCurrency() throws Exception {

        Long id = 1L;
        willDoNothing().given(currencyService).deleteCurrency(id);
        mockMvc.perform(delete("/api/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isOk());
        System.out.println("呼叫testDeleteCurrency");
    }

    
    
}
