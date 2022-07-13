package com.example.fxpro.test.project.utils;

import com.example.fxpro.test.project.model.OhlcPeriod;

import javax.persistence.AttributeConverter;

public class OhlcPeriodConverter implements AttributeConverter<OhlcPeriod, String> {

    @Override
    public String convertToDatabaseColumn(OhlcPeriod ohlcPeriod) {
        switch (ohlcPeriod) {
            case M1 -> { return "M1"; }
            case H1 -> { return "H1"; }
            case D1 -> { return "D1"; }
        }
        return null;
    }

    @Override
    public OhlcPeriod convertToEntityAttribute(String s) {
        switch (s) {
            case "M1" -> { return OhlcPeriod.M1; }
            case "H1" -> { return OhlcPeriod.H1; }
            case "D1" -> { return OhlcPeriod.D1; }
        }
        return null;
    }
}
