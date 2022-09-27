create sequence if not exists location_id_seq;

alter sequence location_id_seq owner to postgres;

create sequence if not exists rental_items_id_seq;

alter sequence rental_items_id_seq owner to postgres;

create sequence if not exists item_type_id_seq
    as integer;

alter sequence item_type_id_seq owner to postgres;

create sequence if not exists item_leased_id_seq;

alter sequence item_leased_id_seq owner to postgres;

create sequence if not exists grade_id_seq;

alter sequence grade_id_seq owner to postgres;

create sequence if not exists cities_id_seq
    as integer;

alter sequence cities_id_seq owner to postgres;

create sequence if not exists sub_item_types_id_seq
    as integer;

alter sequence sub_item_types_id_seq owner to postgres;

create table if not exists countries
(
    id                integer      default nextval('cities_id_seq'::regclass) not null
        constraint cities_pk
            primary key,
    country_name      varchar(30)                                             not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'::character varying
);

alter table countries
    owner to postgres;

alter sequence cities_id_seq owned by countries.id;

create unique index if not exists cities_city_name_uindex
    on countries (country_name);

create unique index if not exists cities_id_uindex
    on countries (id);

create table if not exists locations
(
    id                bigint       default nextval('location_id_seq'::regclass) not null
        constraint location_pk
            primary key,
    postal_code       varchar(6),
    name              varchar(255),
    description       varchar,
    country_id        integer
        constraint location_cities_id_fk
            references countries,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'::character varying
);

alter table locations
    owner to postgres;

alter sequence location_id_seq owned by locations.id;

create table if not exists users
(
    id                bigserial
        constraint users_pk
            primary key,
    username          varchar(25)                               not null,
    user_password     varchar(200)                              not null,
    location_id       integer
        constraint users_location_id_fk
            references locations
            on update cascade on delete cascade,
    location_details  varchar,
    phone_number      varchar(15),
    mobile_number     varchar(15),
    email             varchar(255)                              not null,
    registration_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'::character varying,
    user_login        varchar(100)                              not null
);

alter table users
    owner to postgres;

create index if not exists  users_email_index
    on users (email);

create index if not exists users_username_index
    on users (username);

create unique index if not exists users_user_login_uindex
    on users (user_login);

create table item_categories
(
    id                integer      default nextval('sub_item_types_id_seq'::regclass) not null
        constraint sub_item_types_pkey
            primary key,
    category_name     varchar(35),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'::character varying
);

alter table item_categories
    owner to postgres;

alter sequence sub_item_types_id_seq owned by item_categories.id;

create table if not exists sub_item_types
(
    id                integer      default nextval('item_type_id_seq'::regclass) not null,
    sub_category_name varchar(100),
    category_id       integer
        constraint sub_item_categories_item_categories_id_fk
            references item_categories,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'::character varying
);

alter table sub_item_types
    owner to postgres;

alter sequence item_type_id_seq owned by sub_item_types.id;

create unique index if not exists sub_item_types_id_uindex
    on sub_item_types (id);

create unique index if not exists item_type_id_uindex
    on sub_item_types (id);

create index if not exists item_type_type_name_index
    on sub_item_types (sub_category_name);

create table if not exists items
(
    id                bigint       default nextval('rental_items_id_seq'::regclass) not null
        constraint rental_items_pk
            primary key,
    item_name         varchar(255),
    item_type_id      integer
        constraint rental_items_item_type_id_fk
            references sub_item_types (id),
    location_id       integer
        constraint rental_items_location_id_fk
            references locations,
    item_location     varchar,
    description       varchar,
    owner_id          integer
        constraint rental_items_users_id_fk
            references users,
    price_per_hour    numeric(8, 2),
    available         boolean,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                     not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)                     not null,
    status            varchar(25)  default 'ACTIVE'::character varying
);

alter table items
    owner to postgres;

alter sequence rental_items_id_seq owned by items.id;

create index if not exists items_item_name_index
    on items (item_name);

create index if not exists items_item_location_index
    on items (item_location);

create unique index if not exists rental_items_id_uindex
    on items (id);

create table if not exists items_leased
(
    id                        bigint       default nextval('item_leased_id_seq'::regclass) not null
        constraint item_leased_pk
            primary key,
    item_id                   bigint
        constraint item_leased_rental_items_id_fk
            references items,
    renter_id                 bigint
        constraint item_leased_users_id_fk
            references users,
    time_from                 timestamp(6),
    time_to                   timestamp(6),
    price_per_hour            numeric(8, 2),
    discount                  numeric(8, 2),
    fee                       numeric(8, 2),
    price_total               numeric(8, 2),
    rentier_grade_description varchar,
    renter_grade_description  varchar,
    creation_date             timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date         timestamp(6) default CURRENT_TIMESTAMP(6),
    status                    varchar(25)  default 'ACTIVE'::character varying
);

alter table items_leased
    owner to postgres;

alter sequence item_leased_id_seq owned by items_leased.id;

create unique index if not exists item_leased_id_uindex
    on items_leased (id);

create table if not exists grades
(
    id                bigint       default nextval('grade_id_seq'::regclass) not null
        constraint grade_pk
            primary key,
    item_leased_id    integer
        constraint grade_item_leased_id_fk
            references items_leased,
    grade_category_id integer,
    user_from_id      integer
        constraint grade_users_id_fk
            references users,
    user_to_id        integer
        constraint grade_users_id_fk_2
            references users,
    grade             numeric(3, 1),
    description       varchar,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'::character varying
);

alter table grades
    owner to postgres;

alter sequence grade_id_seq owned by grades.id;

create index if not exists grade_grade_index
    on grades (grade);

create unique index grade_id_uindex
    on grades (id);

create table if not exists roles
(
    id                bigserial
        constraint roles_pk
            primary key,
    name              varchar(100),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    status            varchar(25)  default 'ACTIVE'::character varying
);

alter table  roles
    owner to postgres;

create unique index if not exists roles_id_uindex
    on roles (id);

create table if not exists user_roles
(
    user_id           bigint
        constraint user_roles_users_id_fk
            references users
            on update cascade on delete cascade,
    role_id           bigint
        constraint user_roles_roles_id_fk
            references roles,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)
);

alter table user_roles
    owner to postgres;


