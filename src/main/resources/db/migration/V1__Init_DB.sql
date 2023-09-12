create sequence order_tb_seq start with 1 increment by 50;
create sequence service_tb_seq start with 1 increment by 50;

create table order_tb (
    date timestamp(6) not null,
    id bigint not null,
    service_id bigint not null,
    usr_username varchar(255) not null,
    primary key (id));

create table service_tb (
    id bigint not null,
    description varchar(255) not null,
    name varchar(255) not null,
    primary key (id));

create table user_tb (
    role smallint not null check (role between 0 and 1),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (username));

alter table if exists order_tb
    add constraint order_service_fk foreign key (service_id) references service_tb;

alter table if exists order_tb
    add constraint order_user_fk foreign key (usr_username) references user_tb;
