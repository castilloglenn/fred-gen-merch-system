package utils;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

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
				+ "name VARCHAR(255) NOT NULL, "
				+ "image MEDIUMBLOB, "
				+ "stocks DOUBLE(8, 2) NOT NULL, "
				+ "uom VARCHAR(255) NOT NULL, "
				+ "price_bought DOUBLE(8, 2) NOT NULL, "
				+ "selling_price DOUBLE(8, 2) NOT NULL"
			+ ");"
		);
		stmt.execute("CREATE TABLE IF NOT EXISTS transaction ("
				+ "transaction_id BIGINT PRIMARY KEY, "
				+ "user_id BIGINT NOT NULL, "
				+ "date DATETIME NOT NULL, "
				+ "total_price DOUBLE(8, 2) NOT NULL, "
				+ "FOREIGN KEY (user_id) "
				+ "REFERENCES user(user_id)"
			+ ");"
		);
		stmt.execute("CREATE TABLE IF NOT EXISTS supplies ("
				+ "supplier_id BIGINT NOT NULL, "
				+ "product_id BIGINT NOT NULL, "
				+ "user_id BIGINT NOT NULL, "
				+ "quantity DOUBLE(8, 2) NOT NULL, "
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
	 * 
	 * @param productID The ID must be generated from the Utility class method generateProductID()
	 * @param name The product's general name, may/may not include the brand and company name
	 * @param path The path must come from the Utility's image chooser method namely showImageChooser()
	 * @param stock The quantity of the initial stocks, default can be zero
	 * @param uom The quantity description on how it will be sold or packaged, examples will be on kilograms or pieces
	 * @param priceBought The buying price, this will be used in making statistics on the inventory
	 * @param sellingPrice The selling price, this is also important for monitoring profit margin upon stocks
	 * 
	 * @see utils.Utility#showImageChooser()
	 */
	public void registerProduct(long productID, String name, 
		String path, double stock, String uom, double priceBought, double sellingPrice
	) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO product VALUES ("
				+ "?, ?, ?, ?, ?, ?, ?"
				+ ");"
			);
			ps.setLong(1, productID);
			ps.setString(2, name);
			//Inserting Blob type
			InputStream in = new FileInputStream(path);
			ps.setBinaryStream(3, in);
			ps.setDouble(4, 5.5);
			ps.setString(5, "piece");
			ps.setDouble(6, 10.5);
			ps.setDouble(7, 12.5);
			ps.executeUpdate();
		} catch (SQLException e ) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
					+ "OR name LIKE ? " 
					+ "ORDER BY name"
				+ ";", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] resultProducts = new Object[size][5];
			
		    int index = 0;
            if(rs.next()){
            	Object[] productRow = new Object[5];
            	
            	productRow[0] = rs.getLong("product_id");
            	productRow[1] = rs.getString("name");
            	
                byte[] img = rs.getBytes("image");
                image = new ImageIcon(img);
                im = image.getImage();
                myImg = im.getScaledInstance(64, 64,Image.SCALE_SMOOTH);
                newImage = new ImageIcon(myImg);
                
                productRow[2] = newImage;
                productRow[3] = rs.getString("uom");
                productRow[4] = rs.getDouble("selling_price");
                
                resultProducts[index] = productRow;
                index++;
            }
            
            return resultProducts;
        }catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
}
