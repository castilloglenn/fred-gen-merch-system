package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import utils.Database;
import utils.Utility;

class DatabaseTest {
	
	private Database database = new Database();
	private Utility utility = new Utility();

	@Test
	void test1() {
//		assertEquals(
//			database.setProduct(
//				1, "test_name", "banana", 
//				"C:\\Users\\Administrator\\git\\fred-gen-merch-system\\assets\\images\\products\\foods\\fruit.png", 
//				150.5, "pieces", 65.0, 1234.52)
//			, true);
		
//		assertEquals(database.deleteEntry("product", "product_id", 3L), true);
		
//		assertEquals(database.addUser(1L, "Sample First", null, "Sample Last", 
//				"Sample position", "09568990811", "admin", utility.hashData("password")), true);
//		assertEquals(database.addUser(2L, "Sample First", "Sample Middle", "Sample Last", 
//				"Sample position", "09568990811", "admin", utility.hashData("password")), true);
		
//		assertEquals(database.addTransaction(3L, 1L, 1L, 500.0), true);
		
//		Calendar before = Calendar.getInstance();
//		Calendar after = Calendar.getInstance();
//		
//		before.set(Calendar.YEAR, 2020);
//		database.getTransactionsByRange(before.getTime(), after.getTime());
	}

}
