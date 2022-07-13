package com.example.fxpro.test.project.service;

import com.example.fxpro.test.project.model.OhlcPeriod;
import com.example.fxpro.test.project.model.Quote;

import java.util.List;

public interface QuoteService {
    void store(Quote quote);
    List<Quote> findAll(long beforeUTCTimestamp);
    void removeAll(long beforeUTCTimestamp);
    List<Quote> findForPeriod(long periodStartUtcTimestamp, OhlcPeriod ohlcPeriod);
}
