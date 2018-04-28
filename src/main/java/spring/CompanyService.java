package spring;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import facade.ClientType;
import facade.CompanyFacade;
import javabeans.Company;
import javabeans.Coupon;
import javabeans.CouponType;

@RestController
public class CompanyService {
	
	public CompanyService() {
		
	}
	
	private CompanyFacade getFacadeFromSession(HttpSession session) throws CouponSystemException {
		CompanyFacade companyfacade = (CompanyFacade) session.getAttribute("company");
		if (companyfacade == null) {
			throw new RuntimeException("You are not logged in");
		}
		return companyfacade;
	}
	
	@RequestMapping(
					path="/api/company/login/{username}/{password}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Message login(
						@PathVariable("username") String username, 
						@PathVariable("password") String password, 
						HttpSession session) throws CouponSystemException {
		CompanyFacade companyfacade = (CompanyFacade) CouponSystem.getInstance().login(username, password, ClientType.COMPANY);
		if (username != null && !username.trim().isEmpty()) {
			session.setAttribute("company", companyfacade);
			return new Message("Session ID: " + session.getId());
		}
		return new Message("Login Failed");
	}
	
	@RequestMapping(
					path="/api/company/logout",
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
					path="/api/company/coupons",
					method=RequestMethod.POST,
					consumes=MediaType.APPLICATION_JSON_VALUE,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon createCoupon(@RequestBody Coupon coupon, HttpSession session) throws CouponSystemException {
		if (coupon.getId() != null) {
			throw new CouponSystemException("Coupon must not have an ID");
		}
		CompanyFacade companyfacade = getFacadeFromSession(session);
		return companyfacade.createCoupon(coupon);
	}
	
	@RequestMapping(
					path="/api/company/coupons/{id}",
					method=RequestMethod.DELETE
					)
	public void removeCoupon(@PathVariable("id") Long id, HttpSession session) throws CouponSystemException {
		CompanyFacade companyfacade = getFacadeFromSession(session);
		Coupon coupon = companyfacade.getCoupon(id);
		companyfacade.removeCoupon(coupon);
	}
	
	@RequestMapping(
					path="/api/company/coupons/{id}",
					method=RequestMethod.PUT,
					consumes=MediaType.APPLICATION_JSON_VALUE
					)
	public void updateCoupon(@PathVariable("id") Long id, @RequestBody Coupon coupon, HttpSession session) throws CouponSystemException {
		CompanyFacade companyfacade = getFacadeFromSession(session);
		if (!id.equals(coupon.getId())) {
			throw new CouponSystemException("ID's do not match");
		}
		companyfacade.updateCoupon(coupon);
	}
	
	@RequestMapping(
					path="/api/company/coupons",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Company getCompany(HttpSession session) throws CouponSystemException {
		CompanyFacade companyfacade = getFacadeFromSession(session);
		Company company = companyfacade.getCompany();
		return company;
	}
	
	@RequestMapping(
					path="/api/company/coupons/{id}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon getCoupon(@PathVariable("id") Long id, HttpSession session) throws CouponSystemException {
		CompanyFacade companyfacade = getFacadeFromSession(session);
		Coupon coupon = companyfacade.getCoupon(id);
		return coupon;
	}
	
	@RequestMapping(
					path="/api/company/allcoupons",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon[] getAllCoupons(HttpSession session) throws CouponSystemException {
		CompanyFacade companyfacade = getFacadeFromSession(session);
		return companyfacade.getAllCoupon().toArray(new Coupon[0]);
	}
	
	@RequestMapping(
					path="/api/company/getcouponbytype/{type}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon[] getCouponByType(@PathVariable("type") CouponType type, HttpSession session) throws CouponSystemException {
		CompanyFacade companyfacade = getFacadeFromSession(session);
		return companyfacade.getCompanyCouponByType(type).toArray(new Coupon[0]);
	}
	
	@RequestMapping(
					path="/api/company/getcouponbyprice/{price}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon[] getCouponByPrice(@PathVariable("price") double price, HttpSession session) throws CouponSystemException {
		CompanyFacade companyfacade = getFacadeFromSession(session);
		return companyfacade.getCouponByPrice(price).toArray(new Coupon[0]);
	}
	
	@RequestMapping(
					path="/api/company/getcouponbydate/{endDate}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon[] getCouponByDate(@PathVariable("endDate") String date, HttpSession session) throws CouponSystemException, ParseException {
		CompanyFacade companyfacade = getFacadeFromSession(session);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = format.parse(date);
		return companyfacade.getCouponByDate(newDate).toArray(new Coupon[0]);
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
