package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}