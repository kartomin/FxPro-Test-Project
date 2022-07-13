package com.example.fxpro.test.project.repository;

import com.example.fxpro.test.project.model.Ohlc;
import com.example.fxpro.test.project.model.OhlcPeriod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OhlcRepository extends CrudRepository<Ohlc, Long> {

    default void store(Ohlc ohlc) {
        save(ohlc);
    }

    default List<Ohlc> getHistorical(long instrumentId, OhlcPeriod period) {
        return findByInstrumentIdAndOhlcPeriod(instrumentId, period);
    }

    List<Ohlc> findByInstrumentIdAndOhlcPeriod(long instrumentId, OhlcPeriod ohlcPeriod);
    List<Ohlc> findByOhlcPeriodAndPeriodStartUtcTimestampAfter(OhlcPeriod ohlcPeriod, long periodStartUTCTimestamp);
}
