package couponsystem;

public class CouponSystemException extends Exception {

	
	/**
	 * Handling exceptions that may be thrown, by customizing a user-friendly message
	 */
	private static final long serialVersionUID = 4600280225098781093L;
	
	private String message;

	public CouponSystemException() {
		super();
	}

	public CouponSystemException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}
	
	public CouponSystemException(String message) {
		super(message);
		this.message = message;
	}

	public CouponSystemException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	
	
	
}
