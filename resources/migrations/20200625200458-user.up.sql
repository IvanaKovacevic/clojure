CREATE TABLE IF NOT EXISTS user (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  surname varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  PRIMARY KEY (id)
);
--;;
insert into user(id,name,surname,username,password) values
(1,'Ivana','Kovacevic','ivakov','ivakovaa'),
(2,'Marija','Mrvosevic','marmrv','marija1'),
(3,'Ana','Krstic','anakrs','ana123'),
(4,'Ana','Jacimovic','anajac','ana...321');
