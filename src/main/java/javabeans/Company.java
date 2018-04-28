package javabeans;

import java.util.List;

public class Company {
	
	private Long id;
	private String compName;
	private String password;
	private String email;
	private List<Coupon> coupons;
	
	public Company() {
		
	}
	
	public Company(String compName, String password, String email) {
		super();
		this.compName = compName;
		this.password = password;
		this.email = email;
	}



	public Company(Long id, String compName, String password, String email) {
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> couponlist) {
		this.coupons = couponlist;
	}

	@Override
	public String toString() {
		return "Company [ID: " + id + ", Name: " + compName + ", Password: " + password + ", Email: " + email + "]\n";
	}

	

	
	
	
	
}
