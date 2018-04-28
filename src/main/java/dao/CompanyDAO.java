package dao;

import java.util.List;

import couponsystem.CouponSystemException;
import javabeans.Company;
import javabeans.Coupon;

/**
 * This interface lists methods used by the Admin Facade and the Company Facade
 * and is implemented by CompanyDBDAO class
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public interface CompanyDAO {
	
	Company createCompany(Company company) throws CouponSystemException;
	
	void removeCompany(Company company) throws CouponSystemException;
	
	void updateCompany(Company company) throws CouponSystemException;
	
	Company getCompany(Long id) throws CouponSystemException;
	
	List<Company> getAllCompanies() throws CouponSystemException;
	
	Coupon getCompanyCoupon(Long companyId, Long couponid) throws CouponSystemException;

	List<Coupon> getCoupons(Long companyId) throws CouponSystemException;

	boolean login(String compName, String password) throws CouponSystemException;
	
	void companyCoupon(Long companyId, Coupon coupon) throws CouponSystemException;

	Long getCompanyIdByName(String name) throws CouponSystemException;

}
