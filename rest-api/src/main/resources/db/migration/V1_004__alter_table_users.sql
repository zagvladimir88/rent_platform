alter table users
    alter column status set default 'NOT_ACTIVE';

alter table images
drop constraint table_name_items_id_fk;

alter table images
    alter column item_id drop default;

drop sequence images_item_id_seq;

alter table images
    add constraint table_name_items_id_fk
        foreign key (item_id) references items;

