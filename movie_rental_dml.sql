INSERT INTO Location (ZipCode, City, State)
VALUES
    (11790, 'Stony Brook', 'NY'),
    (11794, 'Stony Brook', 'NY'),
    (93536, 'Los Angeles', 'CA');

INSERT INTO Person (SSN, LastName, FirstName, Address, ZipCode, Telephone)
VALUES
    ('111-11-1111', 'Yang', 'Shang', '123 Success Street', 11790, '516-632-8959'),
    ('222-22-2222', 'Du', 'Victor', '456 Fortune Road', 11790, '516-632-4360'),
    ('333-33-3333', 'Smith', 'John', '789 Peace Blvd.', 93536, '315-443-4321'),
    ('444-44-4444', 'Philip', 'Lewis', '135 Knowledge Lane', 11794, '516-666-8888'),
    ('123-45-6789', 'Smith', 'David', '123 College road', 11790, '516-215-2345'),
    ('789-12-3456', 'Warren', 'David', '456 Sunken Street', 11794, '631-632-9987');

-- EmployeeType Added
INSERT INTO Employee (Id, StartDate, HourlyRate, EmployeeType)
VALUES
    ('123-45-6789', '2005-11-01', 60, 'Clerk'),
    ('789-12-3456', '2006-02-02', 50, 'Manager');

INSERT INTO Customer (Id, Email, Rating, CreditCardNumber)
VALUES
    ('111-11-1111', 'syang@cs.sunysb.edu', 1, '1234-5678-1234-5678'),
    ('222-22-2222', 'vicdu@cs.sunysb.edu', 1, '5678-1234-5678-1234'),
    ('333-33-3333', 'jsmith@ic.sunysb.edu', 1, '2345-6789-2345-6789'),
    ('444-44-4444', 'pml@cs.sunysb.edu', 1, '6789-2345-6789-2345');

INSERT INTO Movie (Id, Name, Type, Rating, DistrFee, NumCopies)
VALUES
    (1, 'The Godfather', 'Drama', 5, 10000.00, 3),
    (2, 'Shawshank Redemption', 'Drama', 4, 1000.00, 2),
    (3, 'Borat', 'Comedy', 3, 500.00, 1);

INSERT INTO Actor (Id, Name, Age, Gender, Rating)
VALUES
    (1, 'Al Pacino', 63, 'M', 5),
    (2, 'Tim Robbins', 53, 'M', 2);

INSERT INTO AppearedIn (ActorId, MovieId)
VALUES
    (1, 1),
    (1, 3),
    (2, 1);

INSERT INTO Account (Id, DateOpened, Type, Customer)
VALUES
    (1, '2006-10-01', 'unlimited-2', '444-44-4444'),
    (2, '2006-10-15', 'limited', '222-22-2222');

INSERT INTO RentalOrder (Id, DateTime, ReturnDate)
VALUES
    (1, '2009-11-11 10:00:00', '2009-11-14'),
    (2, '2009-11-11 18:15:00', NULL),
    (3, '2009-11-12 09:30:00', NULL),
    (4, '2009-11-21 22:22:00', NULL);

INSERT INTO Rental (AccountId, CustRepId, OrderId, MovieId)
VALUES
    (1, '123-45-6789', 1, 1),
    (2, '123-45-6789', 2, 3),
    (1, '789-12-3456', 3, 3),
    (2, '789-12-3456', 4, 2);

INSERT INTO MovieQ (AccountId, MovieId)
VALUES
    (1, 1),
    (1, 3),
    (2, 2),
    (2, 3);


-- To add a new movie
DECLARE @Id INTEGER, @Name CHAR(20), @Type CHAR(20), @Rating INTEGER, @DistrFee DECIMAL(10,2), @NumCopies INTEGER
INSERT INTO Movie (Id, Name, Type, Rating, DistrFee, NumCopies)
VALUES (@Id, @Name, @Type, @Rating, @DistrFee, @NumCopies);

-- To edit movie information
-- Set rating
DECLARE @Name CHAR(20), @Rating INTEGER
UPDATE Movie
SET Rating = @Rating
WHERE Name = @Name;

-- Set distribution fee
DECLARE @Name CHAR(20), @DistrFee DECIMAL(10,2);
UPDATE Movie
SET DistrFee = @DistrFee
WHERE Name = @Name;


-- To delete a movie
DECLARE @Name CHAR(20)
DELETE FROM Movie
WHERE Name = @Name;

-- To add a new employee
DECLARE @SSN CHAR(20), @LastName CHAR(20), @FirstName CHAR(20), @Address CHAR(20), @ZipCode INTEGER, @Telephone CHAR(20), @Id CHAR(20), @StartDate DATE, @HourlyRate DECIMAL(10,2)
INSERT INTO Person (SSN, LastName, FirstName, Address, ZipCode, Telephone)
VALUES (@SSN, @LastName, @FirstName, @Address, @ZipCode, @Telephone);

INSERT INTO Employee (Id, StartDate, HourlyRate)
VALUES (@Id, @StartDate, @HourlyRate);

-- To edit employee information (e.g., hourly rate)
UPDATE Employee
SET HourlyRate = 70
WHERE Id = '789-12-3456';

-- To delete an employee
DECLARE @Id CHAR(20)
DELETE FROM Employee
WHERE Id = @Id;

-- To add a login credential
INSERT INTO Login (Id, Password, Type)
VALUES 
    ('syang@cs.sunysb.edu', '1234', 'Customer Repr'),
    ('vicdu@cs.sunysb.edu', '5678', 'Customer'),
    ('jsmith@ic.sunysb.edu', '2345', 'Customer'),
    ('pml@cs.sunysb.edu', '6789', 'Customer'),
    ('dsmith@gmail.com', '0000', 'Manager'),
    ('dwarren@gmail.com', '1111', 'Manager');


-- Obtain a Sales Report for a particular month
DECLARE @DateOpened DATE
SELECT Type, SUM(CASE Type
                WHEN 'limited' THEN 10
                WHEN 'unlimited-1' THEN 15
                WHEN 'unlimited-2' THEN 20
                WHEN 'unlimited-3' THEN 25
                END) AS Sales
FROM Account
WHERE DATE_FORMAT(DateOpened, '%Y-%m') = @DateOpened
GROUP BY Type
UNION ALL
SELECT 'Total', SUM(CASE Type
                    WHEN 'limited' THEN 10
                    WHEN 'unlimited-1' THEN 15
                    WHEN 'unlimited-2' THEN 20
                    WHEN 'unlimited-3' THEN 25
                    END)
FROM Account
WHERE DATE_FORMAT(DateOpened, '%Y-%m') = @DateOpened;

-- Produce a Comprehensive Listing of All Movies
SELECT * FROM Movie;

-- Prouce a List of Movies by Movie Name, Movie Type, or Customer Name
-- By Movie Name
DECLARE @MovieName CHAR(20)
SELECT R.*, M.Name
FROM Rental R
JOIN Movie M ON R.MovieId = M.Id
WHERE M.Name = @MovieName;

-- By Movie Type
DECLARE @MovieType CHAR(20)
SELECT R.*, M.Type
FROM Rental R
JOIN Movie M ON R.MovieId = M.Id
WHERE M.Type = @MovieType;

-- By Customer Name
DECLARE @CustomerName CHAR(20)
SELECT R.*, P.LastName, P.FirstName
FROM Rental R
JOIN Account A ON R.AccountId = A.Id
JOIN Customer C ON A.Customer = C.Id
JOIN Person P ON C.Id = P.SSN
WHERE P.LastName = @CustomerName;

-- Determine Which Customer Represatative Oversaw the Most Transactions (Rentals)
SELECT CustRepId, COUNT(*) AS TransactionCount
FROM Rental
GROUP BY CustRepId
ORDER BY TransactionCount DESC
LIMIT 1;

-- Produce a List of Most Active Customers
-- Fixed: Most Active Customer subset
CREATE VIEW MostActiveCustomers AS
SELECT AccountId, COUNT(*) AS RentalCount
FROM Rental
GROUP BY AccountId
ORDER BY RentalCount DESC;

-- Produce a List of Most Actively Rented Movies
SELECT MovieId, COUNT(*) AS RentalCount
FROM Rental
GROUP BY MovieId
ORDER BY RentalCount DESC;

-- Record an Order
-- Insert a new row into RentalOrder
DECLARE @Id INTEGER, @DateTime DATETIME, @ReturnDate DATE
INSERT INTO RentalOrder (Id, DateTime, ReturnDate)
VALUES (@Id, @DateTime, @ReturnDate);

-- Insert a new row into Rental
DECLARE @AccountId INTEGER, @CustRepId CHAR(20), @OrderId INTEGER, @MovieId INTEGER
INSERT INTO Rental (AccountId, CustRepId, OrderId, MovieId)
VALUES (@AccountId, @CustRepId, @OrderId, @MovieId);

-- Add, Edit, and Delete Information for a Customer
-- Add a new customer
DECLARE @SSN CHAR(20), @LastName CHAR(20), @FirstName CHAR(20), @Address CHAR(20), @ZipCode INTEGER, @Telephone CHAR(20)
INSERT INTO Person (SSN, LastName, FirstName, Address, ZipCode, Telephone)
VALUES (@SSN, @LastName, @FirstName, @Address, @ZipCode, @Telephone);

DECLARE @Id CHAR(20), @Email CHAR(32), @Rating INTEGER, @CreditCardNumber CHAR(20)
INSERT INTO Customer (Id, Email, Rating, CreditCardNumber)
VALUES (@Id, @Email, @Rating, @CreditCardNumber);

-- Edit customer information
UPDATE Customer
SET Email = 'jun.heo@stonybrook.edu'
WHERE Id = '888-88-8888';

-- Delete a customer
DELETE FROM Customer
WHERE Id = '888-88-8888';

-- Produce Customer Mailing Lists
SELECT P.FirstName, P.LastName, C.Email
FROM Person P, Customer C
WHERE P.SSN = C.Id;

-- Recommendation System
-- Get the preference of a customer
-- Replae 'target_id' with the actual Id of the customer you want to get the preference of
DECLARE @target_id INTEGER
CREATE VIEW PersonalizeMovieSuggestion AS
SELECT 
    R.MovieId,
    M.Name AS MovieName,
    M.Type AS MovieType,
    COUNT(*) AS RentalCount
FROM 
    Rental R
JOIN 
    Movie M ON R.MovieId = M.Id
WHERE 
    R.AccountId = @target_id
GROUP BY 
    R.MovieId, M.Name, M.Type
ORDER BY 
    RentalCount DESC
LIMIT 5;


-- Get the movie suggestion
DECLARE @target_id INTEGER
SELECT R.AccountId, R.MovieId, COUNT(*) AS RecommendedMovie
FROM Rental R
WHERE R.AccountId <> @target_id
AND R.MovieId IN (
    SELECT MovieId From (
        SELECT R.AccountId, R.MovieId, COUNT(*) AS MovieCount
        FROM Rental R
        WHERE R.AccountId = @target_id
        GROUP BY R.AccountId, R.MovieId
        ORDER BY MovieCount DESC
        LIMIT 5
    ) AS CustomerPreferences
)
GROUP BY R.AccountId, R.MovieId
ORDER BY RecommendedMovie DESC
LIMIT 5;

-- A Customer's Currently Held Movies
DECLARE @AccountId INTEGER
SELECT M.Name
FROM Movie M
JOIN Rental R ON R.MovieId = M.Id
JOIN RentalOrder RO ON R.OrderId = RO.Id
WHERE R.AccountId = @AccountId
AND RO.ReturnDate IS NULL;

-- Retrieve a Customer's Queue of Movies
DECLARE @AccountId INTEGER
SELECT ROW_NUMBER() OVER (ORDER BY M.Name) AS '#', M.Name
FROM Movie M
JOIN MovieQ MQ ON MQ.MovieId = M.Id
WHERE MQ.AccountId = @AccountId;

-- Customer Account Setting
-- Change a Customer's Account email
UPDATE Customer
SET Email = 'example@gmail.com'
WHERE Id = '111-11-1111';

-- View Order History
DECLARE @AccountId INTEGER
SELECT RO.Id AS 'Rental ID', M.Name, RO.DateTime, RO.ReturnDate
FROM Movie M
JOIN Rental R ON R.MovieId = M.Id
JOIN RentalOrder RO ON R.OrderId = RO.Id
WHERE R.AccountId = @AccountId;

-- Filter Movies Available of a particular type
DECLARE @MovieType CHAR(20)
SELECT M.Name, M.Type
FROM Movie M
WHERE M.NumCopies <> 0
AND M.Type = @MovieType;

-- Filter movies available with particular keywords
-- Fixed: Check Avialiablity
DECLARE @Keyword CHAR(20)

SELECT Name, NumCopies
FROM Movie
WHERE Name LIKE @Keyword;

-- Movies available with a particular actor or group of actors
-- Fixed: Show NumCopies
DECLARE @ActorName CHAR(20)

SELECT M.Name, M.NumCopies
FROM Movie M
JOIN AppearedIn AI ON AI.MovieId = M.Id
JOIN Actor A ON AI.ActorId = A.Id
WHERE A.Name = @ActorName AND M.NumCopies > 0;

-- Multiple Actors
DECLARE @ActorName1 CHAR(20), @ActorName2 CHAR(20)
SELECT A.Name, M.Name
FROM Movie M
JOIN AppearedIn AI ON AI.MovieId = M.Id
JOIN Actor A ON AI.ActorId = A.Id
WHERE A.Name IN (@ActorName1, @ActorName2) AND M.NumCopies > 0;

-- Best-Seller list of movies VIEW, limit to top 10
-- Fixed: Limit 10, Create View
CREATE VIEW BestSellerList AS
SELECT M.Name, COUNT(*) AS RentalCount
FROM Movie M
JOIN Rental R ON R.MovieId = M.Id
GROUP BY M.Name
ORDER BY RentalCount DESC
LIMIT 10;

-- Personalized Movie Suggestion List
SELECT M.Name, R.MovieId, COUNT(*) AS RecommendLevel
FROM Rental R
JOIN Movie M ON R.MovieId = M.Id
WHERE R.AccountId = 1
GROUP BY R.AccountId, R.MovieId
ORDER BY RecommendLevel DESC
LIMIT 5;


-- Rate a movie
-- Create a new table to store movie ratings
CREATE TABLE MovieRatings (
    MovieId INTEGER,
    Rating INTEGER,
    PRIMARY KEY (MovieId)
);

-- Insert a new Rating
DECLARE @MovieId INTEGER, @Rating INTEGER
INSERT INTO MovieRatings (MovieId, Rating)
VALUES (@MovieId, @Rating);

-- Update the rating of a movie which is avg of old and new ratings
DECLARE @MovieId INTEGER, @Rating INTEGER
UPDATE Movie
SET Rating = (
    SELECT ROUND(AVG(Rating), MR.Rating)
    FROM MovieRatings MR
    WHERE MR.MovieId = Movie.Id
)
WHERE Id = @MovieId;

-- Drop a rating table
DROP TABLE MovieRatings;
