package com.jkudla.codingtest.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Currency holidays for year 2016, since
 * <p>
 * source: https://www.theice.com/publicdocs/clear_us/ICEU_Currency_Bank_Holidays_for_2016.pdf
 * <p>
 *
 */
public class CurrencyHolidayMap {

    private static Map<String, List<LocalDate>> currencyHolidayMap = new HashMap<>();

    public static boolean isHoliday(String currency, LocalDate date){
        return currencyHolidayMap.get(currency).stream().filter(d -> d.equals(date)).findAny().isPresent();
    }

    static {

        currencyHolidayMap.put("USD",
                Arrays.asList(LocalDate.of(2016, 1, 1),
                        LocalDate.of(2016, 1, 18),
                        LocalDate.of(2016, 2, 15),
                        LocalDate.of(2016, 3, 25),
                        LocalDate.of(2016, 5, 30),
                        LocalDate.of(2016, 7, 4),
                        LocalDate.of(2016, 9, 5),
                        LocalDate.of(2016, 10, 10),
                        LocalDate.of(2016, 11, 24),
                        LocalDate.of(2016, 12, 26)));

        currencyHolidayMap.put("EUR",
                Arrays.asList(LocalDate.of(2016, 1, 1),
                        LocalDate.of(2016, 3, 25),
                        LocalDate.of(2016, 3, 28),
                        LocalDate.of(2016, 12, 26)));

        //other currencies.....
    }
}
