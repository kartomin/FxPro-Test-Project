package com.example.fxpro.test.project.service;

import com.example.fxpro.test.project.model.Ohlc;
import com.example.fxpro.test.project.model.OhlcPeriod;

import java.util.List;

public interface OhlcService {
    /** latest non persisted OHLC */
    Ohlc getCurrent (long instrumentId, OhlcPeriod period);

    /** all OHLCs which are kept in a database */
    List<Ohlc> getHistorical(long instrumentId, OhlcPeriod period);

    /** latest non persisted OHLC and OHLCs which are kept in a database */
    List<Ohlc> getHistoricalAndCurrent (long instrumentId, OhlcPeriod period);

    /** persist ohlc **/
    void store(Ohlc ohlc);

    List<Ohlc> findRecentOhlc(OhlcPeriod ohlcPeriod, long periodStartUtcTimestamp);
}
