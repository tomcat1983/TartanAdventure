package edu.cmu.tartan.manager;

public class UserCommandHandler implements IUserCommand {

	@Override
	public boolean inputUserCommand(UserCommandType type, UserCommand command) {
		
		switch (type) {
			case GAME_MODE:
				selectGameMode(command);
				break;
			case LOGIN:
				selectLoginMenu(command);
				break;
			case USER_ID:
				inputUserId(command);
				break;
			case USER_PW:
				inputUserPw(command);
				break;
			case GAME_START:
				startGame(command);
				break;
			case GAME_EXIT:
				exitGame(command);
				break;
			default:
				break;
		}
				
		return false;
	}
	
	private boolean selectGameMode(UserCommand command) {
		switch (command) {
			case LOCAL_MODE:
				break;
			case NETWORK_MODE:
				break;
			default:
				break;
		}
		return false;
	}
	
	private boolean selectLoginMenu(UserCommand command) {
		switch (command) {
			case USER_LOGIN:
				break;
			case USER_REGISTER:
				break;
			case DESIGNER_LOGIN:
				break;
			default:
				break;
		}
		return false;
	}
	
	private boolean inputUserId(UserCommand command) {
		
		return false;
	}
	
	private boolean inputUserPw(UserCommand command) {
		
		return false;
	}
	
	private boolean startGame(UserCommand command) {
		
		return false;
	}
	
	private boolean exitGame(UserCommand command) {
		
		return false;
	}

}
