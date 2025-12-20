Project Overview

The Online Exam System is a console-based Java application that allows:

Admins to add questions and create exams

Students to take exams, select answers, and receive scores

This system uses MySQL as the database to store users, exams, questions, choices, student answers, and results.

Features

Admin Features

Register and login as admin

Add questions with multiple options (mark correct option)

Create exams and assign questions

Student Features

Register and login as student

View available exams

Take exams and select answers

Automatic scoring and storing results

| Column   | Type                           | Description          |
| -------- | ------------------------------ | -------------------- |
| id       | INT AUTO_INCREMENT PRIMARY KEY | User ID              |
| username | VARCHAR(50) UNIQUE             | User login name      |
| password | VARCHAR(50)                    | User password        |
| role     | VARCHAR(10)                    | `admin` or `student` |

| Column        | Type                           | Description   |
| ------------- | ------------------------------ | ------------- |
| id            | INT AUTO_INCREMENT PRIMARY KEY | Question ID   |
| question_text | VARCHAR(255)                   | Question text |

| Column        | Type                           | Description                           |
| ------------- | ------------------------------ | ------------------------------------- |
| id            | INT AUTO_INCREMENT PRIMARY KEY | Choice ID                             |
| question_id   | INT                            | Foreign key → questions(id)           |
| option_number | INT                            | Option number 1–4 (for display order) |
| option_text   | VARCHAR(255)                   | Option text                           |
| is_correct    | TINYINT(1)                     | 1 = correct, 0 = wrong                |

| Column    | Type                           | Description |
| --------- | ------------------------------ | ----------- |
| id        | INT AUTO_INCREMENT PRIMARY KEY | Exam ID     |
| exam_name | VARCHAR(50)                    | Exam title  |

| Column      | Type | Description                 |
| ----------- | ---- | --------------------------- |
| exam_id     | INT  | Foreign key → exams(id)     |
| question_id | INT  | Foreign key → questions(id) |

| Column                                               | Type | Description                 |
| ---------------------------------------------------- | ---- | --------------------------- |
| student_id                                           | INT  | Foreign key → users(id)     |
| exam_id                                              | INT  | Foreign key → exams(id)     |
| question_id                                          | INT  | Foreign key → questions(id) |
| choice_id                                            | INT  | Foreign key → choices(id)   |
| **Primary Key** = (student_id, exam_id, question_id) |      |                             |

| Column     | Type | Description             |
| ---------- | ---- | ----------------------- |
| student_id | INT  | Foreign key → users(id) |
| exam_id    | INT  | Foreign key → exams(id) |
| score      | INT  | Exam score              |
