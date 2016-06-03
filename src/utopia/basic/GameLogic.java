package utopia.basic;

import utopia.game.planet.Planet;

public class GameLogic {
	
	public GameLogic(){
		GameStateManager.setPlanet( new Planet("Merkuri", 1));
        GameStateManager.setGameState(GameState.GAME_STATISTICS);
	}
	
    public void update(){
    	GameStateManager.updatePlanet();
    	GameStateManager.updateGameScreens();
    }
	
}
