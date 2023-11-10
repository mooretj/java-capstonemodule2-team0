package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransferDto {

    private int transId;
    private int transTypeId;
    private int transStatusId;
    @JsonProperty("account_from")
    private int acctFromId;
    @JsonProperty("account_to")
    private int acctToId;
    private BigDecimal amount;
    private String username;

    public TransferDto(){}
    public TransferDto(int acctFromId, int acctToId, BigDecimal amount){
        this.acctFromId = acctFromId;
        this.acctToId = acctToId;
        this.amount = amount;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public int getTransTypeId() {
        return transTypeId;
    }

    public void setTransTypeId(int transTypeId) {
        this.transTypeId = transTypeId;
    }

    public int getTransStatusId() {
        return transStatusId;
    }

    public void setTransStatusId(int transStatusId) {
        this.transStatusId = transStatusId;
    }

    public int getAcctFromId() {
        return this.acctFromId;
    }

    public void setAcctFromId(int acctFromId) {
        this.acctFromId = acctFromId;
    }

    public int getAcctToId() {
        return this.acctToId;
    }

    public void setAcctToId(int acctToId) {
        this.acctToId = acctToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
