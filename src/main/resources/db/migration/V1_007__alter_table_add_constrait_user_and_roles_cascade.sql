alter table user_roles
drop constraint user_roles_users_id_fk;

alter table user_roles
    add constraint user_roles_users_id_fk
        foreign key (user_id) references users
            on update cascade on delete cascade;

