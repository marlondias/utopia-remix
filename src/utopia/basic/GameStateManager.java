package utopia.basic;

import java.util.Date;
import java.util.LinkedList;

import utopia.engine.graphics.gscreen.MGameScreen;
import utopia.game.colony.Colony;
import utopia.game.planet.Planet;
import utopia.engine.graphics.gscreen.GS_ClimateStats;
import utopia.engine.graphics.gscreen.GS_FullTerrain;
import utopia.engine.graphics.gscreen.GS_RingMenus;
import utopia.engine.graphics.gscreen.GS_TitleScreen;

public class GameStateManager {
    private static final LinkedList<MGameScreen> list = new LinkedList<>();
    
    private static GS_TitleScreen tits = new GS_TitleScreen();
    private static GS_FullTerrain terrain;
    private static GS_ClimateStats stats = new GS_ClimateStats();
    
    private static Planet planet;
    private static Date date; //Data no planeta
    private static float temperature; //Temperatura atual no planeta (diária)
       
    
    

    public static void setGameState(GameState gState){
		if (!list.isEmpty()) list.clear();
    	
    	switch (gState) {
		case TITLE_SCREEN:
			list.add(tits);
			break;
		case LOAD_SCREEN:
			break;
		case GAME_FIELD:
			planet.getTime().start();
			if (terrain == null) terrain = new GS_FullTerrain(planet);
			list.add(terrain);
			break;
		case GAME_STATISTICS:
			planet.getTime().start();
			list.add(stats);
			break;
		case GAME_MINISTERS:
			planet.getTime().pause();
			//Abre reunião
			break;
		case GAME_TELENEWS:
			planet.getTime().pause();
			//Abre TV
			break;
		case GAME_OVER:
			planet.getTime().pause();
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
    
    public static void updatePlanet(){
    	if (planet == null) return;
    	planet.updateDay();
    }
    
    public static void setTemperature(float temp){
    	stats.addTemperature(temp);
    	temperature = temp;
    }
    
    public static float getTemperature(){
    	return temperature;
    }
    
    public static void setDate(Date dt){
    	date = dt;
    }

    public static Date getDate(){
    	return date;
    }

}
