package databaseMain;

import couponsystem.ConnectionPool;
import couponsystem.CouponSystemException;
import databaseMain.DatabaseInfrastructure;

/**
 * The objective of this class is to delete the database object and table objects
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public class MainDropDB {

	public static void main(String[] args) throws CouponSystemException {
		
		DatabaseInfrastructure db = new DatabaseInfrastructure();
		
		System.out.println("=== Staring Tables Deletion Process ===");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep", e);
		}
		
		System.out.println("\n=== Deleting Company_Coupon Table ===");
		if (db.dropCompanyCouponTable() == true) {
			System.out.println(db.getCompanyCouponTable() + " table deleted");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep", e);
		}
		
		System.out.println("\n=== Deleting Customer_Coupon Table ===");
		if (db.dropCustomerCouponTable() == true) {
			System.out.println(db.getCustomerCouponTable() + " table deleted");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep", e);
		}
		
		System.out.println("\n=== Deleting Company Table ===");
		if (db.dropCompanyTable() == true) {
			System.out.println(db.getCompanyTable() + " table deleted");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep", e);
		}
		
		System.out.println("\n=== Deleting Customer Table ===");
		if (db.dropCustomerTable() == true) {
			System.out.println(db.getCustomerTable() + " table deleted");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep", e);
		}
		
		System.out.println("\n=== Deleting Coupon Table ===");
		if (db.dropCouponTable() == true) {
			System.out.println(db.getCouponTable() + " table deleted");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep", e);
		}
		
		ConnectionPool.getInstance().closeAllConnections();

		System.out.println("\n=== Deleting CouponDB Database ===");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep", e);
		}		
		if (db.dropDatabase() == true) {
			System.out.println(db.getDatabaseName() + " database deleted");
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new CouponSystemException("Main failed to sleep", e);
		}
		
		if (db.databaseDropSummary() == true) {
			System.out.println("\n=== Database and Tables were Deleted Successfully ===");
			System.out.println(db.getDatabaseName() + " Database\n" + db.getCompanyCouponTable() + " Table\n" 
					+ db.getCustomerCouponTable() + " Table\n" + db.getCompanyTable() + " Table\n" 
					+ db.getCustomerTable() + " Table\n" + db.getCouponTable() + " Table");
		} else {
			System.out.println("Database and Tables Deletion did not Complete Successfully");
		}
		
		
	}

}
