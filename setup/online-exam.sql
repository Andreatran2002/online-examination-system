-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: db:3306
-- Generation Time: Apr 28, 2024 at 05:05 PM
-- Server version: 8.3.0
-- PHP Version: 8.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `online-exam`
--

-- --------------------------------------------------------

--
-- Table structure for table `Answer`
--

CREATE TABLE `Answer` (
  `id` int NOT NULL,
  `question_id` int NOT NULL,
  `content` text NOT NULL,
  `correct` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Answer`
--

INSERT INTO `Answer` (`id`, `question_id`, `content`, `correct`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 3, '3', 0, '2024-04-11 15:24:12', '2024-04-22 01:07:58', NULL),
(2, 3, '2', 1, '2024-04-11 15:24:12', '2024-04-22 01:07:58', NULL),
(3, 3, '1', 0, '2024-04-11 15:24:12', '2024-04-22 01:07:58', NULL),
(4, 3, '4', 0, '2024-04-11 15:24:12', '2024-04-22 01:07:58', NULL),
(5, 4, '12', 0, '2024-04-21 14:57:15', NULL, NULL),
(6, 4, '2', 0, '2024-04-21 14:57:15', NULL, NULL),
(7, 4, '4', 1, '2024-04-21 14:57:15', NULL, NULL),
(8, 5, '11', 0, '2024-04-21 15:21:43', '2024-04-22 01:08:17', NULL),
(9, 5, '2', 0, '2024-04-21 15:21:43', NULL, NULL),
(10, 5, '3', 0, '2024-04-21 15:21:43', '2024-04-22 01:08:17', NULL),
(11, 1, 'a', 1, '2024-04-22 00:41:54', '2024-04-22 01:02:07', NULL),
(12, 1, 'b', 0, '2024-04-22 01:02:07', NULL, NULL),
(13, 2, 'test', 1, '2024-04-25 21:24:40', NULL, NULL),
(14, 6, 'hihi ', 1, '2024-04-25 21:35:39', NULL, NULL),
(15, 6, 'haha', 0, '2024-04-25 21:35:39', NULL, NULL),
(16, 6, 'meme', 0, '2024-04-25 21:35:39', NULL, NULL),
(17, 6, 'limi', 0, '2024-04-25 21:35:39', NULL, NULL),
(18, 7, 'a', 0, '2024-04-25 21:36:04', NULL, NULL),
(19, 7, 'd', 1, '2024-04-25 21:36:04', NULL, NULL),
(20, 7, 'ss', 0, '2024-04-25 21:36:04', NULL, NULL),
(21, 7, 'dd', 0, '2024-04-25 21:36:04', NULL, NULL),
(22, 8, 'sdfsd', 1, '2024-04-25 21:36:19', NULL, NULL),
(23, 8, 'd', 0, '2024-04-25 21:36:19', NULL, NULL),
(24, 8, 's', 1, '2024-04-25 21:36:19', NULL, NULL),
(25, 9, 'bánh', 1, '2024-04-28 23:58:44', NULL, NULL),
(26, 9, 'đá', 0, '2024-04-28 23:58:44', NULL, NULL),
(27, 9, 'cơm', 1, '2024-04-28 23:58:44', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Course`
--

CREATE TABLE `Course` (
  `id` int NOT NULL,
  `teacher_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Course`
--

INSERT INTO `Course` (`id`, `teacher_id`, `name`, `description`, `start`, `end`, `category`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'hihi12', 'hihi1', '2024-04-06 12:12:00', '2024-05-11 02:12:00', 'Artificial Intelligence', '2024-04-06 23:15:03', '2024-04-07 14:54:32', NULL),
(2, 1, 'Python 3', 'Python 2', '2024-04-05 12:00:00', '2024-05-05 01:00:00', 'Machine Learning', '2024-04-06 23:20:35', '2024-04-07 14:46:12', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `CourseRegistration`
--

CREATE TABLE `CourseRegistration` (
  `id` int NOT NULL,
  `course_id` int NOT NULL,
  `student_id` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `CourseRegistration`
--

INSERT INTO `CourseRegistration` (`id`, `course_id`, `student_id`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 1, '2024-04-25 08:34:13', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Examination`
--

CREATE TABLE `Examination` (
  `id` int NOT NULL,
  `course_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `times_retry` int NOT NULL,
  `scoring_type` int NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `total_minutes` int NOT NULL DEFAULT '50',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Examination`
--

INSERT INTO `Examination` (`id`, `course_id`, `name`, `start`, `end`, `times_retry`, `scoring_type`, `description`, `total_minutes`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'test 1', '2024-04-17 12:00:00', '2024-04-18 12:00:00', 5, 2, 'test 1 description', 50, '2024-04-24 21:32:37', NULL, NULL),
(2, 1, 'test', '2024-04-25 12:30:00', '2024-04-28 12:00:00', 4, 2, 'sfds', 50, '2024-04-25 01:17:33', NULL, NULL),
(3, 1, '123', '2024-04-16 12:00:00', '2024-04-27 12:00:00', 10, 2, '123456', 50, '2024-04-25 01:29:40', NULL, NULL),
(4, 1, '6767', '2024-04-11 12:00:00', '2024-04-25 11:00:00', 2, 1, '1234', 50, '2024-04-25 01:31:43', NULL, NULL),
(5, 2, 'Kiem tra lan 1', '2024-04-25 12:00:00', '2024-05-02 12:00:00', 5, 2, 'Kiem tra lan 1 descripton', 50, '2024-04-25 15:37:35', NULL, NULL),
(6, 1, 'Kiem tra quan trong ', '2024-04-25 12:00:00', '2024-05-09 12:00:00', 3, 1, 'Kiem tra quan trong ', 10, '2024-04-25 21:37:53', NULL, NULL),
(7, 1, 'Kiem tra quan trong ', '2024-04-25 12:00:00', '2024-05-09 12:00:00', 3, 1, 'Kiem tra quan trong ', 10, '2024-04-25 21:38:03', NULL, NULL),
(8, 1, 'Kiem tra 2', '2024-04-28 12:00:00', '2024-05-01 12:00:00', 3, 1, 'Kiem tra 2', 5, '2024-04-28 23:32:07', NULL, NULL),
(9, 1, 'Kiem tra 2', '2024-04-28 12:00:00', '2024-05-01 12:00:00', 3, 1, 'Kiem tra 2', 5, '2024-04-28 23:32:33', NULL, NULL),
(10, 1, 'kiem tra 3', '2024-04-28 12:00:00', '2024-05-05 12:00:00', 2, 2, 'kiem tra 3', 2, '2024-04-28 23:59:40', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `ExamQuestion`
--

CREATE TABLE `ExamQuestion` (
  `id` int NOT NULL,
  `exam_id` int NOT NULL,
  `question_id` int NOT NULL,
  `priority` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ExamQuestion`
--

INSERT INTO `ExamQuestion` (`id`, `exam_id`, `question_id`, `priority`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 3, 3, 4, '2024-04-25 01:29:40', NULL, NULL),
(2, 4, 3, 3, '2024-04-25 01:31:43', NULL, NULL),
(3, 4, 4, 8, '2024-04-25 01:31:43', NULL, NULL),
(4, 5, 2, 2, '2024-04-25 15:37:35', NULL, NULL),
(5, 7, 1, 2, '2024-04-25 21:38:03', NULL, NULL),
(6, 7, 3, 4, '2024-04-25 21:38:03', NULL, NULL),
(7, 7, 4, 3, '2024-04-25 21:38:03', NULL, NULL),
(8, 7, 6, 4, '2024-04-25 21:38:03', NULL, NULL),
(9, 7, 7, 2, '2024-04-25 21:38:03', NULL, NULL),
(10, 7, 8, 2, '2024-04-25 21:38:03', NULL, NULL),
(11, 9, 3, 3, '2024-04-28 23:32:33', NULL, NULL),
(12, 9, 4, 3, '2024-04-28 23:32:33', NULL, NULL),
(13, 10, 9, 2, '2024-04-28 23:59:40', NULL, NULL),
(14, 10, 3, 2, '2024-04-28 23:59:40', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Question`
--

CREATE TABLE `Question` (
  `id` int NOT NULL,
  `course_id` int NOT NULL,
  `content` text NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `priority` int NOT NULL DEFAULT '1',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Question`
--

INSERT INTO `Question` (`id`, `course_id`, `content`, `active`, `priority`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'teacher a', 1, 1, '2024-04-11 14:26:21', NULL, NULL),
(2, 2, 'python 1', 1, 1, '2024-04-11 15:00:34', NULL, NULL),
(3, 1, '1+1', 1, 1, '2024-04-11 15:24:12', NULL, NULL),
(4, 1, '2+2', 1, 1, '2024-04-21 14:57:15', NULL, NULL),
(5, 2, '33+2', 1, 1, '2024-04-21 15:21:43', NULL, '2024-04-21 19:34:17'),
(6, 1, 'question test 1', 1, 1, '2024-04-25 21:35:39', NULL, NULL),
(7, 1, 'test question 2', 1, 1, '2024-04-25 21:36:04', NULL, NULL),
(8, 1, 'fasdfasdf', 1, 1, '2024-04-25 21:36:19', NULL, NULL),
(9, 1, 'Tìm đồ ăn được ', 1, 1, '2024-04-28 23:58:44', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Student`
--

CREATE TABLE `Student` (
  `id` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Student`
--

INSERT INTO `Student` (`id`, `created_at`, `updated_at`, `deleted_at`, `user_id`) VALUES
(1, '2024-04-04 05:50:58', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `TakeExam`
--

CREATE TABLE `TakeExam` (
  `id` int NOT NULL,
  `student_id` int NOT NULL,
  `exam_id` int NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime DEFAULT NULL,
  `scoring` double DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `TakeExam`
--

INSERT INTO `TakeExam` (`id`, `student_id`, `exam_id`, `start`, `end`, `scoring`, `created_at`, `updated_at`, `deleted_at`) VALUES
(26, 1, 10, '2024-04-29 00:04:56', '2024-04-29 00:05:02', 5, '2024-04-29 00:04:56', '2024-04-29 00:05:02', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `TakeExamAnswer`
--

CREATE TABLE `TakeExamAnswer` (
  `id` int NOT NULL,
  `take_exam_id` int NOT NULL,
  `answer_id` int NOT NULL,
  `exam_question_id` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `TakeExamAnswer`
--

INSERT INTO `TakeExamAnswer` (`id`, `take_exam_id`, `answer_id`, `exam_question_id`, `created_at`, `updated_at`, `deleted_at`) VALUES
(29, 19, 2, 11, '2024-04-28 23:52:12', NULL, NULL),
(30, 19, 6, 12, '2024-04-28 23:52:12', NULL, NULL),
(31, 19, 7, 12, '2024-04-28 23:52:12', NULL, NULL),
(32, 20, 2, 11, '2024-04-28 23:56:41', NULL, NULL),
(33, 20, 6, 12, '2024-04-28 23:56:42', NULL, NULL),
(34, 20, 7, 12, '2024-04-28 23:56:42', NULL, NULL),
(35, 21, 25, 13, '2024-04-29 00:00:20', NULL, NULL),
(36, 21, 27, 13, '2024-04-29 00:00:20', NULL, NULL),
(37, 21, 2, 14, '2024-04-29 00:00:20', NULL, NULL),
(38, 22, 25, 13, '2024-04-29 00:00:32', NULL, NULL),
(39, 22, 26, 13, '2024-04-29 00:00:32', NULL, NULL),
(40, 22, 2, 14, '2024-04-29 00:00:32', NULL, NULL),
(41, 23, 25, 13, '2024-04-29 00:00:55', NULL, NULL),
(42, 23, 26, 13, '2024-04-29 00:00:55', NULL, NULL),
(43, 23, 27, 13, '2024-04-29 00:00:55', NULL, NULL),
(44, 23, 2, 14, '2024-04-29 00:00:55', NULL, NULL),
(45, 21, 25, 13, '2024-04-29 00:02:14', NULL, NULL),
(46, 21, 27, 13, '2024-04-29 00:02:14', NULL, NULL),
(47, 21, 2, 14, '2024-04-29 00:02:14', NULL, NULL),
(48, 22, 25, 13, '2024-04-29 00:02:24', NULL, NULL),
(49, 22, 26, 13, '2024-04-29 00:02:24', NULL, NULL),
(50, 22, 2, 14, '2024-04-29 00:02:24', NULL, NULL),
(51, 23, 25, 13, '2024-04-29 00:02:50', NULL, NULL),
(52, 23, 26, 13, '2024-04-29 00:02:50', NULL, NULL),
(53, 23, 27, 13, '2024-04-29 00:02:50', NULL, NULL),
(54, 23, 2, 14, '2024-04-29 00:02:50', NULL, NULL),
(55, 24, 25, 13, '2024-04-29 00:03:33', NULL, NULL),
(56, 24, 26, 13, '2024-04-29 00:03:33', NULL, NULL),
(57, 24, 27, 13, '2024-04-29 00:03:33', NULL, NULL),
(58, 24, 2, 14, '2024-04-29 00:03:33', NULL, NULL),
(59, 25, 25, 13, '2024-04-29 00:03:54', NULL, NULL),
(60, 25, 27, 13, '2024-04-29 00:03:54', NULL, NULL),
(61, 25, 2, 14, '2024-04-29 00:03:54', NULL, NULL),
(62, 26, 26, 13, '2024-04-29 00:05:02', NULL, NULL),
(63, 26, 2, 14, '2024-04-29 00:05:02', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Teacher`
--

CREATE TABLE `Teacher` (
  `id` int NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `user_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Teacher`
--

INSERT INTO `Teacher` (`id`, `title`, `address`, `created_at`, `updated_at`, `deleted_at`, `user_id`) VALUES
(1, NULL, NULL, '2024-04-06 20:28:30', NULL, NULL, 2);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `id` int NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id`, `full_name`, `mobile`, `email`, `password_hash`, `last_login`, `is_admin`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'Andrea tran', NULL, 'andreatran2002@gmail.com', '$2a$12$wm/QOTcYZhroiWNl8.XgbuWhOlRlRQ2MFmakyWK2L6Y6d1cT/EYB2', '2024-04-29 00:00:07', 0, '2024-04-04 05:50:58', '2024-04-29 00:00:10', NULL),
(2, 'Teacher A', NULL, 'teachera@gmail.com', '$2a$12$HkabS5OQOL.yIR.sfgbpeuscRL19OgxdbAW4x.ehm8yI/0c8m06.6', '2024-04-28 23:57:58', 0, '2024-04-06 13:21:15', '2024-04-28 23:58:01', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Answer`
--
ALTER TABLE `Answer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `answer_question` (`question_id`);

--
-- Indexes for table `Course`
--
ALTER TABLE `Course`
  ADD PRIMARY KEY (`id`),
  ADD KEY `course_teacher` (`teacher_id`);

--
-- Indexes for table `CourseRegistration`
--
ALTER TABLE `CourseRegistration`
  ADD PRIMARY KEY (`id`),
  ADD KEY `course_registration` (`course_id`),
  ADD KEY `student_registration` (`student_id`);

--
-- Indexes for table `Examination`
--
ALTER TABLE `Examination`
  ADD PRIMARY KEY (`id`),
  ADD KEY `exam_cource` (`course_id`);

--
-- Indexes for table `ExamQuestion`
--
ALTER TABLE `ExamQuestion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `exam_question_exam` (`exam_id`),
  ADD KEY `exam_question_question` (`question_id`);

--
-- Indexes for table `Question`
--
ALTER TABLE `Question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `question_course` (`course_id`);

--
-- Indexes for table `Student`
--
ALTER TABLE `Student`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student_user` (`user_id`);

--
-- Indexes for table `TakeExam`
--
ALTER TABLE `TakeExam`
  ADD PRIMARY KEY (`id`),
  ADD KEY `take_exam_student` (`student_id`),
  ADD KEY `take_exam_exam` (`exam_id`);

--
-- Indexes for table `TakeExamAnswer`
--
ALTER TABLE `TakeExamAnswer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `take_exam_anwer_take_exam` (`take_exam_id`),
  ADD KEY `take_exam_anwer_answer` (`answer_id`),
  ADD KEY `take_exam_answer_exam_question` (`exam_question_id`);

--
-- Indexes for table `Teacher`
--
ALTER TABLE `Teacher`
  ADD PRIMARY KEY (`id`),
  ADD KEY `teacher_user` (`user_id`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Answer`
--
ALTER TABLE `Answer`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `Course`
--
ALTER TABLE `Course`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `CourseRegistration`
--
ALTER TABLE `CourseRegistration`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Examination`
--
ALTER TABLE `Examination`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `ExamQuestion`
--
ALTER TABLE `ExamQuestion`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `Question`
--
ALTER TABLE `Question`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `Student`
--
ALTER TABLE `Student`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `TakeExam`
--
ALTER TABLE `TakeExam`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `TakeExamAnswer`
--
ALTER TABLE `TakeExamAnswer`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT for table `Teacher`
--
ALTER TABLE `Teacher`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Answer`
--
ALTER TABLE `Answer`
  ADD CONSTRAINT `answer_question` FOREIGN KEY (`question_id`) REFERENCES `Question` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `Course`
--
ALTER TABLE `Course`
  ADD CONSTRAINT `course_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `Teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `CourseRegistration`
--
ALTER TABLE `CourseRegistration`
  ADD CONSTRAINT `course_registration` FOREIGN KEY (`course_id`) REFERENCES `Course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `student_registration` FOREIGN KEY (`student_id`) REFERENCES `Student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `Examination`
--
ALTER TABLE `Examination`
  ADD CONSTRAINT `exam_cource` FOREIGN KEY (`course_id`) REFERENCES `Course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `ExamQuestion`
--
ALTER TABLE `ExamQuestion`
  ADD CONSTRAINT `exam_question_exam` FOREIGN KEY (`exam_id`) REFERENCES `Examination` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `exam_question_question` FOREIGN KEY (`question_id`) REFERENCES `Question` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `Question`
--
ALTER TABLE `Question`
  ADD CONSTRAINT `question_course` FOREIGN KEY (`course_id`) REFERENCES `Course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `Student`
--
ALTER TABLE `Student`
  ADD CONSTRAINT `student_user` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `TakeExam`
--
ALTER TABLE `TakeExam`
  ADD CONSTRAINT `take_exam_exam` FOREIGN KEY (`exam_id`) REFERENCES `Examination` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `take_exam_student` FOREIGN KEY (`student_id`) REFERENCES `Student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `TakeExamAnswer`
--
ALTER TABLE `TakeExamAnswer`
  ADD CONSTRAINT `take_exam_answer_exam_question` FOREIGN KEY (`exam_question_id`) REFERENCES `ExamQuestion` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `take_exam_anwer_answer` FOREIGN KEY (`answer_id`) REFERENCES `Answer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `take_exam_anwer_take_exam` FOREIGN KEY (`take_exam_id`) REFERENCES `TakeExam` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `Teacher`
--
ALTER TABLE `Teacher`
  ADD CONSTRAINT `teacher_user` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
