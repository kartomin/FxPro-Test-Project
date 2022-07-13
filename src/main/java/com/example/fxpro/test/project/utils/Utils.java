package com.example.fxpro.test.project.utils;

import com.example.fxpro.test.project.model.Ohlc;
import com.example.fxpro.test.project.model.OhlcPeriod;
import com.example.fxpro.test.project.model.Quote;
import lombok.experimental.UtilityClass;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
public class Utils {
    private static final long ONE_SECOND = 1000L;
    private static final long ONE_MINUTE = 60 * ONE_SECOND;
    private static final long ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;

    public long getPeriodStartUtcTimestamp(OhlcPeriod ohlcPeriod) {
        long currentTimestamp = Calendar.getInstance().getTimeInMillis();
        switch (ohlcPeriod) {
            case M1 -> {
                return currentTimestamp - currentTimestamp % ONE_MINUTE;
            }
            case H1 -> {
                return currentTimestamp - currentTimestamp % ONE_HOUR;
            }
            case D1 -> {
                return currentTimestamp - currentTimestamp % ONE_DAY;
            }
        }

        return 0L;
    }

    public long getPreviousPeriodStartUtcTimestamp(OhlcPeriod ohlcPeriod) {
        long currentTimestamp = getPeriodStartUtcTimestamp(ohlcPeriod);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTimestamp);
        switch (ohlcPeriod) {
            case M1 -> {
                c.add(Calendar.MINUTE, -1);
            }
            case H1 -> {
                c.add(Calendar.HOUR, -1);
            }
            case D1 -> {
                c.add(Calendar.DATE, -1);
            }
        }
        return c.getTimeInMillis();
    }

    public boolean isNewPeriod(OhlcPeriod ohlcPeriod) {
        long currentTimestamp = Calendar.getInstance().getTimeInMillis();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTimestamp);
        switch (ohlcPeriod) {
            case M1 -> {
                return currentTimestamp % ONE_MINUTE < ONE_SECOND;
            }
            case H1 -> {
                return currentTimestamp % ONE_HOUR < ONE_MINUTE;
            }
            case D1 -> {
                return currentTimestamp % ONE_DAY < ONE_HOUR;
            }
        }

        return false;
    }

    public long getPeriodEndUtcTimestamp(long periodStartUtcTimestamp, OhlcPeriod ohlcPeriod) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(periodStartUtcTimestamp);
        switch (ohlcPeriod) {
            case M1 -> {
                c.add(Calendar.MINUTE, 1);
            }
            case H1 -> {
                c.add(Calendar.HOUR, 1);
            }
            case D1 -> {
                c.add(Calendar.DATE, 1);
            }
        }
        return c.getTimeInMillis();
    }

    public Ohlc calculateOhlcByQuotes(
            List<Quote> quotes,
            long periodStartUtcTimestamp,
            OhlcPeriod ohlcPeriod,
            long instrumentId
    ) {
        AtomicReference<Double> lowPrice = new AtomicReference<>(Double.MAX_VALUE);
        AtomicReference<Double> highPrice = new AtomicReference<>(Double.MIN_VALUE);
        AtomicReference<Double> openPrice = new AtomicReference<>((double) 0);
        AtomicReference<Double> closePrice = new AtomicReference<>((double) 0);
        AtomicLong minTimestamp = new AtomicLong(Long.MAX_VALUE);
        AtomicLong maxTimestamp = new AtomicLong(Long.MIN_VALUE);

        quotes.forEach(quote -> {
                if (quote.getUtcTimestamp() > maxTimestamp.get()) {
                    closePrice.set(quote.getPrice());
                    maxTimestamp.set(quote.getUtcTimestamp());
                }

                if (quote.getUtcTimestamp() < minTimestamp.get()) {
                    openPrice.set(quote.getPrice());
                    minTimestamp.set(quote.getUtcTimestamp());
                }

                if (quote.getPrice() > highPrice.get()) {
                    highPrice.set(quote.getPrice());
                }

                if (quote.getPrice() < lowPrice.get()) {
                    lowPrice.set(quote.getPrice());
                }
            });

        return Ohlc.builder()
                .periodStartUtcTimestamp(periodStartUtcTimestamp)
                .ohlcPeriod(ohlcPeriod)
                .instrumentId(instrumentId)
                .openPrice(openPrice.get())
                .closePrice(closePrice.get())
                .highPrice(highPrice.get())
                .lowPrice(lowPrice.get())
                .build();
    }

    public Ohlc calculateOhlcByOhlcList(
            List<Ohlc> ohlcList,
            OhlcPeriod ohlcPeriod,
            long instrumentId
    ) {
        AtomicReference<Double> lowPrice = new AtomicReference<>(Double.MAX_VALUE);
        AtomicReference<Double> highPrice = new AtomicReference<>(Double.MIN_VALUE);
        AtomicReference<Double> openPrice = new AtomicReference<>((double) 0);
        AtomicReference<Double> closePrice = new AtomicReference<>((double) 0);
        AtomicLong minTimestamp = new AtomicLong(Long.MAX_VALUE);
        AtomicLong maxTimestamp = new AtomicLong(Long.MIN_VALUE);

        ohlcList.forEach(ohlc -> {
            if (ohlc.getPeriodStartUtcTimestamp() > maxTimestamp.get()) {
                closePrice.set(ohlc.getClosePrice());
                maxTimestamp.set(ohlc.getPeriodStartUtcTimestamp());
            }

            if (ohlc.getPeriodStartUtcTimestamp() < minTimestamp.get()) {
                openPrice.set(ohlc.getOpenPrice());
                minTimestamp.set(ohlc.getPeriodStartUtcTimestamp());
            }

            if (ohlc.getHighPrice() > highPrice.get()) {
                highPrice.set(ohlc.getHighPrice());
            }

            if (ohlc.getLowPrice() < lowPrice.get()) {
                lowPrice.set(ohlc.getLowPrice());
            }
        });

        return Ohlc.builder()
                .periodStartUtcTimestamp(minTimestamp.get())
                .ohlcPeriod(ohlcPeriod)
                .instrumentId(instrumentId)
                .openPrice(openPrice.get())
                .closePrice(closePrice.get())
                .highPrice(highPrice.get())
                .lowPrice(lowPrice.get())
                .build();
    }
}
