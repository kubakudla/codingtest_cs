package com.jkudla.codingtest.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * DTO class for holding all possible values in trade data
 *
 */

public class TradeDataDto {

    public static final String CUSTOMER_CANNOT_BE_NULL = "'customer' can't be null.";
    public static final String CCY_PAIR_CANNOT_BE_NULL = "'ccyPair' can't be null.";
    public static final String TYPE_CANNOT_BE_NULL = "'type' can't be null.";
    public static final String DIRECTION_CANNOT_BE_NULL = "'direction' can't be null.";
    public static final String LEGAL_ENTITY_CANNOT_BE_NULL = "'legalEntity' can't be null.";
    public static final String TRADER_CANNOT_BE_NULL = "'trader' can't be null.";
    public static final String AMOUNT1_CANNOT_BE_NULL = "'amount1' can't be null.";
    public static final String AMOUNT2_CANNOT_BE_NULL = "'amount2' can't be null.";
    public static final String RATE_CANNOT_BE_NULL = "'rate' can't be null.";

    @NotNull(message = CUSTOMER_CANNOT_BE_NULL)
    @ApiModelProperty(value = "Name of the customer", required = true)
    private String customer;

    @NotNull(message = CCY_PAIR_CANNOT_BE_NULL)
    @ApiModelProperty(value = "Currency pair", required = true)
    private String ccyPair;

    @NotNull(message = TYPE_CANNOT_BE_NULL)
    @ApiModelProperty(value = "Trade type (Spot/Forward/Vanilla option)", required = true)
    private String type;

    @NotNull(message = DIRECTION_CANNOT_BE_NULL)
    @ApiModelProperty(value = "Direction of the trade (SELL/BUY)", required = true)
    private String direction;

    @ApiModelProperty(value = "Style")
    private String style;

    @ApiModelProperty(value = "Strategy (CALL)")
    private String strategy;

    @ApiModelProperty(value = "Pay currency")
    private String payCcy;

    @ApiModelProperty(value = "Premium Currency")
    private String premiumCcy;

    @ApiModelProperty(value = "Premium type")
    private String premiumType;

    @NotNull(message = LEGAL_ENTITY_CANNOT_BE_NULL)
    @ApiModelProperty(value = "Legal entity", required = true)
    private String legalEntity;

    @NotNull(message = TRADER_CANNOT_BE_NULL)
    @ApiModelProperty(value = "Trader of the trade", required = true)
    private String trader;

    @ApiModelProperty(value = "Trade date")
    private LocalDate tradeDate;

    @ApiModelProperty(value = "Value date")
    private LocalDate valueDate;

    @ApiModelProperty(value = "Delivery date")
    private LocalDate deliveryDate;

    @ApiModelProperty(value = "Expiry date")
    private LocalDate expiryDate;

    @ApiModelProperty(value = "Excercise start date")
    private LocalDate excerciseStartDate;

    @ApiModelProperty(value = "Premium date")
    private LocalDate premiumDate;

    @NotNull(message = AMOUNT1_CANNOT_BE_NULL)
    @ApiModelProperty(value = "Amount 1", required = true)
    private BigDecimal amount1;

    @NotNull(message = AMOUNT2_CANNOT_BE_NULL)
    @ApiModelProperty(value = "Amount 2", required = true)
    private BigDecimal amount2;

    @NotNull(message = RATE_CANNOT_BE_NULL)
    @ApiModelProperty(value = "Rate of the trade", required = true)
    private BigDecimal rate;

    @ApiModelProperty(value = "Premium")
    private BigDecimal premium;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public void setCcyPair(String ccyPair) {
        this.ccyPair = ccyPair;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getPayCcy() {
        return payCcy;
    }

    public void setPayCcy(String payCcy) {
        this.payCcy = payCcy;
    }

    public String getPremiumCcy() {
        return premiumCcy;
    }

    public void setPremiumCcy(String premiumCcy) {
        this.premiumCcy = premiumCcy;
    }

    public String getPremiumType() {
        return premiumType;
    }

    public void setPremiumType(String premiumType) {
        this.premiumType = premiumType;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getExcerciseStartDate() {
        return excerciseStartDate;
    }

    public void setExcerciseStartDate(LocalDate excerciseStartDate) {
        this.excerciseStartDate = excerciseStartDate;
    }

    public LocalDate getPremiumDate() {
        return premiumDate;
    }

    public void setPremiumDate(LocalDate premiumDate) {
        this.premiumDate = premiumDate;
    }

    public BigDecimal getAmount1() {
        return amount1;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradeDataDto that = (TradeDataDto) o;
        return Objects.equals(customer, that.customer) &&
            Objects.equals(ccyPair, that.ccyPair) &&
            Objects.equals(type, that.type) &&
            Objects.equals(direction, that.direction) &&
            Objects.equals(style, that.style) &&
            Objects.equals(strategy, that.strategy) &&
            Objects.equals(payCcy, that.payCcy) &&
            Objects.equals(premiumCcy, that.premiumCcy) &&
            Objects.equals(premiumType, that.premiumType) &&
            Objects.equals(legalEntity, that.legalEntity) &&
            Objects.equals(trader, that.trader) &&
            Objects.equals(tradeDate, that.tradeDate) &&
            Objects.equals(valueDate, that.valueDate) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(excerciseStartDate, that.excerciseStartDate) &&
            Objects.equals(premiumDate, that.premiumDate) &&
            Objects.equals(amount1, that.amount1) &&
            Objects.equals(amount2, that.amount2) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(premium, that.premium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, ccyPair, type, direction, style, strategy, payCcy, premiumCcy, premiumType, legalEntity, trader, tradeDate, valueDate, deliveryDate, expiryDate, excerciseStartDate, premiumDate, amount1, amount2, rate, premium);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TradeDataDto.class.getSimpleName() + "[", "]")
            .add("customer='" + customer + "'")
            .add("ccyPair='" + ccyPair + "'")
            .add("type='" + type + "'")
            .add("direction='" + direction + "'")
            .add("style='" + style + "'")
            .add("strategy='" + strategy + "'")
            .add("payCcy='" + payCcy + "'")
            .add("premiumCcy='" + premiumCcy + "'")
            .add("premiumType='" + premiumType + "'")
            .add("legalEntity='" + legalEntity + "'")
            .add("trader='" + trader + "'")
            .add("tradeDate=" + tradeDate)
            .add("valueDate=" + valueDate)
            .add("deliveryDate=" + deliveryDate)
            .add("expiryDate=" + expiryDate)
            .add("excerciseStartDate=" + excerciseStartDate)
            .add("premiumDate=" + premiumDate)
            .add("amount1=" + amount1)
            .add("amount2=" + amount2)
            .add("rate=" + rate)
            .add("premium=" + premium)
            .toString();
    }
}