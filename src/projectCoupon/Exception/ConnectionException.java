package projectCoupon.Exception;

public class ConnectionException extends Exception{
	private static final long serialVersionUID = 1L;
	/**
	 * ConnectionException handle with connection problems.
	 * @param message
	 */
	
	
	public ConnectionException(String message) {
		super(message);
	}

}


