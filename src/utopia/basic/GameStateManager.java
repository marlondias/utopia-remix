package utopia.basic;

import java.util.LinkedList;

import utopia.engine.graphics.gscreen.MGameScreen;
import utopia.game.planet.Planet;
import utopia.engine.graphics.gscreen.GS_FullTerrain;
import utopia.engine.graphics.gscreen.GS_RingMenus;
import utopia.engine.graphics.gscreen.GS_TitleScreen;

public class GameStateManager {
    private static final LinkedList<MGameScreen> list = new LinkedList<>();
    private static Planet planet;
    
    private static GS_TitleScreen tits = new GS_TitleScreen();
    private static GS_RingMenus rings = new GS_RingMenus();
    private static GS_FullTerrain terrain;
    
    
    public static void setPlanet(Planet p){
    	//Verificar a validade do P (!)
    	planet = p;
    	terrain = null;
    }
    
    public static void setGameState(GameState gState){
		if (!list.isEmpty()) list.clear();
    	
    	switch (gState) {
		case TITLE_SCREEN:
			list.add(tits);
			break;
		case LOAD_SCREEN:
			break;
		case GAME_FIELD:
			if (terrain == null) terrain = new GS_FullTerrain(planet);
			list.add(terrain);
			break;
		case GAME_STATISTICS:
			break;
		case GAME_MINISTERS:
			break;
		case GAME_TELENEWS:
			break;
		case GAME_OVER:
			break;
		default:
			break;
		}
    	
    }

    public static LinkedList<MGameScreen> getGameScreens(){
    	return list;
    }
    
    public static void updateGameScreens(){
    	//Atualiza lógica e interação
    	for (MGameScreen gs : list){
    		gs.updateAll();
    	}
    }


}
