package couponsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The connection pool is a mechanism for creating, managing and sharing a
 * collection of connections to the database.
 * 
 * @author Guy Ben-David
 * @author Tomer Banyan
 */
public class ConnectionPool {

	private Set<Connection> conpool = new HashSet<>();
	// private String url = "jdbc:sqlserver://localhost:1433;databaseName=CouponDB;integratedSecurity=true";
	private String url = "jdbc:mysql://localhost:3306/CouponDB?autoReconnect=true&useSSL=false";
	private String username = "root";
	private String password = "mysql";
	private static final int MAX_CON = 10;
	private static ConnectionPool instance = new ConnectionPool();

	/**
	 * The constructor is responsible for creating and adding JDBC connections to
	 * the collection
	 */
	private ConnectionPool() {
		super();
		loadDriver();
		while (conpool.size() < MAX_CON) {
			try {
				Connection con = DriverManager.getConnection(url, username, password);
				//Connection con = DriverManager.getConnection(url);
				conpool.add(con);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Load the JDBC driver associated with this class
	 */
	private void loadDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Singleton for the connection pool
	 * 
	 * @return instance
	 * @throws CouponSystemException
	 */
	public static ConnectionPool getInstance() throws CouponSystemException {
		return instance;
	}

	public int ConPoolSize() {
		return conpool.size();
	}

	/**
	 * Get a connection from the connection pool collection If the connection pool
	 * is empty, the requesting thread will wait. This method is synchronized.
	 * 
	 * @throws CouponSystemException
	 */
	public synchronized Connection getConnection() throws CouponSystemException {
		Iterator<Connection> it = conpool.iterator();
		while (conpool.isEmpty()) {
			try {
				wait();
				it = conpool.iterator();
			} catch (InterruptedException e) {
				throw new CouponSystemException("Failed to get connection", e);
			}
		}
		Connection connection = it.next();
		it.remove();
		return connection;
	}

	/**
	 * Return a connection to the pool's collection and notify other waiting threads
	 * (if exists) This method is synchronized
	 */
	public synchronized void returnConnection(Connection con) {
		conpool.add(con);
		notify();
	}

	/**
	 * Close all connections to the database. Closing a connection is permitted when
	 * the connection pool is at its max capacity
	 * 
	 * @throws CouponSystemException
	 */
	public synchronized void closeAllConnections() throws CouponSystemException {
		Iterator<Connection> it = conpool.iterator();
		if (conpool.size() != MAX_CON) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("Failed to close all connections", e);
			}
		} else {
			while (it.hasNext()) {
				try {
					Connection con = it.next();
					con.close();
				} catch (SQLException e) {
					throw new CouponSystemException("Failed to close all connections", e);
				}
			}
		}
	}
}
