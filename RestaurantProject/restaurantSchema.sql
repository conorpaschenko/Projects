DROP DATABASE IF EXISTS finalProject;
CREATE SCHEMA IF NOT EXISTS finalProject;
use finalProject;

# A category system for restaurants based on the type of cuisine (i.e. American, Chinese, Italian, etc.)
CREATE TABLE IF NOT EXISTS cuisineType (
    name varchar(50) NOT NULL PRIMARY KEY
);

# Inserting some different cuisine types
INSERT INTO cuisineType(name) VALUES
	('American'), ('Chinese'), ('Italian'), ('Greek');


# A restaurant which offers food to be ordered
CREATE TABLE IF NOT EXISTS restaurants (
    rName varchar(50) NOT NULL PRIMARY KEY,
    phoneNum varchar(10) NOT NULL,
    cuisineType varchar(50),
    CONSTRAINT fk_cType FOREIGN KEY (cuisineType) REFERENCES cuisineType (name) 
		ON DELETE SET NULL ON UPDATE CASCADE
);

INSERT INTO restaurants (rName, phoneNum, cuisineType) VALUES
	("Bob's Diner", "9324450101", "American"), 
    ("The Wok", "9220003030", "Chinese"), 
    ("Al Dente", "9000101220", "Italian"),
    ("Poppa's Pizza", "9000101220", "Italian"), 
    ("GyWhoa", "1234567890", "Greek"),
    ("Burgers!", "0987654321", "American");

# A user of the food app (must register before ordering)
CREATE TABLE IF NOT EXISTS users (
	uID INT auto_increment PRIMARY KEY,
    firstName varchar(30) NOT NULL,
    lastName varchar(30) NOT NULL,
    streetAddress varchar(80) NOT NULL,
    zipCode varchar(5) NOT NULL
);

# A meal entity which belongs to a singular restaurant
CREATE TABLE IF NOT EXISTS meals (
	mID INT auto_increment PRIMARY KEY,
    mName varchar(40) NOT NULL,
    price int NOT NULL,
    restaurant varchar(50) NOT NULL,
    CONSTRAINT fk_restaurant FOREIGN KEY (restaurant) REFERENCES restaurants (rName)
		ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO meals (mName, price, restaurant) VALUES
	("Steak Dinner" , 15, "Bob's Diner"), ("Large Cobb Salad", 11, "Bob's Diner"), ("Eggs and Bacon", 7, "Bob's Diner"), ("Pancakes", 6, "Bob's Diner"), ("Chicken Tenders", 8, "Bob's Diner"), ("Mozzarella Sticks", 8, "Bob's Diner"),
    ("Fried Rice" , 4, "The Wok"), ("Chicken & Broccoli", 10, "The Wok"), ("Sesame Chicken", 9, "The Wok"), ("Crab Rangoons", 5, "The Wok"),
    ("Spaghetti with Meatballs", 11, "Al Dente"), ("Lasagna", 12, "Al Dente"), ("Chicken Parmesean", 15, "Al Dente"), ("Veal", 18, "Al Dente"), 
    ("Medium Pizza", 14, "Poppa's Pizza"), ("Large Pizza", 18, "Poppa's Pizza"), ("Mozzarella Sticks", 6, "Poppa's Pizza"), ("Buffalo Wings", 10, "Poppa's Pizza"),
    ("Lamb Gyro", 12, "GyWhoa"), ("Beef Gyro", 10, "GyWhoa"), ("Chicken Gyro", 9, "GyWhoa"),
    ("Double Cheeseburger", 8, "Burgers!"), ("Rodeo Burger", 7, "Burgers!"), ("Fries", 3, "Burgers!"), ("Kid's Meal", 5, "Burgers!");


# 1 cart per user
CREATE TABLE IF NOT EXISTS activeCarts (
	cID INT auto_increment PRIMARY KEY,
    uID INT UNIQUE NOT NULL,
    CONSTRAINT fk_userID FOREIGN KEY (uID) REFERENCES users (uID)
		ON DELETE CASCADE ON UPDATE CASCADE
);

# the meals which are in multiple users' carts designated by the activeCart foreign key
CREATE TABLE IF NOT EXISTS cartItems (
	itemID INT auto_increment PRIMARY KEY,
	activeCart INT NOT NULL,
    CONSTRAINT fk_activeCart FOREIGN KEY (activeCart) REFERENCES activeCarts (cID)
		ON DELETE CASCADE ON UPDATE CASCADE,
    meal INT NOT NULL,
    CONSTRAINT fk_meal FOREIGN KEY (meal) REFERENCES meals (mID)
		ON DELETE CASCADE ON UPDATE CASCADE
);

# once a user checks out their cart, the transaction is recorded here
CREATE TABLE IF NOT EXISTS orderHistory (
	oID INT auto_increment PRIMARY KEY,
    userID INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (userID) REFERENCES users (uID) 
		ON DELETE CASCADE ON UPDATE CASCADE,
    numOrdered INT NOT NULL
);

DELIMITER ;;
DROP PROCEDURE IF EXISTS get_menu;;
# Grab all of the meal items corresponding to a given restaurant to build its 'menu'
CREATE PROCEDURE get_menu(restaurant_name varchar(50))
	BEGIN
		SELECT mID, mName, price FROM meals WHERE restaurant_name = restaurant;
    END ;;

DROP FUNCTION IF EXISTS avg_menu_price;;
# Find the average price of a given restaurant's menu items rounded to the nearest integer
CREATE FUNCTION avg_menu_price(restaurant_name varchar(50))
	RETURNS int
    READS SQL DATA
    BEGIN
		DECLARE averagePrice int;
        SELECT ROUND(AVG(price), 0) into averagePrice FROM meals WHERE restaurant_name = restaurant;
        RETURN averagePrice;
    END;;


DROP FUNCTION IF EXISTS register_user;;
# Create a new user with the given information in the users table, and return its new unique uID.
CREATE FUNCTION register_user(fName varchar(30), lName varchar(30), sAddr varchar(80), zip varchar(5))
	RETURNS int
    MODIFIES SQL DATA
    READS SQL DATA
	BEGIN
		DECLARE newID int;
		INSERT INTO users (firstName, lastName, streetAddress, zipCode) VALUES (fName, lName, sAddr, zip);
        SELECT uID INTO newID FROM users ORDER BY uID DESC LIMIT 1;
        INSERT INTO activeCarts (uID) VALUES (newID);
        RETURN newID;
    END;;

DROP PROCEDURE IF EXISTS add_to_cart;;
# Add the specified meal item into the given user's cart
CREATE PROCEDURE add_to_cart(mealID int, userID int)
	BEGIN
		DECLARE cart int;
        SELECT cID INTO cart FROM activeCarts WHERE userID = uID LIMIT 1;
        INSERT INTO cartItems (activeCart, meal) VALUES (cart, mealID);
    END;;
    
DROP PROCEDURE IF EXISTS remove_from_cart;;
CREATE PROCEDURE remove_from_cart(mealID int, userID int)
	BEGIN
		DECLARE cart int;
        SELECT cID INTO cart FROM activeCarts WHERE userID = uID LIMIT 1;
        DELETE FROM cartItems WHERE activeCart = cart AND meal = mealID;
    END;;

DROP PROCEDURE IF EXISTS build_user_cart;;
# Grab all of the cart items belonging to the provided user
CREATE PROCEDURE build_user_cart(userID int)
	BEGIN
		DECLARE cart int;
        SELECT cID INTO cart FROM activeCarts WHERE userID = uID LIMIT 1;
        SELECT mID, mName, price, restaurant FROM cartItems
			JOIN meals ON cartItems.meal = meals.mID WHERE activeCart = cart;
    END;; 
    
DROP PROCEDURE IF EXISTS user_checkout;;
# On checkout: remove user's cart items, and record into order history
CREATE PROCEDURE user_checkout(id int)
	BEGIN
		DECLARE cart int;
        DECLARE itemsOrdered int;
        SELECT cID INTO cart FROM activeCarts WHERE uID = id LIMIT 1;
        SELECT count(*) INTO itemsOrdered FROM cartItems WHERE activeCart = cart;
        
        INSERT INTO orderHistory (userID, numOrdered) VALUES (id, itemsOrdered);
        DELETE FROM activeCarts WHERE uID = id; # this will cascade to the cartItems table, no need to manually delete
        INSERT INTO activeCarts (uID) VALUES (id); # generate a new cart for next order
    END;;
