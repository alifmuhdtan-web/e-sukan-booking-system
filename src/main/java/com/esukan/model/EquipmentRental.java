package com.esukan.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class EquipmentRental {
    
    public enum RentalStatus {
        ACTIVE, RETURNED, OVERDUE, CANCELLED
    }
    
    private int rentalId;
    private int userId;
    private int equipmentId;
    private int quantityRented;
    private Timestamp rentalDate;
    private Date expectedReturnDate;
    private Date actualReturnDate;
    private BigDecimal totalAmount;
    private BigDecimal depositPaid;
    private boolean depositRefunded;
    private RentalStatus rentalStatus;
    private String conditionOnRental;
    private String conditionOnReturn;
    private String notes;
    private Timestamp createdAt;
    
    private String equipmentName;
    private String username;
    private BigDecimal dailyRate;
    
    public EquipmentRental() {}
    
    
    public int getRentalId() { return rentalId; }
    public int getUserId() { return userId; }
    public int getEquipmentId() { return equipmentId; }
    public int getQuantityRented() { return quantityRented; }
    public Timestamp getRentalDate() { return rentalDate; }
    public Date getExpectedReturnDate() { return expectedReturnDate; }
    public Date getActualReturnDate() { return actualReturnDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public BigDecimal getDepositPaid() { return depositPaid; }
    public boolean isDepositRefunded() { return depositRefunded; }
    public RentalStatus getRentalStatus() { return rentalStatus; }
    public String getConditionOnRental() { return conditionOnRental; }
    public String getConditionOnReturn() { return conditionOnReturn; }
    public String getNotes() { return notes; }
    public Timestamp getCreatedAt() { return createdAt; }
    public String getEquipmentName() { return equipmentName; }
    public String getUsername() { return username; }
    public BigDecimal getDailyRate() { return dailyRate; }
    
    
    public void setRentalId(int rentalId) { this.rentalId = rentalId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setEquipmentId(int equipmentId) { this.equipmentId = equipmentId; }
    public void setQuantityRented(int quantityRented) { this.quantityRented = quantityRented; }
    public void setRentalDate(Timestamp rentalDate) { this.rentalDate = rentalDate; }
    public void setExpectedReturnDate(Date expectedReturnDate) { this.expectedReturnDate = expectedReturnDate; }
    public void setActualReturnDate(Date actualReturnDate) { this.actualReturnDate = actualReturnDate; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setDepositPaid(BigDecimal depositPaid) { this.depositPaid = depositPaid; }
    public void setDepositRefunded(boolean depositRefunded) { this.depositRefunded = depositRefunded; }
    public void setRentalStatus(RentalStatus rentalStatus) { this.rentalStatus = rentalStatus; }
    public void setConditionOnRental(String conditionOnRental) { this.conditionOnRental = conditionOnRental; }
    public void setConditionOnReturn(String conditionOnReturn) { this.conditionOnReturn = conditionOnReturn; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public void setUsername(String username) { this.username = username; }
    public void setDailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; }
    
    public boolean isOverdue() {
        return rentalStatus == RentalStatus.ACTIVE && 
               expectedReturnDate != null && 
               expectedReturnDate.before(new Date(System.currentTimeMillis()));
    }
}