package utopia.basic;

import utopia.game.planet.Planet;

public class GameLogic {
	
	public GameLogic(){
		GameStateManager.setPlanet( new Planet("Merkuri", 1));
        GameStateManager.setGameState(GameState.TESTE);
	}
	
    public void update(){
    	GameStateManager.updatePlanet();
    	GameStateManager.updateGameScreens();
    }
	
}
