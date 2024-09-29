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
(2,6), (2,7), (2,8), (2,9), (2,10),
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
insert into pedagogicalRegistration_element (pedagogicalRegistration_id, element_id) values 
(1, 1), (1, 2), (1, 5), (1, 6), (1, 7), (1, 8),
(2, 3), (2, 9), (2, 10), (2, 11), (2, 12);

-- Bekri - Algo 1
insert into user (username, password, first_name, last_name, gender_id, birthdate, cin) values ('bekri', "oysWza8xz1`]tZ6]/Z", 'Ali', 'BEKRI', 1, '1966-11-02', 'C123411'); -- bekri
insert into instructor (user_id, discipline) values (3, 'Informatics');
insert into majorRep (instructor_id, academicYear_id, major_id) values (1, 1, 3);
insert into moduleRep (instructor_id, academicYear_id, module_id) values (1, 1, 5);
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (1, 1, 8);
insert into assessmentCoef (elementInstructor_id, assessment_id) values (1, 10);
insert into elementCoef (moduleRep_id, element_id) values (1,8);
insert into moduleCoefBound (majorRep_id, module_id) values (1, 1), (1, 2), (1, 3), (1, 4), (1, 5);
insert into academicStageBound (majorRep_id, academicStage_id) values (1, 1);
insert into academicStageBound (majorRep_id, academicStage_id) values (1, 2);
insert into semesterBound (majorRep_id, academicSemester_id) values (1, 1);

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
(15, 1, 10, 1), 
(10, 4, 8, 1), 
(11.5, 5, 7, 1), 
(12.75, 6, 9, 1); -- s1 tofaha


-- bekri - info - genie log 1
insert into moduleRep (instructor_id, academicYear_id, module_id) values (1, 1, 10); -- info
insert into moduleRep (instructor_id, academicYear_id, module_id) values (1, 1, 17); -- genie log 1
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (1, 1, 12); -- info
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (1, 1, 15); -- genie log 1
insert into assessmentCoef (elementInstructor_id, assessment_id) values (7, 14); -- info exam
insert into assessmentCoef (elementInstructor_id, assessment_id) values (8, 17); -- genie log 1 exam
insert into elementCoef (moduleRep_id, element_id) values (6,12); -- info
insert into elementCoef (moduleRep_id, element_id) values (7,15); -- genie log 1
insert into moduleCoefBound (majorRep_id, module_id) values (1, 6), (1, 7), (1, 8), (1, 9), (1, 10); -- s2
insert into moduleCoefBound (majorRep_id, module_id) values (1, 15), (1, 16), (1, 17), (1, 18), (1, 19); -- s3
insert into semesterBound (majorRep_id, academicSemester_id) values (1, 2); -- s2
insert into semesterBound (majorRep_id, academicSemester_id) values (1, 3); -- s3

-- Ismaili - Java 2 - db
insert into moduleRep (instructor_id, academicYear_id, module_id) values (2, 1, 6); -- java 2
insert into moduleRep (instructor_id, academicYear_id, module_id) values (2, 1, 16); -- db
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (2, 1, 3); -- collections
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (2, 1, 14); -- db
insert into assessmentCoef (elementInstructor_id, assessment_id) values (9, 5); -- collection - exam 
insert into assessmentCoef (elementInstructor_id, assessment_id) values (10, 16); -- db - exam

insert into elementCoef (moduleRep_id, element_id) values (8,3); -- collection
insert into elementCoef (moduleRep_id, element_id) values (9,14); -- db
insert into elementCoef (moduleRep_id, element_id) values (8,4); -- gui - not used


-- Khaloui - Analysis 4 - probability
insert into moduleRep (instructor_id, academicYear_id, module_id) values (3, 1, 8); -- analyse 4 
insert into moduleRep (instructor_id, academicYear_id, module_id) values (3, 1, 19); -- proba
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (3, 1, 10); -- analyse 4
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (3, 1, 17); -- proba
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (3, 1, 18); -- enum
insert into assessmentCoef (elementInstructor_id, assessment_id) values (11, 12); -- analyse 4
insert into assessmentCoef (elementInstructor_id, assessment_id) values (12, 19); -- prova
insert into assessmentCoef (elementInstructor_id, assessment_id) values (13, 20); -- enum
insert into elementCoef (moduleRep_id, element_id) values (10,10); -- analyse 4
insert into elementCoef (moduleRep_id, element_id) values (11,17); -- proba
insert into elementCoef (moduleRep_id, element_id) values (11,18); -- enum

-- Elfetenassi - Analysis 3 - analy num 1
insert into moduleRep (instructor_id, academicYear_id, module_id) values (4, 1, 7); -- analyse 3
insert into moduleRep (instructor_id, academicYear_id, module_id) values (4, 1, 15); -- analy num 1
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (4, 1, 9); -- analy 3
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (4, 1, 13); -- analy num 1
insert into assessmentCoef (elementInstructor_id, assessment_id) values (14, 11); -- analy 3
insert into assessmentCoef (elementInstructor_id, assessment_id) values (15, 15); -- analy num 1
insert into elementCoef (moduleRep_id, element_id) values (12,9); -- analy 3
insert into elementCoef (moduleRep_id, element_id) values (13,13); -- analy num 1

-- Kinani - Algebra 2 - genie log 2
insert into moduleRep (instructor_id, academicYear_id, module_id) values (5, 1, 9); -- algb 2
insert into moduleRep (instructor_id, academicYear_id, module_id) values (5, 1, 18); -- genie log 2
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (5, 1, 11); -- algb2
insert into elementInstructor (instructor_id, academicYear_id, element_id) values (5, 1, 16); -- geni log 2
insert into assessmentCoef (elementInstructor_id, assessment_id) values (16, 13); -- algb 2 
insert into assessmentCoef (elementInstructor_id, assessment_id) values (17, 18); -- geni log 2
insert into elementCoef (moduleRep_id, element_id) values (14,11); -- algb 2
insert into elementCoef (moduleRep_id, element_id) values (15,16); -- genie log 2


insert into elementInstructor (instructor_id, academicYear_id, element_id) values (2, 1, 4); -- gui - not used
insert into assessmentCoef (elementInstructor_id, assessment_id) values (18, 6); -- gui - pw - not used exam



insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(11, 9, 5, 2), 
(8, 14, 11, 2), 
(6, 11, 12, 2), 
(6, 16, 13, 2), 
(7.25, 7, 14, 2); -- s2 tofaha


-- Second student
insert into onlineRegistration (massar_code, first_name, last_name, gender_id, bac_year, bac_serie_id, bac_honour_id, major_id, establishment) values ('M222222222', 'prenom_2', 'nom_2', 1, 2020, 1, 1, 3, 'BachMek');
insert into administrativeRegistration (cne, academicYear_id, academicStage_id,  onlineRegistration_id) values ('22222222', 1, 1, 2);
insert into user (username, password, first_name, last_name, gender_id) values ('user_2', ",O!Z$R&/0-s65D,8", 'prenom_2', 'nom_2', 1); 
insert into student (user_id, administrativeRegistration_id) values (8, 2);

insert into pedagogicalRegistration (administrativeRegistration_id) values (2), (2);
insert into pedagogicalRegistration_semester (pedagogicalRegistration_id, semester_id) values (3, 1), (4, 2);
insert into pedagogicalRegistration_element (pedagogicalRegistration_id, element_id) values 
(3, 1), (3, 2), (3, 5), (3, 6), (3, 7), (3, 8),
(4, 3), (4, 9), (4, 10), (4, 11), (4, 12);

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(12, 2, 1, 3), (14, 2, 2, 3), 
(12.5, 3, 3, 3), (13.5, 3, 4, 3), 
(9, 1, 10, 3), 
(8, 4, 8, 3), 
(11.5, 5, 7, 3), 
(16.75, 6, 9, 3); -- s1 user 2

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(12, 9, 5, 4), 
(17, 14, 11, 4), 
(15, 11, 12, 4), 
(12, 16, 13, 4), 
(17.25, 7, 14, 4); -- s2 user 2



-- Third student
insert into onlineRegistration (massar_code, first_name, last_name, gender_id, bac_year, bac_serie_id, bac_honour_id, major_id, establishment) values ('M333333333', 'prenom_3', 'nom_3', 1, 2020, 1, 1, 3, 'BachMek');
insert into administrativeRegistration (cne, academicYear_id, academicStage_id,  onlineRegistration_id) values ('33333333', 1, 1, 3);
insert into user (username, password, first_name, last_name, gender_id) values ('user_3', ",O!Z$R&/0-s65D,8", 'prenom_3', 'nom_3', 1); 
insert into student (user_id, administrativeRegistration_id) values (9, 3);

insert into pedagogicalRegistration (administrativeRegistration_id) values (3), (3);
insert into pedagogicalRegistration_semester (pedagogicalRegistration_id, semester_id) values (5, 1), (6, 2);
insert into pedagogicalRegistration_element (pedagogicalRegistration_id, element_id) values 
(5, 1), (5, 2), (5, 5), (5, 6), (5, 7), (5, 8),
(6, 3), (6, 9), (6, 10), (6, 11), (6, 12);

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(5, 2, 1, 5), (2, 2, 2, 5), 
(5, 3, 3, 5), (4, 3, 4, 5), 
(9, 1, 10, 5), 
(2, 4, 8, 5), 
(4, 5, 7, 5), 
(8, 6, 9, 5); -- s1 user 3

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(13, 9, 5, 6), 
(8, 14, 11, 6), 
(9, 11, 12, 6), 
(17, 16, 13, 6), 
(11.25, 7, 14, 6); -- s2 user 3




-- fourth student
insert into onlineRegistration (massar_code, first_name, last_name, gender_id, bac_year, bac_serie_id, bac_honour_id, major_id, establishment) values ('M444444444', 'prenom_4', 'nom_4', 1, 2020, 1, 1, 3, 'BachMek');
insert into administrativeRegistration (cne, academicYear_id, academicStage_id,  onlineRegistration_id) values ('44444444', 1, 2, 4);
insert into user (username, password, first_name, last_name, gender_id) values ('user_4', ",O!Z$R&/0-s65D,8", 'prenom_4', 'nom_4', 1); 
insert into student (user_id, administrativeRegistration_id) values (10, 4);

insert into pedagogicalRegistration (administrativeRegistration_id) values (4);
insert into pedagogicalRegistration_semester (pedagogicalRegistration_id, semester_id) values (7, 3);
insert into pedagogicalRegistration_element (pedagogicalRegistration_id, element_id) values 
(7, 13), (7, 14), (7, 15), (7, 16), (7, 17), (7, 18);

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(12, 15, 15, 7), 
(14, 10, 16, 7), 
(19, 8, 17, 7), 
(12, 17, 18, 7), 
(14, 13, 20, 7), 
(18, 12, 19, 7); -- s3 user 4

-- fifth student
insert into onlineRegistration (massar_code, first_name, last_name, gender_id, bac_year, bac_serie_id, bac_honour_id, major_id, establishment) values ('M555555555', 'prenom_5', 'nom_5', 1, 2020, 1, 1, 3, 'BachMek');
insert into administrativeRegistration (cne, academicYear_id, academicStage_id,  onlineRegistration_id) values ('55555555', 1, 1, 5);
insert into user (username, password, first_name, last_name, gender_id) values ('user_5', ",O!Z$R&/0-s65D,8", 'prenom_5', 'nom_5', 1); 
insert into student (user_id, administrativeRegistration_id) values (11, 5);

insert into pedagogicalRegistration (administrativeRegistration_id) values (5);
insert into pedagogicalRegistration_semester (pedagogicalRegistration_id, semester_id) values (8, 1);
insert into pedagogicalRegistration_element (pedagogicalRegistration_id, element_id) values 
(8, 1), (8, 2), (8, 5), (8, 6), (8, 7), (8, 8);

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(10, 2, 1, 8), (6, 2, 2, 8), 
(9, 3, 3, 8), (8, 3, 4, 8), 
(9, 1, 10, 8), 
(19, 4, 8, 8), 
(9, 5, 7, 8), 
(17, 6, 9, 8); -- s1 user 5




-- sixth student
insert into onlineRegistration (massar_code, first_name, last_name, gender_id, bac_year, bac_serie_id, bac_honour_id, major_id, establishment) values ('M666666666', 'prenom_6', 'nom_6', 1, 2020, 1, 1, 3, 'BachMek');
insert into administrativeRegistration (cne, academicYear_id, academicStage_id,  onlineRegistration_id) values ('66666666', 1, 1, 6);
insert into user (username, password, first_name, last_name, gender_id) values ('user_6', ",O!Z$R&/0-s65D,8", 'prenom_6', 'nom_6', 1); 
insert into student (user_id, administrativeRegistration_id) values (12, 6);

insert into pedagogicalRegistration (administrativeRegistration_id) values (6);
insert into pedagogicalRegistration_semester (pedagogicalRegistration_id, semester_id) values (9, 1);
insert into pedagogicalRegistration_element (pedagogicalRegistration_id, element_id) values 
(9, 1), (9, 2), (9, 5), (9, 6), (9, 7), (9, 8);

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(12, 2, 1, 9), (16, 2, 2, 9), 
(19, 3, 3, 9), (18, 3, 4, 9), 
(19, 1, 10, 9), 
(19, 4, 8, 9), 
(19, 5, 7, 9), 
(17, 6, 9, 9); -- s1 user 6




-- 7th student
insert into onlineRegistration (massar_code, first_name, last_name, gender_id, bac_year, bac_serie_id, bac_honour_id, major_id, establishment) values ('M777777777', 'prenom_7', 'nom_7', 1, 2020, 1, 1, 3, 'BachMek');
insert into administrativeRegistration (cne, academicYear_id, academicStage_id,  onlineRegistration_id) values ('77777777', 1, 1, 7);
insert into user (username, password, first_name, last_name, gender_id) values ('user_7', ",O!Z$R&/0-s65D,8", 'prenom_7', 'nom_7', 1); 
insert into student (user_id, administrativeRegistration_id) values (13, 7);

insert into pedagogicalRegistration (administrativeRegistration_id) values (7), (7);
insert into pedagogicalRegistration_semester (pedagogicalRegistration_id, semester_id) values (10, 1), (11, 3);
insert into pedagogicalRegistration_element (pedagogicalRegistration_id, element_id) values 
(10, 5), (10, 6),
(11, 13), (11, 14), (11, 15);

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values 
(15, 5, 7, 10), 
(14, 4, 8, 10); -- s1 user 7

insert into assessmentGrade (normal_session, elementInstructor_id, assessment_id, pedagogicalRegistration_id) values  
(19, 15, 15, 11), 
(17, 10, 16, 11), 
(14.25, 8, 17, 11); -- s3 user 7
