package utopia.basic;

import utopia.game.planet.Planet;

public class GameLogic {
	
	public GameLogic(){
		GameStateManager.setPlanet(new Planet(1234, "Atlas"));
        GameStateManager.setGameState(GameState.GAME_FIELD);
	}
	
    public void update(){
    	GameStateManager.getPlanet().getTime().update();
    	GameStateManager.updateGameScreens();
    }
	
}
