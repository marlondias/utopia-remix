package utopia.basic;

import java.util.Date;
import java.util.LinkedList;

import utopia.engine.graphics.gscreen.MGameScreen;
import utopia.game.colony.Colony;
import utopia.game.planet.Planet;
import utopia.engine.graphics.gscreen.GS_AreaTeste;
import utopia.engine.graphics.gscreen.GS_ClimateStats;
import utopia.engine.graphics.gscreen.GS_FullTerrain;
import utopia.engine.graphics.gscreen.GS_RingMenus;
import utopia.engine.graphics.gscreen.GS_TitleScreen;

public class GameStateManager {
    private static final LinkedList<MGameScreen> list = new LinkedList<>();
    
    private static GS_TitleScreen tits = new GS_TitleScreen();
    private static GS_FullTerrain terrain;
    private static GS_ClimateStats stats = new GS_ClimateStats();
    private static GS_AreaTeste teste = new GS_AreaTeste();
    
    private static Planet planet;
    

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
			//Abre planilhas excel
			break;
		case GAME_MINISTERS:
			//Abre reunião
			break;
		case GAME_TELENEWS:
			//Abre TV
			break;
		case GAME_OVER:
			//Um esqueleto com cabeça alien, sentado em um trono high tech.
			break;
		case TESTE:
			list.add(teste);
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

    public static void setPlanet(Planet p){
    	//Verificar a validade (!)
    	planet = p;
    	terrain = null;
    }
    
    public static Planet getPlanet(){
    	return planet;
    }
    
}
