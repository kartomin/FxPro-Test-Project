package com.example.fxpro.test.project.jobs;

import com.example.fxpro.test.project.model.Ohlc;
import com.example.fxpro.test.project.model.OhlcPeriod;
import com.example.fxpro.test.project.model.Quote;
import com.example.fxpro.test.project.service.OhlcService;
import com.example.fxpro.test.project.service.QuoteService;
import com.example.fxpro.test.project.utils.Utils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EnableScheduling
@ComponentScan(basePackageClasses = {QuoteService.class, OhlcService.class})
public class PersistOhlcJob {

    QuoteService quoteService;
    OhlcService ohlcService;

    public void persistOhlcDayPeriod(long periodStartUtcTimestamp) {
        log.info("persistOhlcDayPeriod has started");
        Map<Long, List<Ohlc>> ohlcGroupedByInstrumentId = new HashMap<>();
        ohlcService.findRecentOhlc(OhlcPeriod.H1, periodStartUtcTimestamp).forEach(ohlc -> {
            ohlcGroupedByInstrumentId.putIfAbsent(ohlc.getInstrumentId(), new ArrayList<>());
            ohlcGroupedByInstrumentId.get(ohlc.getInstrumentId()).add(ohlc);
        });
        ohlcGroupedByInstrumentId.forEach((instrumentId, ohlcs) -> {
            Ohlc ohlc = Utils.calculateOhlcByOhlcList(ohlcs, OhlcPeriod.D1, instrumentId);
            ohlcService.store(ohlc);
        });
        quoteService.removeAll(Utils.getPeriodEndUtcTimestamp(periodStartUtcTimestamp, OhlcPeriod.D1));
        log.info("persistOhlcDayPeriod has ended");
    }

    public void persistOhlcHourPeriod(long periodStartUtcTimestamp) {
        log.info("persistOhlcHourPeriod has started");
        Map<Long, List<Ohlc>> ohlcGroupedByInstrumentId = new HashMap<>();
        ohlcService.findRecentOhlc(OhlcPeriod.M1, periodStartUtcTimestamp).forEach(ohlc -> {
            ohlcGroupedByInstrumentId.putIfAbsent(ohlc.getInstrumentId(), new ArrayList<>());
            ohlcGroupedByInstrumentId.get(ohlc.getInstrumentId()).add(ohlc);
        });
        ohlcGroupedByInstrumentId.forEach((instrumentId, ohlcs) -> {
            Ohlc ohlc = Utils.calculateOhlcByOhlcList(ohlcs, OhlcPeriod.H1, instrumentId);
            ohlcService.store(ohlc);
        });
        log.info("persistOhlcHourPeriod has ended");

        if (Utils.isNewPeriod(OhlcPeriod.D1)) {
            persistOhlcDayPeriod(Utils.getPreviousPeriodStartUtcTimestamp(OhlcPeriod.H1));
        }
    }

    @Scheduled(cron = "1 * * * * *", zone = "UTC")
    public void persistOhlcMinutePeriod() {
        log.info("persistOhlcMinutePeriod has started");
        long periodStartUtcTimestamp = Utils.getPreviousPeriodStartUtcTimestamp(OhlcPeriod.M1);
        Map<Long, List<Quote>> quotesGroupedByInstrumentId = new HashMap<>();
        quoteService.findForPeriod(periodStartUtcTimestamp, OhlcPeriod.M1).forEach(quote -> {
            quotesGroupedByInstrumentId.putIfAbsent(quote.getInstrumentId(), new ArrayList<>());
            quotesGroupedByInstrumentId.get(quote.getInstrumentId()).add(quote);
        });
        quotesGroupedByInstrumentId.forEach((instrumentId, quotes) -> {
            Ohlc ohlc = Utils.calculateOhlcByQuotes(quotes, periodStartUtcTimestamp, OhlcPeriod.M1, instrumentId);
            ohlcService.store(ohlc);
        });
        log.info("persistOhlcMinutePeriod has ended");

        if (Utils.isNewPeriod(OhlcPeriod.H1)) {
            persistOhlcHourPeriod(Utils.getPreviousPeriodStartUtcTimestamp(OhlcPeriod.H1));
        }
    }
}
