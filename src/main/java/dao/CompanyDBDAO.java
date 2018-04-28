package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import couponsystem.ConnectionPool;
import couponsystem.CouponSystemException;
import javabeans.Company;
import javabeans.Coupon;

/**
 * CompanyDBDAO is designated for managing and querying company related tasks
 * either by an Administrator or a Company, via SQL statements and queries.
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public class CompanyDBDAO implements CompanyDAO {
	
	public CompanyDBDAO() {
		
	}
	
	/**
	 * Create a company object via sending SQL <code>PreparedStatement</code>
	 * to the database using <code>PreparedStatement</code> parameters. 
	 * Each parameter represents a company attribute.
	 * @param company object
	 * @return company object
	 * @throws CouponSystemException
	 */
	@Override
	public Company createCompany(Company company) throws CouponSystemException {
		if (company.getCompName() != null && !company.getCompName().trim().isEmpty()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				throw new RuntimeException(e1);
			}
			Connection con = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO Company VALUES(?,?,?,?)";
			Long time = System.currentTimeMillis() % 10000000;
			company.setId(time);
			try (
				PreparedStatement pstmt = con.prepareStatement(sql);)
				{
				pstmt.setLong(1, time);
				pstmt.setString(2, company.getCompName());
				pstmt.setString(3, company.getPassword());
				pstmt.setString(4, company.getEmail());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemException("Company: " + company.getCompName() + " creation failed", e);
			} finally {
				ConnectionPool.getInstance().returnConnection(con);
			}
		} else {
			throw new CouponSystemException("Company name must not be empty");
		}
		return company;
	}
	
	/**
	 * Delete a company object via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database.
	 * @param company object
	 * @throws CouponSystemException
	 */
	@Override
	public void removeCompany(Company company) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "DELETE FROM Company WHERE ID=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, company.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Company: " + company.getCompName() + " ID: " + company.getId() + " could not be deleted");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Update a company object via sending SQL <code>PreparedStatement</code> to the database. 
	 * Attributes that are permitted to be updated are the company's Password and Email.
	 * @param company object.
	 * @throws CouponSystemException
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "UPDATE Company SET PASSWORD=?, EMAIL=? WHERE ID=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, company.getPassword());
			pstmt.setString(2, company.getEmail());
			pstmt.setLong(3, company.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Company: " + company.getCompName() + " update failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Get a single company object via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database. 
	 * @param company ID.
	 * @return company object.
	 * @throws CouponSystemException
	 */
	@Override
	public Company getCompany(Long id) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company WHERE ID=?";
		Company company = new Company();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setLong(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					company.setId(rs.getLong(1));
					company.setCompName(rs.getString(2));
					company.setPassword(rs.getString(3));
					company.setEmail(rs.getString(4));
				} else {
					throw new CouponSystemException("Company ID: " + id + " could not be found");
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Company ID: " + id + " could not be retrieved", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return company;
	}
	
	/**
	 * Get a list of all companies objects via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database.
	 * @return sorted collection of all the companies
	 * @throws CouponSystemException
	 */
	@Override
	public List<Company> getAllCompanies() throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company";
		Company company = new Company();
		List<Company> companylist = new ArrayList<>();
		try (
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			){
			while (rs.next()) {
				company.setId(rs.getLong(1));
				company.setCompName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));
				companylist.add(new Company(company.getId(), company.getCompName(), company.getPassword(), company.getEmail()));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Company list could not be retrieved");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return companylist;
	}
	
	/**
	 * Get a single company's coupon object, via sending SQL 
	 * <code>PreparedStatement</code> and <code>ResultSet</code> to the database.
	 * @param company ID, coupon ID.
	 * @return coupon object
	 * @throws CouponSystemException
	 */
	@Override
	public Coupon getCompanyCoupon(Long companyId, Long couponid) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company_Coupon WHERE COMP_ID=? AND COUPON_ID=?";
		CouponDBDAO coupondbdao = new CouponDBDAO();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, companyId);
			pstmt.setLong(2, couponid);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					return coupondbdao.getCoupon(couponid);
				} else {
					throw new CouponSystemException("Coupon ID: " + couponid + " could not be found");
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Company list could not be retrieved");
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Get a list of all company coupons objects, via sending SQL 
	 * <code>PreparedStatement</code> and <code>ResultSet</code> to the database.
	 * @param company ID.
	 * @return a sorted collection of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public List<Coupon> getCoupons(Long companyId) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company_Coupon WHERE COMP_ID=?";
		CouponDBDAO coupondbdao = new CouponDBDAO();
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql)){;
			pstmt.setLong(1, companyId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					Long couponid = rs.getLong(2);
					couponlist.add(coupondbdao.getCoupon(couponid));
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon list could not be retrieved", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		CompanyDBDAO companydbdao = new CompanyDBDAO();
		Company company = companydbdao.getCompany(companyId);
		company.setCoupons(couponlist);
		return company.getCoupons();
	}
	
	/**
	 * Company login via sending SQL <code>PreparedStatement</code> to the database
	 * @param company name, company password.
	 * @return true or false if the login parameters match the values in the database
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String compName, String password) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company WHERE COMP_NAME=? AND PASSWORD=?";
		try(PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, compName);
			pstmt.setString(2, password);
			try(ResultSet rs = pstmt.executeQuery();) {
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
	 * Associate a company with a coupon by inserting a company ID and a coupon ID 
	 * into the Company_Coupon join table via sending SQL <code>PreparedStatement</code> to the database.
	 * @param coupon ID, coupon object.
	 * @throws CouponSystemException
	 */
	@Override
	public void companyCoupon(Long companyId, Coupon coupon) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "INSERT INTO Company_Coupon (COMP_ID, COUPON_ID) SELECT Company.ID, Coupon.ID FROM Company, Coupon WHERE Company.ID=? AND Coupon.ID=?";
		try(PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, companyId);
			pstmt.setLong(2, coupon.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon: " + coupon.getId() + " could not be assigned to Company",e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	/**
	 * Get a company ID via sending SQL <code>PreparedStatement</code>
	 * and <code>ResultSet</code> to the database
	 * @param company name.
	 * @return company ID.
	 * @throws CouponSystemException
	 */
	@Override
	public Long getCompanyIdByName(String name) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "SELECT * FROM Company WHERE COMP_NAME=?";
		Long companyId = null;
		try(PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, name);
			try(ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					companyId = rs.getLong(1);
				} 
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Company: " + name + " could not be retrieved", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return companyId;
	}
	
}
