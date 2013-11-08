package server.models;

import java.util.concurrent.*;

public class Alarm extends Sensor {

    private final ScheduledExecutorService scheduler = 
    	       Executors.newSingleThreadScheduledExecutor();
	
	public Alarm(){
		super();
	}
	
	public void execute(){

		final ScheduledFuture<?> alarm = 
	            scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
	            }, 
			10, TimeUnit.SECONDS);
		
	}
}
