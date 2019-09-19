package nga.hrx.somethingelse;

public class SomeElseException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -817617330276978860L;

	public SomeElseException(Exception errorMessage) {
        super(errorMessage);
    }
	
	public SomeElseException(String errorMessage) {
        super(errorMessage);
    }
}
