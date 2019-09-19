package nga.hrx.azure.consumer;

public class AzureException extends Exception  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8669076822532632507L;

	public AzureException(Exception errorMessage) {
        super(errorMessage);
    }
	
	public AzureException(String errorMessage) {
        super(errorMessage);
    }
}
