alter table users
drop constraint users_location_id_fk;

alter table users
    rename column location_id to addres_line1;

alter table users

alter column addres_line1 type varchar(200) using addres_line1::varchar(200);

alter table users
    rename column location_details to address_line2;

alter table users

alter column address_line2 type varchar(200) using address_line2::varchar(200);

alter table users
    add state varchar(25);

alter table users
    add city varchar(25);

alter table users
    add postal_code varchar(10);

drop table locations;

drop table countries;
