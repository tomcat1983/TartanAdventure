package edu.cmu.tartan;

public final class GamePlayMessage {
	
	private GamePlayMessage() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static final String I_DO_NOT_SEE_THAT_HERE = "I don't see that here.";
	public static final String TAKEN = "Taken";
	public static final String SAVE_FAILURE_2_3 = "Uh-oh. Error is occurred. Please retry.";
	public static final String SAVE_SUCCESSFUL_10_4 = "The current state is saved successfully.";
}
