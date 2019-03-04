package com.jkudla.codingtest.result;

import com.jkudla.codingtest.util.ResponseStatusEnum;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class TradeDataResult {
    private ResponseStatusEnum status;
    private Set<String> errors = new TreeSet<>();

    @SuppressWarnings("unused")
    public TradeDataResult(){

    }

    public TradeDataResult(ResponseStatusEnum status) {
        this.status = status;
    }

    public TradeDataResult(ResponseStatusEnum status, Set<String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public ResponseStatusEnum getStatus() {
        return status;
    }

    public Set<String> getErrors() {
        return errors;
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradeDataResult that = (TradeDataResult) o;
        return status == that.status &&
            Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, errors);
    }
}


