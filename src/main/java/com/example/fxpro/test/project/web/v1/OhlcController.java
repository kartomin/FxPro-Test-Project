package com.example.fxpro.test.project.web.v1;

import com.example.fxpro.test.project.model.Ohlc;
import com.example.fxpro.test.project.model.OhlcPeriod;
import com.example.fxpro.test.project.service.OhlcService;
import com.example.fxpro.test.project.utils.OhlcPeriodConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/v1/ohlc")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OhlcController {

    OhlcService ohlcService;

    @GetMapping("/getCurrent")
    public @ResponseBody Ohlc getCurrent(@RequestParam Map<String, String> params) {
        Optional<Long> instrumentId = Optional.of(Long.parseLong(params.get("instrumentId")));
        Optional<OhlcPeriod> ohlcPeriod = Optional.ofNullable(
                new OhlcPeriodConverter().convertToEntityAttribute(params.get("ohlcPeriod"))
        );
        return ohlcService.getCurrent(instrumentId.orElseThrow(), ohlcPeriod.orElseThrow());
    }

    @GetMapping("/getHistorical")
    public @ResponseBody List<Ohlc> getHistorical(@RequestParam Map<String, String> params) {
        Optional<Long> instrumentId = Optional.of(Long.parseLong(params.get("instrumentId")));
        Optional<OhlcPeriod> ohlcPeriod = Optional.ofNullable(
                new OhlcPeriodConverter().convertToEntityAttribute(params.get("ohlcPeriod"))
        );
        return ohlcService.getHistorical(instrumentId.orElseThrow(), ohlcPeriod.orElseThrow());
    }

    @GetMapping("/getHistoricalAndCurrent")
    public @ResponseBody List<Ohlc> getHistoricalAndCurrent(@RequestParam Map<String, String> params) {
        Optional<Long> instrumentId = Optional.of(Long.parseLong(params.get("instrumentId")));
        Optional<OhlcPeriod> ohlcPeriod = Optional.ofNullable(
                new OhlcPeriodConverter().convertToEntityAttribute(params.get("ohlcPeriod"))
        );
        return ohlcService.getHistoricalAndCurrent(instrumentId.orElseThrow(), ohlcPeriod.orElseThrow());
    }
}
