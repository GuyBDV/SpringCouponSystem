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
import facade.ClientType;
import facade.CustomerFacade;
import javabeans.Coupon;
import javabeans.CouponType;

@RestController
public class CustomerService {
	
	public CustomerService() {
		
	}
	
	private CustomerFacade getFacadeFromSession(HttpSession session) throws CouponSystemException {
		CustomerFacade customerfacade = (CustomerFacade) session.getAttribute("customer");
		if (customerfacade == null) {
			throw new RuntimeException("You are not logged in");
		}
		return customerfacade;
	}
	
	@RequestMapping(
					path="/api/customer/login/{username}/{password}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Message login(
						@PathVariable("username") String username,
						@PathVariable("password") String password,
						HttpSession session) throws CouponSystemException {
		CustomerFacade customerfacade = (CustomerFacade) CouponSystem.getInstance().login(username, password, ClientType.CUSTOMER);
		if (username != null && !username.trim().isEmpty()) {
			session.setAttribute("customer", customerfacade);
			return new Message("Session ID: " + session.getId());
		}
		return new Message("Login Failed");
	}
	
	@RequestMapping(
					path="/api/customer/logout",
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
					path="/api/customer/getallsystemcoupons",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon[] getAllSystemCoupons(HttpSession session) throws CouponSystemException {
		CustomerFacade customerfacade = getFacadeFromSession(session);
		return customerfacade.getAllSystemCoupons().toArray(new Coupon[0]);
	}
	
	@RequestMapping(
					path="/api/customer/purchasecoupon",
					method=RequestMethod.POST,
					consumes=MediaType.APPLICATION_JSON_VALUE,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon purchaseCoupon(@RequestBody Coupon coupon, HttpSession session) throws CouponSystemException {
		CustomerFacade customerfacade = getFacadeFromSession(session);
		return customerfacade.purchaseCoupon(coupon);
	}
	
	@RequestMapping(
					path="/api/customer/getallpurchasedcoupons",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon[] getAllPurchasedCoupons(HttpSession session) throws CouponSystemException {
		CustomerFacade customerfacade = getFacadeFromSession(session);
		return customerfacade.getAllPurchasedCoupons().toArray(new Coupon[0]);
	}
	
	@RequestMapping(
					path="/api/customer/getallpurchasedcouponsbytype/{type}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon[] getAllPurchasedCouponsByType(@PathVariable("type") CouponType type, HttpSession session) throws CouponSystemException {
		CustomerFacade customerfacade = getFacadeFromSession(session);
		return customerfacade.getAllPurchasedCouponByType(type).toArray(new Coupon[0]);
	}
	
	@RequestMapping(
					path="/api/customer/getallpurchasedcouponsbyprice/{price}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE
					)
	public Coupon[] getAllPurchasedCouponsByPrice(@PathVariable("price") double price, HttpSession session) throws CouponSystemException {
		CustomerFacade customerfacade = getFacadeFromSession(session);
		return customerfacade.getAllPurchasedCouponByPrice(price).toArray(new Coupon[0]);
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
