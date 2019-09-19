package nga.hrx.gospel.consumer;

public class GospelException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GospelException(Exception errorMessage) {
        super(errorMessage);
    }
}
