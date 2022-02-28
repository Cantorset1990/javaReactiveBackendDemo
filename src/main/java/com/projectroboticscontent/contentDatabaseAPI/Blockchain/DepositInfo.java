package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import org.springframework.data.mongodb.core.mapping.Field;

public class DepositInfo {

    @Field
    private String address;
    @Field
    private String depositAmount;
    @Field
    private String symbol;
    @Field
    private String units;
    @Field
    private String txHash;

    public DepositInfo(String address, String depositAmount, String symbol, String units, String txHash) {
        this.address = address;
        this.depositAmount = depositAmount;
        this.symbol = symbol;
        this.units = units;
        this.txHash = txHash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }
}
