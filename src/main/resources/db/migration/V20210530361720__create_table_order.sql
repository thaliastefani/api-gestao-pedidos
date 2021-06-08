create table gp_order (
    id serial not null primary key,
    customer_name varchar(50) not null,
    phone varchar(25) not null,
    value_products decimal(10,2) not null,
    discount_amount decimal(10,2) not null,
    amount decimal(10,2) not null
);