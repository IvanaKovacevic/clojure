CREATE TABLE recipe_type (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);
--;;
insert  into recipe_type(id,name) values
(1,'Breakfast'),
(2,'Lunch'),
(3,'Dinner'),
(4,'Desert'),
(5,'Snack'),
(6,'Coctail');
