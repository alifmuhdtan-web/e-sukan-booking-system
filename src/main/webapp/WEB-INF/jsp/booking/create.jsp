<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.esukan.model.Facility" %>
<%
    Facility facility = (Facility) request.getAttribute("facility");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - Book Facility</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <div class="nav-brand">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="nav-logo">
                <div class="nav-brand-text">
                    <span class="nav-brand-name">Libang Libu</span>
                    <span class="nav-brand-sub">E-Sukan System</span>
                </div>
            </div>
            <div class="nav-menu">
                <a href="${pageContext.request.contextPath}/student/dashboard" class="nav-link"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
                <a href="${pageContext.request.contextPath}/facilities" class="nav-link"><i class="fas fa-building"></i> Facilities</a>
                <a href="${pageContext.request.contextPath}/equipment" class="nav-link"><i class="fas fa-dumbbell"></i> Equipment</a>
                <div class="user-dropdown">
                    <button class="user-btn">
                        <i class="fas fa-user-circle"></i> <span>User</span> <i class="fas fa-chevron-down"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user"></i> Profile</a>
                        <a href="${pageContext.request.contextPath}/my-rentals"><i class="fas fa-box"></i> My Rentals</a>
                        <hr>
                        <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    
    <main class="container">
        <div class="fade-in-up" style="max-width: 600px; margin: 0 auto;">
            <div class="card">
                <h1><i class="fas fa-calendar-plus"></i> Book Facility</h1>
                
                <div class="info-box" style="background: #F0F4F8; padding: 16px; border-radius: 10px; margin-bottom: 24px; border-left: 4px solid var(--primary);">
                    <p><strong><i class="fas fa-info-circle"></i> Selected Facility:</strong> Badminton Court 1</p>
                    <p><strong><i class="fas fa-tag"></i> Rate:</strong> RM 15.00 per hour</p>
                </div>
                
                <form action="${pageContext.request.contextPath}/CreateBookingServlet" method="post">
                    <input type="hidden" name="facilityId" value="1">
                    
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-calendar-day"></i> Booking Date:</label>
                        <input type="date" name="bookingDate" class="form-input" required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-clock"></i> Start Time:</label>
                        <select name="startTime" class="form-select" required>
                            <option value="">Select start time</option>
                            <option value="08:00">08:00 AM</option>
                            <option value="09:00">09:00 AM</option>
                            <option value="10:00">10:00 AM</option>
                            <option value="11:00">11:00 AM</option>
                            <option value="12:00">12:00 PM</option>
                            <option value="13:00">01:00 PM</option>
                            <option value="14:00">02:00 PM</option>
                            <option value="15:00">03:00 PM</option>
                            <option value="16:00">04:00 PM</option>
                            <option value="17:00">05:00 PM</option>
                            <option value="18:00">06:00 PM</option>
                            <option value="19:00">07:00 PM</option>
                            <option value="20:00">08:00 PM</option>
                            <option value="21:00">09:00 PM</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-clock"></i> End Time:</label>
                        <select name="endTime" class="form-select" required>
                            <option value="">Select end time</option>
                            <option value="09:00">09:00 AM</option>
                            <option value="10:00">10:00 AM</option>
                            <option value="11:00">11:00 AM</option>
                            <option value="12:00">12:00 PM</option>
                            <option value="13:00">01:00 PM</option>
                            <option value="14:00">02:00 PM</option>
                            <option value="15:00">03:00 PM</option>
                            <option value="16:00">04:00 PM</option>
                            <option value="17:00">05:00 PM</option>
                            <option value="18:00">06:00 PM</option>
                            <option value="19:00">07:00 PM</option>
                            <option value="20:00">08:00 PM</option>
                            <option value="21:00">09:00 PM</option>
                            <option value="22:00">10:00 PM</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-money-bill-wave"></i> Total Cost (RM):</label>
                        <input type="number" name="totalCost" class="form-input" step="0.01" placeholder="e.g., 30.00" required>
                        <small class="form-hint">2 hours = RM 30.00</small>
                    </div>
                    
                    <button type="submit" class="btn btn-primary" style="width: 100%;"><i class="fas fa-check-circle"></i> Confirm Booking</button>
                </form>
            </div>
        </div>
        
        <div class="footer">
            <div class="footer-logo">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="footer-logo-img">
                <span>Libang Libu - E-Sukan System</span>
            </div>
            <p>© 2026 Libang Libu - All Rights Reserved.</p>
        </div>
    </main>
    
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>