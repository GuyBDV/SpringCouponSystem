package dao;

import java.util.Date;
import java.util.List;

import couponsystem.CouponSystemException;
import javabeans.Coupon;
import javabeans.CouponType;

/**
 * This interface lists methods used by all Facades
 * and is implemented by CouponDBDAO class
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public interface CouponDAO {
	
	Coupon createCoupon(Coupon coupon) throws CouponSystemException;
	
	void removeCoupon(Coupon coupon) throws CouponSystemException;
	
	void removeCouponOfCompany(Long compId) throws CouponSystemException;

	void updateCoupon(Coupon coupon) throws CouponSystemException;
	
	Coupon getCoupon(Long id) throws CouponSystemException;
	
	List<Coupon> getAllCoupon() throws CouponSystemException;
	
	List<Coupon> getCouponByType(CouponType type) throws CouponSystemException;
	
	List<Coupon> getCompanyCouponByType(CouponType type, Long companyId) throws CouponSystemException;

	List<Coupon> getCompanyCouponByPrice(double price, Long companyId) throws CouponSystemException;

	List<Coupon> getCompanyCouponByDate(Date date, Long companyId) throws CouponSystemException;

	boolean isCouponOfCompany(Long companyId, Coupon coupon) throws CouponSystemException;

	List<Coupon> getCustomerCouponByType(CouponType type, Long customerId) throws CouponSystemException;

	List<Coupon> getCustomerCouponByPrice(double price, Long customerId) throws CouponSystemException;

	void removeExpirationDateCoupons() throws CouponSystemException;

}
