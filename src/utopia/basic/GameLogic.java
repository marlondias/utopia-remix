package utopia.basic;

import utopia.game.planet.Planet;

public class GameLogic {
	
	public GameLogic(){
		GameStateManager.setPlanet( new Planet("Merkuri", 1));
        GameStateManager.setGameState(GameState.TITLE_SCREEN);
	}
	
    public void update(){
    	GameStateManager.updatePlanet();
    	GameStateManager.updateGameScreens();
    }
	
}
