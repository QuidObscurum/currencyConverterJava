package com.quid.currencyconverter.service;

import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.config.HibernateConfig;
import com.quid.currencyconverter.myutils.CurrencyCode;
import static org.assertj.core.api.Assertions.assertThat;

import com.quid.currencyconverter.dto.ExchangeResultDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {ApplicationConfig.class, HibernateConfig.class})
class ConverterServiceTest {
    @Autowired
    private ConverterService converter;

    @Test
    void shouldConvert() {
        String inputValueString = "1000";
        BigDecimal inputValueBD = new BigDecimal(inputValueString).setScale(4, RoundingMode.HALF_UP);
        List<List<String>> currencyPairs = getCurrencyPairs(
                Arrays.asList(CurrencyCode.values())
        );
        for (List<String> nestedList : currencyPairs) {
            // Prepare args list
            nestedList.add(0, inputValueString);
            nestedList.add(2, ":");
            System.out.println(nestedList);

            // Get ConvertQuery obj out of args list
            ConvertQuery query = ConvertQuery.getQuery(nestedList);

            // Get ExchangeResultDTO obj
            ExchangeResultDTO resultDTO = new ExchangeResultDTO(
                    inputValueBD,
                    CurrencyCode.valueOf(nestedList.get(1)),
                    CurrencyCode.valueOf(nestedList.get(3))
            );

            // Check conversion is successful for ConvertQuery obj
            BigDecimal converted = converter.convert(query);
            System.out.println(converted);

            // Check conversion is successful for ConvertQuery obj
            ExchangeResultDTO exchangeResultDTO = converter.convert(resultDTO);
            System.out.println(exchangeResultDTO.getExchangeResultValue());

            System.out.println();
            assertThat(converted).isNotEqualTo(inputValueBD);
            assertThat(exchangeResultDTO.getExchangeResultValue()).isEqualTo(converted);
        }

        List<String> args = Arrays.asList(inputValueString, "BYN", ":", "BYN");
        ConvertQuery query = ConvertQuery.getQuery(args);
        ExchangeResultDTO resultDTO = new ExchangeResultDTO(
                new BigDecimal(inputValueString),
                CurrencyCode.valueOf(args.get(1)),
                CurrencyCode.valueOf(args.get(3))
        );
        assertThat(converter.convert(query)).isEqualTo(inputValueBD);
        assertThat(converter.convert(resultDTO).getExchangeResultValue()).isEqualTo(inputValueBD);
    }

    private List<List<String>> getCurrencyPairs(List<CurrencyCode> pool) {
        // getCurrencyPairs(new ArrayList<>('A', 'B', 'C', 'D'))
        // --> [[AB], [AC], [AD], [BA], [BC], [BD], [CA], [CB], [CD], [DA], [DB], [DC]]
        List<List<String>> res = new ArrayList<>();

        for (int i = 0; i < pool.size(); i++) {
            List<String> pair = new ArrayList<>();
            pair.add(pool.get(i).toString());
            for (int j = 0; j < pool.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (pair.size() > 1) {
                    pair.set(1, pool.get(j).toString());
                } else {
                    pair.add(pool.get(j).toString());
                }
                res.add(new ArrayList<>(pair));
            }
        }
        return res;
    }
}