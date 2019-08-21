create table flowers (id  bigserial not null, name varchar(255), price numeric(19, 2), quantity int4, primary key (id));
create table order_items (id  bigserial not null, quantity int4, flower_id int8, order_id int8, primary key (id));
create table orders (id  bigserial not null, close_date timestamp, creation_date timestamp, status varchar(255), total_price numeric(19, 2), user_id int8, primary key (id));
create table user_role (user_id int8 not null, roles varchar(255));
create table users (id  bigserial not null, active boolean not null, address varchar(255), balance numeric(19, 2), discount int4, full_name varchar(255), login varchar(255), password varchar(255), phone varchar(255), primary key (id));
alter table order_items add constraint flower_id_fk foreign key (flower_id) references flowers;
alter table order_items add constraint order_id_fk foreign key (order_id) references orders;
alter table orders add constraint uder_id_fk foreign key (user_id) references users;
alter table user_role add constraint user_id_fk foreign key (user_id) references users;