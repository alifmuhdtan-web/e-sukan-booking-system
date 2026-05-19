
USE esukan_db;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    matric_number VARCHAR(20) UNIQUE,
    user_role ENUM('STUDENT', 'MANAGER') NOT NULL DEFAULT 'STUDENT',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_user_role (user_role)
);

CREATE TABLE facilities (
    facility_id INT PRIMARY KEY AUTO_INCREMENT,
    facility_name VARCHAR(100) NOT NULL,
    facility_type ENUM('BADMINTON', 'BASKETBALL', 'VOLLEYBALL', 'TENNIS', 'FUTSAL', 'GYM', 'SWIMMING') NOT NULL,
    description TEXT,
    location VARCHAR(200) NOT NULL,
    capacity INT DEFAULT 10,
    hourly_rate DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(500),
    opening_time TIME DEFAULT '08:00:00',
    closing_time TIME DEFAULT '22:00:00',
    is_available BOOLEAN DEFAULT TRUE,
    maintenance_start_date DATE,
    maintenance_end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_facility_type (facility_type),
    INDEX idx_is_available (is_available)
);

CREATE TABLE equipment (
    equipment_id INT PRIMARY KEY AUTO_INCREMENT,
    equipment_name VARCHAR(100) NOT NULL,
    equipment_type ENUM('RACKET', 'BALL', 'NET', 'GOOGLE', 'FIN', 'LIFE_JACKET', 'WEIGHTS', 'MAT', 'OTHER') NOT NULL,
    facility_id INT,
    description TEXT,
    quantity_total INT NOT NULL,
    quantity_available INT NOT NULL,
    daily_rate DECIMAL(10,2) NOT NULL,
    deposit_amount DECIMAL(10,2) DEFAULT 0.00,
    condition_status ENUM('EXCELLENT', 'GOOD', 'FAIR', 'POOR', 'DAMAGED') DEFAULT 'GOOD',
    last_maintenance_date DATE,
    next_maintenance_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id) ON DELETE SET NULL,
    INDEX idx_equipment_type (equipment_type),
    INDEX idx_condition_status (condition_status)
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_reference VARCHAR(20) UNIQUE NOT NULL,
    user_id INT NOT NULL,
    facility_id INT NOT NULL,
    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    duration_hours DECIMAL(3,1) GENERATED ALWAYS AS 
        (TIMESTAMPDIFF(HOUR, CONCAT(booking_date, ' ', start_time), 
                       CONCAT(booking_date, ' ', end_time))) STORED,
    total_cost DECIMAL(10,2) NOT NULL,
    payment_status ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    booking_status ENUM('CONFIRMED', 'CANCELLED', 'COMPLETED', 'NO_SHOW') DEFAULT 'CONFIRMED',
    special_requests TEXT,
    cancellation_reason TEXT,
    cancelled_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_facility_id (facility_id),
    INDEX idx_booking_date (booking_date),
    INDEX idx_booking_status (booking_status)
);

CREATE TABLE equipment_rental (
    rental_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    equipment_id INT NOT NULL,
    quantity_rented INT NOT NULL,
    rental_date DATETIME NOT NULL,
    expected_return_date DATE NOT NULL,
    actual_return_date DATE,
    total_amount DECIMAL(10,2) NOT NULL,
    deposit_paid DECIMAL(10,2) NOT NULL,
    deposit_refunded BOOLEAN DEFAULT FALSE,
    rental_status ENUM('ACTIVE', 'RETURNED', 'OVERDUE', 'CANCELLED') DEFAULT 'ACTIVE',
    condition_on_rental VARCHAR(50),
    condition_on_return VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_rental_status (rental_status),
    INDEX idx_expected_return_date (expected_return_date)
);

CREATE TABLE booking_equipment (
    booking_id INT NOT NULL,
    equipment_id INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (booking_id, equipment_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id) ON DELETE CASCADE
);

CREATE TABLE usage_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    facility_id INT NOT NULL,
    booking_id INT,
    actual_start_time DATETIME,
    actual_end_time DATETIME,
    participant_count INT DEFAULT 0,
    notes TEXT,
    logged_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id) ON DELETE CASCADE,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE SET NULL,
    FOREIGN KEY (logged_by) REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_facility_id (facility_id),
    INDEX idx_created_at (created_at)
);

DELIMITER $$

CREATE TRIGGER before_insert_bookings
BEFORE INSERT ON bookings
FOR EACH ROW
BEGIN
    DECLARE new_ref VARCHAR(20);
    SET new_ref = CONCAT('BKG', DATE_FORMAT(NOW(), '%Y%m%d'), 
                         LPAD(FLOOR(RAND() * 10000), 4, '0'));
    SET NEW.booking_reference = new_ref;
END$$

DELIMITER ;

INSERT INTO users (username, email, password_hash, full_name, phone_number, user_role, is_active) VALUES
('admin', 'admin@esukan.edu.my', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'System Administrator', '0123456789', 'MANAGER', TRUE),
('manager_ahmad', 'ahmad@esukan.edu.my', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Ahmad Bin Abdullah', '0123456788', 'MANAGER', TRUE),
('manager_siti', 'siti@esukan.edu.my', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Siti Binti Hassan', '0123456787', 'MANAGER', TRUE),
('student_ali', 'ali@student.edu.my', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Ali Bin Mohamed', '0123456780', 'STUDENT', TRUE),
('student_nurul', 'nurul@student.edu.my', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Nurul Aisyah', '0123456781', 'STUDENT', TRUE),
('student_raj', 'raj@student.edu.my', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Raj Kumar', '0123456782', 'STUDENT', TRUE),
('student_wei', 'wei@student.edu.my', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Tan Wei Ming', '0123456783', 'STUDENT', TRUE),
('student_fatimah', 'fatimah@student.edu.my', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Fatimah Zahra', '0123456784', 'STUDENT', TRUE);

INSERT INTO facilities (facility_name, facility_type, description, location, capacity, hourly_rate) VALUES
('Badminton Court 1', 'BADMINTON', 'Indoor badminton court with professional flooring', 'Sports Complex, Level 1', 4, 15.00),
('Badminton Court 2', 'BADMINTON', 'Indoor badminton court with professional flooring', 'Sports Complex, Level 1', 4, 15.00),
('Basketball Court', 'BASKETBALL', 'Full-size basketball court', 'Sports Complex, Level 2', 20, 50.00),
('Futsal Field A', 'FUTSAL', 'Artificial turf futsal field', 'Outdoor Field A', 14, 40.00),
('Swimming Pool', 'SWIMMING', 'Olympic-sized swimming pool', 'Aquatic Centre', 50, 8.00),
('Tennis Court 1', 'TENNIS', 'Outdoor tennis court', 'Outdoor Court 1', 4, 25.00),
('Volleyball Court', 'VOLLEYBALL', 'Indoor volleyball court', 'Sports Complex, Level 2', 12, 30.00),
('Gym Center', 'GYM', 'Fully equipped gym', 'Fitness Centre', 30, 10.00);

INSERT INTO equipment (equipment_name, equipment_type, quantity_total, quantity_available, daily_rate, deposit_amount, condition_status) VALUES
('Badminton Racket - Yonex', 'RACKET', 20, 18, 5.00, 20.00, 'GOOD'),
('Shuttlecock - Aerosensa', 'BALL', 40, 35, 5.00, 10.00, 'EXCELLENT'),
('Basketball - Spalding', 'BALL', 20, 15, 3.00, 20.00, 'GOOD'),
('Futsal Ball - Size 5', 'BALL', 15, 12, 3.00, 15.00, 'GOOD'),
('Swimming Goggles', 'GOOGLE', 50, 45, 2.00, 10.00, 'EXCELLENT'),
('Life Jacket - Adult', 'LIFE_JACKET', 15, 15, 5.00, 25.00, 'EXCELLENT'),
('Tennis Racket - Wilson', 'RACKET', 12, 12, 5.00, 25.00, 'GOOD'),
('Volleyball - Mikasa', 'BALL', 12, 10, 4.00, 20.00, 'GOOD'),
('Yoga Mat', 'MAT', 25, 23, 3.00, 15.00, 'GOOD');


INSERT INTO bookings (user_id, facility_id, booking_date, start_time, end_time, total_cost, payment_status, booking_status) VALUES
(4, 1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:00:00', '12:00:00', 30.00, 'PAID', 'CONFIRMED'),
(4, 3, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '14:00:00', '16:00:00', 100.00, 'PAID', 'CONFIRMED'),
(5, 4, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '20:00:00', '22:00:00', 80.00, 'PENDING', 'CONFIRMED'),
(5, 5, DATE_ADD(CURDATE(), INTERVAL 3 DAY), '09:00:00', '10:00:00', 8.00, 'PAID', 'CONFIRMED'),
(6, 6, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '16:00:00', '18:00:00', 50.00, 'PAID', 'CONFIRMED'),
(6, 2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '19:00:00', '21:00:00', 30.00, 'PAID', 'CONFIRMED'),
(7, 7, DATE_ADD(CURDATE(), INTERVAL 4 DAY), '20:00:00', '22:00:00', 60.00, 'PENDING', 'CONFIRMED');


INSERT INTO equipment_rental (user_id, equipment_id, quantity_rented, rental_date, expected_return_date, total_amount, deposit_paid, rental_status, condition_on_rental) VALUES
(4, 1, 2, NOW(), DATE_ADD(CURDATE(), INTERVAL 3 DAY), 10.00, 40.00, 'ACTIVE', 'GOOD'),
(5, 3, 1, NOW(), DATE_ADD(CURDATE(), INTERVAL 2 DAY), 3.00, 20.00, 'ACTIVE', 'GOOD'),
(6, 5, 1, NOW(), DATE_ADD(CURDATE(), INTERVAL 5 DAY), 2.00, 10.00, 'ACTIVE', 'EXCELLENT');


SELECT '=========================================' AS '';
SELECT 'DATABASE SETUP COMPLETE!' AS 'STATUS';
SELECT '=========================================' AS '';

SELECT 
    (SELECT COUNT(*) FROM users) AS 'Total Users',
    (SELECT COUNT(*) FROM facilities) AS 'Total Facilities',
    (SELECT COUNT(*) FROM equipment) AS 'Total Equipment',
    (SELECT COUNT(*) FROM bookings) AS 'Total Bookings',
    (SELECT COUNT(*) FROM equipment_rental) AS 'Total Rentals';

SELECT * FROM users;
SELECT * FROM facilities;
SELECT * FROM equipment;