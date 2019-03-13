package projectCoupon.exception;

public class UpdateException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * UpdateException to handle with problems of update table.
	 * @param message
	 */
	public UpdateException(String message) {
		super(message);
	}

}


