package com.jkudla.codingtest.controller;

import com.jkudla.codingtest.dto.TradeDataDto;
import com.jkudla.codingtest.errors.ValidationErrors;
import com.jkudla.codingtest.result.TradeDataResult;
import com.jkudla.codingtest.service.TradeValueService;
import com.jkudla.codingtest.util.ResponseStatusEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main rest controller for trade data validation
 */
@RestController
@RequestMapping("/api/trade/data")
public class TradeDataRestController {


    @Autowired
    private TradeValueService tradeValueDateValidator;

    private static final Logger LOGGER = Logger.getLogger(TradeDataRestController.class.getName());

    /**
     * Validates provided trade data
     *
     * @param tradeDataDtoList
     * @return
     */
    @ApiOperation("Consume trades and return validation results")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TradeDataResult> validateTradeData(
        @RequestBody List<TradeDataDto> tradeDataDtoList) {

        LOGGER.log(Level.INFO, "Received request: " + tradeDataDtoList);
        ValidationErrors errors = tradeValueDateValidator.validate(tradeDataDtoList);

        if (!errors.isEmpty()) {
            Set<String> errorMessages = errors.getErrorMessages();
            LOGGER.log(Level.INFO, "Received errors: " + errorMessages);
            return new ResponseEntity<>(new TradeDataResult(ResponseStatusEnum.ERROR, errorMessages), HttpStatus.BAD_REQUEST);
        }
        LOGGER.log(Level.INFO, "No errors received");
        return new ResponseEntity<>(new TradeDataResult(ResponseStatusEnum.SUCCESS), HttpStatus.OK);
    }

}
