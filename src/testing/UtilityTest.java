package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import utils.Utility;

class UtilityTest {
	
	private static Utility utility;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		utility = Utility.getInstance();
	}

	@Test
	void test() {
		
	}

}
