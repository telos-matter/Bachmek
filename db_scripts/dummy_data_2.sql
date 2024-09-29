insert into user (username, password, first_name, last_name, gender_id) values ('root',"nypord#v%G(GC}EoEU-(-u-]$J1l'0)j",'ROOT','ROOT', 1); -- abc123-.-321
insert into permission (value) values ('root');
insert into user_permission (user_id,permission_id) values (1,1);
insert into administrator (user_id) values (1);

insert into module (name) values ('java 1'), ('analysis 1'), ('analysis 2'), ('algebra 1'), ('algorithm 1'), ('java 2'), ('analysis 3'), ('analysis 4'), ('algebra 2'), ('informatics');
insert into module (name, isActive) values ('electronics', false), ('geography', false), ('biochemistry', true), ('physics', true);
insert into element (name, isActive, module_id) values ('basics', true, 1), ('oop', true, 1), ('collections', true, 6), ('gui', false, 6);
insert into element (name, isActive, module_id) values ('analysis 1', true, 2), ('analysis 2', true, 3), ('algebra 1', true, 4), ('algorithm 1', true, 5), ('analysis 3', true, 7), ('analysis 4', true, 8), ('algebra 2', true, 9), ('informatics', true, 10);
insert into assessment (name, isActive, element_id) values ('pw', true, 1), ('exam', true ,1), ('pw', true, 2), ('exam', true, 2), ('exam', true, 3), ('pw', false, 4);
insert into assessment (name, isActive, element_id) values ('exam', true, 5), ('exam', true, 6), ('exam', true, 7), ('exam', true, 8), ('exam', true, 9), ('exam', true, 10), ('exam', true, 11), ('exam', true, 12);
insert into module (name) values ('numerical analysis 1'), ('database'), ('software engineering 1'), ('software engineering 2'), ('probability');
insert into element (name, module_id) values ('numerical analysis 1', 15), ('database', 16), ('software engineering 1', 17), ('software engineering 2', 18), ('probability', 19), ('enumeration', 19); 
insert into assessment (name, element_id) values ('exam', 13), ('exam', 14), ('exam', 15), ('exam', 16), ('exam', 17), ('exam', 18); 

insert into academicYear (start_date, finish_date) values ('2021-09-15', '2022-06-15');
insert into academicYear (start_date, finish_date) values ('2022-09-15', '2023-06-15');

insert into academicStage (sequence, major_id, isDiploma) values (1,3,false), (2,3,false), (3,3,true);
insert into academicSemester (sequence, academicStage_id) values (1,1), (2,1), (1,2), (2,2), (1,3);

insert into semester (academicSemester_id, academicYear_id) values 
(1, 1), (2, 1), (3,1),
(1, 2), (2, 2), (3,2);
insert into semester_module (semester_id, module_id) values 
(1,1), (1,2), (1,3), (1,4), (1,5), 
(2,9), (2,7),
(3,15), (3,16), (3,17), (3,18), (3,19),
(4,1), (4,2), (4,3), (4,4), (4,5), 
(5,6), (5,7), (5,8), (5,9), (5,10),
(6,15), (6,16), (6,17), (6,18), (6,19);

insert into onlineRegistrations (massar_code, ar_first_name, ar_last_name, first_name, last_name, cin, nationality, gender_id, birthdate, ar_birth_place, birth_place, residence_town, province, bac_year, high_school, bac_place, academy, bac_serie_id, bac_honour_id, major_id, establishment) values ('M123456789', 'أحمد', 'تفاحة', 'Ahemd', 'Toufaha', 'C123456', 'Moroccan', 1, '2000-01-02', 'قرطة حنة', 'Karta Hana', 'Meknes', 'Meknes', 2020, 'Flowers', 'Mly Ismail', 'Meknes', 1, 1, 3, 'BachMek');
insert into onlineRegistrations (massar_code, ar_first_name, ar_last_name, first_name, last_name, cin, nationality, gender_id, birthdate, ar_birth_place, birth_place, residence_town, province, bac_year, high_school, bac_place, academy, bac_serie_id, bac_honour_id, major_id, establishment) values ('M123456700', 'أحمد', 'تفاحة', 'Ahemd', 'Toufaha', 'C123456', 'Moroccan', 1, '2000-01-02', 'قرطة حنة', 'Karta Hana', 'Meknes', 'Meknes', 2020, 'Flowers', 'Mly Ismail', 'Meknes', 1, 1, 3, 'BachMek');
insert into onlineRegistration (massar_code, ar_first_name, ar_last_name, first_name, last_name, cin, nationality, gender_id, birthdate, residence_town, province, bac_year, high_school, bac_place, academy, bac_serie_id, bac_honour_id, major_id, establishment) values ('M123456700', 'أحمد', 'تفاحة', 'Ahemd', 'Toufaha', 'C123456', 'Moroccan', 1, '2000-01-02', 'Meknes', 'Meknes', 2020, 'Flowers', 'Mly Ismail', 'Meknes', 1, 1, 3, 'BachMek');
insert into administrativeRegistration (cne, personal_address, phone_number, parents_address, isTuition, academicYear_id, academicStage_id,  onlineRegistration_id) values ('123456789', 'Meknes Zitoune', '212612345678', 'Meknes Zitoune', true, 1, 1, 1);
insert into user (username, password, first_name, last_name, gender_id, birthdate, personal_address, cin) values ('toufaha', "7!360AvHr=z/t}'^v^8T#2", 'Ahmed', 'TOUFAHA', 1, '2000-01-02', 'Meknes', 'C123456'); -- toufaha
insert into student (user_id, administrativeRegistration_id) values (2, 1);

insert into pedagogicalRegistration (administrativeRegistration_id) values (1), (1);
insert into pedagogicalRegistration_semester (pedagogicalRegistration_id, semester_id) values (1, 1), (2, 2);
insert into pedagogicalRegistration_element (pedagogicalRegistration_id, element_id) values (1, 1), (1, 2), (1, 5), (1, 6), (1, 7), (1, 8);
insert into pedagogicalRegistration_element (pedagogicalRegistration_id, element_id) values (2, 11), (2, 9);

-- Bekri - Algo 1
insert into user (username, password, first_name, last_name, gender_id, birthdate, cin) values ('bekri', "oysWza8xz1`]tZ6]/Z", 'Ali', 'BEKRI', 1, '1966-11-02', 'C123411'); -- bekri
insert into instructor (user_id, discipline) values (3, 'Informatics');
insert into majorRep (instructor_id, academicYear_id, major_id) values (1, 1, 3);
insert into moduleRep (instructor_id, academicYear_id, module_id) values (1, 1, 5);
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (1, 1, 8);
insert into assessmentCoef (elementInstructor_id, assessment_id) values (1, 10);
insert into elementCoef (moduleRep_id, element_id) values (1,8);
insert into moduleCoefBound (majorRep_id, module_id) values (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 7), (1, 9);
insert into academicStageBound (majorRep_id, academicStage_id) values (1, 1);
insert into semesterBound (majorRep_id, academicSemester_id) values (1, 1), (1, 2);

-- Ismaili - Java 1
insert into user (username, password, first_name, last_name, gender_id, cin) values ('ismaili', "vW7w2Qq%z^4@2L'_v^8+#Z", 'Mehdi', 'ISMAILI', 1, 'C123422'); -- ismaili
insert into instructor (user_id, discipline) values (4, 'Informatics and programming');
insert into moduleRep (instructor_id, academicYear_id, module_id) values (2, 1, 1);
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (2, 1, 1);
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (2, 1, 2);
insert into assessmentCoef (elementInstructor_id, assessment_id) values (2, 1);
insert into assessmentCoef (elementInstructor_id, assessment_id, coefficient) values (2, 2, 3);
insert into assessmentCoef (elementInstructor_id, assessment_id) values (3, 3);
insert into assessmentCoef (elementInstructor_id, assessment_id, coefficient) values (3, 4, 3);
insert into elementCoef (moduleRep_id, element_id) values (2,1), (2,2);

-- Khaloui - Analysis 2
insert into user (username, password, first_name, last_name, gender_id, cin) values ('khaloui', "x'vEp|2Y6)'D2m'/v{8H#0", 'Mostapha', 'KHALOUI', 1, 'C123433'); -- khaloui
insert into instructor (user_id, discipline) values (5, 'Mathematics');
insert into moduleRep (instructor_id, academicYear_id, module_id) values (3, 1, 3);
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (3, 1, 6);
insert into assessmentCoef (elementInstructor_id, assessment_id) values (4, 8);
insert into elementCoef (moduleRep_id, element_id) values (3,6);

-- Elfetenassi - Analysis 1
insert into user (username, password, first_name, last_name, gender_id, cin) values ('elfetenassi', 'r"zKu(uM`2w)7Uu`,7/z61#~zC"U(m', 'Mohammed', 'ELFETENASSI', 1, 'C123444'); -- elfetenassi
insert into instructor (user_id, discipline) values (6, 'Mathematics');
insert into moduleRep (instructor_id, academicYear_id, module_id) values (4, 1, 2);
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (4, 1, 5);
insert into assessmentCoef (elementInstructor_id, assessment_id) values (5, 7);
insert into elementCoef (moduleRep_id, element_id) values (4,5);

-- Kinani - Algebra 1
insert into user (username, password, first_name, last_name, gender_id, cin) values ('kinani', 'x=w)3{q+5^1j";uR7-!G', 'Hassan', 'KINANI', 1, 'C123455'); -- kinani
insert into instructor (user_id, discipline) values (7, 'Algebra');
insert into moduleRep (instructor_id, academicYear_id, module_id) values (5, 1, 4);
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (5, 1, 7);
insert into assessmentCoef (elementInstructor_id, assessment_id) values (6, 9);
insert into elementCoef (moduleRep_id, element_id) values (5,7);

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(12, 2, 1, 1), (14, 2, 2, 1), 
(12.5, 3, 3, 1), (13.5, 3, 4, 1), 
(17, 1, 10, 1), 
(10, 4, 8, 1), 
(11.5, 5, 7, 1), 
(16.75, 6, 9, 1);

-- Elfetenassi - Analysis 3
insert into moduleRep (instructor_id, academicYear_id, module_id) values (4, 1, 7);
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (4, 1, 9);
insert into assessmentCoef (elementInstructor_id, assessment_id) values (7, 11);
insert into elementCoef (moduleRep_id, element_id) values (6,9);

-- Kinani - Algebra 2
insert into moduleRep (instructor_id, academicYear_id, module_id) values (5, 1, 9);
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (5, 1, 11);
insert into assessmentCoef (elementInstructor_id, assessment_id) values (8, 13);
insert into elementCoef (moduleRep_id, element_id) values (7,11);

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values (7, 7, 11, 2), (11, 8, 13, 2);
