create table if not exists countries
(
    id
                      bigserial
        constraint
            countries_pk
            primary
                key,
    country_name
                      varchar(30),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        ),
    modification_date timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        ),
    status            varchar(25)  default 'ACTIVE':: character varying
);

alter table countries
    owner to test;

create unique index if not exists countries_id_uindex
    on countries (id);


create table locations
(
    id                bigserial,
    postal_code       varchar(6),
    name              varchar(255),
    description       varchar,
    country_id        integer
        constraint locations_countries_id_fk
            references countries,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'
);

create unique index locations_id_uindex
    on locations (id);

alter table locations
    add constraint locations_pk
        primary key (id);

create table if not exists users
(
    id
                      bigserial
        constraint
            users_pk
            primary
                key,
    first_name
                      varchar(25)  not null,
    last_name         varchar(32)  not null,
    user_password     varchar(200) not null,
    location_id       integer
        constraint users_location_id_fk
            references locations
            on update cascade
            on delete cascade,
    location_details  varchar,
    mobile_number     varchar(15),
    email             varchar(255) not null,
    registration_date timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        )                          not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        ),
    modification_date timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        ),
    status            varchar(25)  default 'ACTIVE':: character varying,
    user_login        varchar(100) not null
);

alter table users
    owner to test;

create index if not exists users_email_index
    on users (email);

create index if not exists users_first_name_index
    on users (first_name);

create unique index if not exists users_user_login_uindex
    on users (user_login);

alter table users
    add activation_code varchar(40);

create table categories
(
    id                smallserial
        constraint categories_pk
            primary key,
    category_name     varchar(35) not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'
);


create table sub_categories
(
    id                bigserial
        constraint sub_categories_pk
            primary key,
    sub_category_name varchar(100) not null,
    category_id       bigserial
        constraint sub_categories_categories_id_fk
            references categories (id),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'
);



create table items
(
    id                bigserial,
    item_name         varchar(255) not null,
    item_type_id      bigint,
    location_id       bigint,
    item_location     varchar,
    description       varchar,
    owner_id          bigint,
    price_per_hour    numeric(8, 2),
    available         BOOLEAN      default true,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'
);

create unique index items_id_uindex
    on items (id);

alter table items
    add constraint items_pk
        primary key (id);

create table items_leased
(
    id                        bigserial,
    item_id                   bigint
        constraint items_leased_items_id_fk
            references items,
    renter_id                 bigint
        constraint items_leased_users_id_fk
            references users,
    time_from                 timestamp(6),
    time_to                   timestamp(6),
    price_per_day            numeric(8, 2),
    discount                  numeric(8, 2),
    price_total               numeric(8, 2),
    creation_date             timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date         timestamp(6) default CURRENT_TIMESTAMP(6),
    status                    varchar(25)  default 'ACTIVE'
);

create unique index items_leased_id_uindex
    on items_leased (id);

alter table items_leased
    add constraint items_leased_pk
        primary key (id);


create table grades
(
    id                bigserial,
    item_id           bigint
        constraint grades_items_leased_id_fk
            references items_leased,
    user_id           bigint
        constraint grades_users_id_fk
            references users,
    grade             numeric(3, 1),
    description       varchar,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'
);

create unique index grades_id_uindex
    on grades (id);

alter table grades
    add constraint grades_pk
        primary key (id);

create table if not exists roles
(
    id
                      bigserial
        constraint
            roles_pk
            primary
                key,
    name
                      varchar(100),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        ) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        ) not null,
    status            varchar(25)  default 'ACTIVE':: character varying
);

alter table roles
    owner to test;

create unique index if not exists roles_id_uindex
    on roles (id);

create table if not exists user_roles
(
    user_id
                      bigint
        constraint
            user_roles_users_id_fk
            references
                users
            on
                update
                cascade
            on
                delete
                cascade,
    role_id
                      bigint
        constraint
            user_roles_roles_id_fk
            references
                roles,
    creation_date
                      timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        ),
    modification_date timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        )
);

alter table user_roles
    owner to test;

create table if not exists images
(
    id
                      bigserial
        constraint
            images_pk
            primary
                key
        constraint
            table_name_items_id_fk
            references
                items,
    item_id
                      bigserial,
    link
                      varchar(64) not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        ),
    modification_date timestamp(6) default CURRENT_TIMESTAMP
        (
            6
        ),
    status            varchar(25)  default 'ACTIVE':: character varying
);

alter table images
    owner to test;

create unique index if not exists images_id_uindex
    on images (id);


