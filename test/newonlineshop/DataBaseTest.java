/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package newonlineshop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xiaowang
 */
public class DataBaseTest {
    static final String MOCK_DB_URL = "jdbc:derby:OnlineShopDBMock;create=true;";
    static final String BUYER_NAME = "buyer";
    static final String SELLER_NAME = "seller";
    static final String PASSWORD = "12345";
    static final String PRODUCT_1 = "apple";
    static final String PRODUCT_2 = "Car";
    static final String SOLD_PRODUCT = "Paper";
    static int sellerId, buyerId, product1Id, product2Id, soldProductId;
    static DataBase db;
    
    public DataBaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException{
        // Setup mock DB
        db = new DataBase();
        db.dbsetup(MOCK_DB_URL);

        // Clear all tables
        db.executeSqlUpdate("delete from " + db.USER_TABLE_NAME);
        db.executeSqlUpdate("delete from " + db.PRODUCT_TABLE_NAME);
        db.executeSqlUpdate("delete from " + db.TRANSACTION_TABLE_NAME);
        
        // Setup buyer accoount
        db.executeSqlUpdate("insert into " + db.USER_TABLE_NAME + " (username, password, name, bankaccount, address, phone, email, role, balance) values ('"+BUYER_NAME+"', '"+ PASSWORD +"', 'BUYER', '1234', 'address', '123', '1@2', "+ Role.BUYER.toInt() +", 100)");

        // setup seller account
        db.executeSqlUpdate("insert into " + db.USER_TABLE_NAME + " (username, password, name, bankaccount, address, phone, email, role, balance) values ('"+SELLER_NAME+"', '"+ PASSWORD +"', 'SELLER', '1234', 'address', '123', '1@2', "+ Role.SELLER.toInt() +", 200)");
        
        // Get seller's ID
        try {
            ResultSet rs = db.executeSqlQuery("SELECT userId FROM " + db.USER_TABLE_NAME + " WHERE username='"+ SELLER_NAME + "'");
            if (rs.next()) {
                sellerId = rs.getInt("userId");
            }
            rs.close();
            rs = db.executeSqlQuery("SELECT userId FROM " + db.USER_TABLE_NAME + " WHERE username='"+ BUYER_NAME + "'");
            if (rs.next()) {
                buyerId = rs.getInt("userId");
            }
            rs.close();
            
            // setup 2 products
            db.executeSqlUpdate("INSERT INTO " + db.PRODUCT_TABLE_NAME
                    + " (userId, productName, price, description)"
                    + " VALUES (" + sellerId +", '" + PRODUCT_1 + "', 1.00, 'apple')");
            db.executeSqlUpdate("INSERT INTO " + db.PRODUCT_TABLE_NAME
                    + " (userId, productName, price, description)"
                    + " VALUES (" + sellerId +", '" + PRODUCT_2 + "', 1000.00, 'car')");
            db.executeSqlUpdate("INSERT INTO " + db.PRODUCT_TABLE_NAME
                    + " (userId, productName, price, description)"
                    + " VALUES (" + sellerId +", '" + SOLD_PRODUCT + "', 100.00, 'paper')");
            
            soldProductId = db.getProduct(SOLD_PRODUCT).productID;
            product1Id = db.getProduct(PRODUCT_1).productID;
            product2Id = db.getProduct(PRODUCT_2).productID;
            // Setup 1 transaction
            db.executeSqlUpdate("INSERT INTO " + db.TRANSACTION_TABLE_NAME
                + " VALUES (" + soldProductId +", " + buyerId + ", '12345', "  + sellerId + ", '12345', '2022-01-01')");
        } catch (SQLException e) {
            throw e;
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of dbsetup method, of class DataBase.
     */
    @Test
    public void testDbsetup() {
        System.out.println("dbsetup");
        DataBase instance = new DataBase();
        instance.dbsetup();
        try {
            Statement statement = db.conn.createStatement();
            statement.executeUpdate("insert into " + db.USER_TABLE_NAME 
                    + "(username, password, name, bankaccount, address, phone, email, role) values ('a', '1', 'A', '1', '', '1', '', 0)");
            
            statement.executeUpdate("INSERT INTO " + db.PRODUCT_TABLE_NAME 
                    + " (userId, productName, price, description) VALUES (0, 'apple', 1.00, 'apple')");
            
            statement.executeUpdate("INSERT INTO " + db.TRANSACTION_TABLE_NAME
                + " VALUES ( 0, 0, '123', 1, '123', '2022-1-1')");
        } catch (SQLException ex) {
            
            System.out.println(ex);
            fail("Exception when inserting into TABLES.");
        }
    }

    /**
     * Test of login method, of class DataBase.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        UserBuyer buyer = (UserBuyer)db.login(BUYER_NAME, PASSWORD);
        
        assertEquals(BUYER_NAME, buyer.username);
        assertEquals(100.0, buyer.balance, 0.1);
        
        UserSeller seller = (UserSeller)db.login(SELLER_NAME, PASSWORD);
        assertEquals(SELLER_NAME, seller.username);
        assertEquals(200.0, seller.balance, 0.1);
    }

    /**
     * Test of updateUserBalanceDB method, of class DataBase.
     */
    @Test
    public void testUpdateUserBalanceDB() {
        System.out.println("updateUserBalanceDB");
        
        UserBuyer buyer = (UserBuyer)db.login(BUYER_NAME, PASSWORD);
        
        db.updateUserBalanceDB(buyer.userID, 300.0);
        buyer = (UserBuyer)db.login(BUYER_NAME, PASSWORD);
        assertEquals(300.0, buyer.balance, 0.1);
        
        db.updateUserBalanceDB(buyer.userID, 0);
        buyer = (UserBuyer)db.login(BUYER_NAME, PASSWORD);
        assertEquals(0.0, buyer.balance, 0.1);
    }

    /**
     * Test of hasUser method, of class DataBase.
     */
    @Test
    public void testHasUser() {
        System.out.println("hasUser");
        assertEquals(true, db.hasUser(BUYER_NAME));
        assertEquals(true, db.hasUser(SELLER_NAME));
        assertEquals(false, db.hasUser(""));
        assertEquals(false, db.hasUser("-----$@"));
        assertEquals(false, db.hasUser("NO ONE"));
    }

    /**
     * Test of hasProduct method, of class DataBase.
     */
    @Test
    public void testHasProduct() {
        System.out.println("hasProduct");
        assertEquals(true, db.hasProduct(PRODUCT_1));
        assertEquals(true, db.hasProduct(PRODUCT_2));
        assertEquals(false, db.hasProduct(""));
        assertEquals(false, db.hasProduct("!@#!@#"));
        assertEquals(false, db.hasProduct("NO PRODUCT"));
    }


    /**
     * Test of insertDB method, of class DataBase.
     */
    @Test
    public void testInsertUserDB() {
        System.out.println("insertDB");
        UserBuyer buyer = new UserBuyer(1, "buyer", "123", "buyer", "", "", "", "", 1.1);
        UserSeller seller = new UserSeller(2, "seller", "123", "seller",  "", "", "", "", 2.3);
        db.insertUserDB(buyer);
        db.insertUserDB(seller);
        assertEquals(true, db.hasUser("buyer"));
        assertEquals(true, db.hasUser("seller"));
    }

    /**
     * Test of insertProductDB method, of class DataBase.
     */
    @Test
    public void testInsertProductDB() {
        System.out.println("insertProductDB");
        Product p = new Product(0, 1, "banana", 1.23, "banana");
        db.insertProductDB(p);
        assertEquals(true, db.hasProduct("banana"));
    }

    /**
     * Test of insertTransactionDB method, of class DataBase.
     */
    @Test
    public void testInsertTransactionDB() {
        System.out.println("insertTransactionDB");
        Transaction t = new Transaction(0, 1, "123", 2, "123", LocalDate.now());
        db.insertTransactionDB(t);
        try {
            ResultSet rs = db.executeSqlQuery("SELECT * FROM " + db.TRANSACTION_TABLE_NAME + " WHERE date='"+ LocalDate.now().toString() + "'");
            if (!rs.next()) {
                fail("Transaction insert failed");
            }
            rs.close();
        } catch(SQLException e){
            fail("Exception when querying Transaction table.");
        }
    }

    /**
     * Test of productInfo method, of class DataBase.
     */
    @Test
    public void testOnSaleProduct() {
        System.out.println("productInfo");
        // Ensure SOLD_PRODUCT is the only product sold.
        db.executeSqlUpdate("delete from  " + db.TRANSACTION_TABLE_NAME + " WHERE productId != " + soldProductId);
        ArrayList<String[]> products = db.onSaleProduct();
        for(int i=0; i<products.size(); i++) {
            assert(products.get(i)[0].equals(PRODUCT_1) || products.get(i)[0].equals(PRODUCT_2));
            assert(!products.get(i)[0].equals(SOLD_PRODUCT));
        }
    }

    /**
     * Test of searchProductDB method, of class DataBase.
     */
    @Test
    public void testSearchProductDB() {
        System.out.println("searchProductDB");
        ArrayList<String[]> result = db.searchProductDB("pl");
        assert(result.get(0)[0].equals("apple"));
        
        result = db.searchProductDB("ar");
        assert(result.get(0)[0].equals("Car"));
    }

    /**
     * Test of transactionBuyerInfo method, of class DataBase.
     */
    @Test
    public void testTransactionBuyerInfo() {
        System.out.println("transactionBuyerInfo");
        
        // Ensure SOLD_PRODUCT is the only product sold.
        db.executeSqlUpdate("delete from  " + db.TRANSACTION_TABLE_NAME + " WHERE productId != " + soldProductId);
        
        ArrayList<String[]> result = db.transactionBuyerInfo(buyerId);
        assertEquals(1, result.size());
        assert(result.get(0)[0].equals(SOLD_PRODUCT));
    }

    /**
     * Test of transactionSellerInfo method, of class DataBase.
     */
    @Test
    public void testTransactionSellerInfo() {
        System.out.println("transactionSellerInfo");

        // Ensure SOLD_PRODUCT is the only product sold.
        db.executeSqlUpdate("delete from  " + db.TRANSACTION_TABLE_NAME + " WHERE productId != " + soldProductId);
        
        ArrayList<String[]> result = db.transactionSellerInfo(sellerId);
        assertEquals(1, result.size());
        assert(result.get(0)[0].equals(SOLD_PRODUCT));
    }

    /**
     * Test of getProduct method, of class DataBase.
     */
    @Test
    public void testGetProduct() {
        System.out.println("getProduct");
        
        Product p = db.getProduct(PRODUCT_1);
        assertEquals(product1Id, p.productID);
        
        p = db.getProduct(SOLD_PRODUCT);
        assertEquals(soldProductId, p.productID);
        
        p = db.getProduct("NO PRODUCT");
        assertEquals(null, p);
    }

    /**
     * Test of getProductOwner method, of class DataBase.
     */
    @Test
    public void testGetProductSeller() {
        System.out.println("getProductOwner");
        
        UserSeller seller = db.getProductSeller(PRODUCT_1);
        assertEquals(sellerId, seller.userID);
        seller = db.getProductSeller(SOLD_PRODUCT);
        assertEquals(sellerId, seller.userID);
        seller = db.getProductSeller("NO PRODUCT");
        assertEquals(null, seller);
    }
}
