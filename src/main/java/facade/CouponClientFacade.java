package facade;

import couponsystem.CouponSystemException;

/**
 * This interface is implemented by and lists the method used by the Admin Facade, Company Facade and Customer Facade
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public interface CouponClientFacade {
	
	CouponClientFacade login(String name, String password, ClientType clientType) throws CouponSystemException; 
}
