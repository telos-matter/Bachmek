-- DB script to create / reset the database schema

-- Drop all tables
drop table if exists semesterBound; 
drop table if exists academicStageBound;
drop table if exists moduleCoefBound; 
drop table if exists majorRep;
drop table if exists elementCoef; 
drop table if exists moduleRep; 
drop table if exists assessmentGrade; 
drop table if exists assessmentCoef; 
drop table if exists elementInstructor; 
drop table if exists administrator;
drop table if exists instructor;
drop table if exists student;
drop table if exists user_permission;
drop table if exists permission;
drop table if exists user;
drop table if exists pedagogicalRegistration_element;
drop table if exists pedagogicalRegistration_semester;
drop table if exists pedagogicalRegistration;
drop table if exists semester_module;
drop table if exists semester;
drop table if exists assessment;
drop table if exists element;
drop table if exists module;
drop table if exists administrativeRegistration ;
drop table if exists academicYear;
drop table if exists onlineRegistration;
drop table if exists onlineRegistrations;
drop table if exists academicSemester;
drop table if exists academicStage;
drop table if exists majorEnum;
drop table if exists bacHonourEnum;
drop table if exists bacSerieEnum;
drop table if exists genderEnum;

-- Create all tables
create table if not exists genderEnum (
      id int not null primary key auto_increment,
      gender enum('MALE', 'FEMALE') not null unique -- https://www.youtube.com/watch?v=QJJYpsA5tv8
);
insert into genderEnum (gender) values (1), (2);

create table if not exists bacSerieEnum (
      id int not null primary key auto_increment,
      bac_serie enum('SCIENTIFIC', 'LITERATURE', 'ECONOMY', 'TECHNOLOGY') not null unique
);
insert into bacSerieEnum (bac_serie) values (1), (2), (3), (4);

create table if not exists bacHonourEnum (
      id int not null primary key auto_increment,
      bac_honour enum('ACCEPTABLE', 'FAIR', 'GOOD', 'EXCELLENT') not null unique
);
insert into bacHonourEnum (bac_honour) values (1), (2), (3), (4);

create table if not exists majorEnum (
      id int not null primary key auto_increment,
      major enum('BCA', 'BGB', 'BGI', 'BGE') not null unique,
      isActive boolean not null default (true)
);
insert into majorEnum (major, isActive) values (1,1), (2,1), (3,1), (4,0);

create table if not exists academicStage (
      id int not null primary key auto_increment,
      sequence int not null,
      isDiploma boolean not null default (false),
      isActive boolean not null default (true),
      major_id int not null,

      constraint FK_academicStage_major foreign key (major_id) references majorEnum(id),

      constraint CK_academicStage_order check (sequence > 0),

      constraint UQ_academicStage_order_major unique (sequence, major_id)
);

create table if not exists academicSemester (
      id int not null primary key auto_increment,
      sequence int not null,
      isActive boolean not null default (true),
      academicStage_id int not null,

      constraint FK_academicSemester_academicStage foreign key (academicStage_id) references academicStage(id),

      constraint CK_academicSemester_order check (sequence > 0),

      constraint UQ_academicSemester_order_academicStage unique (sequence, academicStage_id)
);

create table if not exists onlineRegistrations ( -- This table is for the online registrations there can be many of the same student, while the next is the unique one to be used by admins. 
      id int not null primary key auto_increment,
      massar_code char(10) not null, -- Char followed by 9 numbers
      ar_first_name nvarchar(64) not null,
      ar_last_name nvarchar(64) not null,
      first_name varchar(64) not null,
      last_name varchar(64) not null,
      cin varchar(16) not null, -- No defined length
      nationality varchar(32) not null,
      gender_id int not null,
      birthdate date not null,
      ar_birth_place nvarchar(128) not null,
      birth_place varchar(128) not null,
      residence_town varchar(64) not null,
      province varchar(64) not null,
      bac_year int not null,
      high_school varchar(64) not null,
      bac_place varchar(64) not null,
      academy varchar(64) not null,
      bac_serie_id int not null,
      bac_honour_id int not null,
      major_id int not null,
      establishment varchar(64) not null,
      registration_date date not null default (current_date),

      constraint FK_onlineRegistrations_gender foreign key (gender_id) references genderEnum(id),
      constraint FK_onlineRegistrations_bacSerie foreign key (bac_serie_id) references bacSerieEnum(id),
      constraint FK_onlineRegistrations_bacHonour foreign key (bac_honour_id) references bacHonourEnum(id),
      constraint FK_onlineRegistrations_major foreign key (major_id) references majorEnum(id),

      constraint CK_onlineRegistrations_massarCode check (massar_code regexp '^([A-Z][0-9]{9})$'), -- IDK why, but it accepts lowercase letters.
      constraint CK_onlineRegistrations_bacYear check (bac_year between 1900 and 9999)
);

create table if not exists onlineRegistration (
      id int not null primary key auto_increment,
      massar_code char(10) unique not null,
      ar_first_name nvarchar(64),
      ar_last_name nvarchar(64),
      first_name varchar(64) not null,
      last_name varchar(64) not null,
      cin varchar(16) unique,
      nationality varchar(32),
      gender_id int not null,
      birthdate date,
      ar_birth_place nvarchar(128),
      birth_place varchar(128),
      residence_town varchar(64),
      province varchar(64),
      bac_year int not null,
      high_school varchar(64),
      bac_place varchar(64),
      academy varchar(64),
      bac_serie_id int not null,
      bac_honour_id int not null,
      major_id int not null,
      establishment varchar(64) not null,
      registration_date date not null default (current_date),

      constraint FK_onlineRegistration_gender foreign key (gender_id) references genderEnum(id),
      constraint FK_onlineRegistration_bacSerie foreign key (bac_serie_id) references bacSerieEnum(id),
      constraint FK_onlineRegistration_bacHonour foreign key (bac_honour_id) references bacHonourEnum(id),
      constraint FK_onlineRegistration_major foreign key (major_id) references majorEnum(id)
);

create table if not exists academicYear ( -- Triggers were such a hassle, otherwise it would've been nice to add a trigger to check that no 2 years intersect, never the less it is done by the code
      id int not null primary key auto_increment,
      start_date date not null unique,
      finish_date date not null unique,

      constraint CK_academicYear check (start_date < finish_date)
);

create table if not exists administrativeRegistration (
      id int not null primary key auto_increment,
      cne char(9) unique not null, -- 9 numbers
      personal_address varchar(128),
      phone_number varchar(12), -- xxx x xx xx xx xx
      parents_address varchar(128),
      diplomas varchar(10000) not null default(''), -- Seperated by ,
      isTuition boolean,
      isActive boolean not null default (true),
      academicYear_id int not null,
      academicStage_id int not null, 
      onlineRegistration_id int not null unique,
      registration_date date not null default (current_date), 

      constraint FK_administrativeRegistrationr_academicYear foreign key (academicYear_id) references academicYear(id),
      constraint FK_administrativeRegistrationr_academicStage foreign key (academicStage_id) references academicStage(id),
      constraint FK_administrativeRegistration_onlineRegistration foreign key (onlineRegistration_id) references onlineRegistration(id)
);

create table if not exists module (
      id int not null primary key auto_increment,
      name varchar(32) not null unique,
      isActive boolean not null default (true)
);

create table if not exists element (
      id int not null primary key auto_increment,
      name varchar(32) not null,
      isActive boolean not null default (true),
      module_id int not null,

      constraint FK_element_module foreign key (module_id) references module(id),
      constraint UQ_element_name_module unique (name, module_id)
);

create table if not exists assessment (
      id int not null primary key auto_increment,
      name varchar(32) not null,
      isActive boolean not null default (true),
      element_id int not null,

      constraint FK_assessment_element foreign key (element_id) references element(id),
      constraint UQ_assessment_name_element unique (name, element_id)
);

create table if not exists semester (
      id int not null primary key auto_increment, 
      academicSemester_id int not null,
      academicYear_id int not null,

      constraint FK_semester_academicSemester foreign key (academicSemester_id) references academicSemester(id),
      constraint FK_semester_academicYear foreign key (academicYear_id) references academicYear(id),

      constraint UQ_semester_academicSemester_academicYear unique (academicSemester_id, academicYear_id)
);

create table if not exists semester_module (
      id int not null primary key auto_increment,
      semester_id int not null,
      module_id int not null,
      isApproved boolean not null default (false),

      constraint FK_semester_module_semester foreign key (semester_id) references semester(id),
      constraint FK_semester_module_module foreign key (module_id) references module(id),

      constraint UQ_semesterModule unique (semester_id, module_id)
);

create table if not exists pedagogicalRegistration (
      id int not null primary key auto_increment,
      administrativeRegistration_id int not null,
      registration_date date not null default (current_date),

      constraint FK_pedagogicalRegistration_administrativeRegistration foreign key (administrativeRegistration_id) references administrativeRegistration(id)
);

create table if not exists pedagogicalRegistration_semester (
      id int not null primary key auto_increment,
      pedagogicalRegistration_id int not null,
      semester_id int not null,

      constraint FK_pedagogicalRegistration_semester_pedagogicalRegistration foreign key (pedagogicalRegistration_id) references pedagogicalRegistration(id),
      constraint FK_pedagogicalRegistration_semester_semester foreign key (semester_id) references semester(id),

      constraint UQ_pedagogicalRegistration_semester unique (pedagogicalRegistration_id, semester_id)
);

create table if not exists pedagogicalRegistration_element (
      id int not null primary key auto_increment,
      pedagogicalRegistration_id int not null,
      element_id int not null,

      constraint FK_pedagogicalRegistration_element_pedagogicalRegistration foreign key (pedagogicalRegistration_id) references pedagogicalRegistration(id),
      constraint FK_pedagogicalRegistration_element_element foreign key (element_id) references element(id),

      constraint UQ_pedagogicalRegistration_element unique (pedagogicalRegistration_id, element_id) -- Not only that, but in the implementation, you should check wether the selected element is part of the semesters this pedagoReg is subscribed to, same for some others
);

create table if not exists user ( -- No Student, Instructor or Administrator should refere to the same User, again triggers would've been nice
      id int not null primary key auto_increment,
      username varchar(32) not null unique,
      password varchar(256) not null,
      email varchar(64),
      first_name varchar(64) not null,
      last_name varchar(64) not null,
      gender_id int not null,
      birthdate date,
      phone_number varchar(12), 
      personal_address varchar(128),
      cin varchar(16), -- Not unique in case of the same person having two different role accounts
      isActive boolean not null default (true),
      creation_date date not null default (current_date),

      constraint FK_user_gender foreign key (gender_id) references genderEnum(id)
);

create table if not exists permission (
      id int not null primary key auto_increment,
      value varchar(128) not null unique
);

create table if not exists user_permission (
      id int not null primary key auto_increment,
      user_id int not null,
      permission_id int not null,

      constraint FK_user_permission_user foreign key (user_id) references user(id),
      constraint FK_user_permission_permission foreign key (permission_id) references permission(id),

      constraint UQ_user_permission unique (user_id, permission_id)
);

create table if not exists student (
      id int not null primary key auto_increment, 
      user_id int not null unique,
      administrativeRegistration_id int not null,

      constraint FK_student_user foreign key (user_id) references user(id),
      constraint FK_student_administrativeRegistration foreign key (administrativeRegistration_id) references administrativeRegistration(id)
);

create table if not exists instructor (
      id int not null primary key auto_increment, 
      user_id int not null unique,
      discipline varchar(64),

      constraint FK_instructor_user foreign key (user_id) references user(id)
);

create table if not exists administrator (
      id int not null primary key auto_increment, 
      user_id int not null unique,

      constraint FK_administrator_user foreign key (user_id) references user(id)
);

create table if not exists elementInstructor (
      id int not null primary key auto_increment,
      element_id int not null,
      academicYear_id int not null,
      instructor_id int not null,

      constraint FK_elementInstructor_element foreign key (element_id) references element(id),
      constraint FK_elementInstructor_academicYear foreign key (academicYear_id) references academicYear(id),
      constraint FK_elementInstructor_instructor foreign key (instructor_id) references instructor(id),

      constraint UQ_elementInstructor_element_academicYear unique (element_id, academicYear_id)
);

create table if not exists assessmentCoef (
      id int not null primary key auto_increment,
      coefficient int not null default (1),
      elementInstructor_id int not null,
      assessment_id int not null,

      constraint FK_assessmentCoef_elementInstructor foreign key (elementInstructor_id) references elementInstructor(id),
      constraint FK_assessmentCoef_assessment foreign key (assessment_id) references assessment(id),

      constraint UQ_assessmentCoef_elementInstructor_assessment unique (elementInstructor_id, assessment_id)
);

create table if not exists assessmentGrade (
      id int not null primary key auto_increment,
      normal_session float(5,2) not null,
      catch_up_session float(5,2),
      elementInstructor_id int not null,
      assessment_id int not null,
      pedagogicalRegistration_id int not null,

      constraint FK_assessmentGrade_elementInstructor foreign key (elementInstructor_id) references elementInstructor(id),
      constraint FK_assessmentGrade_assessment foreign key (assessment_id) references assessment(id),
      constraint FK_assessmentGrade_pedagogicalRegistration foreign key (pedagogicalRegistration_id) references pedagogicalRegistration(id),

      constraint UQ_assessmentGrade unique (assessment_id, pedagogicalRegistration_id)
);

create table if not exists moduleRep (
      id int not null primary key auto_increment,
      instructor_id int not null,
      academicYear_id int not null,
      module_id int not null,

      constraint FK_moduleRep_instructor foreign key (instructor_id) references instructor(id),
      constraint FK_moduleRep_academicYear foreign key (academicYear_id) references academicYear(id),
      constraint FK_moduleRep_module foreign key (module_id) references module(id),

      constraint UQ_moduleRep_module_academicYear unique (module_id, academicYear_id)
);

create table if not exists elementCoef (
      id int not null primary key auto_increment,
      coefficient int not null default (1),
      moduleRep_id int not null,
      element_id int not null,

      constraint FK_elementCoef_moduleRep foreign key (moduleRep_id) references moduleRep(id),
      constraint FK_elementCoef_element foreign key (element_id) references element(id),

      constraint UQ_elementCoef unique (moduleRep_id, element_id)
);

create table if not exists majorRep (
      id int not null primary key auto_increment,
      instructor_id int not null,
      academicYear_id int not null,
      major_id int not null,

      constraint FK_majorRep_instructor foreign key (instructor_id) references instructor(id),
      constraint FK_majorRep_academicYear foreign key (academicYear_id) references academicYear(id),
      constraint FK_majorRep_major foreign key (major_id) references majorEnum(id),

      constraint UQ_majorRep_major_academicYear unique (major_id, academicYear_id)
);

create table if not exists moduleCoefBound (
      id int not null primary key auto_increment,
      coefficient int not null default (1),
      passing_bound float(5,2) not null default(10),
      failling_bound float(5,2) not null default(4),
      majorRep_id int not null,
      module_id int not null,

      constraint FK_moduleCoefBound_majorRep foreign key (majorRep_id) references majorRep(id),
      constraint FK_moduleCoefBound_module foreign key (module_id) references module(id),

      constraint UQ_moduleCoefBound_majorRep_module unique (majorRep_id, module_id)
);

create table if not exists academicStageBound (
      id int not null primary key auto_increment,
      passing_bound float(5,2) not null default(10),
      majorRep_id int not null,
      academicStage_id int not null,

      constraint FK_academicStageBound_majorRep foreign key (majorRep_id) references majorRep(id),
      constraint FK_academicStageBound_academicStage foreign key (academicStage_id) references academicStage(id),

      constraint UQ_academicStageBound_majorRep_academicStage unique (majorRep_id, academicStage_id)
);

create table if not exists semesterBound (
      id int not null primary key auto_increment,
      passing_bound float(5,2) not null default(10),
      majorRep_id int not null,
      academicSemester_id int not null,

      constraint FK_semesterBound_majorRep foreign key (majorRep_id) references majorRep(id),
      constraint FK_semesterBound_academicSemester foreign key (academicSemester_id) references academicSemester(id),

      constraint UQ_semesterBound_majorRep_academicSemester unique (majorRep_id, academicSemester_id)
);
