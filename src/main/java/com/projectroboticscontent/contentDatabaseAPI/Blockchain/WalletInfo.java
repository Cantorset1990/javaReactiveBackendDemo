package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import org.springframework.data.mongodb.core.mapping.Field;

public class WalletInfo {

    @Field
    private String address;

    @Field
    private String balance;

    @Field
    private String symbol;

    @Field
    private String units;

    public WalletInfo(String address, String balance, String symbol, String units) {
        this.address = address;
        this.balance = balance;
        this.symbol = symbol;
        this.units = units;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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
}
