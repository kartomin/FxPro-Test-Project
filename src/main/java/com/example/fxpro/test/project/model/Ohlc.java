package com.example.fxpro.test.project.model;

import com.example.fxpro.test.project.utils.OhlcPeriodConverter;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ohlc")
public class Ohlc {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @NonNull
    @Column(name = "open_price")
    Double openPrice;

    @NonNull
    @Column(name = "high_price")
    Double highPrice;

    @NonNull
    @Column(name = "low_price")
    Double lowPrice;

    @NonNull
    @Column(name = "close_price")
    Double closePrice;

    @NonNull
    @Convert(converter = OhlcPeriodConverter.class)
    @Column(name = "ohlc_period")
    OhlcPeriod ohlcPeriod;

    @NonNull
    @Column(name = "instrument_id")
    Long instrumentId;

    @NonNull
    @Column(name = "period_start_utc_timestamp")
    Long periodStartUtcTimestamp;
}
