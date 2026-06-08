package com.esukan.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Booking {
    
    public enum BookingStatus {
        CONFIRMED, CANCELLED, COMPLETED, NO_SHOW
    }
    
    public enum PaymentStatus {
        PENDING, PAID, FAILED, REFUNDED
    }
    
    private int bookingId;
    private String bookingReference;
    private int userId;
    private int facilityId;
    private Date bookingDate;
    private Time startTime;
    private Time endTime;
    private double durationHours;
    private BigDecimal totalCost;
    private PaymentStatus paymentStatus;
    private BookingStatus bookingStatus;
    private String specialRequests;
    private String cancellationReason;
    private Timestamp cancelledAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Join fields
    private String facilityName;
    private String userName;
    
    // Constructors
    public Booking() {}
    
    public Booking(int userId, int facilityId, Date bookingDate, 
                   Time startTime, Time endTime, BigDecimal totalCost) {
        this.userId = userId;
        this.facilityId = facilityId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.paymentStatus = PaymentStatus.PENDING;
        this.bookingStatus = BookingStatus.CONFIRMED;
    }
    
    // ==================== GETTERS ====================
    public int getBookingId() { return bookingId; }
    public int getId() { return bookingId; }  // Alias for JSP compatibility
    public String getBookingReference() { return bookingReference; }
    public int getUserId() { return userId; }
    public int getFacilityId() { return facilityId; }
    public Date getBookingDate() { return bookingDate; }
    public Time getStartTime() { return startTime; }
    public Time getEndTime() { return endTime; }
    public double getDurationHours() { return durationHours; }
    public BigDecimal getTotalCost() { return totalCost; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public BookingStatus getBookingStatus() { return bookingStatus; }
    public String getStatus() { return bookingStatus != null ? bookingStatus.toString() : "CONFIRMED"; } // Alias for JSP
    public String getSpecialRequests() { return specialRequests; }
    public String getCancellationReason() { return cancellationReason; }
    public Timestamp getCancelledAt() { return cancelledAt; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public String getFacilityName() { return facilityName; }
    public String getUserName() { return userName; }
    
    // ==================== SETTERS ====================
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setId(int id) { this.bookingId = id; } // Alias for JSP compatibility
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setFacilityId(int facilityId) { this.facilityId = facilityId; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }
    public void setEndTime(Time endTime) { this.endTime = endTime; }
    public void setDurationHours(double durationHours) { this.durationHours = durationHours; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }
    public void setStatus(String status) { 
        if (status != null) {
            try {
                this.bookingStatus = BookingStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                this.bookingStatus = BookingStatus.CONFIRMED;
            }
        }
    }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }
    public void setCancelledAt(Timestamp cancelledAt) { this.cancelledAt = cancelledAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public boolean isValidTimeRange() {
        return startTime != null && endTime != null && startTime.before(endTime);
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingReference='" + bookingReference + '\'' +
                ", bookingDate=" + bookingDate +
                ", bookingStatus=" + bookingStatus +
                '}';
    }
}