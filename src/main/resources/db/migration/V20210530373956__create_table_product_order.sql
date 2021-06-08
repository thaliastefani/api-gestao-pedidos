create table gp_product_order (
	order_id int not null references gp_order,
	product_id int not null references gp_product,

    primary key (order_id, product_id)
)