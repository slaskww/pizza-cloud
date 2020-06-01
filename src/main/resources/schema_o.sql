CREATE TABLE if not exists  Ingredient(
id varchar(4) not null,
name varchar(35) not null,
type varchar (20) not null
);

CREATE TABLE if not exists Pizza(
id identity,
name varchar (50) not null,
createdAt timestamp not null
);

CREATE TABLE if not exists Pizza_Ingredients(
pizza bigint not null,
ingredient varchar (4) not null
);

alter table Pizza_Ingredients
add foreign key (pizza) references Pizza(id);

alter table Pizza_Ingredients
add foreign key (ingredient) references Ingredient(id);

CREATE TABLE if not exists Pizza_Order(
id identity,
deliveryName varchar (50) not null ,
deliveryStreet varchar (50) not null ,
deliveryCity varchar (50) not null ,
deliveryState varchar (50) not null ,
deliveryZip varchar (10) not null ,
ccNumber varchar (16) not null ,
ccExpiration varchar (5) not null ,
ccCVV varchar (3) not null ,
placedAt timestamp not null
);

CREATE TABLE if not exists Pizza_Order_Pizzas (
pizzaOrder bigint not null,
pizza bigint not null
);

ALTER table Pizza_Order_Pizzas
add foreign key (pizzaOrder) references Pizza_Order(id);

ALTER table Pizza_Order_Pizzas
add foreign key (pizza) references Pizza(id);