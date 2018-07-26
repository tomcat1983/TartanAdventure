package edu.cmu.tartan.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.cmu.tartan.GameInterface;

public class MessageQueue implements IQueueHandler{
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();
	
	private static final int QUEUE_SIZE = 100;
	
	private BlockingQueue<String> queue;
	
	public MessageQueue() {
		queue = new LinkedBlockingQueue<String>(QUEUE_SIZE); 
	}
	
	public BlockingQueue<String> getQueue() {
		return queue;
	}
	
	@Override
	public boolean produce (String message) {
		try {
			queue.put(message);
			return queue.contains(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			gameInterface.println("InterruptException : " + e.getMessage());
		}
		return false;
	}
	
	@Override
	public String consume() {
		String message = null;

		try{
			message = queue.take();
			if (message != null) return message;
            
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	@Override
	public int clearQueue() {
		queue.clear();
		
		return queue.size();
	}
	
}
