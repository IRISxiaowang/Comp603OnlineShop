# Comp603OnlineShop

Xiao Wang 21129395 

I completed the project2 individually by myself.

## Existing user
Buyer    username: buyer1   password: 1111
Seller   username: seller1  password: 1111

## Workflow
### Login screen
Existing users can login here. Buyers will automatically go to Buyer Menu and seller will go to Seller Menu after log in.

### Registration view
Register a new user. You can choose the new user will be a buyer or seller.

### Buyer menu
In the buyer menu, all items on sale are displayed. (Sold items are hidden)

**Buy:** add item into purchase history (Transaction records) and transfer payment from buyer balance to seller balance.

**Search:** Filter products from product table if productName contains search string.

**Recharge:** increase balance from by using a credit card. (This is a mock credit card payment page and will always succeed.)

**Purchase history:** Shows all items the current buyer has purchased. 

### Seller menu
In the Seller menu, all items currently on sale are displayed. The seller can better decide what price they should sell their items.

**Sell:** All a new Product to sell. This adds the item into the products table.

**Search:** Filter products from product table if productName contains search string.

**Sell history:** Shows all the items that the current seller has sold.

## Marking
### GUI
Multiple GUI pages were created. 
The user can quit at any stage, or go back to the main menu/login menu easily. 
Error messages are displayed if input does not meet requirement.

### Database
All database related code can be found in Database.java

There are 3 database tables: ShopUser, Product, ProductTransaction

**ShopUser:** Contains information on Users, including username, password, Role(buyer/seller) etc.

**Product:** Contains information on all existing products, including ProductName, productId, price, sellerId etc.

**ProductTransaction**: Contains transaction for selling a product. Include sellerId, buyerId, productId, date etc.

Products on sale are products NOT IN the productTransaction table.
Buyer, Seller and product info is obtained by JOIN ShopUser and Product tables with ProductTransaction table, matching userId and productId.

There are 9 methods that uses DB query/read.
There are 5 methods that uses DB update/write.

### Software Functionality and Usability
Database is setup automatically at the beginning. Database is automatically created if none exists.

Exceptions are handled at code level and does not crash the application.
User inputs that does not meet the requirement are notified by a message on the GUI.

**Major functions implemented:**
Common: Login, Registration, 
Buyers: List products on sale, buy products, Recharge account balance, Purchase history, search products,
Sellers: List products on sale, sell products, view sales history, search products.

### Software Design & Implementation
There are 17 classes in the product and 1 test class. The project reuse some basic clases from the first Project (such as the User, Product classes) but the majority of the code are new.

#### OOP concepts:
All OOP concepts are used. Some examples are provided here:

**Abstraction:** ShopModel: Database and currentUser are private, and functions used for other classes are public. ShopView: Some elements used by other classes (such as TextFields) are public, others (such as buttons and JLabels) are private.

**Encapsulation:** ShopView (any elements that are used by outside classes are private). ShopModel has private currentUser, which can only be set by Login method and can be get by a getCurrentUser() method. The "info" JLabel used to display messages in many views are private, but an function updateMessage is provided to set the value of the message.

**Inheritance:** Both UserBuyer and UserSeller inherits User class.

**Polymorphism:** UserBuyer and UserSeller are treated as User where it makes sense, demonstrating the use of Polymorphism.


#### MVC
MVC is used in this project. 

**Model:** ShopModel class. Provides functions to get and set data in the model. Since it uses a Database innately, all data reads and writes are redirected to the Database Class. Database is private and only interacted by this classes and no other classes.

**View:** GUIs are written in the different View classes, such as: ShopView, MainBuyMenuView, MainSellMenuView (any classes with "View" post-fix) etc. Views get its data from the Model. When DB are updated, controller will tell it to re-get data from the Model.

**Controls:** ShopController classes contains all the controller functions. It handles events triggered in the GUIs. It updates error messages in the GUI. It calls the ShopModel to read and write data. It also tells the GUI to update its data when DB has been updated.

#### Version control
Classes and methods are commented.
The project is hosted in Github. Vision control is used (git).
This assignment is done individually, I only branched and merged to show I know how to do it.

*Database files and some netbeans config files are not tracked in git, and are added to gitignore. They will be included in the .zip submission.*


#### Tests
Unit test framework was used. Database was the class used for the unit tests. 
14 tests are written. Each tests contains one or more test cases.
Mock database is used for test to not effect the production database. 
Mock database is cleared at the beginning of the tests, with controlled test data put in to ensure unit tests are done in a controlled manner. All functions in the Database class is tested.

