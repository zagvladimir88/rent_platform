--COUNTRIES

alter table countries
    add creation_date timestamp(6) default CURRENT_TIMESTAMP(6);

alter table countries
    add modification_date timestamp(6) default CURRENT_TIMESTAMP(6);

alter table countries
    add status varchar(25) default 'ACTIVE';


--USERS

alter table users
alter column user_password type varchar(200) using user_password::varchar(200);

alter table users
    add status varchar(25) default 'ACTIVE';

alter table users
    add user_login varchar(100) default 'default_login' not null;


--GRADES

alter table grades
    add status varchar(25) default 'ACTIVE';


--items_category

alter table item_categories
    add creation_date timestamp(6) default CURRENT_TIMESTAMP(6);

alter table item_categories
    add modification_date timestamp(6) default CURRENT_TIMESTAMP(6);

alter table item_categories
    add status varchar(25) default 'ACTIVE';


--items

alter table items
    add status varchar(25) default 'ACTIVE';


--items_leased
alter table items_leased
    add status varchar(25) default 'ACTIVE';

--locations

alter table locations
    add status varchar(25) default 'ACTIVE';


--sub_item_types
alter table sub_item_types
    add creation_date timestamp(6) default CURRENT_TIMESTAMP(6);

alter table sub_item_types
    add modification_date timestamp(6) default CURRENT_TIMESTAMP(6);

alter table sub_item_types
    add status varchar(25) default 'ACTIVE';


--user_roles

alter table user_roles
    add creation_date timestamp(6) default CURRENT_TIMESTAMP(6);

alter table user_roles
    add modification_date timestamp(6) default CURRENT_TIMESTAMP(6);