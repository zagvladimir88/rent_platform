create table if not exists refreshtoken
(
    id           bigserial
        constraint refreshtoken_pk
        primary key,
    user_id      bigint,
    token        varchar,
    "expiryDate" timestamp
);

create unique index refreshtoken_id_uindex
    on refreshtoken (id);

create unique index refreshtoken_token_uindex
    on refreshtoken (token);