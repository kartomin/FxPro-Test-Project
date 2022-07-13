package com.example.fxpro.test.project.utils;

import com.example.fxpro.test.project.model.Ohlc;
import com.example.fxpro.test.project.model.OhlcPeriod;
import com.example.fxpro.test.project.model.Quote;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class UtilsTest {
    @Test
    public void calculateOhlcByQuotes() {
        List<Quote> quotes = new ArrayList<>();
        quotes.add(new Quote(1L, 542D, 3L, 1L));
        quotes.add(new Quote(2L, 545D, 3L, 2L));
        quotes.add(new Quote(3L, 540D, 3L, 3L));
        quotes.add(new Quote(4L, 543D, 3L, 4L));
        Ohlc ohlc = Utils.calculateOhlcByQuotes(quotes, 0L, OhlcPeriod.M1, 3L);
        assertNotNull(ohlc);
        assertEquals("Open price", ohlc.getOpenPrice(), 542D);
        assertEquals("Low price", ohlc.getLowPrice(), 540D);
        assertEquals("High price", ohlc.getHighPrice(), 545D);
        assertEquals("Close price", ohlc.getClosePrice(), 543D);
    }

    @Test
    public void calculateOhlcByOhlcList() {
        List<Ohlc> ohlcList = generateOhlcList();
        Ohlc ohlc = Utils.calculateOhlcByOhlcList(ohlcList, OhlcPeriod.M1, 3L);
        assertNotNull(ohlc);
        assertEquals("Open price", ohlc.getOpenPrice(), 115D);
        assertEquals("Low price", ohlc.getLowPrice(), 0D);
        assertEquals("High price", ohlc.getHighPrice(), 100D);
        assertEquals("Close price", ohlc.getClosePrice(), 13D);
    }

    private Ohlc buildOhlc(double openPrice, double lowPrice, double highPrice, double closePrice, long periodStart) {
        return Ohlc.builder()
                .id((long)(Math.random() * Long.MAX_VALUE))
                .instrumentId(3L)
                .ohlcPeriod(OhlcPeriod.M1)
                .openPrice(openPrice)
                .lowPrice(lowPrice)
                .highPrice(highPrice)
                .closePrice(closePrice)
                .periodStartUtcTimestamp(periodStart)
                .build();
    }

    private List<Ohlc> generateOhlcList() {
        List<Ohlc> ohlcList = new ArrayList<>();
        ohlcList.add(buildOhlc(115D, 0D, 3D, 4D, 0L));
        ohlcList.add(buildOhlc(1D, 2D, 3D, 4D, 1L));
        ohlcList.add(buildOhlc(1D, 2D, 3D, 4D, 2L));
        ohlcList.add(buildOhlc(1D, 2D, 100D, 4D, 3L));
        ohlcList.add(buildOhlc(1D, 2D, 3D, 4D, 4L));
        ohlcList.add(buildOhlc(1D, 0D, 1D, 4D, 5L));
        ohlcList.add(buildOhlc(1D, 2D, 3D, 4D, 6L));
        ohlcList.add(buildOhlc(1D, 2D, 3D, 13D, 7L));
        return ohlcList;
    }
}
