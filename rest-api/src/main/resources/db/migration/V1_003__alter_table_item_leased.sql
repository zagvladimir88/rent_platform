alter table items_leased
    alter column item_id set not null;

alter table items_leased
    alter column renter_id set not null;

alter table items_leased
    alter column time_from set default CURRENT_TIMESTAMP(6);

