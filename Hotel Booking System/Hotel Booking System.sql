CREATE DATABASE hotel_booking_system;
USE hotel_booking_system;

-- Create RoomTypes Table
CREATE TABLE RoomTypes (
    TypeID INT AUTO_INCREMENT PRIMARY KEY,
    TypeName VARCHAR(50),
    MaxOccupancy INT,
    BedType VARCHAR(50),
    Amenities TEXT
);

-- Create Customers Table
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100),
    Phone VARCHAR(20),
    Address VARCHAR(255)
);


-- Create Rooms Table
CREATE TABLE Rooms (
    RoomID INT AUTO_INCREMENT PRIMARY KEY,
    RoomNumber VARCHAR(20),
    TypeID INT, -- Change to INT to match RoomTypes.TypeID
    Rate DECIMAL(10, 2),
    Description TEXT,
    IsAvailable BOOLEAN,
    FOREIGN KEY (TypeID) REFERENCES RoomTypes(TypeID)
);


-- Create Bookings Table
CREATE TABLE Bookings (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    RoomID INT,
    CheckInDate DATE,
    CheckOutDate DATE,
    TotalAmount DECIMAL(10, 2),
    BookingStatus VARCHAR(50),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);

-- Create Payments Table
CREATE TABLE Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT,
    Amount DECIMAL(10, 2),
    PaymentDate DATE,
    PaymentMethod VARCHAR(50),
    TransactionID VARCHAR(100),
    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID)
);

-- Create RoomBookings Table
CREATE TABLE RoomBookings (
    RoomBookingID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT,
    RoomID INT,
    Quantity INT,
    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);

-- Create RoomRates Table
CREATE TABLE RoomRates (
    RateID INT AUTO_INCREMENT PRIMARY KEY,
    RoomID INT,
    StartDate DATE,
    EndDate DATE,
    Rate DECIMAL(10, 2),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);
