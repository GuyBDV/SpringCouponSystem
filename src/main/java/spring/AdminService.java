package spring;


import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import couponsystem.CouponSystem;
import couponsystem.CouponSystemException;
import facade.AdminFacade;
import facade.ClientType;
import javabeans.Company;
import javabeans.Customer;

@RestController
public class AdminService {
	
	public AdminService() {
		
	}
	
	private AdminFacade getFacadeFromSession(HttpSession session) {
		AdminFacade adminfacade = (AdminFacade) session.getAttribute("admin");
		if (adminfacade == null) {
			throw new RuntimeException("You are not logged in");
		}
		return adminfacade;
	}
	
	@RequestMapping(
					path="/api/admin/login/{username}/{password}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Message login(@PathVariable("username") String username,
						 @PathVariable("password") String password,
						 HttpSession session) throws CouponSystemException {
		AdminFacade adminfacade = (AdminFacade) CouponSystem.getInstance().login(username, password, ClientType.ADMIN);
		if (username != null && !username.trim().isEmpty()) {
			session.setAttribute("admin", adminfacade);
			return new Message("Session ID: " + session.getId());
		}
		return new Message("Login Failed");
	}
	
	@RequestMapping(
					path="/api/admin/logout",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Message logout(HttpSession session) throws CouponSystemException {
		if (session != null) {
			session.invalidate();
			return new Message("Session Invalidated Successfully");
		}
		return new Message("Session Invalidation Failed");
	}
	
	@RequestMapping(
					path="/api/admin/companies",
					method=RequestMethod.POST,
					consumes=MediaType.APPLICATION_JSON_VALUE,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Company createCompany(@RequestBody Company company, HttpSession session) throws CouponSystemException {
		if (company.getId() != null) {
			throw new CouponSystemException("Company must not have an ID");
		}
		AdminFacade adminfacade = getFacadeFromSession(session);
		return adminfacade.createCompany(company);
	}
	
	@RequestMapping(
					path="/api/admin/companies/{id}",
					method=RequestMethod.DELETE
					)
	public void removeCompany(@PathVariable("id") Long id, HttpSession session) throws CouponSystemException {
		AdminFacade adminfacade = getFacadeFromSession(session);
		Company company = adminfacade.getCompany(id);
		adminfacade.removeCompany(company);
	}
	
	@RequestMapping(
					path="/api/admin/companies/{id}",
					method=RequestMethod.PUT,
					consumes=MediaType.APPLICATION_JSON_VALUE
					)
	public void updateCompany(@PathVariable("id") Long id, @RequestBody Company company, HttpSession session) throws CouponSystemException {
		AdminFacade adminfacade = getFacadeFromSession(session);
		if (!id.equals(company.getId())) {
			throw new CouponSystemException("ID's do not match");
		}
		adminfacade.updateCompany(company);
	}
	
	@RequestMapping(
					path="/api/admin/companies/{id}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Company getCompany(@PathVariable("id") Long id, HttpSession session) throws CouponSystemException{
		AdminFacade adminfacade = getFacadeFromSession(session);
		return adminfacade.getCompany(id);
	}
	
	@RequestMapping(
			path="/api/admin/companies",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public Company[] getAllCompanies (HttpSession session) throws CouponSystemException {
		AdminFacade adminfacade = getFacadeFromSession(session);
		return adminfacade.getAllCompanies().toArray(new Company[0]);
	}
	
	@RequestMapping(
			path="/api/admin/customers",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public Customer createCustomer(@RequestBody Customer customer, HttpSession session) throws CouponSystemException {
		if (customer.getId() != null) {
			throw new CouponSystemException("Customer must not have an ID");
		}
		AdminFacade adminfacade = getFacadeFromSession(session);
		return adminfacade.createCustomer(customer);
	}
	
	@RequestMapping(
			path="/api/admin/customers/{id}",
			method=RequestMethod.DELETE
			)
	public void removeCustomer(@PathVariable("id") Long id, HttpSession session) throws CouponSystemException {
		AdminFacade adminfacade = getFacadeFromSession(session);
		Customer customer = adminfacade.getCustomer(id);
		adminfacade.removeCustomer(customer);
	}
	
	@RequestMapping(
			path="/api/admin/customers/{id}",
			method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE
			)
	public void updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer, HttpSession session) throws CouponSystemException {
		AdminFacade adminfacade = getFacadeFromSession(session);
		if (!id.equals(customer.getId())) {
			throw new CouponSystemException("ID's do not match");
		}
		adminfacade.updateCustomer(customer);
	}
	
	@RequestMapping(
			path="/api/admin/customers/{id}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public Customer getCustomer(@PathVariable("id") Long id, HttpSession session) throws CouponSystemException {
		AdminFacade adminfacade = getFacadeFromSession(session);
		return adminfacade.getCustomer(id);
	}
	
	@RequestMapping(
			path="/api/admin/customers",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public Customer[] getAllCustomers(HttpSession session) throws CouponSystemException {
		AdminFacade adminfacade = getFacadeFromSession(session);
		return adminfacade.getAllCustomer().toArray(new Customer[0]);
	}
	
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Message handleException(Exception e) {
		String message = e.getMessage();
		if (message == null) {
			message = "Error while executing method";
		}
		return new Message(message);
	}
	
}