SET client_encoding TO 'utf8';
INSERT INTO countries (id, country_name, creation_date, modification_date, status)
VALUES (1, 'Belarus', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (2, 'Russia', '2022-09-28 22:06:39.673000', '2022-09-28 22:06:39.673000', 'ACTIVE'),
       (5, 'Germany', '2022-10-03 16:55:10.241000', '2022-10-03 16:55:10.241000', 'ACTIVE');
select SETVAL('countries_id_seq', 5);


INSERT INTO locations (id, postal_code, name, description, country_id, creation_date, modification_date, status)
VALUES (1, '246028', 'Gomel', 'Gomelskaya oblast''', 1, '2022-09-04 12:48:05.000000', '2022-09-04 12:48:07.000000','ACTIVE'),
       (2, '247210', 'Zhlobin', 'Gomelskaya oblast''', 1, '2022-08-23 10:42:18.055651', '2022-08-23 10:42:18.060082','ACTIVE'),
       (4, '247250', 'Rogachev', 'Gomelskaya oblast''', 1, '2022-09-30 16:15:07.018000', '2022-09-30 16:15:07.018000','ACTIVE'),
       (5, '212001', 'Mogilev', 'Mogilevskaya oblast''', 1, '2022-10-03 16:59:19.313000', '2022-10-03 16:59:19.313000','ACTIVE'),
       (3, '213801', 'Bobruisk', 'Mogilevskaya oblast''', 1, '2022-09-28 22:05:28.377000', '2022-09-28 22:05:28.377000','ACTIVE');
select SETVAL('locations_id_seq', 5);

INSERT INTO roles (id, name, creation_date, modification_date, status)
VALUES (2, 'ROLE_ADMIN', '2022-09-07 18:36:34.381538', '2022-09-07 18:36:34.381538', 'ACTIVE'),
       (1, 'ROLE_USER', '2022-09-07 18:36:34.381538', '2022-09-07 18:36:34.381538', 'ACTIVE'),
       (4, 'ROLE_ANONYMOUS', '2022-09-19 15:15:21.167000', '2022-09-19 15:15:21.167000', 'ACTIVE'),
       (3, 'ROLE_MODERATOR', '2022-09-07 18:38:34.381538', '2022-09-07 18:38:34.381538', 'ACTIVE');
select SETVAL('roles_id_seq', 4);




INSERT INTO users (id, first_name, user_password, location_id, location_details,  mobile_number, email, registration_date, creation_date, modification_date, status, user_login, last_name)
VALUES (6, 'Oleg', '5555', 2, '19 4 33', '+375293245432', 'olegpopkov@gmail.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-08-24 22:32:52.546000', 'ACTIVE', 'olegpopkov','test_last_name'),
       (5, 'Anastasia', '5555', 2, '16 51 106', '+375256198520', 'nastyapetrova@gmail.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-08-23 19:33:51.017000', 'ACTIVE', 'nastya99','test_last_name'),
       (3, 'Sergey', '5555', 2, '18 9 55','+375256145343', 'SergeyBRN@gmail.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-08-23 17:13:04.996000', 'ACTIVE', 'serg123','test_last_name'),
       (7, 'Billi', '435342', 2, 'Stavropolskaya st 6-2', '+3752964366661', 'billimilligan@yahoo.com', '2022-08-28 17:09:53.490000', '2022-08-28 17:09:53.490000', '2022-08-28 17:09:53.490000', 'ACTIVE', 'milligan','test_last_name'),
       (16, 'Pasha', 'asdasd', 2, '18-2-15',  '+375291854765', 'pashaprokopev@gmail.com', '2022-09-15 13:37:27.081024', '2022-09-15 13:37:27.081024', '2022-09-15 13:37:27.081024', 'ACTIVE', 'pasha22','test_last_name'),
       (18, 'Igor', 'fdsfr', 2, '18 4 33',  '+375291599325', 'igorpython@gmail.com', '2022-09-15 17:04:14.468000', '2022-09-15 17:04:14.468000', '2022-09-15 17:04:14.468000', 'ACTIVE', 'igor1233','test_last_name'),
       (22, 'Slava', 'dsadd', 2, '18 3 23',  '+375291599325', 'slava32323@gmail.com', '2022-09-18 16:18:53.956000', '2022-09-18 16:18:53.956000', '2022-09-18 16:18:53.956000', 'ACTIVE', 'slava1233','test_last_name'),
       (4, 'Vladimir', '$2a$04$VviJxSCWVE.gWeV7dtozROSjMpgY/z3dtOwfGoFUbcQ2sikFnb6VC', 2, '17 4/5 83',  '+375291597740', 'zagvladimir88@gmail.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-08-23 17:15:08.877000', 'ACTIVE', 'strjke','test_last_name'),
       (29, 'Daniil', '$2a$10$9G/Slur8Lt2F4HU2mFA04epVtFW33czjB5mp32XF/lEipxqgWjrwa', 2, '16 52 172',  '+375291269582', 'daniil72@gmail.com', '2022-09-19 20:29:22.624000', '2022-09-19 20:29:22.624000', '2022-09-19 20:29:22.624000', 'ACTIVE', 'Daniil123','test_last_name'),
       (42, 'Evgenii', '$2a$10$OkoQ8O4FsKdLDQSctqH0i.8w85cp7QmD53hvhtx2.j0rC8hs4q/ke', 2, '3 5 32', '+37529232123', 'evgenii@gmail.com', '2022-09-25 19:02:39.449000', '2022-09-25 19:02:39.449000', '2022-09-25 19:02:39.449000', 'ACTIVE', 'Grichevski','test_last_name'),
       (40, 'David', '$2a$10$oZQZFQ4.u4vSDNJQhKJKYu6tXjwNYzwt7mKwVq9MTiKxWUAWIGWsq', 2, '3 5 32', '+37529232123', 'David60@gmail.com', '2022-09-25 18:59:00.561000', '2022-09-25 18:59:00.561000', '2022-09-25 18:59:00.561000', 'ACTIVE', 'Duchovny','test_last_name'),
       (41, 'Siarhei', '$2a$10$WyFxjAN9OpChADqQTBQ6EO3qHbNMtsCTmsSRp4BjV9Qskiqt8raWy', 2, '3 5 32',  '+37529232123', 'siarheiignat@gmail.com', '2022-09-25 19:02:04.513000', '2022-09-25 19:02:04.513000', '2022-09-25 19:02:04.513000', 'ACTIVE', 'Ploskozemel','test_last_name'),
       (62, 'Evgenii', '$2a$10$m32NaDG6DJJnlaXjxBUR1./BkTJUOlmvhvRua5Hls1ZF3tLMGg3Zm', 2, '16 23 139',  '+375253633333', 'evgenkaluta@gmail.com', '2022-09-26 21:13:19.706000', '2022-09-26 21:13:19.706000', '2022-09-26 21:13:19.706000', 'ACTIVE', 'kaluta','test_last_name'),
       (2, 'Alexey', '$2a$10$Jaec24M8OuSXLoXsb3bDFORF6XgUD8uZYxaGJzS1yfoaK5Ktos3K2', null, '19 9 55',  '+375256145343', 'Alexey89@gmail.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-09-28 19:26:10.112000', 'ACTIVE', 'alexey123','test_last_name'),
       (63, 'Evgenii', '$2a$10$zCXlJuq89egHrJBeYK3ML.2vXV.X8geOU1kksxTt5FI/NTlEbV7tK', 2, '20 30 5',  '+375256666666', 'nuteki@gmail.com', '2022-09-30 21:41:38.589000', '2022-09-30 21:41:38.589000', '2022-09-30 21:41:38.589000', 'ACTIVE', 'nuteki','test_last_name'),
       (66, 'Anastasia', '$2a$10$.5ARmu5z5eYvYfGhRnUH6utAARKPYVwTnnWt683FF5qsCw9bpFQE.', 2, '20 1 5',  '+375256145343', 'anastasia87@gmail.com', '2022-10-10 16:01:30.141000', '2022-10-10 16:01:30.141000', '2022-10-10 16:01:30.141000', 'ACTIVE', 'nastya87','test_last_name'),
       (74, 'Galina', '$2a$10$YAwfyEHW/D1LMugzEBHvienMhXgtOYpaifHZlQFaoPA2VSV/N5CUq', 2, '19 2 15',  '+375256145343', 'galina@gmail.com', '2022-10-12 12:11:19.168000', '2022-10-12 12:11:19.168000', '2022-10-12 12:11:19.168000', 'ACTIVE', 'galya78','test_last_name'),
       (76, 'Grigorii', '$2a$10$68MaBCOgX0vGeSX9Iuqy.eCkZ7y3iXJ/.P6roLR2o90Am9hqiRtwu', 2, '19 2 15',  '+375256145343', 'grig87@gmail.com', '2022-10-13 21:28:05.983000', '2022-10-13 21:28:05.983000', '2022-10-13 21:28:05.983000', 'ACTIVE', 'grisha87','test_last_name'),
       (77, 'Kail', '$2a$10$8Gj01wVr9j9hTp7dIc2.6uA5UPvnA5.ewq8GsKwXrXDUM9syiiuXC', 2, '20 30 5',  '+375256145343', 'kail95@gmail.com', '2022-10-15 22:31:15.892000', '2022-10-15 22:31:15.892000', '2022-10-15 22:31:15.892000', 'ACTIVE', 'Kail123','test_last_name'),
       (85, 'Genry', '$2a$10$dJu1CL6vGHdeBk2yUmSXUuXMom2CamVqN2RainqqtQP.s/Oa0Ulky', 2, '20 30 5',  '+375256145343', 'genrybukovski@gmail.com', '2022-10-16 21:05:40.602000', '2022-10-16 21:05:40.602000', '2022-10-16 21:05:40.602000', 'ACTIVE', 'Genry453','test_last_name'),
       (15, 'Evgenii', '5555', 2, '20 30 8',  '+375256145343', 'evgeniiArgs@gmail.com', '2022-09-07 18:41:48.931000', '2022-09-07 18:41:48.931000', '2022-10-16 21:51:32.917000', 'ACTIVE', 'joniq','test_last_name'),
       (39, 'Charles', '$2a$10$vC6mLAvJ6a37NZP4sXHWg.UoaryTtSVMdCIlyPzgVS/JUIQ2fF09W', 2, '1 5 18',  '+375292658952', 'Charles20@gmail.com', '2022-09-25 18:57:50.306000', '2022-09-25 18:57:50.306000', '2022-10-16 22:03:07.785000', 'ACTIVE', 'Bukowski','test_last_name');
select SETVAL('users_id_seq', 85);


INSERT INTO user_roles (user_id, role_id, creation_date, modification_date)
VALUES (4, 1, '2022-09-18 17:08:28.977071', '2022-09-18 17:08:28.977071'),
       (4, 2, '2022-09-18 17:08:28.977071', '2022-09-18 17:08:28.977071'),
       (39, 1, '2022-09-25 18:57:50.336252', '2022-09-25 18:57:50.336252'),
       (40, 1, '2022-09-25 18:59:00.563297', '2022-09-25 18:59:00.563297'),
       (41, 1, '2022-09-25 19:02:04.514605', '2022-09-25 19:02:04.514605'),
       (42, 1, '2022-09-25 19:02:39.486198', '2022-09-25 19:02:39.486198'),
       (62, 1, '2022-09-26 21:13:19.708545', '2022-09-26 21:13:19.708545'),
       (63, 1, '2022-09-30 21:41:38.488032', '2022-09-30 21:41:38.488032'),
       (66, 1, '2022-10-10 16:01:30.089344', '2022-10-10 16:01:30.089344'),
       (76, 1, '2022-10-13 21:28:05.884149', '2022-10-13 21:28:05.884149'),
       (77, 1, '2022-10-15 22:31:15.792920', '2022-10-15 22:31:15.792920'),
       (85, 1, '2022-10-16 21:05:40.447517', '2022-10-16 21:05:40.447517');



INSERT INTO item_categories (id, category_name, creation_date, modification_date, status)
VALUES (1, 'TEST1', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (2, 'TEST2', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (3, 'TEST3', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (4, 'TEST4', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (5, 'TEST5', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (6, 'TEST6', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (7, 'TEST7', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (8, 'TEST8', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (9, 'TEST9', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (10, 'TEST10', '2022-10-03 16:56:10.739000', '2022-10-03 16:56:10.739000', 'ACTIVE');
select SETVAL('item_categories_id_seq', 10);


INSERT INTO sub_item_types (id, sub_category_name, category_id, creation_date, modification_date, status)
VALUES (1, 'TEST1', 1, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (2, 'TEST2', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (3, 'TEST3', 1, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (4, 'TEST4', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (5, 'TEST5', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (6, 'TEST6', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (7, 'TEST7', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (8, 'TEST8', 1, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
       (9, 'TEST9', 1, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE');
select SETVAL('sub_item_types_id_seq', 10);

INSERT INTO items (id, item_name, item_type_id, location_id, item_location, description, owner_id, price_per_hour, available, creation_date, modification_date, status)
VALUES (2, 'Makita hr2470ft', 5, 2, 'Microdistrict 17', 'Rotary Hammer makita hr2470ft', 3, 5.00, true, '2022-08-28 17:17:29.000000', '2022-08-28 17:17:31.000000', 'ACTIVE'),
       (3, 'Makita hr2450ft', 5, 2, 'Microdistrict 20', 'Rotary Hammer makita hr2450', 3, 5.00, true, '2022-08-28 17:32:34.668000', '2022-08-28 17:32:34.668000', 'ACTIVE'),
       (4, 'Bosch GBH 2-26', 5, 2, 'Microdistrict 16', 'Rotary Hammer Bosch GBH 2-26 DFR 0.611.254.768', 3, 6.00, true, '2022-08-28 17:32:34.685000', '2022-08-28 17:32:34.685000', 'ACTIVE'),
       (5, 'GST 150 BCE PROFESSIONAL', 4, 2, 'Microdistrict 16', 'JIGSAW GST 150 BCE PROFESSIONAL', 5, 3.00, true, '2022-08-28 19:54:08.000000', '2022-08-28 19:54:10.000000', 'ACTIVE'),
       (6, 'GST 12V-70 PROFESSIONAL', 4, 2, 'Microdistrict 16', 'Extremely compact and lightweight 12 volt professional cordless jigsaw', 5, 3.00, true, '2022-08-28 19:54:11.000000', '2022-08-28 19:54:13.000000', 'ACTIVE'),
       (8, 'DEKO DKRH20H3', 5, 2, 'Microdistrict 3', 'Rotary Hammer DEKO DKRH20H3', 5, 5.00, true, '2022-09-04 15:53:19.140000', '2022-09-04 15:53:19.140000', 'ACTIVE');
select SETVAL('items_id_seq', 10);

INSERT INTO items_leased (id, item_id, renter_id, time_from, time_to, price_per_hour, discount, fee, price_total, rentier_grade_description, renter_grade_description, creation_date, modification_date, status)
VALUES (1, 2, 5, '2022-09-06 16:20:05.000000', '2022-09-06 18:20:11.000000', 5.00, 0.00, 0.10, 5.10, '5', '5', '2022-09-06 15:20:54.000000', '2022-09-06 15:20:55.000000', 'ACTIVE'),
       (2, 3, 5, '2022-09-06 17:21:14.000000', '2022-09-06 22:21:19.000000', 5.00, 0.00, 0.10, 5.10, '5', '5', '2022-09-06 15:21:33.000000', '2022-09-06 15:21:34.000000', 'ACTIVE');
select SETVAL('items_leased_id_seq', 2);

INSERT INTO public.grades (id, item_id, user_id, grade, description, creation_date, modification_date, status)
VALUES (3, 2, 22, 5.0, 'Good', '2022-09-28 22:33:35.000000', '2022-09-28 22:33:36.000000', 'ACTIVE'),
 (31, 2, 3, 6.0, 'string', '2022-10-22 18:05:27.474000', null, 'ACTIVE'),
 (5, 2, 3, 5.0, 'ExcellentTest', '2022-09-29 17:51:06.263000', '2022-09-29 17:51:06.263000', 'ACTIVE'),
 (7, 2, 3, 5.0, 'ExcellentTestTestTest', '2022-09-29 19:13:48.990000', '2022-09-29 19:13:48.990000', 'ACTIVE'),
 (4, 2, 3, 5.0, 'Excellent', '2022-09-29 17:46:54.973000', '2022-09-29 17:47:15.772000', 'ACTIVE'),
 (6, 2, 3, 5.0, 'ExcellentTestTestTest', '2022-09-29 17:52:38.842000', '2022-09-29 17:52:38.842000', 'ACTIVE'),
 (8, 2, 3, 5.0, 'TEST', '2022-10-03 17:12:00.371000', '2022-10-03 17:12:00.371000', 'ACTIVE'),
 (1, 2, 6, 5.0, 'Good', '2022-09-28 22:31:44.000000', '2022-09-28 22:31:45.000000', 'ACTIVE');
select SETVAL('grades_id_seq', 8);