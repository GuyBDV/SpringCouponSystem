package couponsystem;

import couponsystem.ConnectionPool;
import couponsystem.CouponSystemException;
import facade.AdminFacade;
import facade.ClientType;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

/**
 * The objective of this class is to initiate the Coupon System and the Daily Thread
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public class CouponSystem implements CouponClientFacade {
	
	private static CouponSystem instance = new CouponSystem();
	
	private CouponSystem(){
	}
	
	/**
	 * Singleton for the Coupon System
	 * @return instance
	 */
	public static CouponSystem getInstance(){
		return instance;
	}
	
	/**
	 * @param name, password, client type as credentials.
	 * @returns a Coupon Client Facade associated with the login credentials
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws CouponSystemException {
		CouponClientFacade clientFacade = null;
		switch (clientType) {
		case CUSTOMER:
			clientFacade = new CustomerFacade();
			break;
		case COMPANY:
			clientFacade = new CompanyFacade();
			break;
		case ADMIN:
			clientFacade = new AdminFacade();
			break;
		}
		return clientFacade.login(name, password, clientType);
	}
	
	/**
	 * Coupon System shut down responsible for interrupting the daily thread
	 * and calling for the method which closes all connections to the database
	 * @throws CouponSystemException
	 */
	public void shutDown() throws CouponSystemException {
		if (ConnectionPool.getInstance().ConPoolSize() == 10) {
			ConnectionPool.getInstance().closeAllConnections();
		}

	}
	
}
