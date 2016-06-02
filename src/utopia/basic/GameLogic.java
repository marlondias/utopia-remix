package utopia.basic;

import utopia.game.planet.Planet;

public class GameLogic {
	
	public GameLogic(){
		GameStateManager.setPlanet( new Planet("Merkuri", 1234));
        GameStateManager.setGameState(GameState.TITLE_SCREEN);
	}
	
    public void update(){
    	GameStateManager.updateGameScreens();
    	GameStateManager.updatePlanet();
    }
	
}
