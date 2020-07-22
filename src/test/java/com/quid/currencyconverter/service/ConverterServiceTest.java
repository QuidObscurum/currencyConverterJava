package com.quid.currencyconverter.service;

import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.config.HibernateConfig;
import static com.quid.currencyconverter.service.ConvertQuery.CurrencyCode;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
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
        String inputValue = "1000";
        List<List<String>> currencyPairs = getCurrencyPairs(
                Arrays.asList(CurrencyCode.values())
        );
        for (List<String> nestedList : currencyPairs) {
            // Prepare args list
            nestedList.add(0, inputValue);
            nestedList.add(2, ":");
            System.out.println(nestedList);
            // Get ConvertQuery obj out of args list
            ConvertQuery query = ConvertQuery.getQuery(nestedList);
            // Check conversion is successful
            BigDecimal converted = converter.convert(query);
            System.out.println(converted);
            System.out.println();
            assertNotEquals(converted, new BigDecimal(inputValue));
        }
    }

    private List<List<String>> getCurrencyPairs(List<CurrencyCode> pool) {
        // permutations(new ArrayList<>('A', 'B', 'C', 'D'))
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