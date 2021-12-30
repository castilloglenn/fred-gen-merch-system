package main;

import java.util.Calendar;
import java.util.Date;

import pos.Report;
import utils.Database;
import utils.Statistic;

public class ReportTest {

	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Database db = Database.getInstance();
		Object[][] dummyUser = db.getUsersByKeyword("glenn");
		Statistic stat = Statistic.getInstance();
		
		
		
		Report r = Report.getInstance(dummyUser[0]);
//		System.out.println(r.generateDailyReport(Report.CURRENT_DAY_REPORT));

//		System.out.println(r.generateFileName(true));
//		System.out.println(r.generateFileName(false));
	}

}
