package edu.cmu.tartan;

public final class GamePlayMessage {
	
	private GamePlayMessage() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static final String I_DO_NOT_SEE_THAT_HERE = "I don't see that here.\n";
	public static final String I_DO_NOT_UNDERSTAND = "I don't understand that.\n";
	public static final String TAKEN = "Taken";
	// Scene common 2
	public static final String WILL_YOU_SAVE_2_1 = "Will you save the current state?(yes/no)\n";
	public static final String SAVE_FAILURE_2_3 = "Uh-oh. Error is occurred. Please retry.\n";
	public static final String REJECT_SAVE_2_4 = "Save is rejected.\n";
	// Scene common 10
	public static final String GANE_IS_STARTED_10_1 = "Game is started!\n";
	public static final String LOAD_FAILURE_10_2 = "There is no saved game.\n";
	public static final String SAVE_SUCCESSFUL_10_4 = "The current state is saved successfully.\n";
	public static final String SAVE_CANNOT_10_6 = "You cannot save the current status in network game mode.\n";
	
	// 
	public static final String POINTS = " points.";

}
