package com.projectroboticscontent.contentDatabaseAPI.Hardware;

import org.springframework.data.mongodb.core.mapping.Field;

public class HardwareInfo {

    @Field
    private String HARDWARE_ID;

    @Field
    private String INITIALIZE_DATE_TIME;

    @Field
    private String TERMINATE_DATE_TIME;

    @Field
    private long WORK_DONE;

    @Field
    private long POWER_CONSUMPTION;

    @Field
    private String STATUS;

    @Field
    private long MACHINE_OWNER;

    @Field
    private String WALLET_ID_INVESTOR;

    @Field
    private String WALLET_ID_USER;

    @Field
    private String WALLET_ID_SOLUTION_PROVIDER;

    @Field
    private long PAYMENT;

    @Field
    private long USAGE_TIME;

    @Field
    private long TOTAL_COST;



    public HardwareInfo(String HARDWARE_ID, String INITIALIZE_DATE_TIME, String TERMINATE_DATE_TIME, long WORK_DONE,
    long POWER_CONSUMPTION, String STATUS, long MACHINE_OWNER, String WALLET_ID_INVESTOR,
                        long PAYMENT, String WALLET_ID_USER, long USAGE_TIME, long TOTAL_COST, String WALLET_ID_SOLUTION_PROVIDER) {
        this.HARDWARE_ID = HARDWARE_ID;
        this.INITIALIZE_DATE_TIME = INITIALIZE_DATE_TIME;
        this.TERMINATE_DATE_TIME = TERMINATE_DATE_TIME;
        this.WORK_DONE = WORK_DONE;
        this.POWER_CONSUMPTION = POWER_CONSUMPTION;
        this.STATUS = STATUS;
        this.MACHINE_OWNER = MACHINE_OWNER;
        this.WALLET_ID_INVESTOR = WALLET_ID_INVESTOR;
        this.PAYMENT = PAYMENT;
        this.WALLET_ID_USER = WALLET_ID_USER;
        this.USAGE_TIME = USAGE_TIME;
        this.TOTAL_COST = TOTAL_COST;
        this.WALLET_ID_SOLUTION_PROVIDER = WALLET_ID_SOLUTION_PROVIDER;
    }

    public String getHARDWARE_ID() {
        return HARDWARE_ID;
    }

    public void setHARDWARE_ID(String HARDWARE_ID) {
        this.HARDWARE_ID = HARDWARE_ID;
    }

    public String getINITIALIZE_DATE_TIME() {
        return INITIALIZE_DATE_TIME;
    }

    public void setINITIALIZE_DATE_TIME(String INITIALIZE_DATE_TIME) {
        this.INITIALIZE_DATE_TIME = INITIALIZE_DATE_TIME;
    }

    public String getTERMINATE_DATE_TIME() {
        return TERMINATE_DATE_TIME;
    }

    public void setTERMINATE_DATE_TIME(String TERMINATE_DATE_TIME) {
        this.TERMINATE_DATE_TIME = TERMINATE_DATE_TIME;
    }

    public long getWORK_DONE() {
        return WORK_DONE;
    }

    public void setWORK_DONE(long WORK_DONE) {
        this.WORK_DONE = WORK_DONE;
    }

    public long getPOWER_CONSUMPTION() {
        return POWER_CONSUMPTION;
    }

    public void setPOWER_CONSUMPTION(long POWER_CONSUMPTION) {
        this.POWER_CONSUMPTION = POWER_CONSUMPTION;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public long getMACHINE_OWNER() {
        return MACHINE_OWNER;
    }

    public void setMACHINE_OWNER(long MACHINE_OWNER) {
        this.MACHINE_OWNER = MACHINE_OWNER;
    }

    public String getWALLET_ID_INVESTOR() {
        return WALLET_ID_INVESTOR;
    }

    public void setWALLET_ID_INVESTOR(String WALLET_ID_INVESTOR) {
        this.WALLET_ID_INVESTOR = WALLET_ID_INVESTOR;
    }

    public long getPAYMENT() {
        return PAYMENT;
    }

    public void setPAYMENT(long PAYMENT) {
        this.PAYMENT = PAYMENT;
    }

    public String getWALLET_ID_USER() {
        return WALLET_ID_USER;
    }

    public void setWALLET_ID_USER(String WALLET_ID_USER) {
        this.WALLET_ID_USER = WALLET_ID_USER;
    }

    public long getUSAGE_TIME() {
        return USAGE_TIME;
    }

    public void setUSAGE_TIME(long USAGE_TIME) {
        this.USAGE_TIME = USAGE_TIME;
    }

    public long getTOTAL_COST() {
        return TOTAL_COST;
    }

    public void setTOTAL_COST(long TOTAL_COST) {
        this.TOTAL_COST = TOTAL_COST;
    }

    public String getWALLET_ID_SOLUTION_PROVIDER() {
        return WALLET_ID_SOLUTION_PROVIDER;
    }

    public void setWALLET_ID_SOLUTION_PROVIDER(String WALLET_ID_SOLUTION_PROVIDER) {
        this.WALLET_ID_SOLUTION_PROVIDER = WALLET_ID_SOLUTION_PROVIDER;
    }
}
