create database archisman;
use archisman;
CREATE TABLE Customers (
    CustomerId BIGINT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255),
    Password VARCHAR(255),
    Email VARCHAR(255),
    ContactNumber VARCHAR(20)
);

CREATE TABLE RoomTypes (
    TypeID INT AUTO_INCREMENT PRIMARY KEY,
    TypeName VARCHAR(255),
    Purpose VARCHAR(255),
    RentPrice INT,
    Amenities TEXT
);

CREATE TABLE Bookings (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID BIGINT,
    RoomTypeID INT,
    CheckInDate DATE,
    CheckOutDate DATE,
    TotalAmount DOUBLE,
    BookingStatus VARCHAR(50),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerId),
    FOREIGN KEY (RoomTypeID) REFERENCES RoomTypes(TypeID)
);

CREATE TABLE Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT,
    Amount DOUBLE,
    PaymentDate DATE,
    PaymentMethod VARCHAR(50),
    TransactionID VARCHAR(100),
    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID)
);
