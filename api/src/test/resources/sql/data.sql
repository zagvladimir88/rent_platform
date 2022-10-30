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

INSERT INTO users (id, first_name, last_name, user_password, address_line1, address_line2, mobile_number, email, registration_date, creation_date, modification_date, status, user_login, activation_code, state, city, postal_code)
VALUES (15, 'Evgenii', 'test_last_name', '5555', '2', '20 30 8', '+375256145343', 'evgeniiArgs@gmail.com', '2022-09-07 18:41:48.931000', '2022-09-07 18:41:48.931000', '2022-10-16 21:51:32.917000', 'ACTIVE', 'joniq', null, 'Gomel', 'Svetlogorsk', '247433'),
(4, 'Vladimir', 'test_last_name', '$2a$04$VviJxSCWVE.gWeV7dtozROSjMpgY/z3dtOwfGoFUbcQ2sikFnb6VC', '2', '17 4/5 83', '+375291597740', 'zagvladimir88@gmail.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-08-23 17:15:08.877000', 'ACTIVE', 'strjke', null, 'Gomel', 'Svetlogorsk', '247433'),
(5, 'Anastasia', 'test_last_name', '5555', '2', '16 51 106', '+375256198520', 'jegomas684@evilant.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-08-23 19:33:51.017000', 'ACTIVE', 'nastya99', null, 'Gomel', 'Svetlogorsk', '247433'),
(76, 'Grigorii', 'test_last_name', '$2a$10$68MaBCOgX0vGeSX9Iuqy.eCkZ7y3iXJ/.P6roLR2o90Am9hqiRtwu', '2', '19 2 15', '+375256145343', 'grig87@gmail.com', '2022-10-13 21:28:05.983000', '2022-10-13 21:28:05.983000', '2022-10-13 21:28:05.983000', 'ACTIVE', 'grisha87', null, 'Gomel', 'Svetlogorsk', '247433'),
(29, 'Daniil', 'test_last_name', '$2a$10$9G/Slur8Lt2F4HU2mFA04epVtFW33czjB5mp32XF/lEipxqgWjrwa', '2', '16 52 172', '+375291269582', 'daniil72@gmail.com', '2022-09-19 20:29:22.624000', '2022-09-19 20:29:22.624000', '2022-09-19 20:29:22.624000', 'ACTIVE', 'Daniil123', null, 'Gomel', 'Svetlogorsk', '247433'),
(74, 'Galina', 'test_last_name', '$2a$10$YAwfyEHW/D1LMugzEBHvienMhXgtOYpaifHZlQFaoPA2VSV/N5CUq', '2', '19 2 15', '+375256145343', 'galina@gmail.com', '2022-10-12 12:11:19.168000', '2022-10-12 12:11:19.168000', '2022-10-12 12:11:19.168000', 'ACTIVE', 'galya78', null, 'Gomel', 'Svetlogorsk', '247433'),
(77, 'Kail', 'test_last_name', '$2a$10$8Gj01wVr9j9hTp7dIc2.6uA5UPvnA5.ewq8GsKwXrXDUM9syiiuXC', '2', '20 30 5', '+375256145343', 'kail95@gmail.com', '2022-10-15 22:31:15.892000', '2022-10-15 22:31:15.892000', '2022-10-15 22:31:15.892000', 'ACTIVE', 'Kail123', null, 'Gomel', 'Svetlogorsk', '247433'),
(18, 'Igor', 'test_last_name', 'fdsfr', '2', '18 4 33', '+375291599325', 'igorpython@gmail.com', '2022-09-15 17:04:14.468000', '2022-09-15 17:04:14.468000', '2022-09-15 17:04:14.468000', 'ACTIVE', 'igor1233', null, 'Gomel', 'Svetlogorsk', '247433'),
(22, 'Slava', 'test_last_name', 'dsadd', '2', '18 3 23', '+375291599325', 'slava32323@gmail.com', '2022-09-18 16:18:53.956000', '2022-09-18 16:18:53.956000', '2022-09-18 16:18:53.956000', 'ACTIVE', 'slava1233', null, 'Gomel', 'Svetlogorsk', '247433'),
(85, 'Genry', 'test_last_name', '$2a$10$dJu1CL6vGHdeBk2yUmSXUuXMom2CamVqN2RainqqtQP.s/Oa0Ulky', '2', '20 30 5', '+375256145343', 'genrybukovski@gmail.com', '2022-10-16 21:05:40.602000', '2022-10-16 21:05:40.602000', '2022-10-16 21:05:40.602000', 'ACTIVE', 'Genry453', null, 'Gomel', 'Svetlogorsk', '247433'),
(62, 'Evgenii', 'test_last_name', '$2a$10$m32NaDG6DJJnlaXjxBUR1./BkTJUOlmvhvRua5Hls1ZF3tLMGg3Zm', '2', '16 23 139', '+375253633333', 'evgenkaluta@gmail.com', '2022-09-26 21:13:19.706000', '2022-09-26 21:13:19.706000', '2022-09-26 21:13:19.706000', 'ACTIVE', 'kaluta', null, 'Gomel', 'Svetlogorsk', '247433'),
(63, 'Evgenii', 'test_last_name', '$2a$10$zCXlJuq89egHrJBeYK3ML.2vXV.X8geOU1kksxTt5FI/NTlEbV7tK', '2', '20 30 5', '+375256666666', 'nuteki@gmail.com', '2022-09-30 21:41:38.589000', '2022-09-30 21:41:38.589000', '2022-09-30 21:41:38.589000', 'ACTIVE', 'nuteki', null, 'Gomel', 'Svetlogorsk', '247433'),
(66, 'Anastasia', 'test_last_name', '$2a$10$.5ARmu5z5eYvYfGhRnUH6utAARKPYVwTnnWt683FF5qsCw9bpFQE.', '2', '20 1 5', '+375256145343', 'anastasia87@gmail.com', '2022-10-10 16:01:30.141000', '2022-10-10 16:01:30.141000', '2022-10-10 16:01:30.141000', 'ACTIVE', 'nastya87', null, 'Gomel', 'Svetlogorsk', '247433'),
(39, 'Charles', 'test_last_name', '$2a$10$vC6mLAvJ6a37NZP4sXHWg.UoaryTtSVMdCIlyPzgVS/JUIQ2fF09W', '2', '1 5 18', '+375292658952', 'Charles20@gmail.com', '2022-09-25 18:57:50.306000', '2022-09-25 18:57:50.306000', '2022-10-16 22:03:07.785000', 'ACTIVE', 'Bukowski', null, 'Gomel', 'Svetlogorsk', '247433'),
(40, 'David', 'test_last_name', '$2a$10$oZQZFQ4.u4vSDNJQhKJKYu6tXjwNYzwt7mKwVq9MTiKxWUAWIGWsq', '2', '3 5 32', '+37529232123', 'David60@gmail.com', '2022-09-25 18:59:00.561000', '2022-09-25 18:59:00.561000', '2022-10-30 14:54:11.158690', 'DELETED', 'Duchovny', null, 'Gomel', 'Svetlogorsk', '247433'),
(6, 'Oleg', 'test_last_name', '5555', '2', '19 4 33', '+375293245432', 'olegpopkov@gmail.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-08-24 22:32:52.546000', 'ACTIVE', 'olegpopkov', null, 'Gomel', 'Svetlogorsk', '247433'),
(7, 'Billi', 'test_last_name', '435342', '2', 'Stavropolskaya st 6-2', '+3752964366661', 'billimilligan@yahoo.com', '2022-08-28 17:09:53.490000', '2022-08-28 17:09:53.490000', '2022-08-28 17:09:53.490000', 'ACTIVE', 'milligan', null, 'Gomel', 'Svetlogorsk', '247433'),
(41, 'Siarhei', 'test_last_name', '$2a$10$WyFxjAN9OpChADqQTBQ6EO3qHbNMtsCTmsSRp4BjV9Qskiqt8raWy', '2', '3 5 32', '+37529232123', 'siarheiignat@gmail.com', '2022-09-25 19:02:04.513000', '2022-09-25 19:02:04.513000', '2022-09-25 19:02:04.513000', 'ACTIVE', 'Ploskozemel', null, 'Gomel', 'Svetlogorsk', '247433'),
(2, 'Alexey', 'test_last_name', '$2a$10$Jaec24M8OuSXLoXsb3bDFORF6XgUD8uZYxaGJzS1yfoaK5Ktos3K2', null, '19 9 55', '+375256145343', 'Alexey89@gmail.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-09-28 19:26:10.112000', 'ACTIVE', 'alexey123', null, 'Gomel', 'Svetlogorsk', '247433'),
(3, 'Sergey', 'test_last_name', '5555', '2', '18 9 55', '+375256145343', 'SergeyBRN@gmail.com', '2022-08-23 16:01:57.655000', '2022-08-23 16:01:57.655000', '2022-08-23 17:13:04.996000', 'ACTIVE', 'serg123', null, 'Gomel', 'Svetlogorsk', '247433'),
(42, 'Evgenii', 'test_last_name', '$2a$10$OkoQ8O4FsKdLDQSctqH0i.8w85cp7QmD53hvhtx2.j0rC8hs4q/ke', '2', '3 5 32', '+37529232123', 'evgenii@gmail.com', '2022-09-25 19:02:39.449000', '2022-09-25 19:02:39.449000', '2022-09-25 19:02:39.449000', 'ACTIVE', 'Grichevski', null, 'Gomel', 'Svetlogorsk', '247433');

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



INSERT INTO categories (id, category_name, creation_date, modification_date, status) VALUES (1, 'TEST1', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
(2, 'TEST2', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
(3, 'TEST3', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
(4, 'TEST4', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
(5, 'TEST5', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
(6, 'TEST6', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
(7, 'TEST7', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
(8, 'TEST8', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
(9, 'TEST9', '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
(10, 'TEST10', '2022-10-03 16:56:10.739000', '2022-10-03 16:56:10.739000', 'ACTIVE');

select SETVAL('categories_id_seq', 10);


INSERT INTO sub_categories (id, sub_category_name, category_id, creation_date, modification_date, status) VALUES (1, 'TEST1', 1, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
 (2, 'TEST2', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
 (3, 'TEST3', 1, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
 (4, 'TEST4', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
 (5, 'TEST5', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
 (6, 'TEST6', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
 (7, 'TEST7', 2, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
 (8, 'TEST8', 1, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE'),
 (9, 'TEST9', 1, '2022-09-15 15:15:22.361321', '2022-09-15 15:15:22.361321', 'ACTIVE');

select SETVAL('sub_categories_id_seq', 10);

INSERT INTO items (id, item_name, sub_category_id, description, price_per_day, available, creation_date, modification_date, status, brand)
VALUES (2, 'Makita hr2470ft', 5, 'Rotary Hammer makita hr2470ft', 5.00, true, '2022-08-28 17:17:29.000000', '2022-08-28 17:17:31.000000', 'ACTIVE', null),
 (3, 'Makita hr2450ft', 5, 'Rotary Hammer makita hr2450', 5.00, true, '2022-08-28 17:32:34.668000', '2022-08-28 17:32:34.668000', 'ACTIVE', null),
 (4, 'Bosch GBH 2-26', 5, 'Rotary Hammer Bosch GBH 2-26 DFR 0.611.254.768', 6.00, true, '2022-08-28 17:32:34.685000', '2022-08-28 17:32:34.685000', 'ACTIVE', null),
 (5, 'GST 150 BCE PROFESSIONAL', 4, 'JIGSAW GST 150 BCE PROFESSIONAL', 3.00, true, '2022-08-28 19:54:08.000000', '2022-08-28 19:54:10.000000', 'ACTIVE', null),
 (6, 'GST 12V-70 PROFESSIONAL', 4, 'Extremely compact and lightweight 12 volt professional cordless jigsaw', 3.00, true, '2022-08-28 19:54:11.000000', '2022-08-28 19:54:13.000000', 'ACTIVE', null),
 (8, 'DEKO DKRH20H3', 5, 'Rotary Hammer DEKO DKRH20H3', 5.00, true, '2022-09-04 15:53:19.140000', '2022-09-04 15:53:19.140000', 'ACTIVE', null);

select SETVAL('items_id_seq', 10);

INSERT INTO items_leased (id, item_id, renter_id, time_from, time_to, price_per_day, discount, price_total, creation_date, modification_date, status)
VALUES (1, 2, 5, '2022-09-06 16:20:05.000000', '2022-09-06 18:20:11.000000', 5.00, 0.00, 5.10, '2022-09-06 15:20:54.000000', '2022-09-06 15:20:55.000000', 'ACTIVE'),
(2, 3, 5, '2022-09-06 17:21:14.000000', '2022-09-06 22:21:19.000000', 5.00, 0.00, 5.10, '2022-09-06 15:21:33.000000', '2022-09-06 15:21:34.000000', 'ACTIVE');

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