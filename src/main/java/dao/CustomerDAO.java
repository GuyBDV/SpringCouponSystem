package dao;

import java.util.List;

import couponsystem.CouponSystemException;
import javabeans.Coupon;
import javabeans.Customer;

/**
 * This interface lists methods used by the Admin Facade and the Customer Facade
 * and is implemented by CustomerDBDAO class
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public interface CustomerDAO {
	
	Customer createCustomer(Customer customer) throws CouponSystemException;
	
	void removeCustomer(Customer customer) throws CouponSystemException;
	
	void updateCustomer(Customer customer) throws CouponSystemException;
	
	Customer getCustomer(Long id) throws CouponSystemException;
	
	List<Customer> getAllCustomer() throws CouponSystemException;
	
	List<Coupon> getCoupons(Long customerId) throws CouponSystemException;
	
	boolean login(String custName, String password) throws CouponSystemException;
	
	void customerPurchaseCoupon(Long customerId, Coupon coupon) throws CouponSystemException;
	
	Long getCustomerIdByName(String name) throws CouponSystemException;

}
