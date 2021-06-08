create table gp_product (
    id serial not null primary key,
    description varchar(50),
    sku varchar(25) not null,
    weight decimal(10,2) not null,
    height decimal(10,2) not null,
    width decimal(10,2) not null,
    depth decimal(25) not null,
    manufacturer varchar(25) not null,
    price decimal(10,2) not null
);