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
import javabeans.CouponType;

/**
 * CouponDBDAO is designated for managing and querying coupon related tasks
 * either by a Company or a Customer, via SQL statements and queries.
 * @author Guy Ben-David
 * @author Tomer Banyan
 */

public class CouponDBDAO implements CouponDAO{
	
	public CouponDBDAO() {
		
	}
	
	/**
	 * Create a coupon object via sending SQL <code>PreparedStatement</code>
	 * to the database using <code>PreparedStatement</code> parameters. 
	 * Each parameter represents a coupon attribute
	 * @param coupon object.
	 * This method takes into account the following conditions:
	 * valid coupon's start date and end date
	 * valid coupon's price
	 * @return coupon object.
	 * @throws CouponSystemException
	 */
	@Override
	public Coupon createCoupon(Coupon coupon) throws CouponSystemException {
		if (coupon.getTitle() != null && !coupon.getTitle().trim().isEmpty()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Connection con = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO Coupon VALUES(?,?,?,?,?,?,?,?,?)";
			Date today = new Date();
			Long time = System.currentTimeMillis() % 10000000;
			coupon.setId(time);
			try (PreparedStatement pstmt = con.prepareStatement(sql);) {
				pstmt.setLong(1, time);
				pstmt.setString(2, coupon.getTitle());
				pstmt.setDate(3, new java.sql.Date(coupon.getStartDate().getTime()));
				pstmt.setDate(4, new java.sql.Date(coupon.getEndDate().getTime()));
				pstmt.setInt(5, coupon.getAmount());
				pstmt.setString(6, coupon.getType().name());
				pstmt.setString(7, coupon.getMessage());
				pstmt.setDouble(8, coupon.getPrice());
				pstmt.setString(9, coupon.getImage());
				if (new java.sql.Date(coupon.getEndDate().getTime()).compareTo(new java.sql.Date(coupon.getStartDate().getTime())) <= 0) {
					throw new CouponSystemException("Cannot set End Date before or the same as Start Date");
				} else if (new java.sql.Date(coupon.getEndDate().getTime()).compareTo(today) <= 0) {
					throw new CouponSystemException("Cannot set End Date before today's Date");
				} else if (new java.sql.Date(coupon.getStartDate().getTime()).compareTo(today) <= 0) {
					throw new CouponSystemException("Cannot set Start Date before today's date");
				} else if (coupon.getAmount() <= 0) {
					throw new CouponSystemException("Cannot set a negative or zero amount value");
				} else if (coupon.getPrice() <= 0) {
					throw new CouponSystemException("Cannot set a negative or zero price value"); 
				} else {
					pstmt.executeUpdate();
				}
			} catch (SQLException e) {
				throw new CouponSystemException("Coupon: " + coupon.getTitle() + " creation failed", e);
			} finally {
				ConnectionPool.getInstance().returnConnection(con);
			}
		} else {
			throw new CouponSystemException("Coupon title must not be empty");
		}
		return coupon;
	}
	
	/**
	 * Delete a coupon object via sending SQL <code>PreparedStatement</code> to the database. 
	 * @param coupon object.
	 * @throws CouponSystemException
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "DELETE FROM Coupon WHERE ID=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, coupon.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon " + coupon.getTitle() + " could not be removed", e);
		}finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	@Override
	public void removeCouponOfCompany(Long compId) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company_Coupon WHERE COMP_ID=?";
		CouponDBDAO coupondbdao = new CouponDBDAO();
		Coupon coupon = null;
		try (PreparedStatement pstmt = con.prepareStatement(sql)){;
			pstmt.setLong(1, compId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					Long couponid = rs.getLong(2);
					coupon = coupondbdao.getCoupon(couponid);
					coupondbdao.removeCoupon(coupon);
				}
			}
		}catch(SQLException e) {
			throw new CouponSystemException("Coupon: " + coupon.getTitle() + " ID: " + coupon.getId() + " could not be deleted");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Update a coupon object via sending SQL <code>PreparedStatement</code> to the database. 
	 * Attributes that are permitted to be updated are the coupon's End Date and Price.
	 * @param coupon object.
	 * @throws CouponSystemException
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "UPDATE Coupon SET END_DATE=?, PRICE=? WHERE ID=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setDate(1, new java.sql.Date(coupon.getEndDate().getTime()));
			pstmt.setDouble(2, coupon.getPrice());
			pstmt.setLong(3, coupon.getId());
			if (new java.sql.Date(coupon.getEndDate().getTime()).compareTo(new java.sql.Date(coupon.getStartDate().getTime())) <= 0 ) {
				throw new CouponSystemException("Cannot set End Date before or the same as Start Date");
			} else if (coupon.getPrice() <= 0) {
				throw new CouponSystemException("Cannat set a negative price value");
			} else {
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon " + coupon.getTitle() + " update failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * This method returns a single coupon object via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database. 
	 * @param coupon ID.
	 * @return coupon object.
	 * @throws CouponSystemException
	 */
	@Override
	public Coupon getCoupon(Long id) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Coupon WHERE ID=?";
		Coupon coupon = new Coupon();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setLong(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					coupon.setId(rs.getLong(1));
					coupon.setTitle(rs.getString(2));
					coupon.setStartDate(rs.getDate(3));
					coupon.setEndDate(rs.getDate(4));
					coupon.setAmount(rs.getInt(5));
					coupon.setType(CouponType.valueOf(rs.getString(6)));
					coupon.setMessage(rs.getString(7));
					coupon.setPrice(rs.getDouble(8));
					coupon.setImage(rs.getString(9));
				} 
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon ID: " + id + " could not be retrieved", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return coupon;
	}
	
	/**
	 * Get a list of all coupon objects via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database.
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public List<Coupon> getAllCoupon() throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Coupon ORDER BY TYPE ASC, TITLE ASC";
		Coupon coupon = new Coupon();
		List<Coupon> couponlist = new ArrayList<>();
		try (
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();)
			{
			while (rs.next()) {
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate(rs.getDate(3));
				coupon.setEndDate(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setType(CouponType.valueOf(rs.getString(6)));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));
				couponlist.add(new Coupon(coupon.getId(), coupon.getTitle(), coupon.getStartDate(), coupon.getEndDate(), 
						coupon.getAmount(), coupon.getType(), coupon.getMessage(), coupon.getPrice(), coupon.getImage()));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon list could not be retrieved");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return couponlist;
	}
	
	/**
	 * Get a list of all coupon objects by required coupon type via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database. 
	 * @param coupon type.
	 * @return a sorted collection of coupons.
	 * @throws CouponSystemException
	 */
	@Override
	public List<Coupon> getCouponByType(CouponType type) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql ="SELECT * FROM Coupon WHERE TYPE=?";
		Coupon coupon = new Coupon();
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, type.name());
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					if (CouponType.valueOf(rs.getString(6)) == type) {
						coupon.setId(rs.getLong(1));
						coupon.setTitle(rs.getString(2));
						coupon.setStartDate(rs.getDate(3));
						coupon.setEndDate(rs.getDate(4));
						coupon.setAmount(rs.getInt(5));
						coupon.setType(CouponType.valueOf(rs.getString(6)));
						coupon.setMessage(rs.getString(7));
						coupon.setPrice(rs.getDouble(8));
						coupon.setImage(rs.getString(9));
						couponlist.add(new Coupon(coupon.getId(), coupon.getTitle(), coupon.getStartDate(), coupon.getEndDate(), 
								coupon.getAmount(), coupon.getType(), coupon.getMessage(), coupon.getPrice(), coupon.getImage()));
					}
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
	 * Get a list of a company's coupons objects by required coupon type, via sending SQL 
	 * <code>PreparedStatement</code> and <code>ResultSet</code> to the database.
	 * @param coupon type, company ID.
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public List<Coupon> getCompanyCouponByType(CouponType type, Long companyId) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company_Coupon WHERE COMP_ID=?";
		CouponDBDAO coupondbdao = new CouponDBDAO();
		Coupon coupon = new Coupon();
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql)){;
			pstmt.setLong(1, companyId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					Long couponid = rs.getLong(2);
					coupon = coupondbdao.getCoupon(couponid);
					if (coupon.getType() == type) {
						couponlist.add(coupon);
					}
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon list could not be retrieved");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return couponlist;
	}
	
	/**
	 * Get a list of a company's coupons objects by required price, via sending SQL 
	 * <code>PreparedStatement</code> and <code>ResultSet</code> to the database.
	 * @param price, company ID.
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public List<Coupon> getCompanyCouponByPrice(double price, Long companyId) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company_Coupon WHERE COMP_ID=?";
		CouponDBDAO coupondbdao = new CouponDBDAO();
		Coupon coupon = new Coupon();
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql)){;
			pstmt.setLong(1, companyId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					Long couponid = rs.getLong(2);
					coupon = coupondbdao.getCoupon(couponid);
					if (coupon.getPrice() <= price) {
						couponlist.add(coupon);
					}
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon list could not be retrieved");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return couponlist;
	}
	
	/**
	 * Get a list of a company's coupons objects by required coupon End Date, via sending SQL 
	 * <code>PreparedStatement</code> and <code>ResultSet</code> to the database.
	 * @param Date, company ID.
	 * @return a sorted collection of coupons.
	 * @throws CouponSystemException
	 */
	@Override
	public List<Coupon> getCompanyCouponByDate(Date date, Long companyId) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company_Coupon WHERE COMP_ID=?";
		CouponDBDAO coupondbdao = new CouponDBDAO();
		Coupon coupon = new Coupon();
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql)){;
			pstmt.setLong(1, companyId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					Long couponid = rs.getLong(2);
					coupon = coupondbdao.getCoupon(couponid);
					if (coupon.getEndDate().compareTo(date) <= 0 ) {
						couponlist.add(coupon);
					}
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon list could not be retrieved");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return couponlist;
	}
	
	/**
	 * The objective of this method is to allow a company to perform certain actions
	 * on its coupons, and only if the coupon belongs to the company, via sending 
	 * SQL <code>PreparedStatement</code> and <code>ResultSet</code> to the database. 
	 * @param company ID, coupon object.
	 * @return true or false if a coupon belongs to the company
	 * @throws CouponSystemException
	 */
	@Override
	public boolean isCouponOfCompany(Long companyId, Coupon coupon) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company_Coupon WHERE COMP_ID=? AND COUPON_ID=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, companyId);
			pstmt.setLong(2, coupon.getId());
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon: " + coupon.getId() + " query of Company could not be retrieved", e); 
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Get a list of a customer's coupons objects by required coupon type, via sending SQL 
	 * <code>PreparedStatement</code> and <code>ResultSet</code> to the database.
	 * @param coupon type, customer ID.
	 * @return a sorted collection of coupons.
	 * @throws CouponSystemException.
	 */
	@Override
	public List<Coupon> getCustomerCouponByType(CouponType type, Long customerId) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Customer_Coupon WHERE CUST_ID=?";
		Coupon coupon = new Coupon();
		CouponDBDAO coupondbdao = new CouponDBDAO();
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, customerId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					Long couponId = rs.getLong(2);
					coupon = coupondbdao.getCoupon(couponId);
					if (coupon.getType() == type) {
						coupon.setAmount(1);
						couponlist.add(coupon);
					}
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
	 * Get a list of a customer's coupons objects by required price, via sending SQL 
	 * <code>PreparedStatement</code> and <code>ResultSet</code> to the database.
	 * @param price, customer ID.
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public List<Coupon> getCustomerCouponByPrice(double price, Long customerId) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Customer_Coupon WHERE CUST_ID=?";
		Coupon coupon = new Coupon();
		CouponDBDAO coupondbdao = new CouponDBDAO();
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, customerId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					Long couponId = rs.getLong(2);
					coupon = coupondbdao.getCoupon(couponId);
					if (coupon.getPrice() <= price) {
						coupon.setAmount(1);
						couponlist.add(coupon);
					}
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon list could not be retrieved");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return couponlist;
	}
	
	/**
	 * Delete a coupon object from the database if the coupon has expired, via sending SQL 
	 * <code>PreparedStatement</code> to the database.
	 * Expiration date of a coupon is determined if its End Date is before today's date
	 * @throws CouponSystemException
	 */
	@Override
	public void removeExpirationDateCoupons() throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "DELETE FROM Coupon WHERE END_DATE < CURRENT_TIMESTAMP";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Cannot run Daily Coupon Expiration Task", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
}
