CREATE TABLE participates (
   id serial PRIMARY KEY,
   accident_id int not null REFERENCES accidents(id),
   rule_id int not null REFERENCES accident_rules(id),
   UNIQUE (accident_id, rule_id)
);