package com.esukan.model;

import java.sql.Date;
import java.sql.Time;

public class Booking {

    private int id;
    private int userId;
    private int facilityId;
    private String facilityName;
    private Date bookingDate;
    private Time startTime;
    private Time endTime;
    private double totalCost;
    private String status;

    // Constructors
    public Booking() {}

    public Booking(int userId, int facilityId, Date bookingDate, Time startTime, Time endTime, double totalCost) {
        this.userId = userId;
        this.facilityId = facilityId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.status = "CONFIRMED";
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getFacilityId() { return facilityId; }
    public String getFacilityName() { return facilityName; }
    public Date getBookingDate() { return bookingDate; }
    public Time getStartTime() { return startTime; }
    public Time getEndTime() { return endTime; }
    public double getTotalCost() { return totalCost; }
    public String getStatus() { return status; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setFacilityId(int facilityId) { this.facilityId = facilityId; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }
    public void setEndTime(Time endTime) { this.endTime = endTime; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", facilityName='" + facilityName + '\'' +
                ", bookingDate=" + bookingDate +
                ", status='" + status + '\'' +
                '}';
    }
}