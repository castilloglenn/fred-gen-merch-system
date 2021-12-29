package main;

import pos.Report;
import utils.Database;

public class ReportTest {

	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Database db = Database.getInstance();
		Object[][] dummyUser = db.getUsersByKeyword("glenn");
		
		Report r = Report.getInstance(dummyUser[0]);
		
		
		r.generateDailyReport();
	}

}
