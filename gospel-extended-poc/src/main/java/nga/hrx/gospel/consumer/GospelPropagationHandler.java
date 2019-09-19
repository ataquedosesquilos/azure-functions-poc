package nga.hrx.gospel.consumer;


public class GospelPropagationHandler  implements  Runnable {
	
	private String id;
	private GospelConsumer gospelConsumer;
	
	public GospelPropagationHandler (String id){
		this.id=id;
	}
	
	public GospelPropagationHandler (String id, GospelConsumer gospelConsumer){
		this.id=id;
		this.gospelConsumer=gospelConsumer;
	}

	@Override
	public void run() {}
	
	

}
