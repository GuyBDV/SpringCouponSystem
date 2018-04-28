package facade;

import java.util.Date;
import java.util.List;

import couponsystem.CouponSystem;
import couponsystem.CouponSystemException;
import dao.CompanyDBDAO;
import dao.CouponDBDAO;
import javabeans.Company;
import javabeans.Coupon;
import javabeans.CouponType;
import facade.ClientType;

/**
* CompanyFacade is designated for managing and querying company 
* related tasks using the DAO layer.
* @author Tomer Banyan
* @author Guy Ben-David
*/
public class CompanyFacade implements CouponClientFacade {

	private CompanyDBDAO companydbdao;
	private CouponDBDAO coupondbdao;
	private Long companyId;
	
	public CompanyFacade() {
		companydbdao = new CompanyDBDAO();
		coupondbdao = new CouponDBDAO();
	}
	
	public CompanyFacade(CouponSystem couponSystem) {
	}
	
	/**
     * Login as Company
     * @param name, password and client type as credentials.
     * @return CompanyFacade if the login credentials are valid.
     * @throws CouponSystemException
     */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws CouponSystemException {
		if (companydbdao.login(name, password) && clientType.equals(ClientType.COMPANY)) {
			this.companyId = companydbdao.getCompanyIdByName(name);
			return this;
		} else {
			throw new CouponSystemException("Login Failed");
		}
	}
	
	/**
	 * Create a coupon object using the DAO layer
	 * @param coupon object
	 * @return coupon object
	 * @throws CouponSystemException
	 */
	public Coupon createCoupon(Coupon coupon) throws CouponSystemException {
		coupondbdao.createCoupon(coupon);
		companydbdao.companyCoupon(companyId, coupon);
		return coupon;
	}
	
	/**
	 * Remove a coupon object using the DAO layer
	 * @param coupon object
	 * @throws CouponSystemException
	 */
	public void removeCoupon(Coupon coupon ) throws CouponSystemException {
		if (coupondbdao.isCouponOfCompany(companyId, coupon)) {
			coupondbdao.removeCoupon(coupon);
		} else {
			throw new CouponSystemException("You are not the owner of the Coupon: " + coupon.getId());
		}
	}
	
	/**
	 * Update a coupon object using the DAO layer
	 * @param coupon object
	 * @throws CouponSystemException
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		if (coupondbdao.isCouponOfCompany(companyId, coupon)) {
			coupondbdao.updateCoupon(coupon);
		} else {
			throw new CouponSystemException("You are not the owner of the Coupon: " + coupon.getId());
		}
	}
	
	/**
	 * Get a company object of the current CompanyFacade using the DAO layer
	 * @return company object
	 * @throws CouponSystemException
	 */
	public Company getCompany() throws CouponSystemException {
		return companydbdao.getCompany(companyId);
	}
	
	/**
	 * Get a coupon object of the current CompanyFacade using the DAO layer
	 * @param coupon ID
	 * @return coupon object
	 * @throws CouponSystemException
	 */
	public Coupon getCoupon(Long id) throws CouponSystemException {
		return companydbdao.getCompanyCoupon(companyId, id);
	}
	
	/**
	 * Get a collection of all coupons objects of the current CompanyFacade
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCoupon() throws CouponSystemException{
		return companydbdao.getCoupons(companyId);
	}
	
	/**
	 * Get a collection of all coupons objects by type of the current CompanyFacade
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCompanyCouponByType(CouponType type) throws CouponSystemException {
		return coupondbdao.getCompanyCouponByType(type, companyId);
	}
	
	/**
	 * Get a collection of all coupons objects by price of the current CompanyFacade
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCouponByPrice(double price) throws CouponSystemException{
		return coupondbdao.getCompanyCouponByPrice(price, companyId);
	}
	
	/**
	 * Get a collection of all coupons objects by date of the current CompanyFacade
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCouponByDate(Date date) throws CouponSystemException{
		return coupondbdao.getCompanyCouponByDate(date, companyId);
	}
}
