create table if not exists images
(
    id                bigserial
        constraint table_name_items_id_fk
            references items,
    item_id           bigserial   not null,
    image             varchar(64) not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'
);

create unique index if not exists images_id_uindex
    on images (id);

alter table images
    add constraint images_pk
        primary key (id);

