package edu.cmu.tartan.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.socket.SocketClient;

public class TestTartanGameManagerClient {
	
	private ResponseMessage gameMessage;
	private TartanGameManagerClient clientGameManager;
	private SocketClient socket = null;
	private IQueueHandler queue = null;
	
	@BeforeEach
	public void beforeTest () {
		gameMessage = new ResponseMessage();
		clientGameManager = new TartanGameManagerClient(false);
		Thread th = new Thread(clientGameManager);
		th.start();
	}
	
	@Test
	public void trueWhenSendMessage() {
		
		String returnValue = "";

		Notifier noti = new Notifier(gameMessage);
		
		Thread thread = new Thread(noti);
		thread.start();
		
//		returnValue = clientGameManager.login("abc", "abc");
//		synchronized(gameMessage) {
//			gameMessage.setMessage("Hello world");
//			gameMessage.notify();
//		}
		System.out.println(returnValue);
	}

}

class Notifier implements Runnable {

    private ResponseMessage msg;
    
    public Notifier(ResponseMessage msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            Thread.sleep(1000);
            synchronized (msg) {
                msg.setMessage(name+" Notifier work done");
                Thread.sleep(1000);
                noty();
                // msg.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
    public void noty() {
    	msg.notify();
    }

}
