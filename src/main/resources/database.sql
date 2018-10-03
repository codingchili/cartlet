DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS order_product;
DROP TABLE IF EXISTS orderstatus;
DROP TABLE IF EXISTS role;

CREATE TABLE `webshop`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `zip` VARCHAR(12) NOT NULL,
  `street` VARCHAR(32) NOT NULL,
  `role` INT DEFAULT 1,
  `password` VARCHAR(256) NOT NULL,
  `salt` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));
  
  CREATE TABLE `webshop`.`cart` (
  `product` INT NOT NULL,
  `owner` INT NOT NULL,
  `count` INT NOT NULL,
  PRIMARY KEY (`product`, `owner`));

CREATE TABLE `webshop`.`product_category` (
  `product` INT NOT NULL,
  `category` INT NOT NULL,
  PRIMARY KEY (`product`, `category`));

CREATE TABLE `webshop`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));

CREATE TABLE `webshop`.`image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product` INT NOT NULL,
  `data` MEDIUMBLOB NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `product_UNIQUE` (`product` ASC));

CREATE TABLE `webshop`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `count` INT NOT NULL DEFAULT 0,
  `cost` INT NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `webshop`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `owner` INT NOT NULL,
  `created` VARCHAR(24) NOT NULL,
  `status` INT NOT NULL,
  `changed` VARCHAR(24) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `webshop`.`order_product` (
  `order` INT NOT NULL,
  `product` INT NOT NULL,
  `count` INT NOT NULL);

CREATE TABLE `webshop`.`orderstatus` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `webshop`.`role` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));



INSERT INTO product (name, description, count, cost) VALUES ('Python for Snakes', 'Everything you need to know about slithering.', 23, 199);
INSERT INTO product (name, description, count, cost) VALUES ('C# for Sharks', 'Get sharper with C# for sharks!', 8, 325);
INSERT INTO product (name, description, count, cost) VALUES ('A-Z with C', 'Learn the whole alphabet.', 16, 399);
INSERT INTO product (name, description, count, cost) VALUES ('C++: True Story', 'Bitwise operations with C++, ^A + ^B = ?', 12, 699);
INSERT INTO product (name, description, count, cost) VALUES ('Delphi Magics', 'Become the wizard you always wanted to be.', 42, 299);
INSERT INTO product (name, description, count, cost) VALUES ('Ruby for Diamonds', 'Data mining with Ruby.', 9,799);
INSERT INTO product (name, description, count, cost) VALUES ('F# for Sharks', 'Get effing sharp you shark!', 3, 249);
INSERT INTO product (name, description, count, cost) VALUES ('JavaScript: vars & stuff!', 'The basics of the basics.', 33, 179);
INSERT INTO product (name, description, count, cost) VALUES ('Get ready with R', 'Nothing says ready like R, get it now.', 2, 129);
INSERT INTO product (name, description, count, cost) VALUES ('Java for Narwhals', 'Narwhal, do you even Java?', 76, 299);
INSERT INTO product (name, description, count, cost) VALUES ('C++ for Dummies', 'The first book in the series.', 11, 299);
INSERT INTO product (name, description, count, cost) VALUES ('C++ for Beginners', 'The second book in the series.', 43, 399);
INSERT INTO product (name, description, count, cost) VALUES ('C++ for Apprentices', 'The third book in the series.', 212, 499);
INSERT INTO product (name, description, count, cost) VALUES ('C++ for Adepts', 'The fourth book in the series.', 376, 699);
INSERT INTO product (name, description, count, cost) VALUES ('C++ for Masters', 'The fifth book in the series.', 31337, 1024);

INSERT INTO category (name) VALUES ('C');
INSERT INTO category (name) VALUES ('C#');
INSERT INTO category (name) VALUES ('C++');
INSERT INTO category (name) VALUES ('Delphi');
INSERT INTO category (name) VALUES ('F#');
INSERT INTO category (name) VALUES ('Java');
INSERT INTO category (name) VALUES ('JavaScript');
INSERT INTO category (name) VALUES ('Python');
INSERT INTO category (name) VALUES ('R');
INSERT INTO category (name) VALUES ('Ruby');

INSERT INTO product_category (product, category) VALUES (1,8);
INSERT INTO product_category (product, category) VALUES (2, 2);
INSERT INTO product_category (product, category) VALUES (3, 1);
INSERT INTO product_category (product, category) VALUES (4, 3);
INSERT INTO product_category (product, category) VALUES (5, 4);
INSERT INTO product_category (product, category) VALUES (6, 10);
INSERT INTO product_category (product, category) VALUES (7, 5);
INSERT INTO product_category (product, category) VALUES (8, 7);
INSERT INTO product_category (product, category) VALUES (9, 9);
INSERT INTO product_category (product, category) VALUES (10, 6);
INSERT INTO product_category (product, category) VALUES (11, 3);
INSERT INTO product_category (product, category) VALUES (12, 3);
INSERT INTO product_category (product, category) VALUES (13, 3);
INSERT INTO product_category (product, category) VALUES (14, 3);
INSERT INTO product_category (product, category) VALUES (15, 3);

INSERT INTO orderstatus (name) VALUES ('Waiting');
INSERT INTO orderstatus (name) VALUES ('Approved');
INSERT INTO orderstatus (name) VALUES ('Processing');
INSERT INTO orderstatus (name) VALUES ('Shipped');

INSERT INTO role (id, name) VALUES (1, 'user');
INSERT INTO role (id, name) VALUES (2, 'employee');
INSERT INTO role (id, name) VALUES (3, 'administrator')