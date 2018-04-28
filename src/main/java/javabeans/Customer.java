package javabeans;

import java.util.List;

public class Customer {
	
	private Long id;
	private String custName;
	private String password;
	private List<Coupon> coupons;
	
	public Customer() {
		
	}

	public Customer(String custName, String password) {
		super();
		this.custName = custName;
		this.password = password;
	}

	public Customer(Long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [ID: " + id + ", Name: " + custName + ", Password: " + password + "]\n";
	}
}
