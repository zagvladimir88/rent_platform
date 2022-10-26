alter table users
    rename column username to first_name;

alter table users
    add last_name varchar(32) default 'last_name' not null;

alter table users
drop column phone_number;