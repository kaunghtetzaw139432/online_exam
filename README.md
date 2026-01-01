# Online Exam System

## Project Overview

The **Online Exam System** is a console-based Java application that allows:

- **Admins** to add questions and create exams  
- **Students** to take exams, select answers, and receive scores  

It uses **MySQL** as the database to store users, exams, questions, choices, student answers, and results.

---

## Features

### Admin Features
1. Register and login as admin  
2. Add questions with multiple options (mark correct option)  
3. Create exams and assign questions  

### Student Features
1. Register and login as student  
2. View available exams  
3. Take exams and select answers  
4. Automatic scoring and storing results  

---

## Database Structure

### Users Table

| Column   | Type                           | Description          |
| -------- | ------------------------------ | ------------------ |
| id       | INT AUTO_INCREMENT PRIMARY KEY | User ID             |
| username | VARCHAR(50) UNIQUE             | User login name     |
| password | VARCHAR(50)                    | User password       |
| role     | VARCHAR(10)                    | `admin` or `student` |

### Questions Table

| Column        | Type                           | Description      |
| ------------- | ------------------------------ | ---------------- |
| id            | INT AUTO_INCREMENT PRIMARY KEY | Question ID      |
| question_text | VARCHAR(255)                   | Question content |

### Choices Table

| Column        | Type                           | Description                       |
| ------------- | ------------------------------ | --------------------------------- |
| id            | INT AUTO_INCREMENT PRIMARY KEY | Choice ID                         |
| question_id   | INT                            | Foreign key → questions(id)       |
| option_number | INT                            | Option number 1–4 (display order) |
| option_text   | VARCHAR(255)                   | Option content                    |
| is_correct    | TINYINT(1)                     | 1 = correct, 0 = wrong            |

### Exams Table

| Column    | Type                           | Description |
| --------- | ------------------------------ | ----------- |
| id        | INT AUTO_INCREMENT PRIMARY KEY | Exam ID     |
| exam_name | VARCHAR(50)                    | Exam title  |

### Exam_Questions Table

| Column      | Type | Description                 |
| ----------- | ---- | --------------------------- |
| exam_id     | INT  | Foreign key → exams(id)     |
| question_id | INT  | Foreign key → questions(id) |

### Student_Answers Table

| Column                                                | Type | Description                 |
| ----------------------------------------------------- | ---- | --------------------------- |
| student_id                                            | INT  | Foreign key → users(id)     |
| exam_id                                               | INT  | Foreign key → exams(id)     |
| question_id                                           | INT  | Foreign key → questions(id) |
| choice_id                                             | INT  | Foreign key → choices(id)   |
| **Primary Key:** `(student_id, exam_id, question_id)` |      |                             |

### Results Table

| Column     | Type | Description             |
| ---------- | ---- | ----------------------- |
| student_id | INT  | Foreign key → users(id) |
| exam_id    | INT  | Foreign key → exams(id) |
| score      | INT  | Exam score              |

---

OnlineExamSystem/
├─ src/
│  ├─ App.java              # Main application with menu
│  ├─ User.java             # Defines User object
│  ├─ Admin.java            # Admin-specific operations
│  ├─ Student.java          # Student-specific operations
│  ├─ Question.java         # Question model
│  ├─ Choice.java           # Choice model
│  ├─ Exam.java             # Exam model
│  ├─ ExamManager.java      # Manage exams & questions
│  ├─ ExamService.java      # Handles student exams & scoring
│  ├─ DBHandler.java        # Database connection & operations
│  ├─ Helper.java           # Utility methods / menu / formatting
├─ README.md                # Project description
├─ .gitignore               # Ignore compiled files / IDE folders



---

## Usage

1. Create MySQL database and tables according to the structure above.  
2. Configure database connection in `DBHandler.java`.  
3. Compile Java classes:

```bash
cd src
javac *.java


## Project Structure (Suggested)

