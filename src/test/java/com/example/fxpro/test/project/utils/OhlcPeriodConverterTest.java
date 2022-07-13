package com.example.fxpro.test.project.utils;

import com.example.fxpro.test.project.model.OhlcPeriod;
import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class OhlcPeriodConverterTest {

    @Test
    public void convertToDatabaseColumn_correct_input() {
        OhlcPeriodConverter ohlcPeriodConverter = new OhlcPeriodConverter();
        assertEquals("M1", OhlcPeriod.M1, ohlcPeriodConverter.convertToEntityAttribute("M1"));
        assertEquals("H1", OhlcPeriod.H1, ohlcPeriodConverter.convertToEntityAttribute("H1"));
        assertEquals("D1", OhlcPeriod.D1, ohlcPeriodConverter.convertToEntityAttribute("D1"));
    }

    @Test
    public void convertToDatabaseColumn_incorrect_input() {
        OhlcPeriodConverter ohlcPeriodConverter = new OhlcPeriodConverter();
        assertEquals("Should be null", null, ohlcPeriodConverter.convertToEntityAttribute("lshfafa"));
    }

    @Test
    public void convertToEntityAttribute_correct_input() {
        OhlcPeriodConverter ohlcPeriodConverter = new OhlcPeriodConverter();
        assertEquals("M1", ohlcPeriodConverter.convertToEntityAttribute("M1"), OhlcPeriod.M1);
        assertEquals("H1", ohlcPeriodConverter.convertToEntityAttribute("H1"), OhlcPeriod.H1);
        assertEquals("D1", ohlcPeriodConverter.convertToEntityAttribute("D1"), OhlcPeriod.D1);
    }
}
