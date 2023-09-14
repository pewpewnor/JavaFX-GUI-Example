-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 25, 2022 at 12:04 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `watchesdealer`
--

-- --------------------------------------------------------

--
-- Table structure for table `brand`
--

CREATE TABLE `brand` (
  `BrandID` int(11) NOT NULL,
  `BrandName` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `brand`
--

INSERT INTO `brand` (`BrandID`, `BrandName`) VALUES
(1, 'Rolex'),
(2, 'Omega');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `UserID` int(11) NOT NULL,
  `WatchID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `detailtransaction`
--

CREATE TABLE `detailtransaction` (
  `TransactionID` int(11) NOT NULL,
  `WatchID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `headertransaction`
--

CREATE TABLE `headertransaction` (
  `TransactionID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `UserName` varchar(40) NOT NULL,
  `UserEmail` varchar(40) NOT NULL,
  `UserPassword` varchar(20) NOT NULL,
  `UserGender` varchar(6) NOT NULL,
  `UserRole` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `UserName`, `UserEmail`, `UserPassword`, `UserGender`, `UserRole`) VALUES
(1, 'Audina Chandra', 'a@b.com', 'a12345', 'Female', 'Admin'),
(2, 'Gav Reynald', 'gavrey@gmail.com', 'a12345', 'Male', 'Customer');

-- --------------------------------------------------------

--
-- Table structure for table `watch`
--

CREATE TABLE `watch` (
  `WatchID` int(11) NOT NULL,
  `BrandID` int(11) NOT NULL,
  `WatchName` varchar(40) NOT NULL,
  `WatchPrice` int(11) NOT NULL,
  `WatchStock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `watch`
--

INSERT INTO `watch` (`WatchID`, `BrandID`, `WatchName`, `WatchPrice`, `WatchStock`) VALUES
(1, 1, 'Submariner 300 Watch', 10500, 4),
(2, 2, 'Seamaster Planet Ocean Watch', 6900, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`BrandID`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD KEY `UserID` (`UserID`),
  ADD KEY `WatchID` (`WatchID`);

--
-- Indexes for table `detailtransaction`
--
ALTER TABLE `detailtransaction`
  ADD KEY `TransactionID` (`TransactionID`),
  ADD KEY `WatchID` (`WatchID`);

--
-- Indexes for table `headertransaction`
--
ALTER TABLE `headertransaction`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `watch`
--
ALTER TABLE `watch`
  ADD PRIMARY KEY (`WatchID`),
  ADD KEY `BrandID` (`BrandID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brand`
--
ALTER TABLE `brand`
  MODIFY `BrandID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `headertransaction`
--
ALTER TABLE `headertransaction`
  MODIFY `TransactionID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `watch`
--
ALTER TABLE `watch`
  MODIFY `WatchID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`WatchID`) REFERENCES `watch` (`WatchID`) ON DELETE CASCADE;

--
-- Constraints for table `detailtransaction`
--
ALTER TABLE `detailtransaction`
  ADD CONSTRAINT `detailtransaction_ibfk_1` FOREIGN KEY (`TransactionID`) REFERENCES `headertransaction` (`TransactionID`) ON DELETE CASCADE,
  ADD CONSTRAINT `detailtransaction_ibfk_2` FOREIGN KEY (`WatchID`) REFERENCES `watch` (`WatchID`) ON DELETE CASCADE;

--
-- Constraints for table `headertransaction`
--
ALTER TABLE `headertransaction`
  ADD CONSTRAINT `headertransaction_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE;

--
-- Constraints for table `watch`
--
ALTER TABLE `watch`
  ADD CONSTRAINT `watch_ibfk_1` FOREIGN KEY (`BrandID`) REFERENCES `brand` (`BrandID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
