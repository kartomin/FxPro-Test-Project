package com.example.fxpro.test.project.kafka;

import com.example.fxpro.test.project.model.Quote;
import com.example.fxpro.test.project.service.QuoteService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class QuoteMessageListener {

    QuoteService quoteService;

    @KafkaListener(topics="quote", groupId = "quoteGroup")
    public void onQuote(Quote quote) {
        log.info("Received quote: {}", quote);
        quoteService.store(quote);
    }

}
