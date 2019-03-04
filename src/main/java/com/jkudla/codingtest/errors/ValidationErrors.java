package com.jkudla.codingtest.errors;

import com.jkudla.codingtest.dto.TradeDataDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ValidationErrors {

    protected Map<TradeDataDto, Set<String>> errorsMap = new HashMap<>();

    // used to separate error messages from trade data linkage
    protected String TRADE_DATA_SPLITTER = " LINKAGE: ";

    public Set<String> getErrorMessages() {
        if (errorsMap.size() == 1){
            return getErrorListWithoutTradeDataLinkage(errorsMap.keySet().stream().findFirst().get());
        }
        return errorsMap.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
    }

    private Set<String> getErrorListWithoutTradeDataLinkage(TradeDataDto key) {
        return errorsMap.get(key).stream().map(v -> v = v.split(TRADE_DATA_SPLITTER)[0]).collect(Collectors.toSet());
    }

    public Map<TradeDataDto, Set<String>> getErrorsMap() {
        return errorsMap;
    }

    public boolean isEmpty() {
        return errorsMap.isEmpty();
    }
}
