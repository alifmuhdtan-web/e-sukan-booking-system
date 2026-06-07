package com.esukan.model;

import java.math.BigDecimal;

public class Equipment {
    private int id;
    private String name;
    private String type;
    private int quantity;
    private int quantityAvailable;
    private BigDecimal dailyRate;
    private BigDecimal deposit;
    private String conditionStatus;
    private boolean active;

    public Equipment() {}

    public Equipment(int id, String name, String type, int quantity, int quantityAvailable,
                     BigDecimal dailyRate, BigDecimal deposit, String conditionStatus, boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.quantityAvailable = quantityAvailable;
        this.dailyRate = dailyRate;
        this.deposit = deposit;
        this.conditionStatus = conditionStatus;
        this.active = active;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getQuantityAvailable() { return quantityAvailable; }
    public void setQuantityAvailable(int quantityAvailable) { this.quantityAvailable = quantityAvailable; }

    public BigDecimal getDailyRate() { return dailyRate; }
    public void setDailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; }

    public BigDecimal getDeposit() { return deposit; }
    public void setDeposit(BigDecimal deposit) { this.deposit = deposit; }

    public String getConditionStatus() { return conditionStatus; }
    public void setConditionStatus(String conditionStatus) { this.conditionStatus = conditionStatus; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getStatus() { return active ? "Available" : "Maintenance"; }
}
