CREATE TABLE Person (
    SSN CHAR(20),
    -- changed to CHAR(20) to allow for leading zeros and '-'
    LastName CHAR(20) NOT NULL,
    FirstName CHAR(20) NOT NULL,
    Address CHAR(20),
    ZipCode INTEGER,
    Telephone CHAR(20),
    -- since some of the numbers exceeds INTEGER range
    PRIMARY KEY (SSN),
    FOREIGN KEY (ZipCode) REFERENCES Location(ZipCode)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

CREATE TABLE Location (
    ZipCode INTEGER,
    City CHAR(20) NOT NULL,
    State CHAR(20) NOT NULL,
    PRIMARY KEY (ZipCode)
);

CREATE TABLE Employee (
    Id CHAR(20),
    -- SSN INTEGER,
    StartDate DATE,
    HourlyRate INTEGER,
    -- EmployeeType Added
    EmployeeType CHAR(20),
    PRIMARY KEY (Id),
    FOREIGN KEY (Id) REFERENCES Person(SSN)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

CREATE TABLE Account (
    -- This is Account #, not Customer ID
    Id INTEGER,
    DateOpened DATE,
    Type CHAR(20),
    -- CHANGED TO CHAR(20) SINCE, NO CUSTOM DATATYPE
    -- This is Customer ID, not Account #
    Customer CHAR(20),
    -- CHANGED TO CHAR(20) to MATCH W/ CUSTOMER ID
    PRIMARY KEY (Id),
    FOREIGN KEY (Customer) REFERENCES Customer(Id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

CREATE TABLE Customer (
    Id CHAR(20),
    Email CHAR(32),
    Rating INTEGER,
    CreditCardNumber CHAR(20),
    PRIMARY KEY (Id),
    FOREIGN KEY (Id) REFERENCES Person(SSN)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- Table Login Added
CREATE TABLE Login (
    Id CHAR(20),
    Password CHAR(20),
    Role CHAR(20),
    PRIMARY KEY (Id)
)

-- Name changed to RentalOrder to avoid conflict w/ SQL keyword
CREATE TABLE RentalOrder (
    Id INTEGER,
    DateTime DATETIME,
    ReturnDate DATE,
    PRIMARY KEY (Id)
);

CREATE TABLE Movie (
    Id INTEGER,
    Name CHAR(20) NOT NULL,
    Type CHAR(20) NOT NULL,
    Rating INTEGER,
    DistrFee DECIMAL (10,2),
    -- CHANGED TO DECIMAL SINCE, NO CUSTOM DATATYPE
    NumCopies INTEGER,
    PRIMARY KEY (Id)
);

CREATE TABLE Actor (
    Id INTEGER,
    Name CHAR(20) NOT NULL,
    Age INTEGER NOT NULL,
    Gender CHAR(20) NOT NULL,
    -- CHANED NAME TO Gender since, MySql does not support '/' in the name
    Rating INTEGER,
    PRIMARY KEY (Id)
);

CREATE TABLE Rental (
    AccountId INTEGER,
    CustRepId CHAR(20),
    OrderId INTEGER,
    MovieId INTEGER,
    PRIMARY KEY (AccountId, CustRepId, OrderId, MovieId),
    FOREIGN KEY (AccountId) REFERENCES Account(Id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (CustRepId) REFERENCES Employee(Id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (OrderId) REFERENCES RentalOrder(Id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (MovieId) REFERENCES Movie(Id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

CREATE TABLE MovieQ (
    AccountId INTEGER,
    MovieId INTEGER,
    PRIMARY KEY (AccountId, MovieId),
    FOREIGN KEY (AccountId) REFERENCES Account(Id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (MovieId) REFERENCES Movie(Id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

CREATE TABLE AppearedIn (
    ActorId INTEGER,
    MovieId INTEGER,
    PRIMARY KEY (ActorId, MovieId),
    FOREIGN KEY (ActorId) REFERENCES Actor(Id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (MovieId) REFERENCES Movie(Id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);
