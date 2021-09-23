package utils;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

import javax.swing.ImageIcon;

public class Database {
	
	private String db_url = "jdbc:mysql://localhost/?serverTimezone=UTC";
	private String db_name = "fred_gen_merch";
	private String db_user = "root";
	private String db_pass = "";
	
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private Utility ut;
	
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
	
	public void uploadImage(String path) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO product VALUES ("
				+ "?, ?, ?, ?, ?, ?, ?"
				+ ");"
			);
			ps.setLong(1, 5L);
			ps.setString(2, "Apple");
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
	
	public ImageIcon getImage(long productID) {
		try {
			ps = con.prepareStatement(
				  "SELECT image "
				  + "FROM product "
				  + "WHERE product_id="
				  + "5"
				  + ";"
			);
			
			ResultSet rs = ps.executeQuery();
            if(rs.next()){
                byte[] img = rs.getBytes("image");
                //Resize The ImageIcon
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image myImg = im.getScaledInstance(64, 64,Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myImg);
                return newImage;
            }
            else{
                System.out.println("Wala");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
		
		return null;
	}
}
