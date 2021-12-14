package utils;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.ImageIcon;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Database {
	
	/**
	 * Singleton instance
	 */
	private static Database singletonInstance = null;
	
	/**
	 * Database headers for table
	 */
	public static Object[] userHeaders = {
			"User ID", "First Name", "Middle Name", "Last Name", 
			"Position", "Contact", "Username", "Password"
	};
	public static Object[] supplierHeaders = {
			"Supplier ID", "Name", "Contact Number", "Address"
	};
	public static String[] productCategories = {
			"agriculture", "consumables", "electronics", "general-hygiene",
			"school-supplies", "tools"
	};
	
	private String defaultAdminPassword = "superadmin!";
	
	private String db_url = "jdbc:mysql://localhost/?serverTimezone=UTC";
	private String db_name = "fred_gen_merch";
	private String db_user = "root";
	private String db_pass = "";
	
	
	private DateFormat sqlDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private ResultSet initialRecordsTest;
	
	private ImageIcon image, newImage;
	private Image im, myImg;
	public int imageSize = 48;
	
	private Database() {
		try {
			con = DriverManager.getConnection(
				db_url, db_user, db_pass
			);
			stmt = con.createStatement();
			createDatabase();
			createTables();
		} catch (SQLException e) {
			Gallery.getInstance().showMessage(new String[] {"Please open your database first."});
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static Database getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new Database();
		}
		
		return singletonInstance;
	}
	
	public void createDatabase() throws SQLException {
		stmt.execute(String.format("CREATE DATABASE IF NOT EXISTS %s;", db_name));
		stmt.execute(String.format("USE %s;", db_name));
	}
	
	public void createTables() throws SQLException {
		stmt.execute("CREATE TABLE IF NOT EXISTS user ("
				+ "user_id BIGINT PRIMARY KEY, "
				+ "fname VARCHAR(255) NOT NULL, "
				+ "mname VARCHAR(255) DEFAULT \"\", "
				+ "lname VARCHAR(255) NOT NULL, "
				+ "position VARCHAR(255) NOT NULL, "
				+ "contact VARCHAR(255) NOT NULL, "
				+ "username VARCHAR(255) NOT NULL, "
				+ "password VARCHAR(255) NOT NULL"
			+ ");"
		);
		stmt.execute("CREATE TABLE IF NOT EXISTS supplier ("
				+ "supplier_id BIGINT PRIMARY KEY, "
				+ "name VARCHAR(255) NOT NULL, "
				+ "contact_no VARCHAR(255) NOT NULL, "
				+ "address VARCHAR(255) NOT NULL"
			+ ");"
		);
		stmt.execute("CREATE TABLE IF NOT EXISTS product ("
				+ "product_id BIGINT PRIMARY KEY, "
				+ "category VARCHAR(255) NOT NULL, "
				+ "name VARCHAR(255) NOT NULL, "
				+ "image MEDIUMBLOB, "
				+ "stocks DOUBLE(8, 2) NOT NULL, "
				+ "uom VARCHAR(255) NOT NULL, "
				+ "price_bought DOUBLE(8, 2) NOT NULL, "
				+ "selling_price DOUBLE(8, 2) NOT NULL"
			+ ");"
		);
		stmt.execute("CREATE TABLE IF NOT EXISTS customer_discount ("
				+ "customer_discount_id BIGINT PRIMARY KEY, "
				+ "type VARCHAR(255) NOT NULL, "
				+ "id_number VARCHAR(255) NOT NULL UNIQUE, "
				+ "fname VARCHAR(255) NOT NULL, "
				+ "mname VARCHAR(255), "
				+ "lname VARCHAR(255) NOT NULL"
			+ ");"
		);
		stmt.execute("CREATE TABLE IF NOT EXISTS transaction ("
				+ "transaction_id BIGINT PRIMARY KEY, "
				+ "user_id BIGINT NOT NULL, "
				+ "customer_discount_id BIGINT NOT NULL, "
				+ "date DATETIME NOT NULL, "
				+ "total_price DOUBLE(8, 2) NOT NULL, "
				+ "FOREIGN KEY (user_id) "
				+ "REFERENCES user(user_id), "
				+ "FOREIGN KEY (customer_discount_id) "
				+ "REFERENCES customer_discount(customer_discount_id)"
			+ ");"
		);
		stmt.execute("CREATE TABLE IF NOT EXISTS supplies ("
				+ "supplier_id BIGINT NOT NULL, "
				+ "product_id BIGINT NOT NULL, "
				+ "user_id BIGINT NOT NULL, "
				+ "quantity DOUBLE(8, 2) NOT NULL, "
				+ "total_price DOUBLE(8, 2) NOT NULL, "
				+ "date DATETIME NOT NULL, "
				+ "FOREIGN KEY (supplier_id) "
				+ "REFERENCES supplier(supplier_id), "
				+ "FOREIGN KEY (product_id) "
				+ "REFERENCES product(product_id), "
				+ "FOREIGN KEY (user_id) "
				+ "REFERENCES user(user_id)"
			+ ");"
		);
		stmt.execute("CREATE TABLE IF NOT EXISTS contains ("
				+ "transaction_id BIGINT NOT NULL, "
				+ "product_id BIGINT NOT NULL, "
				+ "quantity DOUBLE(8, 2) NOT NULL, "
				+ "FOREIGN KEY (transaction_id) "
				+ "REFERENCES transaction(transaction_id), "
				+ "FOREIGN KEY (product_id) "
				+ "REFERENCES product(product_id)"
			+ ");"
		);
		
		/* Initial records */
		
		/**
		 * The following codes will insert an initial record for the customers which is
		 * the default category named Regular.
		 */
		initialRecordsTest = stmt.executeQuery(
			"SELECT COUNT(*) "
			+ "FROM customer_discount "
			+ "WHERE customer_discount_id=3000000000;");
		initialRecordsTest.next();
		if (initialRecordsTest.getInt(1) == 0) {
			stmt.execute("INSERT INTO customer_discount "
					+ "VALUES (3000000000, \"REGULAR\", \"\", \"\", \"\", \"\");");
		}
		
		/**
		 * The following codes will insert the default administrators user and password
		 */
		initialRecordsTest = stmt.executeQuery(
			"SELECT COUNT(*) "
			+ "FROM user "
			+ "WHERE user_id=10000000000;");
		initialRecordsTest.next();
		if (initialRecordsTest.getInt(1) == 0) {
			stmt.execute("INSERT INTO user "
					+ "VALUES (10000000000, \"\", \"\", \"\", \"Administrator\", "
					+ "\"\", \"admin\", \"" 
					+ Utility.getInstance().hashData(defaultAdminPassword) 
					+ "\");");
		}

	}
	
	/**
	 * Registering an employee account to the system.
	 * 
	 * @param userID The ID must be generated from the Utility class method generateUserID()
	 * @param fname First name of the user
	 * @param mname Middle name of the user 
	 * @param lname Last name of the user
	 * @param position Job role of the user
	 * @param contact Contact number of the user
	 * @param username User name of the user who will use the system
	 * @param password Secured password for the employee (MD5)
	 * 
	 * @return true if the process is successful
	 * @see utils.Utility#hashData(String)
	 */
	public boolean addUser(long userID, String fname, String mname, String lname, 
		String position, String contact, String username, String password
	) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO user VALUES ("
				+ "?, ?, ?, ?, ?, ?, ?, ?"
				+ ");"
			);
			ps.setLong(1, userID);
			ps.setString(2, fname);
			
			if (mname.isBlank()) {
				ps.setString(3, "");
			} else {
				ps.setString(3, mname);
			}
			
			ps.setString(4, lname);
			ps.setString(5, position);
			ps.setString(6, contact);
			ps.setString(7, username);
			ps.setString(8, password);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param supplierID The ID must be generated from the Utility class method generateSupplierID()
	 * @param name Name of supplier/business
	 * @param contactNo Shows contact number of an active supplier
	 * @param address Physical location of the supplier
	 * 
	 * @return returns true if the process is successful
	 */
	public boolean addSupplier(long supplierID, String name, String contactNo, String address) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO supplier VALUES ("
				+ "?, ?, ?, ?"
				+ ");"
			);
			ps.setLong(1, supplierID);
			ps.setString(2, name);
			ps.setString(3, contactNo);
			ps.setString(4, address);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param productID The ID must be generated from the Utility class method generateProductID()
	 * @param category the category in which the product belongs to
	 * @param name The product's general name, may/may not include the brand and company name
	 * @param path The path must come from the Utility's image chooser method namely showImageChooser()
	 * @param stock The quantity of the initial stocks, default can be zero
	 * @param uom The quantity description on how it will be sold or packaged, examples will be on kilograms or pieces
	 * @param priceBought The buying price, this will be used in making statistics on the inventory
	 * @param sellingPrice The selling price, this is also important for monitoring profit margin upon stocks
	 * 
	 * @return returns true if the process is successful
	 * @see utils.Utility#showImageChooser()
	 */
	public boolean addProduct(long productID, String category, String name, 
		String path, double stock, String uom, double priceBought, double sellingPrice
	) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO product VALUES ("
				+ "?, ?, ?, ?, ?, ?, ?, ?"
				+ ");"
			);
			ps.setLong(1, productID);
			ps.setString(2, category);
			ps.setString(3, name);
			
			//Inserting Blob type
			InputStream in = new FileInputStream(path);
			ps.setBinaryStream(4, in);
			
			ps.setDouble(5, stock);
			ps.setString(6, uom);
			ps.setDouble(7, priceBought);
			ps.setDouble(8, sellingPrice);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Adding a new account for discounted customers (PWD & Senior Citizen)
	 * 
	 * @param customerDiscountID The ID must be generated from the Utility class method generateCustomerDiscountID()
	 * @param type States if regular,senior or PWD
	 * @param idNumber States if discounted (senior or PWD) the id number that is written on customer presented id.
	 * @param fname Discounted customer first name 
	 * @param mname Discounted customer middle name 
	 * @param lname Discounted customer last name 
	 * 
	 * @return returns true if the process is successful
	 */
	public boolean addCustomerDiscount(long customerDiscountID, String type, 
		String idNumber, String fname, String mname, String lname
	) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO customer_discount VALUES ("
				+ "?, ?, ?, ?, ?, ?"
				+ ");"
			);
			ps.setLong(1, customerDiscountID);
			ps.setString(2, type);
			ps.setString(3, idNumber);
			ps.setString(4, fname);

			if (mname == null) {
				ps.setNull(5,  Types.NULL);
			} else {
				ps.setString(5, mname);
			}
			
			ps.setString(6, lname);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param transactionID The ID must be generated from the Utility class method generateTransactionID()
	 * @param userID Identification of the user who handled the transaction
	 * @param customerDiscountID For unique identification of the discounted customer
	 * @param totalPrice Overall total price of the transaction
	 * 
	 * @return returns true if the process is successful
	 */
	public boolean addTransaction(long transactionID, long userID, long customerDiscountID, double totalPrice) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO transaction VALUES ("
				+ "?, ?, ?, NOW(), ?"
				+ ");"
			);
			ps.setLong(1, transactionID);
			ps.setLong(2, userID);
			ps.setLong(3, customerDiscountID);
			ps.setDouble(4, totalPrice);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param supplierID The ID must be generated from the Utility class method generateSupplierID()
	 * @param productID The ID must be generated from the Utility class method generateProductID()
	 * @param userID The ID must be generated from the Utility class method generateUserID()
	 * @param quantity Quantity of products supplied
	 * @param totalPrice Total amount to be paid to supplier (price_bought * quantity)
	 * 
	 * @return returns true if the process is successful
	 */
	public boolean addSupplies(long supplierID, long productID, long userID, double quantity, double totalPrice) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO supplies VALUES ("
				+ "?, ?, ?, ?, ?, NOW()"
				+ ");"
			);
			ps.setLong(1, supplierID);
			ps.setLong(2, productID);
			ps.setLong(3, userID);
			ps.setDouble(4, quantity);
			ps.setDouble(5, totalPrice);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param transactionID The ID must be generated from the Utility class method generateTransactionID()
	 * @param product_id The ID must be generated from the Utility class method generateProductID()
	 * @param quantity the amount of products bought by the customer
	 * 
	 * @return returns true if the process is successful
	 */
	public boolean addContains(long transactionID, long product_id, double quantity) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO contains VALUES ("
				+ "?, ?, ?"
				+ ");"
			);
			ps.setLong(1, transactionID);
			ps.setLong(2, product_id);
			ps.setDouble(3, quantity);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Fetches all users contained inside a transaction
	 * 
	 * @param keyword key-sensitive search term use to get users from the database
	 * @return 2D Object array containing the results, null if no results found
	 */
	public Object[][] getUsersByKeyword(String keyword) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM user " 
					+ "WHERE username LIKE ?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ps.setString(1, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][8];

		    int index = 0;
            while (rs.next()){
            	Object[] row = new Object[8];

            	row[0] = rs.getLong("user_id");
            	row[1] = rs.getString("fname");
            	row[2] = rs.getString("mname");
            	row[3] = rs.getString("lname");
            	row[4] = rs.getString("position");
            	row[5] = rs.getString("contact");
            	row[6] = rs.getString("username");
            	row[7] = rs.getString("password");
                
    			result[index] = row;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * Gets a detail of a specific user name.
	 * 
	 * @param username the user name given by the input 
	 * @return the details from the database
	 */
	public Object[] getUserLogin(String username) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM user " 
					+ "WHERE username=?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[] result = new Object[8];
            while (rs.next()){

            	result[0] = rs.getLong("user_id");
            	result[1] = rs.getString("fname");
            	result[2] = rs.getString("mname");
            	result[3] = rs.getString("lname");
            	result[4] = rs.getString("position");
            	result[5] = rs.getString("contact");
            	result[6] = rs.getString("username");
            	result[7] = rs.getString("password");
            }
            if (size != 0) {
                return result;
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
		return null;
	}
	
	/**
	 * Fetches suppliers based on a keyword of any of its details
	 * 
	 * @param keyword key-sensitive search term use to get suppliers from the database
	 * @return 2D Object array containing the results, null if no results found
	 */
	public Object[][] getSuppliersByKeyword(String keyword) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM supplier " 
					+ "WHERE supplier_id LIKE ? "
					+ "OR name LIKE ? "
					+ "OR contact_no LIKE ? "
					+ "OR address LIKE ?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			ps.setString(4, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][4];

		    int index = 0;
            while (rs.next()){
            	Object[] row = new Object[4];

            	row[0] = rs.getLong("supplier_id");
            	row[1] = rs.getString("name");
            	row[2] = rs.getString("contact_no");
            	row[3] = rs.getString("address");
                
    			result[index] = row;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * The information stored in the rows are only the following: product_id, name, image, uom and selling_price
	 * 
	 * @param keyword key-sensitive search term use to get products from the database.
	 * @return 2-dimensional array of products.
	 * 			<br> returns null if there are no results.
	 */
	public Object[][] getProductsByKeyword(String keyword) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM product " 
					+ "WHERE product_id LIKE ? "
					+ "OR category LIKE ? " 
					+ "OR name LIKE ?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] resultProducts = new Object[size][6];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[6];
            	
            	productRow[0] = rs.getLong("product_id");
            	productRow[1] = rs.getString("category");
            	productRow[2] = rs.getString("name");
            	
                byte[] img = rs.getBytes("image");
                image = new ImageIcon(img);
                im = image.getImage();
                myImg = im.getScaledInstance(
                	imageSize, imageSize,
                	Image.SCALE_SMOOTH
                );
                newImage = new ImageIcon(myImg);
                
                productRow[3] = newImage;
                productRow[4] = rs.getString("uom");
                productRow[5] = rs.getDouble("selling_price");
                
                resultProducts[index] = productRow;
                index++;
            }
            
            return resultProducts;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * Fetches all customer details regarding their discounts
	 * 
	 * @param keyword key-sensitive search term use to get customer details from the database.
	 * @return 2-dimensional array of customer details.
	 * 			<br> returns null if there are no results.
	 */
	public Object[][] getCustomerDiscountsByKeyword(String keyword) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM customer_discount " 
					+ "WHERE customer_discount_id LIKE ? "
					+ "OR type LIKE ? "
					+ "OR id_number LIKE ? " 
					+ "OR fname LIKE ? " 
					+ "OR mname LIKE ? " 
					+ "OR lname LIKE ?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			ps.setString(4, "%" + keyword + "%");
			ps.setString(5, "%" + keyword + "%");
			ps.setString(6, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] resultProducts = new Object[size][6];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[6];
            	
            	productRow[0] = rs.getLong("customer_discount_id");
            	productRow[1] = rs.getString("type");
            	productRow[2] = rs.getString("id_number");
                productRow[3] = rs.getString("fname");;
                productRow[4] = rs.getString("mname");
                productRow[5] = rs.getString("lname");
                
                resultProducts[index] = productRow;
                index++;
            }
            
            return resultProducts;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * Fetches all transaction records using a keyword
	 * 
	 * @param keyword key-sensitive search term use to get transaction details from the database.
	 * @return 2-dimensional array of transaction details.
	 * 			<br> returns null if there are no results.
	 */
	public Object[][] getTransactionsByKeyword(String keyword) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM transaction " 
					+ "WHERE transaction_id LIKE ? "
					+ "OR user_id LIKE ? "
					+ "OR customer_discount_id LIKE ?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] resultProducts = new Object[size][5];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[6];
            	
            	productRow[0] = rs.getLong("transaction_id");
            	productRow[1] = rs.getLong("user_id");
            	productRow[2] = rs.getLong("customer_discount_id");
                productRow[3] = rs.getTimestamp("date", Calendar.getInstance());
                productRow[4] = rs.getDouble("total_price");
                
                resultProducts[index] = productRow;
                index++;
            }
            
            return resultProducts;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * @param from Date start selection
	 * @param to Date end selection
	 * 
	 * @return 2D Object array containing the results, null if no results found
	 */
	public Object[][] getTransactionsByRange(Date from, Date to) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM transaction " 
					+ "WHERE date "
					+ "BETWEEN ? "
					+ "AND ?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			
			ps.setString(1, sqlDateTimeFormat.format(from));
			ps.setString(2, sqlDateTimeFormat.format(to));
			
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][5];

		    int index = 0;
            while (rs.next()){
            	Object[] row = new Object[5];
            	
            	row[0] = rs.getLong("transaction_id");
            	row[1] = rs.getLong("user_id");
            	row[2] = rs.getLong("customer_discount_id");
            	row[3] = rs.getTimestamp("date", Calendar.getInstance());
            	row[4] = rs.getDouble("total_price");
                
            	result[index] = row;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * Fetches all supplied product records using a keyword
	 * 
	 * @param keyword key-sensitive search term use to get customer details from the database.
	 * @return 2-dimensional array of customer details.
	 * 			<br> returns null if there are no results.
	 */
	public Object[][] getSuppliesByKeyword(String keyword) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM supplies " 
					+ "WHERE supplier_id LIKE ? "
					+ "OR product_id LIKE ? "
					+ "OR user_id LIKE ?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] resultProducts = new Object[size][6];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[6];
            	
            	productRow[0] = rs.getLong("supplier_id");
            	productRow[1] = rs.getLong("product_id");
            	productRow[2] = rs.getLong("user_id");
                productRow[3] = rs.getDouble("quantity");
                productRow[4] = rs.getDouble("total_price");
                productRow[5] = rs.getTimestamp("date", Calendar.getInstance());
                
                resultProducts[index] = productRow;
                index++;
            }
            
            return resultProducts;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * @param from Date start selection
	 * @param to Date end selection
	 * 
	 * @return 2D Object array containing the results, null if no results found
	 */
	public Object[][] getSuppliesByRange(Date from, Date to) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM supplies " 
					+ "WHERE date "
					+ "BETWEEN ? "
					+ "AND ?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			
			ps.setString(1, sqlDateTimeFormat.format(from));
			ps.setString(2, sqlDateTimeFormat.format(to));
			
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][6];

		    int index = 0;
            while (rs.next()){
            	Object[] row = new Object[6];
            	
            	row[0] = rs.getLong("supplier_id");
            	row[1] = rs.getLong("product_id");
            	row[2] = rs.getLong("user_id");
            	row[3] = rs.getDouble("quantity");
            	row[4] = rs.getDouble("total_price");
            	row[4] = rs.getTimestamp("date", Calendar.getInstance());
                
            	result[index] = row;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * Fetches all products contained inside a transaction
	 * 
	 * @param transactionID The ID must be generated from the Utility class method generateTransactionID()
	 * @return 2D Object array containing the results, null if no results found
	 */
	public Object[][] getContainsByID(long transactionID) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM contains " 
					+ "WHERE transaction_id = ?;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ps.setLong(1, transactionID);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][3];

		    int index = 0;
            while (rs.next()){
            	Object[] row = new Object[3];
            	
            	row[0] = rs.getLong("transaction_id");
            	row[1] = rs.getLong("product_id");
            	row[2] = rs.getDouble("quantity");
                
            	result[index] = row;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * Method for updating the details of a user.
	 * 
	 * @param userID The ID must be generated from the Utility class method generateUserID()
	 * @param fname First name of the user
	 * @param mname Middle name of the user 
	 * @param lname Last name of the user
	 * @param position Job role of the user
	 * @param contact Contact number of the user
	 * @param username User name of the user who will use the system
	 * @param password Secured password for the employee (MD5)
	 * 
	 * @return true if the process is successful
	 * @see utils.Utility#hashData(String)
	 */
	public boolean setUser(long userID, String fname, String mname, String lname, 
		String position, String contact, String username, String password
	) {
		try {
			ps = con.prepareStatement(
				"UPDATE user "
				+ "SET "
					+ "fname = ?, "
					+ "mname = ?, "
					+ "lname = ?, "
					+ "position = ?, "
					+ "contact = ?, " 
					+ "username = ?, "
					+ "password = ? "
				+ "WHERE user_id = ?;"
			);
			ps.setString(1, fname);

			if (mname.isBlank()) {
				ps.setString(2, "");
			} else {
				ps.setString(2, mname);
			}
			
			ps.setString(3, lname);
			ps.setString(4, position);
			ps.setString(5, contact);
			ps.setString(6, username);
			ps.setString(7, password);
			ps.setLong(8, userID);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Method for updating the details of a supplier.
	 * 
	 * @param supplierID The ID must be generated from the Utility class method generateSupplierID()
	 * @param name Name of supplier/business
	 * @param contactNo Shows contact number of an active supplier
	 * @param address Physical location of the supplier
	 * 
	 * @return returns true if the process is successful
	 */
	public boolean setSupplier(long supplierID, String name, String contactNo, String address) {
		try {
			ps = con.prepareStatement(
				"UPDATE supplier "
				+ "SET "
					+ "name = ?, "
					+ "contact_no = ?, "
					+ "address = ? "
				+ "WHERE supplier_id = ?;"
			);
			ps.setString(1, name);
			ps.setString(2, contactNo);
			ps.setString(3, address);
			ps.setLong(4, supplierID);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Method for updating the details of a product.
	 * 
	 * @param productID The ID must be generated from the Utility class method generateProductID()
	 * @param category the category in which the product belongs to
	 * @param name The product's general name, may/may not include the brand and company name
	 * @param path The path must come from the Utility's image chooser method namely showImageChooser()
	 * @param stock The quantity of the initial stocks, default can be zero
	 * @param uom The quantity description on how it will be sold or packaged, examples will be on kilograms or pieces
	 * @param priceBought The buying price, this will be used in making statistics on the inventory
	 * @param sellingPrice The selling price, this is also important for monitoring profit margin upon stocks
	 * 
	 * @see utils.Utility#showImageChooser()
	 */
	public boolean setProduct(long productID, String category, String name, 
		String path, double stock, String uom, double priceBought, double sellingPrice
	) {
		try {
			ps = con.prepareStatement(
				"UPDATE product "
				+ "SET category = ?,"
					+ "name = ?, "
					+ "image = ?, "
					+ "stocks = ?, "
					+ "uom = ?, "
					+ "price_bought = ?, " 
					+ "selling_price = ? "
				+ "WHERE product_id = ?;"
			);
			ps.setString(1, category);
			ps.setString(2, name);
			
			//Inserting Blob type
			InputStream in = new FileInputStream(path);
			ps.setBinaryStream(3, in);
			
			ps.setDouble(4, stock);
			ps.setString(5, uom);
			ps.setDouble(6, priceBought);
			ps.setDouble(7, sellingPrice);
			ps.setLong(8, productID);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Method for updating the details of a discounter customer.
	 * 
	 * @param customerDiscountID The ID must be generated from the Utility class method generateCustomerDiscountID()
	 * @param type States if regular,senior or PWD
	 * @param idNumber States if discounted (senior or PWD) the id number that is written on customer presented id.
	 * @param fname Discounted customer first name 
	 * @param mname Discounted customer middle name 
	 * @param lname Discounted customer last name 
	 * 
	 * @return returns true if the process is successful
	 */
	public boolean setCustomerDiscount(long customerDiscountID, String type, 
		String idNumber, String fname, String mname, String lname
	) {
		try {
			ps = con.prepareStatement(
				"UPDATE customer_discount "
				+ "SET "
					+ "type = ?, "
					+ "id_number = ?, "
					+ "fname = ? "
					+ "mname = ? "
					+ "lname = ? "
				+ "WHERE customer_discount_id = ?;"
			);
			ps.setString(1, type);
			ps.setString(2, idNumber);
			ps.setString(3, fname);
			
			if (mname == null) {
				ps.setNull(4,  Types.NULL);
			} else {
				ps.setString(4, mname);
			}
			
			ps.setString(5, lname);
			ps.setLong(6, customerDiscountID);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This adaptive method fits in all table, just specify the parameters required to delete an entry
	 * <br> anywhere in the database.
	 * 
	 * @param table the table of the data
	 * @param idColumnName this is the primary key of the table specified
	 * @param id long-type data that is the id targeted to be deleted
	 * 
	 * @return true if the process is successful
	 */
	public boolean deleteEntry(String table, String idColumnName, long id) {
		try {
			ps = con.prepareStatement(
				"DELETE FROM " + table + " WHERE " + idColumnName + " = ?;");
			ps.setLong(1, id);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Fetches the latest user id not by directly getting the max in id column,
	 * but by the entry number which is the substring of the full id <p>
	 * 
	 * @return returns the latest employee by the substring 3 to end.
	 */
	public long fetchLastEmployee() {
		try {
			long max = 0;
			for (int level = 1; level <= 3; level++) {
				ps = con.prepareStatement(
					  "SELECT MAX(user_id) "
					+ "FROM user "
					+ "WHERE user_id > ? "
					+ "AND user_id < ?"
					+ ";"
				);
				StringBuilder least = new StringBuilder("1");
				StringBuilder most = new StringBuilder("1");
				
				least.append(Integer.toString(level));
				most.append(Integer.toString(level + 1));
				
				least.append(String.format("%09d", 0));
				most.append(String.format("%09d", 0));
				
				ps.setLong(1, Long.parseLong(least.toString()));
				ps.setLong(2, Long.parseLong(most.toString()));
				
				ResultSet pid = ps.executeQuery();
				pid.next();
				long current = pid.getLong(1);
				
				if (max == 0) {
					max = current;
				} else if (current != 0) {
					long subs = Long.parseLong(Long.toString(current).substring(8));
					long maxsub = Long.parseLong(Long.toString(max).substring(8));
					
					if (subs > maxsub) max = current;
				}
			}
			
			if (max > 1) return max;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Returns the max value of the table's ID column.
	 * 
	 * @param table database table to be inspected
	 * @return the id value from the table
	 */
	public long fetchLastID(String table, String column) {
		try {
			ps = con.prepareStatement(
					"SELECT MAX("
					+ column
					+ ") "
					+ "FROM "
					+ table
					+ ";"
				);
			ResultSet pid = ps.executeQuery();
			pid.next();
			long maxID = pid.getLong(1);
			
			if (maxID != 0) {
				return maxID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Fetches only the hashed passwords of all managers
	 * 
	 * @return String array of managers hashed password
	 */
	public String[] fetchManagerHashes() {
		try {
			ps = con.prepareStatement(
					"SELECT password " 
					+ "FROM user " 
					+ "WHERE user_id = 10000000000 "
					+ "OR user_id  >= 12000000000;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    String[] result = new String[size];

		    int index = 0;
            while (rs.next()){
            	result[index] = rs.getString("password");
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	/**
	 * This will check if there is an existing user that holds the same user name
	 * 
	 * @param username the value of the user name submitted by the input
	 * @return true if it exists, false if not
	 */
	public boolean checkUsername(String username) {
		try {
			ps = con.prepareStatement(
					"SELECT COUNT(*) "
				+ "FROM user "
				+ "WHERE username = ?;"
			);
			ps.setString(1, username);
			
			ResultSet result = ps.executeQuery();
			result.next();
			int count = result.getInt(1);
			
			if (count == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
