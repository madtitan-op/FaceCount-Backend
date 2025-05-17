-- Inserting dummy data into the students table
INSERT INTO students (student_id, email, name, password, department, yop) VALUES
(15800121016, 'animesh@example.com', 'Animesh Mahata', '$2a$12$XDvKtWqNinuSF2tWEufqvOanxfNwVgSaBhLKbedpfn7K6VO8TU8Aa', 'CSE', 2025),
--pass: 121212
(15800121005, 'surjya@example.com', 'Surjya Chakrabortty', '$2a$12$Le/Bi9KkKwyA3O4vqukGWOdSiQk41hSeuNSghOGuō0W4lyhnFtiLw2', 'CSE', 2025),
(15800121007, 'babai@example.com', 'Babai Das', '$2a$12$Le/Bi9KkKwyA3O4vqukGWOdSiQk41hSeuNSghOGu0W4lyhnFtiLw2', 'CSE', 2025),
(15800121013, 'rohan@example.com', 'Rohan Bhakat', '$2a$12$Le/Bi9KkKwyA3O4vqukGWOdSiQk41hSeuNSghOGu0W4lyhnFtiLw2', 'CSE', 2025),
(15800122073, 'priyanshu@example.com', 'Priyanshu Kumar', '$2a$12$Le/Bi9KkKwyA3O4vqukGWOdSiQk41hSeuNSghOGu0W4lyhnFtiLw2', 'CSE', 2025);

-- Inserting dummy data into the faculty table
INSERT INTO faculty (faculty_id, email, name, password, role, department) VALUES
--PASS: faculty
(101, 'faculty1@example.com', 'Dr. Rajesh Kumar', '$2a$12$7lKbc0TqSwUtIfwUyRI5UeaGy7jOb3nupAsGqpQqeuw6dk4hfcd42', 'FACULTY', 'ME'),
(102, 'faculty2@example.com', 'Prof. Anjali Gupta', 'faculty_pass2', 'FACULTY', 'CE'),
--pass: admin
(103, 'admin1@example.com', 'Mr. Animesh Mahata', '$2a$12$ePoGXF/8FXzFO1Mxns1aOesPukTYoCXtl3eYLMoM8JHEOQGdkeMPy', 'ADMIN', 'CSE'),
(104, 'faculty3@example.com', 'Ms. Shweta Reddy', 'faculty_pass3', 'FACULTY', 'EE'),
--pass: moderator
(105, 'moderator1@example.com', 'Dr. Karthik Menon', '$2a$12$Jx/FsoLTyMf2wcrtCneemupW7/mImgRzNXnRFvN69Dx.HV/6gysYW', 'MODERATOR', 'ECE');

-- Inserting dummy data into the facecount_systems table
INSERT INTO facecount_systems (department, password) VALUES
--pass: system
('CSE', '$2a$12$EBnLIdwemqSfCPSDkrRuKe2SFa4AzVd1EZDJaWZnCu.jWOP8TatG6'),
('EE', '$2a$12$EBnLIdwemqSfCPSDkrRuKe2SFa4AzVd1EZDJaWZnCu.jWOP8TatG6'),
('ME', '$2a$12$EBnLIdwemqSfCPSDkrRuKe2SFa4AzVd1EZDJaWZnCu.jWOP8TatG6');

-- Attendance data for specific student IDs on 2025-05-12
INSERT INTO attendance_record (userid, status, date, time, marked_by_faculty_id, marked_by_system_id) VALUES
('15800121016', 'PRESENT', '2025-05-12', '09:00:00', 103, NULL),
('15800121005', 'PRESENT', '2025-05-12', '09:05:00', NULL, 1000),
('15800121007', 'ABSENT', '2025-05-12', '09:10:00', 103, NULL),
('15800121013', 'PRESENT', '2025-05-12', '10:15:00', NULL, 1001),
('15800122073', 'PRESENT', '2025-05-12', '10:20:00', 105, NULL);

-- You can also add data for a different date if needed
INSERT INTO attendance_record (userid, status, date, time, marked_by_faculty_id, marked_by_system_id) VALUES
('15800121016', 'PRESENT', '2025-05-11', '08:55:00', 103, NULL),
('15800121005', 'ABSENT', '2025-05-11', '09:02:00', NULL, 1000),
('15800121007', 'PRESENT', '2025-05-11', '09:18:00', 105, NULL),
('15800121013', 'ABSENT', '2025-05-11', '10:05:00', NULL, 1002),
('15800122073', 'PRESENT', '2025-05-11', '10:30:00', 105, NULL);