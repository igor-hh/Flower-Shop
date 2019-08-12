create table flowers (id  bigserial not null, name varchar(255), price float8, quantity int4, primary key (id));
create table order_items (id  bigserial not null, quantity int4, flower_id int8, order_id int8, primary key (id));
create table orders (id  bigserial not null, close_date timestamp, creation_date timestamp, status varchar(255), total_price float8, user_id int8, primary key (id));
create table user_role (user_id int8 not null, roles varchar(255));
create table users (id  bigserial not null, active boolean, address varchar(255), balance float8, discount int4, full_name varchar(255), login varchar(255), password varchar(255), phone varchar(255), primary key (id));
alter table order_items add constraint FKgcld7l1l15xokg344c58xnur2 foreign key (flower_id) references flowers;
alter table order_items add constraint FKbioxgbv59vetrxe0ejfubep1w foreign key (order_id) references orders;
alter table orders add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;
alter table user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;