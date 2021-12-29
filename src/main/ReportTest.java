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
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		Date from = cal.getTime();
		
		cal.add(Calendar.HOUR, 3);
		Date to = cal.getTime();
		
		Object[] result = stat.getHourlySaleStatistic(from, to);
		
		for (Object o : result) {
			System.out.println(o.toString());
		}
		
		
		
		r.generateDailyReport();
	}

}
