package com.example.fxpro.test.project.repository;

import com.example.fxpro.test.project.model.Quote;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends RedisDocumentRepository<Quote, Long> {
    List<Quote> findByInstrumentIdAndUtcTimestampAfter(long instrumentId, long periodStartUtcTimestamp);
    List<Quote> findByUtcTimestampBefore(long date);
    List<Quote> findByUtcTimestampBetween(long begin, long end);
    void removeByUtcTimestampBefore(long date);
}
