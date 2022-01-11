#!/usr/bin/env python
# -*- coding: utf-8 -*-

import pymysql
import json

# The cart ID which belongs to this current user session
cartID = None

# Prompt for username and password, connect to database
def connectToSQLDB():
    success = False
    while(not success):
        try:
            username = input("Enter your mySQL username: ")
            password = input("Enter your mySQL password: ")

            cnx = pymysql.connect(host='localhost', user=username, password=password,
                        db='finalProject', charset='utf8mb4', cursorclass=pymysql.cursors.DictCursor)
            success = True
            return cnx
        except:
            print("Failed to connect, try again.\n")
            success = False

# Handles the registration process for a user and sends their information into users table in DB
def registerUser(cnx, cur):
    print("\nWelcome to FoodApp. Before building your order, we'll need to collect some user information to create your profile.\n")
    registered = False
    while (not registered):
        try:
            firstName = input("Enter your first name: ")
            lastName = input("Enter your last name: ")
            streetAddress = input("Enter your street address: ")
            zipCode = input("Enter your ZIP code: ")

            register_stmt = "SELECT register_user(%s, %s, %s, %s)"
            cur.execute(register_stmt, (firstName, lastName, streetAddress, zipCode))
            userID = cur.fetchall()
            registered = True
            print("\nSuccesfully registered! Begin building your order now.\n")
            cnx.commit()
            return userID
        except:
            print("Failed to register! Please try again.\n")

# Grab a list of names of the available restaurants along with their type of cuisine
def fetchAvailableRestaurants(cnx, cur):
    get_rName_query = "SELECT rName, cuisineType FROM restaurants"
    cur.execute(get_rName_query)
    restaurant_list = cur.fetchall()
    return restaurant_list

# Handle the main view of the app, which allows for choosing to view a restaurants menu or go to the user's cart
def mainPage(cnx, cur, restaurantNames):
    print("\nType a restaurant name to view their menu, 'cart' to view your current order,\n    'profile' to edit registration details, or 'exit' to close the app.\n")
    print("----------------")
    for entity in restaurantNames:
        print(entity['rName'])
        print("     " + entity['cuisineType'])
        # print("\n")
    print("----------------")
    # print("Cart")

    selection = input("Navigate to: ")
    nameList = []
    for item in restaurantNames:
        nameList.append(item['rName'])

    invalidSelection = True
    while(invalidSelection):
        if selection == 'cart' or selection == 'exit' or selection == 'profile' or selection in nameList:
            invalidSelection = False
            return selection
        else:
            print("Invalid entry, try again.")
            selection = input("Navigate to: ")

def getMenu(cnx, cur, restaurant):
    query = "CALL get_menu(%s)"
    cur.execute(query, restaurant)
    return cur.fetchall()

def addToCart(cnx, cur, item, user):
    add_stmt = "CALL add_to_cart(%s, %s)"
    # try:
    cur.execute(add_stmt, (item, user))
    cnx.commit()
    print("Item added to cart!\n")
    # except:
        # print("Failed to add item to cart...\n")

def mealNameToID(meal, menu):
    for item in menu:
        if item['mName'] == meal:
            return item['mID']

def menuPage(cnx, cur, restaurant, uID):
    meals = getMenu(cnx, cur, restaurant)
    mealNameList = []

    print("\n" + restaurant + ": Online Menu")

    avg_price_stmt = "SELECT avg_menu_price(%s)"
    cur.execute(avg_price_stmt, restaurant)
    avg_price = list(cur.fetchall()[0].values())[0]
    print("Average price of a menu item: $" + str(avg_price))

    print("Type the name of any menu item to add it to your cart, or type 'exit' to return to the restaurant selection page.")
    print("------------")
    for meal in meals:
        print(meal['mName'] + " -- $" + str(meal['price']))
        mealNameList.append(meal['mName'])
    print("------------")

    ordering = True
    while(ordering):
        uinput = input("ADD: ")

        if uinput == "exit":
            # return to main selection screen
            ordering = False
            return "main"
        elif uinput in mealNameList:
            # add to cart
            mID = mealNameToID(uinput, meals)
            addToCart(cnx, cur, mID, uID)
        else:
            # unrecognized input
            print("Unrecognized input, please enter a valid option.\n")

# Fetch the cart items belonging to the current user
def getCartItems(cnx, cur, uID):
    cart_stmt = "CALL build_user_cart(%s)"
    cur.execute(cart_stmt, uID)
    items = cur.fetchall()
    return items

def cartPage(cnx, cur, uID):
    cItems = getCartItems(cnx, cur, uID)
    totalPrice = 0
    mealNames = []
    print("\nYour cart currently contains " + str(len(cItems)) + " items.")
    print("----------------")
    for item in cItems:
        mealNames.append(item['mName'])
        print(item['mName'] + " -- $" + str(item['price']))
        print("     [from " + item['restaurant'] + "]")
        totalPrice = totalPrice + int(item['price'])

    print("----------------")
    print("\nYour total is: $" + str(totalPrice) + "\n")

    print("Enter an item name to remove it from cart, 'checkout' to place your order, or 'exit' to return to the main screen.")
    noSelection = True
    while(noSelection):
        uinput = input("Enter here: ")
        if (uinput == "checkout"):
            # call checkout process
            checkout_query = "CALL user_checkout(%s)"
            cur.execute(checkout_query, uID)
            cnx.commit()
            print("\nYour order has been placed!\n")
            return "main"
        elif (uinput == "exit"):
            return "main"
        elif uinput in mealNames:
            for item in cItems:
                if (item['mName'] == uinput):
                    remove_query = "CALL remove_from_cart(%s, %s)"
                    cur.execute(remove_query, (item['mID'], uID))
                    cnx.commit()
                    return "cart" # will result in cartPage() being called again, refreshing the cart and total
        else:
            print("Unrecognized input, please try again.")


    return "main"

def getProfileInfo(cur, uID):
    profile_query = "SELECT firstName, lastName, streetAddress, zipCode FROM users WHERE uID = %s"
    cur.execute(profile_query, uID)
    profileInfo = cur.fetchall()[0]
    return profileInfo

def editPage(cnx, cur, uID):
    profileInfo = getProfileInfo(cur, uID)
    print("\nEnter a field name (i.e. 'first') to edit its value, or type 'exit' to return to main screen.\n(Changes are automatically saved)\n")
    print("------------")
    print("first: " + profileInfo['firstName'])
    print("last: " + profileInfo['lastName'])
    print("address: " + profileInfo['streetAddress'])
    print("zip: " + profileInfo['zipCode'])
    print("------------")
    while(True):
        uinput = input("EDIT: ")
        if (uinput == "first"):
            firstname_query = "UPDATE users SET firstName = %s WHERE uID = %s"
            try:
                cur.execute(firstname_query, (input("New first name: "), uID))
                cnx.commit()
                return "profile"
            except:
                print("Failed to update field, restart & try again...")
        elif (uinput == "last"):
            lastname_query = "UPDATE users SET lastName = %s WHERE uID = %s"
            try:
                cur.execute(lastname_query, (input("New last name: "), uID))
                cnx.commit()
                return "profile"
            except:
                print("Failed to update field, restart & try again...")
        elif(uinput == "address"):
            address_query = "UPDATE users SET streetAddress = %s WHERE uID = %s"
            try:
                cur.execute(address_query, (input("New address: "), uID))
                cnx.commit()
                return "profile"
            except:
                print("Failed to update field, restart & try again...")
        elif(uinput == "zip"):
            zip_query = "UPDATE users SET zipCode = %s WHERE uID = %s"
            try:
                cur.execute(zip_query, (input("New ZIP code: "), uID))
                cnx.commit()
                return "profile"
            except:
                print("Failed to update field, restart & try again...")
        elif(uinput == "exit"):
            return "main"
        else:
            print("Unrecognized input, please try again.")
        




# Establish connection to the running SQL database
cnx = connectToSQLDB()
# Establish cursor variable
cur = cnx.cursor()

# Begin registration process to create a new user object in the database
uID = list(registerUser(cnx, cur)[0].values())[0]

restaurantNames = fetchAvailableRestaurants(cnx, cur)

# main program loop
selection = "main"
while(True):
    if selection == "main":
        selection = mainPage(cnx, cur, restaurantNames)
    elif selection == "cart":
        selection = cartPage(cnx, cur, uID)
    elif selection == "profile":
        selection = editPage(cnx, cur, uID)
    elif selection == "exit":
        print("Closing app...")
        exit(0)
    else:
        selection = menuPage(cnx, cur, selection, uID)
