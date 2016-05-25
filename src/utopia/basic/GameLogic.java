package utopia.basic;

import utopia.game.planet.Planet;

public class GameLogic {
    private Planet planeta = new Planet("Hempa");

	
	public GameLogic(){
		GameStateManager.setPlanet(planeta);
        GameStateManager.setGameState(GameState.TITLE_SCREEN);
	}
	
    public void update(){
    	GameStateManager.updateGameScreens();
    	
    	//Atualizar planeta e tal..
    }
	
}
