alter table accidents add type_id
int not null references accident_types(id);