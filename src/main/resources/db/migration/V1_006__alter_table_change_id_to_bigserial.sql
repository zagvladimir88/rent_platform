
--grades
alter sequence grade_id_seq as bigint;

alter table grades
alter column id type bigint using id::bigint;

--items
alter sequence rental_items_id_seq as bigint;

alter table items
alter column id type bigint using id::bigint;

--tems_leased
alter sequence item_leased_id_seq as bigint;

alter table items_leased
alter column id type bigint using id::bigint;

--locations
alter sequence location_id_seq as bigint;

alter table locations
alter column id type bigint using id::bigint;


--users
alter sequence users_id_seq as bigint;

alter table users
alter column id type bigint using id::bigint;

