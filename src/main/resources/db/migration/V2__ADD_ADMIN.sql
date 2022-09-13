insert into roles (name, creation_date, modification_date, status)
values ('ROLE_USER',current_timestamp(6),current_timestamp(6),'ACTIVE');
insert into roles (name, creation_date, modification_date, status)
values ('ROLE_ADMIN',current_timestamp(6),current_timestamp(6),'ACTIVE');
insert into users (username, user_password, location_id, location_details, phone_number, mobile_number, email, registration_date, creation_date, modification_date)
values ('ADMIN',5555,null,'test','11111111111','11111111111','admin@gmail.com',current_timestamp(6),current_timestamp(6),current_timestamp(6));
