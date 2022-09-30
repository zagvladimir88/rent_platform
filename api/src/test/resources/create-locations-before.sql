delete
from locations;
delete
from countries;

insert into countries (id, country_name, creation_date, modification_date, status)
values (1, 'Belarus', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 'ACTIVE');

insert into locations(id, postal_code, name, description, country_id, creation_date, modification_date, status)
values (1,'246028','Gomel','Gomelskaya oblast',1,CURRENT_TIMESTAMP(6),CURRENT_TIMESTAMP(6),'ACTIVE');


