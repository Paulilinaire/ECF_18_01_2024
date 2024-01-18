CREATE DATABASE school;
-- DROP DATABASE schooL;
USE school;

CREATE TABLE department(
   id_dep INT NOT NULL AUTO_INCREMENT,
   name_dep VARCHAR(50),
   PRIMARY KEY(id_dep)
);

CREATE TABLE subject(
   id_sub INT NOT NULL AUTO_INCREMENT,
   title_sub VARCHAR(50),
   duration_sub INT,
   coeff_sub INT,
   desc_sub VARCHAR(300),
   PRIMARY KEY(id_sub)
);

CREATE TABLE classroom(
   id_class INT NOT NULL AUTO_INCREMENT,
   name_class VARCHAR(50),
   level_class VARCHAR(50),
   id_dep INT NOT NULL,
   PRIMARY KEY(id_class),
   FOREIGN KEY(id_dep) REFERENCES department(id_dep)
);

CREATE TABLE schedule(
   id_sch INT NOT NULL AUTO_INCREMENT,
   date_sch DATE,
   hour_sch TIME,
   PRIMARY KEY(id_sch)
);

CREATE TABLE student(
   id_s INT NOT NULL AUTO_INCREMENT,
   lastName_s VARCHAR(50),
   firstName_s VARCHAR(50),
   birth_date_s DATE,
   email_s VARCHAR(100),
   id_class INT NOT NULL,
   PRIMARY KEY(id_s),
   FOREIGN KEY(id_class) REFERENCES classroom(id_class)
);

CREATE TABLE teacher(
   id_t INT NOT NULL AUTO_INCREMENT,
   lastName_t VARCHAR(50),
   firstName_t VARCHAR(50),
   age_t INT,
   level_t VARCHAR(50),
   is_form_teacher BOOLEAN,
   is_head_department BOOLEAN,
   id_dep INT NOT NULL,
   PRIMARY KEY(id_t),
   FOREIGN KEY(id_dep) REFERENCES department(id_dep)
);

CREATE TABLE grade(
   id_grade INT NOT NULL AUTO_INCREMENT,
   value_g DECIMAL(4,2),
   comment_g VARCHAR(200),
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

CREATE TABLE teacher_classroom(
   id_t INT,
   id_class INT,
   PRIMARY KEY(id_t, id_class),
   FOREIGN KEY(id_t) REFERENCES teacher(id_t),
   FOREIGN KEY(id_class) REFERENCES classroom(id_class)
);

CREATE TABLE subject_schedule(
   id_sub INT,
   id_sch INT,
   PRIMARY KEY(id_sub, id_sch),
   FOREIGN KEY(id_sub) REFERENCES subject(id_sub),
   FOREIGN KEY(id_sch) REFERENCES schedule(id_sch)
);