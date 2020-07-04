CREATE TABLE amount_type (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(5) DEFAULT NULL,
  PRIMARY KEY (id)
);
--;;
insert  into amount_type(id,name) values
(1,'g'),
(2,'kg'),
(3,'ml'),
(4,'l'),
(5,'tsp'),
(6,'cups'),
(7,'pcs'),
(8,'tbs');
