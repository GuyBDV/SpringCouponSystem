package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import couponsystem.ConnectionPool;
import couponsystem.CouponSystemException;
import javabeans.Coupon;
import javabeans.Customer;

public class CustomerDBDAO implements CustomerDAO {

	public CustomerDBDAO() {

	}
	/**
	 * Create a customer object via sending SQL <code>PreparedStatement</code>
	 * to the database using <code>PreparedStatement</code> parameters. 
	 * Each parameter represents a customer attribute.
	 * @param customer object.
	 * @return customer object.
	 * @throws CouponSystemException.
	 */
	@Override
	public Customer createCustomer(Customer customer) throws CouponSystemException {
		if (customer.getCustName() != null && !customer.getCustName().trim().isEmpty()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Connection con = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO Customer VALUES(?,?,?)";
			Long time = System.currentTimeMillis()  % 10000000;
			customer.setId(time);
			try (PreparedStatement pstmt = con.prepareStatement(sql);) {
				pstmt.setLong(1, customer.getId());
				pstmt.setString(2, customer.getCustName());
				pstmt.setString(3, customer.getPassword());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemException("Customer: " + customer.getCustName() + " creation failed", e);
			}finally {
				ConnectionPool.getInstance().returnConnection(con);
			} 
		} else {
			throw new CouponSystemException("Customer name must not be empty");
		}
		return customer;
	}
	
	/**
	 * Delete a customer object via sending SQL <code>PreparedStatement</code> to the database.
	 * @param customer object.
	 * @throws CouponSystemException
	 */
	@Override
	public void removeCustomer(Customer customer) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "DELETE FROM Customer WHERE ID=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, customer.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Customer: " + customer.getCustName() + " could not be removed",e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Update a customer object via sending SQL <code>PreparedStatement</code> to the database. 
	 * Attribute that is permitted to be updated is the customer's Password.
	 * @param customer object.
	 * @throws CouponSystemException
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "UPDATE Customer SET PASSWORD=? WHERE ID=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, customer.getPassword());
			pstmt.setLong(2, customer.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Customer: " + customer.getCustName() + " update failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Get a single customer object via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database. 
	 * @param customer ID.
	 * @return customer object.
	 * @throws CouponSystemException.
	 */
	@Override
	public Customer getCustomer(Long id) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Customer WHERE ID=?";
		Customer customer = new Customer();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					customer.setId(rs.getLong(1));
					customer.setCustName(rs.getString(2));
					customer.setPassword(rs.getString(3));
				} 
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new CouponSystemException("Customer with ID: " + id + " could not be found");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return customer;
	}
	
	/**
	 * Get a list of all customers objects via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database.
	 * @return a sorted collection of customers
	 * @throws CouponSystemException
	 */
	@Override
	public List<Customer> getAllCustomer() throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Customer";
		Customer customer = new Customer();
		List<Customer> customerlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				customer.setId(rs.getLong(1));
				customer.setCustName(rs.getString(2));
				customer.setPassword(rs.getString(3));
				customerlist.add(new Customer(customer.getId(), customer.getCustName(), customer.getPassword()));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Customer list could not be retrieved", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
			return customerlist;
		
	}
	
	/**
	 * Get a list of all customer coupons objects, via sending SQL 
	 * <code>PreparedStatement</code> and <code>ResultSet</code> to the database.
	 * @param customer ID.
	 * @return a sorted collection of coupons.
	 * @throws CouponSystemException
	 */
	@Override
	public List<Coupon> getCoupons(Long customerId) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Customer_Coupon WHERE CUST_ID=?";
		CouponDBDAO coupondbdao = new CouponDBDAO();
		Coupon coupon = new Coupon();
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, customerId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					Long couponid = rs.getLong(2);
					coupon = coupondbdao.getCoupon(couponid);
					coupon.setAmount(1);
					couponlist.add(coupon);
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon list could not be retrieved", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return couponlist;
	}
	
	/**
	 * Customer login via sending SQL <code>PreparedStatement</code> and <code>ResultSet</code> to the database
	 * @param customer name, customer password.
	 * @return true or false if the login parameters match the values in the database
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String custName, String password) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Customer WHERE CUST_NAME=? AND PASSWORD=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, custName);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Login Execution Failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Associate a customer with a coupon by inserting a customer ID and the customer's coupon ID 
	 * into Customer_Coupon join table via sending SQL <code>PreparedStatement</code> 
	 * and <code>ResultSet</code> to the database.
	 * @param customer ID, coupon object.
	 * This method takes into account the following conditions:
	 * coupon was not previously purchase by the customer
	 * coupon is not out of stock
	 * coupon is not expired
	 * coupon's amount is updated accordingly upon successful purchase
	 * @throws CouponSystemException
	 */
	@Override
	public void customerPurchaseCoupon(Long customerId, Coupon coupon) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Customer_Coupon WHERE CUST_ID=? AND COUPON_ID=?";
		Date today = new Date();
		try (PreparedStatement pstmt1 = con.prepareStatement(sql);) {
			pstmt1.setLong(1, customerId);
			pstmt1.setLong(2, coupon.getId());
			try (ResultSet rs = pstmt1.executeQuery();) {
				if (rs.next()) {
					throw new CouponSystemException(coupon.getTitle() + ": coupon was previously purchased, please purchase a new one");
				} else if (coupon.getAmount() <= 0) {
					throw new CouponSystemException(coupon.getTitle() + ": is out of stock");
				} else if (coupon.getEndDate().compareTo(today) <= 0) {
					throw new CouponSystemException(coupon.getTitle() + ":  coupon has expired");
				} else {
					String sql2 = "INSERT INTO Customer_Coupon (CUST_ID, COUPON_ID) SELECT Customer.ID, Coupon.ID FROM Customer, Coupon WHERE Customer.ID=? AND Coupon.ID=?";
					try (PreparedStatement pstmt2 = con.prepareStatement(sql2);) {
						pstmt2.setLong(1, customerId);
						pstmt2.setLong(2, coupon.getId());
						if (pstmt2.executeUpdate() > 0) {
							String sql3 = "UPDATE Coupon SET AMOUNT=? WHERE ID=?";
							try (PreparedStatement pstmt3 = con.prepareStatement(sql3);) {
								pstmt3.setInt(1, coupon.getAmount() - 1);
								pstmt3.setLong(2, coupon.getId());
								if (pstmt3.executeUpdate() > 0) {
								} else {
									throw new CouponSystemException(
											"Failed to update amount for Coupon: " + coupon.getId());
								}
							}
						} else {
							throw new CouponSystemException("Cannot Assign Coupon " + coupon.getId() + "to Customer");
						}

					}
				}
			}
		}catch (SQLException e) {
			throw new CouponSystemException("Coupon: " + coupon.getId() + " could not be purchased");
		}finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Get a customer ID via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database
	 * @param customer name.
	 * @return customer ID.
	 * @throws CouponSystemException.
	 */
	@Override
	public Long getCustomerIdByName(String name) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Customer WHERE CUST_NAME=?";
		Long customerId = null;
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, name);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					customerId = rs.getLong(1);
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Customer: " + name + " could not be retrieved", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return customerId;
	}
	
}
