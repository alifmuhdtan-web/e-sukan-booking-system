package com.esukan.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Equipment {
    
    public enum EquipmentType {
        RACKET, BALL, NET, GOOGLE, FIN, LIFE_JACKET, WEIGHTS, MAT, OTHER
    }
    
    public enum ConditionStatus {
        EXCELLENT, GOOD, FAIR, POOR, DAMAGED
    }
    
    private int equipmentId;
    private String equipmentName;
    private EquipmentType equipmentType;
    private int facilityId;
    private String description;
    private int quantityTotal;
    private int quantityAvailable;
    private BigDecimal dailyRate;
    private BigDecimal depositAmount;
    private ConditionStatus conditionStatus;
    private Timestamp lastMaintenanceDate;
    private Timestamp nextMaintenanceDate;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    private String facilityName;
    
    public Equipment() {}
    
    
    public int getEquipmentId() { return equipmentId; }
    public String getEquipmentName() { return equipmentName; }
    public EquipmentType getEquipmentType() { return equipmentType; }
    public int getFacilityId() { return facilityId; }
    public String getDescription() { return description; }
    public int getQuantityTotal() { return quantityTotal; }
    public int getQuantityAvailable() { return quantityAvailable; }
    public BigDecimal getDailyRate() { return dailyRate; }
    public BigDecimal getDepositAmount() { return depositAmount; }
    public ConditionStatus getConditionStatus() { return conditionStatus; }
    public Timestamp getLastMaintenanceDate() { return lastMaintenanceDate; }
    public Timestamp getNextMaintenanceDate() { return nextMaintenanceDate; }
    public boolean isActive() { return isActive; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public String getFacilityName() { return facilityName; }
    
    
    public void setEquipmentId(int equipmentId) { this.equipmentId = equipmentId; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public void setEquipmentType(EquipmentType equipmentType) { this.equipmentType = equipmentType; }
    public void setFacilityId(int facilityId) { this.facilityId = facilityId; }
    public void setDescription(String description) { this.description = description; }
    public void setQuantityTotal(int quantityTotal) { this.quantityTotal = quantityTotal; }
    public void setQuantityAvailable(int quantityAvailable) { this.quantityAvailable = quantityAvailable; }
    public void setDailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; }
    public void setDepositAmount(BigDecimal depositAmount) { this.depositAmount = depositAmount; }
    public void setConditionStatus(ConditionStatus conditionStatus) { this.conditionStatus = conditionStatus; }
    public void setLastMaintenanceDate(Timestamp lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }
    public void setNextMaintenanceDate(Timestamp nextMaintenanceDate) { this.nextMaintenanceDate = nextMaintenanceDate; }
    public void setActive(boolean active) { isActive = active; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
    
    public boolean isAvailable() {
        return isActive && quantityAvailable > 0;
    }
}