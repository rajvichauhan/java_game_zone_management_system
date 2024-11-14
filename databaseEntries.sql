-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 31, 2024 at 10:40 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gamezone`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `gameTitle` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `time` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`id`, `username`, `gameTitle`, `date`, `time`) VALUES
(1, 'player1', 'genshin', '2024-03-10', '14:00'),
(2, 'player1', 'minecraft', '2024-04-19', '20:00'),
(3, 'player2', 'minecraft', '2024-05-10', '10:00'),
(6, 'player1', 'valorant', '2020-02-02', '10:00'),
(7, 'player1', 'gta5', '2024-05-10', '10:00'),
(8, 'player1', 'gta5', '2024-05-05', '10:00'),
(9, 'player2', 'gta5', '2024-01-01', '10:00'),
(10, 'player4', 'minecraft', '2024-03-01', '12:00'),
(11, 'player3', 'fortnite', '2024-04-10', '13:00'),
(12, 'player5', 'destiny2', '2024-03-05', '10:00');

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

CREATE TABLE `games` (
  `id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  `availability` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `games`
--

INSERT INTO `games` (`id`, `title`, `type`, `availability`) VALUES
(1, 'genshin', 'adventure', 1),
(2, 'valorant', 'tactical shooter', 1),
(3, 'minecraft', 'sandbox', 1),
(4, 'league of legends', 'action', 0),
(5, 'gta5', 'shooting', 1),
(6, 'vice city', 'action', 1),
(7, 'good pizza great pizza', 'storyline', 1),
(8, 'gta4', 'action', 1),
(9, 'fortnite', 'survival', 1),
(10, 'destiny2', 'survival', 1);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `amount` double NOT NULL,
  `paymentMethod` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `username`, `amount`, `paymentMethod`) VALUES
(1, 'player1', 1000, 'cash'),
(2, 'player1', 5000, 'cash'),
(3, 'player2', 4000, 'card'),
(4, 'player3', 1000, 'cash'),
(5, 'player1', 1000, 'cash'),
(6, 'player4', 10000, 'cash'),
(7, 'player2', 1000, 'cash'),
(9, 'player1', 1000, 'cash'),
(10, 'player4', 1000, 'card'),
(11, 'player3', 3000, 'card');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `profile` enum('admin','player') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `profile`) VALUES
(1, 'rajvi', '123', 'admin'),
(2, 'player1', '123', 'player'),
(3, 'admin1', '123', 'admin'),
(4, 'admin2', '123', 'admin'),
(5, 'player2', '123', 'player'),
(6, 'player3', '123', 'player'),
(7, 'player4', '123', 'player'),
(8, 'admin5', '123', 'admin'),
(9, 'player6', '123', 'player'),
(10, 'admin3', '123', 'admin'),
(11, 'admin4', '123', 'admin'),
(12, 'player5', '123', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`),
  ADD KEY `gameTitle` (`gameTitle`);

--
-- Indexes for table `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `title` (`title`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `games`
--
ALTER TABLE `games`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  ADD CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`gameTitle`) REFERENCES `games` (`title`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
