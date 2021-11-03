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
import java.sql.Types;

import javax.swing.ImageIcon;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Database {
	
	private String db_url = "jdbc:mysql://localhost/?serverTimezone=UTC";
	private String db_name = "fred_gen_merch";
	private String db_user = "root";
	private String db_pass = "";
	
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	
	private ImageIcon image, newImage;
	private Image im, myImg;
	public int imageSize = 48;
	
	public Database() {
		try {
			con = DriverManager.getConnection(
				db_url, db_user, db_pass
			);
			stmt = con.createStatement();
			createDatabase();
			createTables();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
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
			
			if (mname == null) {
				ps.setNull(3,  Types.NULL);
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
	 * @param keyword
	 * @return 2D Object array containing the results, null if no results found
	 */
	public Object[][] getUsersByKeyword(String keyword) {
		try {
			ps = con.prepareStatement(
					"SELECT * " 
					+ "FROM user " 
					+ "WHERE user_id LIKE ?;", 
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
        }catch(Exception ex){
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
					+ "OR name LIKE ? " 
					+ "ORDER BY name"
				+ ";", 
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
        }catch(Exception ex){
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
	public Object[][] getContains(long transactionID) {
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
        }catch(Exception ex){
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
				+ "SET user_id = ?,"
					+ "fname = ?, "
					+ "mname = ?, "
					+ "lname = ?, "
					+ "position = ?, "
					+ "contact = ?, " 
					+ "username = ?, "
					+ "password = ? "
				+ "WHERE product_id = ?;"
			);
			ps.setLong(1, userID);
			ps.setString(2, fname);

			if (mname == null) {
				ps.setNull(3,  Types.NULL);
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
}
