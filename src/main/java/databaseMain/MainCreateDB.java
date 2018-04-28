package databaseMain;

import couponsystem.CouponSystemException;
import databaseMain.DatabaseInfrastructure;

/**
 * The objective of this class is to create the database object and table objects
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public class MainCreateDB {

	public static void main(String[] args) throws CouponSystemException {
		
		DatabaseInfrastructure db = new DatabaseInfrastructure();
		
		System.out.println("=== Staring Database Creation Process ===");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep", e);
		}
		
		System.out.println("\n=== Creating Database Object ===");
		if (db.createDatabase() == true) {
			System.out.println(db.getDatabaseName() + " database created");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep");
		}
		
		System.out.println("\n=== Creating Coupon Table ===");
		if (db.createCouponTable() == true) {
			System.out.println(db.getCouponTable() + " table created");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep");
		}
		
		System.out.println("\n=== Creating Customer Table ===");
		if (db.createCustomerTable() == true) {
			System.out.println(db.getCustomerTable() + " table created");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep");
		}
		
		System.out.println("\n=== Creating Company Table ===");
		if (db.createCompanyTable() == true) {
			System.out.println(db.getCompanyTable() + " table created");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep");
		}
		
		System.out.println("\n=== Creating Customer_Coupon Table ===");
		if (db.createCustomerCouponTable() == true) {
			System.out.println(db.getCustomerCouponTable() + " table created");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep");
		}
		
		System.out.println("\n=== Creating Company_Coupon Table ===");
		if (db.createCompanyCouponTable() == true) {
			System.out.println(db.getCompanyCouponTable() + " table created");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep");
		}

		
		if (db.databaseCreationSummary() == true) {
			System.out.println("\n=== Database Objects Creation Completed Successfully ===");
			System.out.println(db.getDatabaseName() + " Database\n" + db.getCustomerTable() + " Table\n" 
					+ db.getCompanyTable() + " Table\n" + db.getCouponTable() + " Table\n" 
					+ db.getCustomerCouponTable() + " Table\n" + db.getCompanyCouponTable() + " Table\n");
		} else {
			System.out.println("Database and Tables Objects Creation did not Complete Successfully");
		}
		
	}
}
