package facade;

import java.util.List;

import couponsystem.CouponSystemException;
import dao.CompanyDBDAO;
import dao.CouponDBDAO;
import dao.CustomerDBDAO;
import javabeans.Company;
import javabeans.Customer;

/**
* AdminFacade is designated for managing and querying Admin 
* related tasks using the DAO layer.
* @author Tomer Banyan
* @author Guy Ben-David
*/

public class AdminFacade implements CouponClientFacade {
	
	private CompanyDBDAO companydbdao;
	private CustomerDBDAO customerdbdao;
	private CouponDBDAO coupondbdao;
	
	public AdminFacade( ) {
		companydbdao = new CompanyDBDAO();
		customerdbdao = new CustomerDBDAO();
		coupondbdao = new CouponDBDAO();
	}
	
	/**
     * Login as Admin.
     * @param name, password and client type as credentials.
     * @return AdminFacade if the login credentials are valid.
     * @throws CouponSystemException
     */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws CouponSystemException {
		if (name.equals("admin") && password.equals("1234")  && clientType.equals(ClientType.ADMIN)) {
			return this;
		} else {
			throw new CouponSystemException("Login Failed");
		}
	}
	
	/**
	 * Create a company object using the DAO layer
	 * @param company object
	 * @return company object
	 * @throws CouponSystemException
	 */
	public Company createCompany(Company company) throws CouponSystemException {
		return companydbdao.createCompany(company);
	}
	
	/**
	 * Remove a company object using the DAO layer
	 * @param company object
	 * @throws CouponSystemException
	 */
	public void removeCompany(Company company) throws CouponSystemException {
		coupondbdao.removeCouponOfCompany(company.getId());
		companydbdao.removeCompany(company);
	}
	
	/**
	 * Update a company object using the DAO layer
	 * @param company object
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		companydbdao.updateCompany(company);
	}
	
	/**
	 * Get a company object using the DAO layer
	 * @param company ID
	 * @return company object
	 * @throws CouponSystemException
	 */
	public Company getCompany(Long id) throws CouponSystemException {
		return companydbdao.getCompany(id);
	}
	
	/**
	 * Get a collection of all companies objects using the DAO layer
	 * @return a sorted collection of companies
	 * @throws CouponSystemException
	 */
	public List<Company> getAllCompanies() throws CouponSystemException {
		return companydbdao.getAllCompanies();
	}
	
	/**
	 * Create a customer object using the DAO layer
	 * @param customer object
	 * @return customer object
	 * @throws CouponSystemException
	 */
	public Customer createCustomer(Customer customer) throws CouponSystemException {
		return customerdbdao.createCustomer(customer);
	}
	
	/**
	 * Remove a customer object using the DAO layer
	 * @param customer object
	 * @throws CouponSystemException
	 */
	public void removeCustomer(Customer customer) throws CouponSystemException {
		customerdbdao.removeCustomer(customer);
	}
	
	/**
	 * Update a customer object using the DAO layer
	 * @param customer object
	 * @throws CouponSystemException
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		customerdbdao.updateCustomer(customer);
	}
	
	/**
	 * Get a customer object using the DAO layer
	 * @param customer ID
	 * @return customer object
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(Long id) throws CouponSystemException {
		return customerdbdao.getCustomer(id);
	}
	
	/**
	 * Get a collection of all customers objects using the DAO layer
	 * @return a sorted collection of customers
	 * @throws CouponSystemException
	 */
	public List<Customer> getAllCustomer() throws CouponSystemException {
		return customerdbdao.getAllCustomer();
	}

}
