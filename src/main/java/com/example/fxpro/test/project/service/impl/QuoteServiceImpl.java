package com.example.fxpro.test.project.service.impl;

import com.example.fxpro.test.project.model.OhlcPeriod;
import com.example.fxpro.test.project.model.Quote;
import com.example.fxpro.test.project.repository.QuoteRepository;
import com.example.fxpro.test.project.service.QuoteService;
import com.example.fxpro.test.project.utils.Utils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service("quoteService")
public class QuoteServiceImpl implements QuoteService {

    QuoteRepository quoteRepository;

    @Override
    public void store(Quote quote) {
        quoteRepository.save(quote);
    }

    @Override
    public List<Quote> findAll(long beforeDate) {
        return quoteRepository.findByUtcTimestampBefore(beforeDate);
    }

    @Override
    public void removeAll(long beforeDate) {
        quoteRepository.removeByUtcTimestampBefore(beforeDate);
    }

    @Override
    public List<Quote> findForPeriod(long periodStartUtcTimestamp, OhlcPeriod ohlcPeriod) {
        long periodEndUtcTimestamp = Utils.getPeriodEndUtcTimestamp(periodStartUtcTimestamp, ohlcPeriod);
        return quoteRepository.findByUtcTimestampBetween(periodStartUtcTimestamp, periodEndUtcTimestamp);
    }
}
