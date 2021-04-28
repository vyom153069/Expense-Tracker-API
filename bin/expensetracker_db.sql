drop database expensetrackerdb;
drop user expensetracker;
create user expensetracker with password='root';
create database expensetrackerdb with template=template0 owner=expensetracker;
\connect expensetrackerdb;

alter default privileges grant all on tables to expensetracker;
alter default privileges grant all on sequences to expensetracker;

create table et_user(
    user_id integer primary key not null,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    email varchar(30) not null,
    password text not null
);

create table et_categories(
    category_id integer primary key not null,
    user_id integer not null,
    title varchar(20) not null,
    description varchar(50) not null
);

alter table et_categories add constraint cat_users_fk
foreign key (user_id) references et_user(user_id);

create table et_transaction(
    transaction_id integer primary key not null,
    category_id integer not null,
    user_id integer not null,
    amount numeric(10,2) not null,
    note varchar(50) not null,
    transaction_date bigint not null 
);

alter table et_transaction add constraint trans_cat_fk
foreign key (category_id) references et_categories(category_id)

alter table et_transaction add constraint trans_users_fk
foreign key (user_id) references et_user(user_id);


create sequence et_user_seq increment 1 start 1;
create sequence et_categories_seq increment 1 start 1;
create sequence et_transaction_seq increment 1 start 1000;