package com.example.fxpro.test.project.service.impl;

import com.example.fxpro.test.project.model.Ohlc;
import com.example.fxpro.test.project.model.OhlcPeriod;
import com.example.fxpro.test.project.model.Quote;
import com.example.fxpro.test.project.repository.OhlcRepository;
import com.example.fxpro.test.project.repository.QuoteRepository;
import com.example.fxpro.test.project.service.OhlcService;
import com.example.fxpro.test.project.utils.Utils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service("ohlcService")
public class OhlcServiceImpl implements OhlcService {

    OhlcRepository ohlcRepository;
    QuoteRepository quoteRepository;

    @Transactional
    @Override
    public Ohlc getCurrent(long instrumentId, OhlcPeriod period) {
        long periodStartUtcTimestamp = Utils.getPeriodStartUtcTimestamp(period);
        List<Quote> quotes = quoteRepository.findByInstrumentIdAndUtcTimestampAfter(
                instrumentId,
                periodStartUtcTimestamp
        );
        return Utils.calculateOhlcByQuotes(quotes, periodStartUtcTimestamp, period, instrumentId);
    }

    @Transactional
    @Override
    public List<Ohlc> getHistorical(long instrumentId, OhlcPeriod period) {
        return ohlcRepository.getHistorical(instrumentId, period).stream()
                .sorted((a, b) -> b.getPeriodStartUtcTimestamp().compareTo(a.getPeriodStartUtcTimestamp()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Ohlc> getHistoricalAndCurrent(long instrumentId, OhlcPeriod period) {
        return Stream.concat(
                Stream.of(getCurrent(instrumentId, period)),
                getHistorical(instrumentId, period).stream()
        ).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void store(Ohlc ohlc) {
        ohlcRepository.store(ohlc);
    }

    @Transactional
    @Override
    public List<Ohlc> findRecentOhlc(OhlcPeriod ohlcPeriod, long periodStartUtcTimestamp) {
        return ohlcRepository.findByOhlcPeriodAndPeriodStartUtcTimestampAfter(ohlcPeriod, periodStartUtcTimestamp);
    }
}
