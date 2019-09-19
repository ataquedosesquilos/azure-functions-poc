package nga.hrx.utils;

public class ApiException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ApiException(Exception errorMessage) {
        super(errorMessage);
    }
	
	public ApiException(String errorMessage) {
        super(errorMessage);
    }
}
