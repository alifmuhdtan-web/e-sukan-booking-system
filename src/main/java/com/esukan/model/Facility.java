package com.esukan.model;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

public class Facility {
    
    public enum FacilityType {
        BADMINTON, BASKETBALL, VOLLEYBALL, TENNIS, FUTSAL, GYM, SWIMMING
    }
    
    private int facilityId;
    private String facilityName;
    private FacilityType facilityType;
    private String description;
    private String location;
    private int capacity;
    private BigDecimal hourlyRate;
    private String imageUrl;
    private Time openingTime;
    private Time closingTime;
    private boolean isAvailable;
    private Timestamp maintenanceStartDate;
    private Timestamp maintenanceEndDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    
    public Facility() {}
    
    public Facility(String facilityName, FacilityType facilityType, String location, 
                    int capacity, BigDecimal hourlyRate) {
        this.facilityName = facilityName;
        this.facilityType = facilityType;
        this.location = location;
        this.capacity = capacity;
        this.hourlyRate = hourlyRate;
        this.isAvailable = true;
        this.openingTime = Time.valueOf("08:00:00");
        this.closingTime = Time.valueOf("22:00:00");
    }
    
    
    public int getFacilityId() { return facilityId; }
    public String getFacilityName() { return facilityName; }
    public FacilityType getFacilityType() { return facilityType; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public int getCapacity() { return capacity; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public String getImageUrl() { return imageUrl; }
    public Time getOpeningTime() { return openingTime; }
    public Time getClosingTime() { return closingTime; }
    public boolean isAvailable() { return isAvailable; }
    public Timestamp getMaintenanceStartDate() { return maintenanceStartDate; }
    public Timestamp getMaintenanceEndDate() { return maintenanceEndDate; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    
    
    public void setFacilityId(int facilityId) { this.facilityId = facilityId; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
    public void setFacilityType(FacilityType facilityType) { this.facilityType = facilityType; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setOpeningTime(Time openingTime) { this.openingTime = openingTime; }
    public void setClosingTime(Time closingTime) { this.closingTime = closingTime; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setMaintenanceStartDate(Timestamp maintenanceStartDate) { this.maintenanceStartDate = maintenanceStartDate; }
    public void setMaintenanceEndDate(Timestamp maintenanceEndDate) { this.maintenanceEndDate = maintenanceEndDate; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    
    
    public boolean isOpenAt(Time time) {
        return time != null && openingTime != null && closingTime != null &&
               time.after(openingTime) && time.before(closingTime);
    }
    
    @Override
    public String toString() {
        return "Facility{" +
                "facilityId=" + facilityId +
                ", facilityName='" + facilityName + '\'' +
                ", facilityType=" + facilityType +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}