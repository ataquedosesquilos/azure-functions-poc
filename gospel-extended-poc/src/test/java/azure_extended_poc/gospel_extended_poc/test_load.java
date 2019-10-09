package azure_extended_poc.gospel_extended_poc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import nga.hrx.gospel.consumer.GospelException;

public class test_load {

	public static void main(String[] args) throws InterruptedException  {
		
		int count = 0;
		while(count < 1000) {
			ExecutorService service = Executors.newFixedThreadPool(4);
			 service.submit(new Runnable() {
			        public void run() {
			        	try {
							TestAddPerson.main(null);
						} catch (GospelException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			    });
			 service.awaitTermination(1200, TimeUnit.SECONDS);
			 count ++;
		}
	   

	}

}
