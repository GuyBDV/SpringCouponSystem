package facade;

import java.util.List;

import couponsystem.CouponSystemException;
import dao.CouponDBDAO;
import dao.CustomerDBDAO;
import javabeans.Coupon;
import javabeans.CouponType;

/**
* CustomerFacade is designated for managing and querying customer 
* related tasks using the DAO layer.
* @author Tomer Banyan
* @author Guy Ben-David
*/
public class CustomerFacade implements CouponClientFacade {
	
	private CustomerDBDAO customerdbdao;
	private CouponDBDAO coupondbdao;
	private Long customerId;
	
	public CustomerFacade() {
		customerdbdao = new CustomerDBDAO();
		coupondbdao = new CouponDBDAO();
	}
	
	/**
     * Login as Customer
     * @param name, password and client type as credentials.
     * @return CustomerFacade if the login credentials are valid
     * @throws CouponSystemException
     */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws CouponSystemException {
		if (customerdbdao.login(name, password) && clientType.equals(ClientType.CUSTOMER)) {
			this.customerId = customerdbdao.getCustomerIdByName(name);
			return this;
		} else {
			throw new CouponSystemException("Login Failed");
		}
	}
	
	/**
	 * Associate a coupon to a customer using the DAO layer
	 * @param coupon object
	 * @return coupon object
	 * @throws CouponSystemException
	 */
	public Coupon purchaseCoupon(Coupon coupon) throws CouponSystemException {
		customerdbdao.customerPurchaseCoupon(customerId, coupon);
		return coupon;
	}
	
	/**
	 * Get a collection of all coupons objects of the current CustomerFacade
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllPurchasedCoupons() throws CouponSystemException {
		return customerdbdao.getCoupons(customerId);
	}
	
	/**
	 * Get a collection of all coupons objects by type of the current CustomerFacade
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllPurchasedCouponByType(CouponType type) throws CouponSystemException {
		return coupondbdao.getCustomerCouponByType(type, customerId);
	}
	
	/**
	 * Get a collection of all coupons objects by price of the current CustomerFacade
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllPurchasedCouponByPrice(double price) throws CouponSystemException {
		return coupondbdao.getCustomerCouponByPrice(price, customerId);
	}
	
	/**
	 * Get a collection of all coupons available for purchase from all companies
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllSystemCoupons() throws CouponSystemException {
		return coupondbdao.getAllCoupon();
	}
}
