# Online Examination with JavaFX + MySql + PhpMyadmin
`Note : This is purpose-built for educational use, offering a simplified and focused approach to remote exams within school environments. `
## Overview
The Online Examination System project combines JavaFX, MySQL, and PHPMyAdmin to create a user-friendly platform for remote exams.

## Key Features
* User Authentication: Secure login for administrators, teachers, and students.
* Course Management: Creation and management of courses with details like name and dates.
* Exam Creation: Teachers can create exams with time limits and retry options.
* Question Bank: Teachers can add, edit, and organize questions by course.
* Student Enrollment: Students can enroll in courses to access exams.
* Exam Taking: Timed exams with automatic submission and timer alerts.
* Scoring and Feedback: Automatic evaluation of submissions with instant feedback.
* Data Management: PHPMyAdmin for efficient MySQL database management.

## Setup environment
### Step 1 : Setup database
* Method 1 : use docker 
```
cd ./setup
docker compose up -d 
```
* Method 2 : use normal Mysql + phpmyadmin ( search online). Remember to update value in DBConnectionFactory class
```
public class DBConnectionFactory {
    private static final String DATABASE_URL "jdbc:mysql://<host>:<host-port>/online-exam?enabledTLSProtocols=TLSv1.2";
    private static final String DATABASE_USER_NAME = "<db_user>";
    private static final String DATABASE_PASSWORD = "<db_pass>";
 (...)
}
```
### Step 2 : Import database
`Import online-exam.sql at setup folder`

## Some images overview
