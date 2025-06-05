CREATE TABLE IF NOT EXISTS students (
    student_id BIGINT,
    email VARCHAR(80) NOT NULL,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(25) NOT NULL DEFAULT 'STUDENT',
    department VARCHAR(50) NOT NULL,
    yop smallint NOT NULL,
    PRIMARY KEY (student_id)
);

CREATE TYPE ROLE AS ENUM('ADMIN', 'MODERATOR', 'FACULTY', 'SYSTEM');

CREATE TABLE IF NOT EXISTS faculty(
    faculty_id BIGINT,
    email VARCHAR(80) NOT NULL,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ROLE NOT NULL,
    department VARCHAR(50) NOT NULL,
    PRIMARY KEY (faculty_id)
);

CREATE SEQUENCE system_id_seq START WITH 1000 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS facecount_systems(
    system_id INTEGER DEFAULT nextval('system_id_seq'),
    department VARCHAR(50),
    password VARCHAR(255),
    role ROLE NOT NULL DEFAULT 'SYSTEM',
    PRIMARY KEY (system_id)
);

CREATE TABLE IF NOT EXISTS attendance_record(
    attendance_id SERIAL,
    userid BIGINT NOT NULL REFERENCES students(student_id),
    status VARCHAR(10) NOT NULL DEFAULT 'ABSENT',
    date DATE DEFAULT CURRENT_DATE,
    time TIME(0) DEFAULT CURRENT_TIME::TIME(0),
    marked_by_faculty_id BIGINT REFERENCES faculty(faculty_id),
    marked_by_system_id INTEGER REFERENCES facecount_systems(system_id),
    PRIMARY KEY (attendance_id),
    UNIQUE (userid, date)
);
