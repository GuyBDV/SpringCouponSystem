package databaseMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import couponsystem.ConnectionPool;
import couponsystem.CouponSystemException;

/**
 * The objective of this class is to manage creation and deletion of database object and 
 * database table objects when necessary.
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public class DatabaseInfrastructure {
	
	private String username = "root";
	private String password = "mysql";
	private String databaseName = "CouponDB";
	private String couponTable = "Coupon";
	private String customerTable = "Customer";
	private String companyTable = "Company";
	private String customerCouponTable = "Customer_Coupon";
	private String companyCouponTable = "Company_Coupon";
	private int dboCreateCount;
	private int dboDropCount;
	
	public DatabaseInfrastructure() {
		super();
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public String getCouponTable() {
		return couponTable;
	}

	public String getCustomerTable() {
		return customerTable;
	}

	public String getCompanyTable() {
		return companyTable;
	}

	public String getCustomerCouponTable() {
		return customerCouponTable;
	}

	public String getCompanyCouponTable() {
		return companyCouponTable;
	}

	/**
	 * Create database object via sending URL connection
	 * @throws CouponSystemException
	 */
	public boolean createDatabase() throws CouponSystemException {
		
		String url = "jdbc:mysql://localhost:3306?useSSL=false";
		//String url = "jdbc:sqlserver://localhost:1433;integratedSecurity=true";
		//String url = "jdbc:derbyclient://localhost:1527/CouponDB; create=true";
		String sql = "CREATE DATABASE " + databaseName;
		boolean execute = false;
		
		try (
			Connection con = DriverManager.getConnection(url, username, password);
			Statement stmt = con.createStatement();)
			{
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboCreateCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to create database " + databaseName, e);
		}
		return execute;
	}
	
	/**
	 * Delete database object via sending URL connection
	 * @throws CouponSystemException
	 */
	public boolean dropDatabase() throws CouponSystemException {
		
		String url = "jdbc:mysql://localhost:3306?useSSL=false";
		//String url = "jdbc:sqlserver://localhost:1433;integratedSecurity=true";
		String sql = "DROP DATABASE " + databaseName;
		boolean execute = false;
		
		try (
			Connection con = DriverManager.getConnection(url, username, password);
			Statement stmt = con.createStatement();)
			{
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboDropCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to delete database " + databaseName, e);
		}
		return execute;
	}
	
	/**
	 * Create Customer table object via sending SQL <code>Statement</code> to the database
	 * @throws CouponSystemException
	 */
	public boolean createCustomerTable() throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "CREATE TABLE " + customerTable
				+ " (ID BIGINT PRIMARY KEY, CUST_NAME VARCHAR(25) NOT NULL UNIQUE, PASSWORD VARCHAR(25))";
		boolean execute = false;
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboCreateCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(customerTable + " Table creation failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	/**
	 * Delete Customer table object via sending SQL <code>Statement</code> to the database
	 * @throws CouponSystemException
	 */
	public boolean dropCustomerTable() throws CouponSystemException {
		
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "DROP TABLE " + customerTable;
		boolean execute = false;
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboDropCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(customerTable + " Table deletion failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	/**
	 * Create Company table object via sending SQL <code>Statement</code> to the database
	 * @throws CouponSystemException
	 */
	public boolean createCompanyTable() throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "CREATE TABLE " + companyTable 
				+ " (ID BIGINT PRIMARY KEY, COMP_NAME VARCHAR(25) NOT NULL UNIQUE, PASSWORD VARCHAR(25), EMAIL VARCHAR(50))";
		boolean execute = false;
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboCreateCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(companyTable + " Table creation failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	
	/**
	 * Delete Company table object via sending SQL <code>Statement</code> to the database
	 * @throws CouponSystemException
	 */
	public boolean dropCompanyTable() throws CouponSystemException {
		
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "DROP TABLE " + companyTable;
		boolean execute = false;
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboDropCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(companyTable + " Table deletion failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	
	/**
	 * Create Coupon table object via sending SQL <code>Statement</code> to the database
	 * @throws CouponSystemException
	 */
	public boolean createCouponTable() throws CouponSystemException {
		
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "CREATE TABLE " + couponTable 
				+ " (ID BIGINT PRIMARY KEY, TITLE VARCHAR(25) NOT NULL UNIQUE, START_DATE DATE, END_DATE DATE, AMOUNT INTEGER, TYPE VARCHAR(25), MESSAGE VARCHAR(255),"
				+ " PRICE FLOAT, IMAGE VARCHAR(255))";
		boolean execute = false;
		
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboCreateCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(couponTable + " Table creation failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	
	/**
	 * Delete Coupon table object via sending SQL <code>Statement</code> to the database
	 * @throws CouponSystemException
	 */
	public boolean dropCouponTable() throws CouponSystemException {
		
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "DROP TABLE " + couponTable;
		boolean execute = false;
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboDropCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(couponTable + " Table deletion failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	
	/**
	 * Create CustomerCoupon table object via sending SQL <code>Statement</code> to the database.
	 * This table object has a Primary Key and Foreign Key relations to the Customer table and Coupon table objects.
	 * The relationship between the tables is defined in a way that on update and deletion from the Parent table (Customer and Coupon tables)
	 * is affecting this table as well.
	 * @throws CouponSystemException
	 */
	public boolean createCustomerCouponTable() throws CouponSystemException {
		
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "CREATE TABLE " + customerCouponTable
				+ "(CUST_ID BIGINT, COUPON_ID BIGINT, PRIMARY KEY (CUST_ID, COUPON_ID), "
				+ "FOREIGN KEY (CUST_ID) REFERENCES Customer (ID) ON UPDATE CASCADE ON DELETE CASCADE, "
				+ "FOREIGN KEY (COUPON_ID) REFERENCES Coupon (ID) ON UPDATE CASCADE ON DELETE CASCADE)";
		boolean execute = false;
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboCreateCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(customerCouponTable + " Table creation failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	
	/**
	 * Delete CustomerCoupon table object via sending SQL <code>Statement</code> to the database.
	 * @throws CouponSystemException
	 */
	public boolean dropCustomerCouponTable() throws CouponSystemException {
		
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "DROP TABLE " + customerCouponTable;
		boolean execute = false;
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboDropCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(customerCouponTable + " Table deletion failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	
	/**
	 * Create CompanyCoupon table object via sending SQL <code>Statement</code> to the database.
	 * This table object has a Primary Key and Foreign Key relations to the Company table and Coupon table objects.
	 * The relationship between the tables is defined in a way that on update and deletion from the Parent table (Company and Coupon tables)
	 * is affecting this table as well.
	 * @throws CouponSystemException
	 */
	public boolean createCompanyCouponTable() throws CouponSystemException {
		
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "CREATE TABLE " + companyCouponTable
				+ "(COMP_ID BIGINT, COUPON_ID BIGINT, PRIMARY KEY (COMP_ID, COUPON_ID), "
				+ "FOREIGN KEY (COMP_ID) REFERENCES Company (ID) ON UPDATE CASCADE ON DELETE CASCADE, "
				+ "FOREIGN KEY (COUPON_ID) REFERENCES Coupon (ID) ON UPDATE CASCADE ON DELETE CASCADE)";
		boolean execute = false;
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboCreateCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(companyCouponTable + " Table creation failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	
	/**
	 * Delete CompanyCoupon table object via sending SQL <code>Statement</code> to the database.
	 * @throws CouponSystemException
	 */
	public boolean dropCompanyCouponTable() throws CouponSystemException {
		
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "DROP TABLE " + companyCouponTable;
		boolean execute = false;
		try (Statement stmt = con.createStatement();){
			if (stmt.executeUpdate(sql) == 0) {
				execute = true;
				dboDropCount++;
			}
		} catch (SQLException e) {
			throw new CouponSystemException(companyCouponTable + " Table deletion failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return execute;
	}
	
	public boolean databaseCreationSummary() {
		
		boolean summary = false;
		if (dboCreateCount == 5) {
			summary = true;
		}
		return summary;
	}
	
	public boolean databaseDropSummary() {
		
		boolean summary = false;
		if (dboDropCount == 6) {
			summary = true;
		}
		return summary;
	}
}
