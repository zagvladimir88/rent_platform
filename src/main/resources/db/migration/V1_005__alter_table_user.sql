alter table users
    alter column user_login drop default;

create unique index users_user_login_uindex
    on users (user_login);