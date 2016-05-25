package utopia.basic;

import utopia.game.planet.Colony;

public class GameLogic {
	
	public GameLogic(){
		GameStateManager.setColony(new Colony("Tesla Hempa I", "The Guy"));
        GameStateManager.setGameState(GameState.TITLE_SCREEN);
	}
	
    public void update(){
    	GameStateManager.updateGameScreens();
    	GameStateManager.updateColony();
    }
	
}
