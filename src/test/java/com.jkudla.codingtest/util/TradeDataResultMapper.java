package com.jkudla.codingtest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkudla.codingtest.result.TradeDataResult;

import java.io.IOException;

public class TradeDataResultMapper {

    private TradeDataResultMapper(){

    }

    public static TradeDataResult readTradeDataResult(String result){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(result, TradeDataResult.class);
        } catch(IOException e){
            throw new RuntimeException("Couldn't read tradeDataDto data", e);
        }
    }
}
