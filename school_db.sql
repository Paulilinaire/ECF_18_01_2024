CREATE DATABASE school_db;

USE school_db;

CREATE TABLE department(
   id_dep INT,
   name_dep VARCHAR(50),
   PRIMARY KEY(id_dep)
);

CREATE TABLE subject(
   id_sub INT,
   title_sub VARCHAR(50),
   duration_sub INT,
   coeff_sub INT,
   desc_sub VARCHAR(300),
   PRIMARY KEY(id_sub)
);

CREATE TABLE class(
   id_class INT,
   name_class VARCHAR(50),
   level_class VARCHAR(50),
   id_dep INT NOT NULL,
   PRIMARY KEY(id_class),
   FOREIGN KEY(id_dep) REFERENCES department(id_dep)
);

CREATE TABLE schedule(
   id_sch INT,
   date_sch DATE,
   hour_sch TIME,
   PRIMARY KEY(id_sch)
);

CREATE TABLE student(
   id_s INT,
   lastName_s VARCHAR(50) NOT NULL CHECK (LENGTH(lastName_s) >= 3),
   firstName_s VARCHAR(50) NOT NULL CHECK (LENGTH(firstName_s) >= 3),
   birth_date_s DATE,
   email_s VARCHAR(100) CHECK (email_s LIKE '%@gmail.com%'),
   id_class INT NOT NULL,
   PRIMARY KEY(id_s),
   FOREIGN KEY(id_class) REFERENCES class(id_class)
);

CREATE TABLE teacher(
   id_t INT,
   lastName_t VARCHAR(50) NOT NULL CHECK (LENGTH(lastName_t) >= 3),
   firstName_t VARCHAR(50) NOT NULL CHECK (LENGTH(firstName_t) >= 3),
   age_t INT CHECK (age_t >= 18),
   level_t VARCHAR(50),
   is_form_teacher BOOLEAN,
   is_head_department_teacher BOOLEAN,
   id_dep INT NOT NULL,
   PRIMARY KEY(id_t),
   FOREIGN KEY(id_dep) REFERENCES department(id_dep)
);

CREATE TABLE grade(
   id_grade INT,
   value_g DECIMAL(4,2) CHECK (value_g >= 0 AND value_g <= 20),
   comment_g VARCHAR(100),
   id_s INT NOT NULL,
   id_sub INT NOT NULL,
   PRIMARY KEY(id_grade),
   FOREIGN KEY(id_s) REFERENCES student(id_s),
   FOREIGN KEY(id_sub) REFERENCES subject(id_sub)
);

CREATE TABLE teacher_subject(
   id_t INT,
   id_sub INT,
   PRIMARY KEY(id_t, id_sub),
   FOREIGN KEY(id_t) REFERENCES teacher(id_t),
   FOREIGN KEY(id_sub) REFERENCES subject(id_sub)
);

CREATE TABLE teacher_class(
   id_t INT,
   id_class INT,
   PRIMARY KEY(id_t, id_class),
   FOREIGN KEY(id_t) REFERENCES teacher(id_t),
   FOREIGN KEY(id_class) REFERENCES class(id_class)
);

CREATE TABLE subject_schedule(
   id_sub INT,
   id_sch INT,
   PRIMARY KEY(id_sub, id_sch),
   FOREIGN KEY(id_sub) REFERENCES subject(id_sub),
   FOREIGN KEY(id_sch) REFERENCES schedule(id_sch)
);

