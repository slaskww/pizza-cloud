
delete from Pizza_Order_Pizzas;
delete from Pizza_Ingredients;
delete from Pizza;
delete from Pizza_Order;
delete from Ingredient;

insert into Ingredient(id, name, type) values('DC', 'Corn', 'DOUGH');
insert into Ingredient(id, name, type) values('CM', 'Mozarella', 'CHEESE');
insert into Ingredient(id, name, type) values('DW', 'Wheat', 'DOUGH');
insert into Ingredient(id, name, type) values('CC', 'Cheddar', 'CHEESE');
insert into Ingredient(id, name, type) values('MC', 'Chicken', 'MEAT');
insert into Ingredient(id, name, type) values('MF', 'Salmon', 'MEAT');
insert into Ingredient(id, name, type) values('MB', 'Beef', 'MEAT');
insert into Ingredient(id, name, type) values('VT', 'Tomato', 'VEGETABLE');
insert into Ingredient(id, name, type) values('VB', 'Basil', 'VEGETABLE');
insert into Ingredient(id, name, type) values('VP', 'Jalapeno', 'VEGETABLE');
insert into Ingredient(id, name, type) values('VO', 'Onion', 'VEGETABLE');
insert into Ingredient(id, name, type) values('VOR', 'Oregano', 'VEGETABLE');
insert into Ingredient(id, name, type) values('VM', 'Mushrooms', 'VEGETABLE');
insert into Ingredient(id, name, type) values('SG', 'Garlic sauce', 'SAUCE');
insert into Ingredient(id, name, type) values('ST', 'Tomato sauce', 'SAUCE');
insert into Ingredient(id, name, type) values('SB', 'Barbecue sauce', 'SAUCE');