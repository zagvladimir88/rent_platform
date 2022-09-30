delete from user_roles;
delete from users;
delete from roles;
-- delete from locations;
-- delete from countries;

-- insert into countries(country_name,creation_date,modification_date,status)
-- values('Belarus',CURRENT_TIMESTAMP(6),CURRENT_TIMESTAMP(6),'ACTIVE');
--
--
-- insert into locations (postal_code,name,description,country_id,creation_date,modification_date,status)
-- values ('247210','Zhlobin','Gomelskaya oblast',1,CURRENT_TIMESTAMP(6),CURRENT_TIMESTAMP(6),ACTIVE);

insert into users (id,username,user_password,location_id,location_details,phone_number,mobile_number,email,registration_date,creation_date,modification_date,status,user_login)
values
(1,'Vladimir','test',null,'16-5-139','80233429792','+375201597740','strjke@gmail.com',CURRENT_TIMESTAMP(6),CURRENT_TIMESTAMP(6),CURRENT_TIMESTAMP(6),'ACTIVE','strjke'),
(2,'Sergey','test',null,'16-5-139','80233429792','+375201597740','serg@gmail.com',CURRENT_TIMESTAMP(6),CURRENT_TIMESTAMP(6),CURRENT_TIMESTAMP(6),'ACTIVE','sergg');

insert into roles (id, name, creation_date, modification_date, status) values
(1,'ROLE_USER',current_timestamp(6),current_timestamp(6),'ACTIVE'),
(2,'ROLE_ADMIN',current_timestamp(6),current_timestamp(6),'ACTIVE');

insert into user_roles (user_id, role_id) values
(1,1), (1,2),(2,1),(2,2) ;