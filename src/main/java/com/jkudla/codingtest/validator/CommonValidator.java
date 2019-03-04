package com.jkudla.codingtest.validator;

import com.jkudla.codingtest.errors.BusinessErrors;
import com.jkudla.codingtest.util.CurrencyHolidayMap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Common validator for general purpose.
 */
public class CommonValidator {

    public static List<String> SUPPORTED_COUNTERPARTIES = Arrays.asList("PLUTO1", "PLUTO2");

    public static void validateValueDateNotWeekend(BusinessErrors errors, LocalDate date) {
        if (date != null) {
            if (isWeekend(date)) {
                errors.add("Date " + date + " cannot be during the weekend");
            }
        }
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static void validateDateAfterAnotherDate(BusinessErrors errors, LocalDate olderDate, LocalDate newerDate, String olderDateName, String
        newerDateName) {
        if (olderDate != null && newerDate != null) {
            if (olderDate.isBefore(newerDate) || olderDate.isEqual(newerDate)) {
                errors.add("Date(" + newerDateName + "):" + newerDate + " has to be before date (" + olderDateName + "): " +
                    olderDate);
            }
        }
    }

    public static void validateCounterparty(BusinessErrors errors, String counterparty) {
        if (!SUPPORTED_COUNTERPARTIES.contains(counterparty)) {
            errors.add("Counterparty not supported: " + counterparty);
        }
    }

    public static void validateCurrency(BusinessErrors errors, String currency, String fieldName) {
        String errorMsg = "Currency validation failed for field: " + fieldName + ". Failed currency: " + currency;
        validateCurrencyCustomErrorMsg(errors, currency, errorMsg);
    }

    public static void validateCurrencyCustomErrorMsg(BusinessErrors errors, String currency, String errorMsg) {
        if (currency != null) {
            try {
                Currency.getInstance(currency);
            } catch (IllegalArgumentException e) {
                errors.add(errorMsg);
            }
        }
    }

    public static void validateCurrencyPair(BusinessErrors requestErrors, String currencyPair, String fieldName) {
        if (currencyPair != null) {
            String errorMsg = "Currency pair validation failed for field: " + fieldName + ". Failed currency: " + currencyPair;
            if (currencyPair.length() != 6) {
                requestErrors.add(errorMsg);
            } else {
                validateCurrencyCustomErrorMsg(requestErrors, currencyPairPart1(currencyPair), errorMsg);
                validateCurrencyCustomErrorMsg(requestErrors, currencyPairPart2(currencyPair), errorMsg);
            }
        }
    }

    public static void validateDateNotWeekendNotNonWorkingDayForCurrency(BusinessErrors requestErrors, LocalDate date, String ccyPair) {
        validateValueDateNotWeekend(requestErrors, date);

        if (isHolidayForGivenDateAndCurrencyPair(date, ccyPair)) {
            requestErrors.add("Value date is holiday: " + date);
        }
    }

    public static boolean isHolidayForGivenDateAndCurrencyPair(LocalDate date, String ccyPair) {
        return CurrencyHolidayMap.isHoliday(currencyPairPart1(ccyPair), date) ||
            CurrencyHolidayMap.isHoliday(currencyPairPart2(ccyPair), date);
    }

    public static String currencyPairPart1(String currencyPair) {
        return currencyPair.substring(0, 3);
    }

    public static String currencyPairPart2(String currencyPair) {
        return currencyPair.substring(3, 6);
    }

    public static <E extends Enum<E>> void validateWithEnum(BusinessErrors errors, String value, String fieldName,
                                                            Class<E> enumData) {
        boolean isValid = false;
        for (Enum<E> enumVal : enumData.getEnumConstants()) {
            if (enumVal.toString().equalsIgnoreCase(value)) {
                isValid = true;
            }
        }
        if (!isValid) {
            List<String> enumValues = Arrays.asList(enumData.getEnumConstants()).stream().map(e -> e.toString()).collect(Collectors.toList());
            errors.add("Field (" + fieldName + "): '" + value + "' is incorrect. Correct values: " + String.join(",", enumValues));
        }
    }
}
