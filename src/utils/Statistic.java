package utils;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Statistic {
	
	/**
	 * Singleton instance
	 */
	private static Statistic singletonInstance = null;
	
	private Database database;
	private PreparedStatement ps;
	private Connection con;
	
	private ImageIcon image, newImage;
	private Image im, myImg;
	public int imageSize = 48;
	
	private Statistic() {
		database = Database.getInstance();
		con = database.getConnection();
	}
	
	public static Statistic getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new Statistic();
		}
		
		return singletonInstance;
	}
	
	public Object[][] getTopFiveMostStocks() {
		try {
			ps = con.prepareStatement(
					"SELECT * "
					+ "FROM product "
					+ "ORDER BY stocks DESC "
					+ "LIMIT 5;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][8];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[8];
            	
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
                productRow[4] = rs.getDouble("stocks");
                productRow[5] = rs.getString("uom");
                productRow[6] = rs.getDouble("price_bought");
                productRow[7] = rs.getDouble("selling_price");
                
                result[index] = productRow;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	public Object[][] getTopFiveLeastStocks() {
		try {
			ps = con.prepareStatement(
					"SELECT * "
					+ "FROM product "
					+ "ORDER BY stocks ASC "
					+ "LIMIT 5;", 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][8];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[8];
            	
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
                productRow[4] = rs.getDouble("stocks");
                productRow[5] = rs.getString("uom");
                productRow[6] = rs.getDouble("price_bought");
                productRow[7] = rs.getDouble("selling_price");
                
                result[index] = productRow;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	public Object[][] getCategoryStock() {
		try {
			ps = con.prepareStatement(
					"SELECT category, SUM(stocks) AS total_stocks "
					+ "FROM product "
					+ "GROUP BY category "
					+ "ORDER BY total_stocks DESC; ",
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][2];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[2];
            	
            	productRow[0] = rs.getString("category");
                productRow[1] = rs.getDouble("total_stocks");
                
                result[index] = productRow;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	public Object[][] getCategoryCount() {
		try {
			ps = con.prepareStatement(
					"SELECT category, COUNT(product_id) AS product_count "
					+ "FROM product "
					+ "GROUP BY category "
					+ "ORDER BY product_count DESC;",
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][2];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[2];
            	
            	productRow[0] = rs.getString("category");
                productRow[1] = rs.getDouble("product_count");
                
                result[index] = productRow;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	public Object[][] getProductMostSales() {
		try {
			ps = con.prepareStatement(
					"SELECT p.name, SUM(p.selling_price * c.quantity) AS amount "
					+ "FROM product p, transaction t, contains c "
					+ "WHERE p.product_id = c.product_id AND t.transaction_id = c.transaction_id "
					+ "GROUP BY p.name "
					+ "ORDER BY amount DESC "
					+ "LIMIT 5;",
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][2];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[2];
            	
            	productRow[0] = rs.getString("p.name");
                productRow[1] = rs.getDouble("amount");
                
                result[index] = productRow;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	public Object[][] getProductLeastSales() {
		try {
			ps = con.prepareStatement(
					"SELECT p.name, SUM(p.selling_price * c.quantity) AS amount "
					+ "FROM product p, transaction t, contains c "
					+ "WHERE p.product_id = c.product_id AND t.transaction_id = c.transaction_id "
					+ "GROUP BY p.name "
					+ "ORDER BY amount ASC "
					+ "LIMIT 5;",
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][2];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[2];
            	
            	productRow[0] = rs.getString("p.name");
                productRow[1] = rs.getDouble("amount");
                
                result[index] = productRow;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	public Object[][] getCategorySale() {
		try {
			ps = con.prepareStatement(
					"SELECT p.category, SUM(p.selling_price * c.quantity) AS amount "
					+ "FROM product p, transaction t, contains c "
					+ "WHERE p.product_id = c.product_id AND t.transaction_id = c.transaction_id "
					+ "GROUP BY p.category "
					+ "ORDER BY amount DESC;",
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][2];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[2];
            	
            	productRow[0] = rs.getString("p.category");
                productRow[1] = rs.getDouble("amount");
                
                result[index] = productRow;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}
	
	public Object[][] getUserMostSales() {
		try {
			ps = con.prepareStatement(
					"SELECT CONCAT(u.fname, \" \", u.mname , \" \", u.lname) AS `name`, "
					+ "	SUM(t.total_price) AS sales "
					+ "FROM user u, transaction t "
					+ "WHERE u.user_id = t.user_id "
					+ "GROUP BY `name` "
					+ "ORDER BY sales DESC "
					+ "LIMIT 5;",
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = ps.executeQuery();
			
			int size = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		    
		    Object[][] result = new Object[size][2];

		    int index = 0;
            while (rs.next()){
            	Object[] productRow = new Object[2];
            	
            	productRow[0] = rs.getString("name");
                productRow[1] = rs.getDouble("sales");
                
                result[index] = productRow;
                index++;
            }
            return result;
        } catch(Exception ex){
            ex.printStackTrace();
    		return null;
        }
	}

}
