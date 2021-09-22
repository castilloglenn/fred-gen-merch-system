package utils;

import java.sql.*;

public class Database {
	
	private String db_url = "jdbc:mysql://localhost/?serverTimezone=UTC";
	private String db_name = "fred-gen-merch";
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
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS employee ("
					+ "employee_id BIGINT PRIMARY KEY,"
					+ "position VARCHAR(255) NOT NULL,"
					+ "fname VARCHAR(255) NOT NULL,"
					+ "mname VARCHAR(255) DEFAULT \"\","
					+ "lname VARCHAR(255) NOT NULL,"
					+ "address VARCHAR(255) NOT NULL,"
					+ "basic DOUBLE(8, 2) NOT NULL,"
					+ "incentives DOUBLE(8, 2) NOT NULL,"
					+ "contributions DOUBLE(8, 2) NOT NULL,"
					+ "penalty DOUBLE(8, 2) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS logs ("
					+ "log_id BIGINT PRIMARY KEY,"
					+ "employee_id BIGINT NOT NULL,"
					+ "type VARCHAR(255) NOT NULL,"
					+ "description VARCHAR(4096) NOT NULL,"
					+ "date DATETIME NOT NULL,"
					+ "FOREIGN KEY (employee_id)"
					+ "REFERENCES employee(employee_id)"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS product ("
					+ "product_id BIGINT PRIMARY KEY,"
					+ "category VARCHAR(255) NOT NULL,"
					+ "quantity DOUBLE(8, 2) NOT NULL,"
					+ "uom VARCHAR(255) NOT NULL,"
					+ "name VARCHAR(255) NOT NULL,"
					+ "purchase_value DOUBLE(8, 2) NOT NULL,"
					+ "sell_value DOUBLE(8, 2) NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS supplier ("
					+ "supplier_id BIGINT PRIMARY KEY,"
					+ "name VARCHAR(255) NOT NULL,"
					+ "address VARCHAR(255) NOT NULL,"
					+ "contact_no VARCHAR(255) NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS customer ("
					+ "customer_id BIGINT PRIMARY KEY,"
					+ "rebate DOUBLE(8, 2) NOT NULL,"
					+ "fname VARCHAR(255) NOT NULL,"
					+ "mname VARCHAR(255) DEFAULT \"\","
					+ "lname VARCHAR(255) NOT NULL,"
					+ "address VARCHAR(255) NOT NULL,"
					+ "contact_no VARCHAR(255) NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS transaction ("
					+ "transaction_id BIGINT PRIMARY KEY,"
					+ "employee_id BIGINT NOT NULL,"
					+ "customer_id BIGINT NOT NULL,"
					+ "type VARCHAR(255) NOT NULL,"
					+ "total_amount DOUBLE(10, 2) NOT NULL,"
					+ "amount_tendered DOUBLE(8, 2) NOT NULL,"
					+ "date DATETIME NOT NULL,"
					+ "FOREIGN KEY (employee_id)"
					+ "REFERENCES employee(employee_id),"
					+ "FOREIGN KEY (customer_id)"
					+ "REFERENCES customer(customer_id)"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS promo ("
					+ "product_id BIGINT NOT NULL,"
					+ "name VARCHAR(255) NOT NULL,"
					+ "conditions VARCHAR(255) NOT NULL,"
					+ "discount DOUBLE(8, 2) NOT NULL,"
					+ "start_date DATETIME NOT NULL,"
					+ "end_date DATETIME NOT NULL,"
					+ "FOREIGN KEY(product_id) "
					+ "REFERENCES product(product_id)"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS transacts ("
					+ "transaction_id BIGINT NOT NULL,"
					+ "product_id BIGINT NOT NULL,"
					+ "quantity DOUBLE(8, 2) NOT NULL,"
					+ "total_price DOUBLE(10, 2) NOT NULL,"
					+ "FOREIGN KEY (transaction_id)"
					+ "REFERENCES transaction(transaction_id),"
					+ "FOREIGN KEY (product_id)"
					+ "REFERENCES product(product_id)"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS supplies ("
					+ "supplier_id BIGINT NOT NULL,"
					+ "product_id BIGINT NOT NULL,"
					+ "quantity DOUBLE(8, 2) NOT NULL,"
					+ "total_price DOUBLE(10, 2) NOT NULL,"
					+ "FOREIGN KEY (supplier_id)"
					+ "REFERENCES supplier(supplier_id),"
					+ "FOREIGN KEY (product_id)"
					+ "REFERENCES product(product_id)"
			+ ");"
		);
		stmt.execute(
			  "INSERT INTO customer "
			+ "SELECT * FROM (SELECT 1, 0, \"WALK\", \"-\", \"IN\", \"System\", \"None\") AS tmp "
			+ "WHERE NOT EXISTS ("
			+ "    SELECT customer_id FROM customer WHERE customer_id = 1"
			+ ") LIMIT 1;"
		);
	}
	
}
